package com.lys.utils;

import com.lys.App;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.LOG;
import com.lys.base.utils.LOGJson;
import com.lys.config.AppConfig;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogWait;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SNotePage;
import com.lys.protobuf.SNotePageSet;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SProtocol;
import com.lys.protobuf.SRequest_SvnGetDir;
import com.lys.protobuf.SResponse_SvnGetDir;
import com.lys.protobuf.SSvnDirObj;

import org.tmatesoft.svn.core.SVNCancelException;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNErrorCode;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.ISVNConflictHandler;
import org.tmatesoft.svn.core.wc.ISVNEventHandler;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNConflictChoice;
import org.tmatesoft.svn.core.wc.SVNConflictDescription;
import org.tmatesoft.svn.core.wc.SVNConflictReason;
import org.tmatesoft.svn.core.wc.SVNConflictResult;
import org.tmatesoft.svn.core.wc.SVNCopyClient;
import org.tmatesoft.svn.core.wc.SVNCopySource;
import org.tmatesoft.svn.core.wc.SVNEvent;
import org.tmatesoft.svn.core.wc.SVNMergeFileSet;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNStatusClient;
import org.tmatesoft.svn.core.wc.SVNStatusType;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SVNManager
{
	public static SVNRepository repository = null;

	public static SVNClientManager svnClientManager = null;

	public static SVNCopyClient copyClient = null;
	public static SVNUpdateClient updateClient = null;
	public static SVNCommitClient commitClient = null;
	public static SVNWCClient wcClient = null;
	public static SVNStatusClient statusClient = null;

	public static boolean init(String url, String username, String password)
	{
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
		FSRepositoryFactory.setup();

		try
		{
			repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
			ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
			repository.setAuthenticationManager(authManager);

			LOG.v("root : " + repository.getRepositoryRoot(true));
//			LOG.v("latestRevision : " + repository.getLatestRevision());

			ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
//			svnClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options, username, password);
			svnClientManager = SVNClientManager.newInstance(options, authManager);

			copyClient = svnClientManager.getCopyClient();
			copyClient.setIgnoreExternals(false);
			copyClient.setEventHandler(new ISVNEventHandler()
			{
				@Override
				public void checkCancelled() throws SVNCancelException
				{
//					LOG.v("svn copy checkCancelled");
				}

				@Override
				public void handleEvent(SVNEvent event, double progress) throws SVNException
				{
					LOG.v(String.format("svn copy %s : %s", event, progress));
				}
			});

			updateClient = svnClientManager.getUpdateClient();
			updateClient.setIgnoreExternals(false);
			DefaultSVNOptions updateOptions = (DefaultSVNOptions) updateClient.getOptions();
			updateOptions.setConflictHandler(new ISVNConflictHandler()
			{
				@Override
				public SVNConflictResult handleConflict(SVNConflictDescription conflictDescription) throws SVNException
				{
					SVNConflictReason reason = conflictDescription.getConflictReason();
					SVNMergeFileSet mergeFiles = conflictDescription.getMergeFiles();

					LOG.v("conflict reason : " + reason);
//					LOG.v("conflict getWCFile : " + mergeFiles.getWCFile());
					LOG.v("conflict getResultFile : " + mergeFiles.getResultFile());

//					SVNConflictChoice choice = SVNConflictChoice.POSTPONE;
//					SVNConflictChoice choice = SVNConflictChoice.MINE_FULL;
					SVNConflictChoice choice = SVNConflictChoice.THEIRS_FULL;

					return new SVNConflictResult(choice, mergeFiles.getResultFile());
				}
			});
			updateClient.setEventHandler(new ISVNEventHandler()
			{
				@Override
				public void checkCancelled() throws SVNCancelException
				{
//					LOG.v("svn update checkCancelled");
				}

				@Override
				public void handleEvent(SVNEvent event, double progress) throws SVNException
				{
					LOG.v(String.format("svn update %s : %s", event, progress));
					String str = String.format("%s", event.toString().replaceFirst(FsUtils.SD_CARD, ""));
					if (str.length() > 20)
						str = "..." + str.substring(str.length() - 20);
					DialogWait.messageWithUIThread("更新：" + str);
				}
			});

			commitClient = svnClientManager.getCommitClient();
			commitClient.setIgnoreExternals(false);
			commitClient.setEventHandler(new ISVNEventHandler()
			{
				@Override
				public void checkCancelled() throws SVNCancelException
				{
//					LOG.v("svn commit checkCancelled");
				}

				@Override
				public void handleEvent(SVNEvent event, double progress) throws SVNException
				{
					LOG.v(String.format("svn commit %s : %s", event, progress));
					String str = String.format("%s", event.toString().replaceFirst(FsUtils.SD_CARD, ""));
					if (str.length() > 20)
						str = "..." + str.substring(str.length() - 20);
					DialogWait.messageWithUIThread("提交：" + str);
				}
			});

			wcClient = svnClientManager.getWCClient();

			statusClient = svnClientManager.getStatusClient();

			return true;
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	// ------------------------------------ repository --------------------------------------------

	public static void getDir(String path, List<SSvnDirObj> objs) throws SVNException
	{
		Collection entries = repository.getDir(path, -1, null, (Collection) null);
		Iterator iterator = entries.iterator();
		while (iterator.hasNext())
		{
			SVNDirEntry entry = (SVNDirEntry) iterator.next();
//			LOG.v(String.format("%s : %s : %s : %s : %s", entry.getKind(), entry.getName(), entry.getAuthor(), entry.getRevision(), entry.getDate()));
			SSvnDirObj obj = new SSvnDirObj();
			obj.isDir = entry.getKind() == SVNNodeKind.DIR;
			obj.name = entry.getName();
			objs.add(obj);
			if (obj.isDir)
			{
				getDir(path + "/" + obj.name, obj.objs);
			}
		}
	}

	public static List<SSvnDirObj> getDir(String path)
	{
		try
		{
			List<SSvnDirObj> objs = new ArrayList<>();
			getDir(path, objs);
			return objs;
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static void checkPath(String path)
	{
		try
		{
			SVNNodeKind nodeKind = repository.checkPath(path, -1);
			if (nodeKind == SVNNodeKind.NONE)
			{
				LOG.v(path + " not exists");
			}
			else if (nodeKind == SVNNodeKind.DIR)
			{
				LOG.v(path + " is dir");
			}
			else if (nodeKind == SVNNodeKind.FILE)
			{
				LOG.v(path + " is file");
			}
			if (false)
			{
				SVNProperties fileProperties = new SVNProperties();
				repository.getFile(path, -1, fileProperties, null);
				String mimeType = fileProperties.getStringValue(SVNProperty.MIME_TYPE);
				LOG.v("mimeType : " + mimeType);
				LOG.v("isTextMimeType : " + SVNProperty.isTextMimeType(mimeType));
				Iterator iterator = fileProperties.nameSet().iterator();
				while (iterator.hasNext())
				{
					String propertyName = (String) iterator.next();
					String propertyValue = fileProperties.getStringValue(propertyName);
					LOG.v(propertyName + " : " + propertyValue);
				}
			}
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
	}

	// ------------------------------------ copyClient --------------------------------------------

	public static SVNCommitInfo doCopy(String urlSrc, String urlDst, String message)
	{
		LOG.v("svn copy : " + urlSrc + " --> " + urlDst + " : " + message);
		SVNCommitInfo ret = null;
		try
		{
			SVNCopySource[] copySources = new SVNCopySource[]{new SVNCopySource(SVNRevision.HEAD, SVNRevision.HEAD, SVNURL.parseURIEncoded(urlSrc))};
			ret = copyClient.doCopy(copySources, SVNURL.parseURIEncoded(urlDst), false, true, true, message, null);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
		if (ret != null)
			LOG.v("success : " + ret);
		else
			LOG.e("fail");
		return ret;
	}

	// ------------------------------------ updateClient --------------------------------------------

	public static long reCheckout(String url, File local)
	{
		FsUtils.delete(local);
		if (!local.exists())
			return doCheckout(url, local);
		else
			return -1;
	}

	public static long checkoutOrUpdate(String url, File local)
	{
		if (isVersionedDirectory(local))
//		if (local.exists())
			return doUpdate(local);
		else
			return doCheckout(url, local);
	}

	public static long doUpdate(File local)
	{
		LOG.v("svn update : " + local);
		long ret = -1;
		try
		{
			ret = updateClient.doUpdate(local, SVNRevision.HEAD, SVNDepth.INFINITY, true, false);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
		if (ret != -1)
			LOG.v("success : " + ret);
		else
			LOG.e("fail");
		return ret;
	}

	public static long doCheckout(String url, File local)
	{
		LOG.v("svn checkout : " + url + " --> " + local);
		long ret = -1;
//		FsUtils.createDir(local);
		try
		{
			ret = updateClient.doCheckout(SVNURL.parseURIEncoded(url), local, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY, true);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
		if (ret != -1)
			LOG.v("success : " + ret);
		else
			LOG.e("fail");
		return ret;
	}

	// ------------------------------------ commitClient --------------------------------------------

	public static void commitAll(File local, String message)
	{
//		if (!isWorkingCopy(local))
//		{
//			checkout(url, local);
//		}
		if (!isVersionedDirectory(local))
		{
			doAdd(local);
		}
		doCommit(local, message);
	}

	public static SVNCommitInfo doCommit(File local, String message)
	{
		LOG.v("svn commit : " + local + " : " + message);
		SVNCommitInfo ret = null;
		try
		{
			ret = commitClient.doCommit(new File[]{local}, true, message, null, null, true, false, SVNDepth.INFINITY);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
		if (ret != null)
			LOG.v("success : " + ret);
		else
			LOG.e("fail");
		return ret;
	}

	public static SVNCommitInfo doMkDir(String url, String message)
	{
		LOG.v("svn makeDirectory : " + url + " : " + message);
		SVNCommitInfo ret = null;
		try
		{
			ret = commitClient.doMkDir(new SVNURL[]{SVNURL.parseURIEncoded(url)}, message);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
		if (ret != null)
			LOG.v("success : " + ret);
		else
			LOG.e("fail");
		return ret;
	}

	public static SVNCommitInfo doImport(String url, File local, String message)
	{
		LOG.v("svn importDirectory : " + url + " <-- " + local + " : " + message);
		SVNCommitInfo ret = null;
		try
		{
			ret = commitClient.doImport(local, SVNURL.parseURIEncoded(url), message, null, false, false, SVNDepth.INFINITY);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
		if (ret != null)
			LOG.v("success : " + ret);
		else
			LOG.e("fail");
		return ret;
	}

	// ------------------------------------ wcClient --------------------------------------------

	public static void doAdd(File local)
	{
		LOG.v("svn addEntry : " + local);
		try
		{
			wcClient.doAdd(new File[]{local}, false, false, false, SVNDepth.INFINITY, false, false, true);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
	}

	public static File getWorkingCopyRoot(File local)
	{
//		LOG.v("svn getWorkingCopy : " + local);
		File ret = null;
		try
		{
			ret = SVNWCUtil.getWorkingCopyRoot(local, false);
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
//		if (ret != null)
//			LOG.v("success : " + ret);
//		else
//			LOG.e("fail");
		return ret;
	}

	public static boolean isWorkingCopy(File local)
	{
		return getWorkingCopyRoot(local) != null;
	}

	public static boolean isVersionedDirectory(File local)
	{
		return SVNWCUtil.isVersionedDirectory(local);
	}

	// ------------------------------------ statusClient --------------------------------------------

	public static SVNStatus doStatus(File local, boolean remote)
	{
		LOG.v("svn showStatus : " + local);
		SVNStatus ret = null;
		if (isWorkingCopy(local))
		{
			try
			{
				ret = statusClient.doStatus(local, remote);
			}
			catch (SVNException e)
			{
				e.printStackTrace();
			}
		}
		if (ret != null)
		{
			LOG.v("success : " + ret);
			System.out.println(String.format("%30s%40s = %s", "SVNURL", "getURL", ret.getURL()));
			System.out.println(String.format("%30s%40s = %s", "SVNURL", "getRemoteURL", ret.getRemoteURL()));
			System.out.println(String.format("%30s%40s = %s", "File", "getFile", ret.getFile()));
			System.out.println(String.format("%30s%40s = %s", "SVNNodeKind", "getKind", ret.getKind()));
			System.out.println(String.format("%30s%40s = %s", "SVNRevision", "getRevision", ret.getRevision()));
			System.out.println(String.format("%30s%40s = %s", "SVNRevision", "getCommittedRevision", ret.getCommittedRevision()));
			System.out.println(String.format("%30s%40s = %s", "Date", "getCommittedDate", ret.getCommittedDate()));
			System.out.println(String.format("%30s%40s = %s", "String", "getAuthor", ret.getAuthor()));
			System.out.println(String.format("%30s%40s = %s", "SVNStatusType", "getContentsStatus", ret.getContentsStatus()));
			System.out.println(String.format("%30s%40s = %s", "SVNStatusType", "getPropertiesStatus", ret.getPropertiesStatus()));
			System.out.println(String.format("%30s%40s = %s", "SVNStatusType", "getRemoteContentsStatus", ret.getRemoteContentsStatus()));
			System.out.println(String.format("%30s%40s = %s", "SVNStatusType", "getRemotePropertiesStatus", ret.getRemotePropertiesStatus()));
			System.out.println(String.format("%30s%40s = %s", "boolean", "isLocked", ret.isLocked()));
			System.out.println(String.format("%30s%40s = %s", "boolean", "isCopied", ret.isCopied()));
			System.out.println(String.format("%30s%40s = %s", "boolean", "isSwitched", ret.isSwitched()));
			System.out.println(String.format("%30s%40s = %s", "boolean", "isFileExternal", ret.isFileExternal()));
			System.out.println(String.format("%30s%40s = %s", "File", "getConflictNewFile", ret.getConflictNewFile()));
			System.out.println(String.format("%30s%40s = %s", "File", "getConflictOldFile", ret.getConflictOldFile()));
			System.out.println(String.format("%30s%40s = %s", "File", "getConflictWrkFile", ret.getConflictWrkFile()));
			System.out.println(String.format("%30s%40s = %s", "File", "getPropRejectFile", ret.getPropRejectFile()));
			System.out.println(String.format("%30s%40s = %s", "String", "getCopyFromURL", ret.getCopyFromURL()));
			System.out.println(String.format("%30s%40s = %s", "SVNRevision", "getCopyFromRevision", ret.getCopyFromRevision()));
			System.out.println(String.format("%30s%40s = %s", "SVNLock", "getRemoteLock", ret.getRemoteLock()));
			System.out.println(String.format("%30s%40s = %s", "SVNLock", "getLocalLock", ret.getLocalLock()));
			System.out.println(String.format("%30s%40s = %s", "Map", "getEntryProperties", ret.getEntryProperties()));
			System.out.println(String.format("%30s%40s = %s", "SVNRevision", "getRemoteRevision", ret.getRemoteRevision()));
			System.out.println(String.format("%30s%40s = %s", "SVNNodeKind", "getRemoteKind", ret.getRemoteKind()));
			System.out.println(String.format("%30s%40s = %s", "Date", "getRemoteDate", ret.getRemoteDate()));
			System.out.println(String.format("%30s%40s = %s", "String", "getRemoteAuthor", ret.getRemoteAuthor()));
			System.out.println(String.format("%30s%40s = %s", "Date", "getWorkingContentsDate", ret.getWorkingContentsDate()));
			System.out.println(String.format("%30s%40s = %s", "Date", "getWorkingPropertiesDate", ret.getWorkingPropertiesDate()));
			System.out.println(String.format("%30s%40s = %s", "SVNEntry", "getEntry", ret.getEntry()));
			System.out.println(String.format("%30s%40s = %s", "String", "getChangelistName", ret.getChangelistName()));
			System.out.println(String.format("%30s%40s = %s", "SVNTreeConflictDescription", "getTreeConflict", ret.getTreeConflict()));
			System.out.println(String.format("%30s%40s = %s", "int", "getWorkingCopyFormat", ret.getWorkingCopyFormat()));
			System.out.println(String.format("%30s%40s = %s", "boolean", "isConflicted", ret.isConflicted()));
			System.out.println(String.format("%30s%40s = %s", "boolean", "isVersioned", ret.isVersioned()));
			System.out.println(String.format("%30s%40s = %s", "SVNStatusType", "getRemoteNodeStatus", ret.getRemoteNodeStatus()));
			System.out.println(String.format("%30s%40s = %s", "SVNStatusType", "getCombinedNodeAndContentsStatus", ret.getCombinedNodeAndContentsStatus()));
			System.out.println(String.format("%30s%40s = %s", "SVNStatusType", "getCombinedRemoteNodeAndContentsStatus", ret.getCombinedRemoteNodeAndContentsStatus()));
			System.out.println(String.format("%30s%40s = %s", "SVNStatusType", "getNodeStatus", ret.getNodeStatus()));
			System.out.println(String.format("%30s%40s = %s", "SVNURL", "getRepositoryRootURL", ret.getRepositoryRootURL()));
			System.out.println(String.format("%30s%40s = %s", "String", "getRepositoryUUID", ret.getRepositoryUUID()));
			System.out.println(String.format("%30s%40s = %s", "String", "getRepositoryRelativePath", ret.getRepositoryRelativePath()));
			System.out.println(String.format("%30s%40s = %s", "SVNDepth", "getDepth", ret.getDepth()));
			System.out.println(String.format("%30s%40s = %s", "File", "getMovedToPath", ret.getMovedToPath()));
			System.out.println(String.format("%30s%40s = %s", "File", "getMovedFromPath", ret.getMovedFromPath()));
		}
//		else
//			LOG.e("fail");
		return ret;
	}

	// ------------------------------------ task --------------------------------------------

	public static boolean hasDotSvn(File taskDir)
	{
		return new File(taskDir, ".svn").exists();
	}

	public static File localRoot = new File(FsUtils.SD_CARD);

	public static String getTaskPath(String userId)
	{
		return String.format("/lys.tasks/%s", userId);
	}

	public static String getTaskUrl(String userId) throws SVNException
	{
		return repository.getRepositoryRoot(true) + getTaskPath(userId);
	}

	public static File getTaskDir(String userId)
	{
		return new File(localRoot, getTaskPath(userId));
	}

	public static String getTaskPath(String userId, String id)
	{
		return String.format("/lys.tasks/%s/%s", userId, id);
	}

	public static String getTaskUrl(String userId, String id) throws SVNException
	{
		return repository.getRepositoryRoot(true) + getTaskPath(userId, id);
	}

	public static File getTaskDir(String userId, String id)
	{
		return new File(localRoot, getTaskPath(userId, id));
	}

	public static String getTaskPath(String userId, String id, String pageDir)
	{
		return String.format("/lys.tasks/%s/%s/%s", userId, id, pageDir);
	}

	public static String getTaskUrl(String userId, String id, String pageDir) throws SVNException
	{
		return repository.getRepositoryRoot(true) + getTaskPath(userId, id, pageDir);
	}

	public static long update(File taskDir) throws SVNException
	{
		TimeDebug.record("-------- update start " + taskDir);
		long version = updateClient.doUpdate(taskDir, SVNRevision.HEAD, SVNDepth.INFINITY, true, false);
		TimeDebug.record("-------- update over " + version);
		return version;
	}

	public static long checkout(String taskUrl, File taskDir) throws SVNException
	{
		TimeDebug.record("-------- checkout start " + taskDir);
		long version = updateClient.doCheckout(SVNURL.parseURIEncoded(taskUrl), taskDir, SVNRevision.HEAD, SVNRevision.HEAD, SVNDepth.INFINITY, true);
		TimeDebug.record("-------- checkout over " + version);
		return version;
	}

	public static SVNCommitInfo Import(String taskUrl, File taskDir, String message) throws SVNException
	{
		TimeDebug.record("-------- Import start " + taskDir);
		SVNCommitInfo info = commitClient.doImport(taskDir, SVNURL.parseURIEncoded(taskUrl), message, null, false, false, SVNDepth.INFINITY);
		TimeDebug.record("-------- Import over " + info);
		return info;
	}

	public static void commitAdd(File taskDir) throws SVNException
	{
		File[] files = taskDir.listFiles();
		for (File file : files)
		{
			if (!file.getName().equals(".svn"))
			{
				TimeDebug.record("");
				SVNStatus status = statusClient.doStatus(file, false);
				TimeDebug.record("doStatus");
				if (status.getNodeStatus() == SVNStatusType.STATUS_UNVERSIONED)
				{
					TimeDebug.record("");
					wcClient.doAdd(new File[]{file}, false, false, false, SVNDepth.INFINITY, false, false, true);
					TimeDebug.record("doAdd : " + file);
				}
				else
				{
					if (file.isDirectory())
					{
						commitAdd(file);
					}
				}
			}
		}
	}

	public static void commitDelete(String taskPath, File taskDir) throws SVNException
	{
		TimeDebug.record("");
		Collection entries = repository.getDir(taskPath, -1, null, (Collection) null);
		TimeDebug.record("getDir");
		Iterator iterator = entries.iterator();
		while (iterator.hasNext())
		{
			SVNDirEntry entry = (SVNDirEntry) iterator.next();
//			LOG.v(String.format("%s : %s : %s : %s : %s", entry.getKind(), entry.getName(), entry.getAuthor(), entry.getRevision(), entry.getDate()));
			File file = new File(taskDir, entry.getName());
			if (!file.exists() || (entry.getKind() == SVNNodeKind.DIR && file.isFile()) || (entry.getKind() == SVNNodeKind.FILE && file.isDirectory()))
			{
				TimeDebug.record("");
				wcClient.doDelete(file, false, false);
				TimeDebug.record("doDelete : " + taskPath + "/" + entry.getName());
			}
			else
			{
				if (entry.getKind() == SVNNodeKind.DIR)
					commitDelete(taskPath + "/" + entry.getName(), file);
			}
		}
	}

	public static void commitDeleteAdd(String taskPath, List<SSvnDirObj> objs, File taskDir) throws SVNException
	{
		HashMap<String, SSvnDirObj> map = new HashMap<>();

		for (SSvnDirObj obj : objs)
		{
			File file = new File(taskDir, obj.name);
			if (!file.exists() || (obj.isDir && file.isFile()) || (!obj.isDir && file.isDirectory()))
			{
				TimeDebug.record("");
				wcClient.doDelete(file, false, false);
				TimeDebug.record("doDelete : " + taskPath + "/" + obj.name);
			}
			else
			{
				map.put(obj.name, obj);
			}
		}

		File[] files = taskDir.listFiles();
		for (File file : files)
		{
			if (!file.getName().equals(".svn"))
			{
				if (!map.containsKey(file.getName()))
				{
					TimeDebug.record("");
					wcClient.doAdd(new File[]{file}, false, false, false, SVNDepth.INFINITY, false, false, true);
					TimeDebug.record("doAdd : " + file);
				}
			}
		}

		for (Map.Entry<String, SSvnDirObj> entry : map.entrySet())
		{
			String name = entry.getKey();
			SSvnDirObj obj = entry.getValue();
			if (obj.isDir)
			{
				File file = new File(taskDir, name);
				commitDeleteAdd(taskPath + "/" + name, obj.objs, file);
			}
		}
	}

	public static SVNCommitInfo commit(String taskPath, String taskUrl, File taskDir, String message) throws SVNException
	{
//		LOG.v("-------- commit add " + taskDir);
//		commitAdd(taskDir);
//
//		LOG.v("-------- commit delete " + taskDir);
//		commitDelete(taskPath, taskDir);

		TimeDebug.record("");

		SRequest_SvnGetDir request = new SRequest_SvnGetDir();
		request.path = taskPath;
		SProtocol transRcv = Protocol.doPost(App.getApi(), SHandleId.SvnGetDir, request.saveToStr());
		if (transRcv != null && transRcv.code == 200)
		{
			SResponse_SvnGetDir response = SResponse_SvnGetDir.load(transRcv.data);

			TimeDebug.record("SvnGetDir");

			LOG.v("-------- commit delete add " + taskDir);
			commitDeleteAdd(taskPath, response.objs, taskDir);

			LOG.v("-------- commit start " + taskDir);
			TimeDebug.record("");
			SVNCommitInfo info = commitClient.doCommit(new File[]{taskDir}, false, message, null, null, false, false, SVNDepth.INFINITY);
			TimeDebug.record("doCommit");
			LOG.v("-------- commit over " + info);

			return info;
		}
		return null;
	}

	public static void deleteIfExists(String userId, String id, String message) throws SVNException
	{
		String taskPath = getTaskPath(userId, id);
		String taskUrl = getTaskUrl(userId, id);
		SVNNodeKind nodeKind = repository.checkPath(taskPath, -1);
		if (nodeKind == SVNNodeKind.DIR || nodeKind == SVNNodeKind.FILE)
		{
			LOG.v("-------- delete start " + taskPath);
			SVNCommitInfo info = commitClient.doDelete(new SVNURL[]{SVNURL.parseURIEncoded(taskUrl)}, message);
			LOG.v("-------- delete over " + info);
		}
	}

	public static void deleteIfExists(String userId, String message) throws SVNException
	{
		String taskPath = getTaskPath(userId);
		String taskUrl = getTaskUrl(userId);
		SVNNodeKind nodeKind = repository.checkPath(taskPath, -1);
		if (nodeKind == SVNNodeKind.DIR || nodeKind == SVNNodeKind.FILE)
		{
			LOG.v("-------- delete start " + taskPath);
			SVNCommitInfo info = commitClient.doDelete(new SVNURL[]{SVNURL.parseURIEncoded(taskUrl)}, message);
			LOG.v("-------- delete over " + info);
		}
	}

	public static void copy(String userIdSrc, String idSrc, String userIdDst, String idDst, String message)
	{
		try
		{
			String taskPathSrc = getTaskPath(userIdSrc, idSrc);
			String taskUrlSrc = getTaskUrl(userIdSrc, idSrc);
			SVNNodeKind nodeKindSrc = repository.checkPath(taskPathSrc, -1);
			if (nodeKindSrc == SVNNodeKind.DIR)
			{
				String taskPathDst = getTaskPath(userIdDst, idDst);
				String taskUrlDst = getTaskUrl(userIdDst, idDst);
				SVNNodeKind nodeKindDst = repository.checkPath(taskPathDst, -1);
				if (nodeKindDst == SVNNodeKind.NONE)
				{
					LOG.v("-------- copy start " + taskPathSrc + " --> " + taskPathDst);
					SVNCopySource[] copySources = new SVNCopySource[]{new SVNCopySource(SVNRevision.HEAD, SVNRevision.HEAD, SVNURL.parseURIEncoded(taskUrlSrc))};
					SVNCommitInfo info = copyClient.doCopy(copySources, SVNURL.parseURIEncoded(taskUrlDst), false, true, true, message, null);
					LOG.v("-------- copy over " + info);
				}
			}
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}
	}

	private static void copy(String userIdSrc, String idSrc, String pageDirSrc, String userIdDst, String idDst, String pageDirDst, String message) throws Exception
	{
		String taskPathSrc = getTaskPath(userIdSrc, idSrc, pageDirSrc);
		String taskUrlSrc = getTaskUrl(userIdSrc, idSrc, pageDirSrc);
		SVNNodeKind nodeKindSrc = repository.checkPath(taskPathSrc, -1);
		if (nodeKindSrc == SVNNodeKind.DIR)
		{
			String taskPathDst = getTaskPath(userIdDst, idDst, pageDirDst);
			String taskUrlDst = getTaskUrl(userIdDst, idDst, pageDirDst);
			SVNNodeKind nodeKindDst = repository.checkPath(taskPathDst, -1);
			if (nodeKindDst == SVNNodeKind.NONE)
			{
				LOG.v("-------- copy start " + taskPathSrc + " --> " + taskPathDst);
				SVNCopySource[] copySources = new SVNCopySource[]{new SVNCopySource(SVNRevision.HEAD, SVNRevision.HEAD, SVNURL.parseURIEncoded(taskUrlSrc))};
				SVNCommitInfo info = copyClient.doCopy(copySources, SVNURL.parseURIEncoded(taskUrlDst), false, true, true, message, null);
				LOG.v("-------- copy over " + info);
			}
		}
	}

//	public static long getRemoteVision(String taskPath) throws SVNException
//	{
//		TimeDebug.record("");
//		long version = repository.getFile(taskPath, -1, null, null);
//		TimeDebug.record("getFile : " + version);
//		return version;
//	}
//
//	public static long getLocalVision(File taskDir) throws SVNException
//	{
//		TimeDebug.record("");
//		SVNStatus status = statusClient.doStatus(taskDir, false);
//		long version = status.getRevision().getNumber();
//		TimeDebug.record("doStatus : " + version);
//		return version;
//	}
//
//	public static boolean isNewestVision(String userId, String id) throws SVNException
//	{
//		String taskPath = getTaskPath(userId, id);
//		File taskDir = getTaskDir(userId, id);
//		return getRemoteVision(taskPath) == getLocalVision(taskDir);
//	}

	public static boolean isNewestVision(String userId, String id) throws SVNException
	{
		File taskDir = getTaskDir(userId, id);
		TimeDebug.record("");
		SVNStatus status = statusClient.doStatus(taskDir, true);
		long localVision = status.getCommittedRevision().getNumber();
		long remoteVision = status.getRemoteRevision().getNumber();
		if (remoteVision == -1)
			remoteVision = status.getRevision().getNumber();
		TimeDebug.record("isNewestVision : " + status.getCommittedRevision() + "  " + status.getRemoteRevision() + "  " + status.getRevision());
		return localVision == remoteVision;
	}

	public static boolean relocateIfNeed(File taskDir) throws SVNException
	{
		if (taskDir.exists())
		{
			if (taskDir.isDirectory())
			{
				if (hasDotSvn(taskDir))
				{
					TimeDebug.record("");
					SVNURL newURL = repository.getRepositoryRoot(true);
					TimeDebug.record("getRepositoryRoot : " + newURL);

					TimeDebug.record("");
					SVNStatus status = statusClient.doStatus(taskDir, false);
					SVNURL oldURL = status.getRepositoryRootURL();
					TimeDebug.record("doStatus : " + oldURL);

					if (!newURL.equals(oldURL))
					{
						TimeDebug.record("");
						updateClient.doRelocate(taskDir, oldURL, newURL, true);
						TimeDebug.record("doRelocate : " + oldURL + " --> " + newURL);
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void deleteLocalTaskDir(String userId, String id)
	{
		File taskDir = getTaskDir(userId, id);
		FsUtils.delete(taskDir);
	}

	public static void deleteLocalTaskSvnDir(String userId, String id)
	{
		File taskDir = getTaskDir(userId, id);
		FsUtils.delete(new File(taskDir, ".svn"));
	}

	public static SvnTaskResult updateTaskImpl(String userId, String id) throws SVNException
	{
		String taskPath = getTaskPath(userId, id);
		String taskUrl = getTaskUrl(userId, id);
		File taskDir = getTaskDir(userId, id);

		TimeDebug.record("");
		SVNNodeKind nodeKind = repository.checkPath(taskPath, -1);
		TimeDebug.record("checkPath");
		if (nodeKind == SVNNodeKind.NONE)
		{
			LOG.v(taskPath + " not exists");
			return new SvnTaskResult(ResultCode_Success);
		}
		else if (nodeKind == SVNNodeKind.DIR)
		{
			LOG.v(taskPath + " is dir");
			if (taskDir.exists())
			{
				if (taskDir.isDirectory())
				{
					if (hasDotSvn(taskDir))
					{
						if (!isNewestVision(userId, id))
						{
							update(taskDir);
						}
					}
					else
					{
						checkout(taskUrl, taskDir);
					}
				}
				else
				{
					return new SvnTaskResult(ResultCode_Fail, taskDir + " is file");
				}
			}
			else
			{
				FsUtils.createFolder(taskDir);
				checkout(taskUrl, taskDir);
			}
		}
		else if (nodeKind == SVNNodeKind.FILE)
		{
			return new SvnTaskResult(ResultCode_Fail, taskPath + " is file");
		}

		return new SvnTaskResult(ResultCode_Success);
	}

	public static SvnTaskResult commitTaskImpl(String userId, String id, String message) throws SVNException
	{
		String taskPath = getTaskPath(userId, id);
		String taskUrl = getTaskUrl(userId, id);
		File taskDir = getTaskDir(userId, id);

		if (taskDir.exists())
		{
			if (taskDir.isDirectory())
			{
				TimeDebug.record("");
				SVNNodeKind nodeKind = repository.checkPath(taskPath, -1);
				TimeDebug.record("checkPath");
				if (nodeKind == SVNNodeKind.NONE)
				{
					LOG.v(taskPath + " not exists");
					if (!hasDotSvn(taskDir))
					{
						Import(taskUrl, taskDir, message);
						checkout(taskUrl, taskDir);
					}
					else
					{
						Import(taskUrl, taskDir, message);
						checkout(taskUrl, taskDir);
					}
				}
				else if (nodeKind == SVNNodeKind.DIR)
				{
					LOG.v(taskPath + " is dir");
					if (!hasDotSvn(taskDir))
					{
						checkout(taskUrl, taskDir);
						if (commit(taskPath, taskUrl, taskDir, message) == null)
						{
							return new SvnTaskResult(ResultCode_NetError, "网络错误");
						}
					}
					else
					{
						if (isNewestVision(userId, id))
						{
							if (commit(taskPath, taskUrl, taskDir, message) == null)
							{
								return new SvnTaskResult(ResultCode_NetError, "网络错误");
							}
						}
						else
						{
							return new SvnTaskResult(ResultCode_NotNewestVision, "当前不是最新版本");
						}
					}
				}
				else if (nodeKind == SVNNodeKind.FILE)
				{
					return new SvnTaskResult(ResultCode_Fail, taskPath + " is file");
				}
			}
			else
			{
				return new SvnTaskResult(ResultCode_Fail, taskDir + " is file");
			}
		}

		return new SvnTaskResult(ResultCode_Success);
	}

	public static void updateTask(final boolean force, final String userId, final String id, final OnCallback callback)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				TimeDebug.init();
				SvnTaskResult result;
				try
				{
					if (force)
						FsUtils.delete(getTaskDir(userId, id));
					result = updateTaskImpl(userId, id);
				}
				catch (SVNException e)
				{
					e.printStackTrace();
					if (e.getErrorMessage().getErrorCode() == SVNErrorCode.RA_DAV_REQUEST_FAILED)
					{
						try
						{
							File taskDir = getTaskDir(userId, id);
							if (relocateIfNeed(taskDir))
							{
								result = new SvnTaskResult(ResultCode_Relocate, "服务器地址变更，已更正！");
							}
							else
							{
								result = new SvnTaskResult(ResultCode_Fail, "网络错误 " + e.getErrorMessage().getMessage());
							}
						}
						catch (SVNException e1)
						{
							e1.printStackTrace();
							result = new SvnTaskResult(ResultCode_Fail, "二次处理错误 " + e.getErrorMessage().getMessage());
						}
					}
					else if (e.getErrorMessage().getErrorCode() == SVNErrorCode.WC_LOCKED)
					{
						result = new SvnTaskResult(ResultCode_Locked, "目录锁定，已修正！");
					}
					else
					{
						result = new SvnTaskResult(ResultCode_Fail, "未处理错误 " + e.getErrorMessage().getMessage());
					}
				}
				TimeDebug.over("update over");
				if (callback != null)
					callback.over(result);
			}
		}).start();
	}

	public static void commitTask(final boolean force, final String userId, final String id, final String message, final OnCallback callback)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				TimeDebug.init();
				SvnTaskResult result;
				try
				{
					if (force)
						deleteIfExists(userId, id, "delete by " + AppConfig.readAccount() + " for force commit");
					result = commitTaskImpl(userId, id, message);
				}
				catch (SVNException e)
				{
					e.printStackTrace();
					if (e.getErrorMessage().getErrorCode() == SVNErrorCode.RA_DAV_REQUEST_FAILED)
					{
						result = new SvnTaskResult(ResultCode_Fail, "网络错误 " + e.getErrorMessage().getMessage());
					}
					else if (e.getErrorMessage().getErrorCode() == SVNErrorCode.WC_FOUND_CONFLICT)
					{
						result = new SvnTaskResult(ResultCode_Conflict, "版本冲突！");
					}
					else
					{
						result = new SvnTaskResult(ResultCode_Fail, "未处理错误 " + e.getErrorMessage().getMessage());
					}
				}
				TimeDebug.over("commit over");
				if (callback != null)
					callback.over(result);
			}
		}).start();
	}

	private static void addPageDirs(String svnPath, File tmpFile, List<String> pageDirs) throws Exception
	{
		String svnUrl = repository.getRepositoryRoot(true) + svnPath;
		SVNNodeKind nodeKind = repository.checkPath(svnPath, -1);
		if (nodeKind == SVNNodeKind.NONE)
		{
			LOG.v(svnPath + " not exists");

			SNotePageSet pageset = new SNotePageSet();

			for (String pageDir : pageDirs)
			{
				SNotePage page = new SNotePage();
				page.pageDir = pageDir;
				pageset.pages.add(page);
			}

			FsUtils.writeText(tmpFile, LOGJson.getStr(pageset.saveToStr()));

			commitClient.doImport(tmpFile, SVNURL.parseURIEncoded(svnUrl), "import pageset by client", null, false, false, SVNDepth.INFINITY);
		}
		else if (nodeKind == SVNNodeKind.DIR)
		{
			LOG.v(svnPath + " is dir");
		}
		else if (nodeKind == SVNNodeKind.FILE)
		{
			LOG.v(svnPath + " is file");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			repository.getFile(svnPath, -1, null, baos);
			baos.close();
			byte[] buffer = baos.toByteArray();
			String str = new String(buffer, "UTF-8");
			SNotePageSet pageset = SNotePageSet.load(str);

			HashMap<String, Integer> pageMap = new HashMap<String, Integer>();
			for (SNotePage page : pageset.pages)
			{
				pageMap.put(page.pageDir, 0);
			}

			boolean hasModify = false;
			for (String pageDir : pageDirs)
			{
				if (!pageMap.containsKey(pageDir))
				{
					SNotePage page = new SNotePage();
					page.pageDir = pageDir;
					pageset.pages.add(page);
					hasModify = true;
				}
			}

			LOG.v("hasModify : " + hasModify);
			if (hasModify)
			{
				FsUtils.writeText(tmpFile, LOGJson.getStr(pageset.saveToStr()));
				commitClient.doDelete(new SVNURL[]{SVNURL.parseURIEncoded(svnUrl)}, "delete pageset by client");
				commitClient.doImport(tmpFile, SVNURL.parseURIEncoded(svnUrl), "import pageset by client", null, false, false, SVNDepth.INFINITY);
			}
		}
	}

	public static void copyWrong(final SPTask task, final List<String> pageDirs, final String message, final OnCallback callback)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				TimeDebug.init();
				SvnTaskResult result;
				try
				{
					for (String pageDir : pageDirs)
					{
						copy(task.userId, task.id, pageDir, task.userId, String.format("%s-错题", task.group), pageDir, String.format("%s copy wrong at %s", message, pageDir));
					}
					String pagesetPath = getTaskPath(task.userId, String.format("%s-错题", task.group), "pageset.json");
					addPageDirs(pagesetPath, Config.tmpPagesetFile, pageDirs);
					result = new SvnTaskResult(ResultCode_Success);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					result = new SvnTaskResult(ResultCode_Fail, "收集错题失败！");
				}
				TimeDebug.over("copy wrong over");
				if (callback != null)
					callback.over(result);
			}
		}).start();
	}

	public static final int ResultCode_Fail = 0;
	public static final int ResultCode_Success = 1;
	public static final int ResultCode_NotNewestVision = 2;
	public static final int ResultCode_Relocate = 3;
	public static final int ResultCode_Locked = 4;
	public static final int ResultCode_Conflict = 5;
	public static final int ResultCode_NetError = 6;

	public static class SvnTaskResult
	{
		public int resultCode;
		public String errorMsg;

		public SvnTaskResult(int resultCode)
		{
			this.resultCode = resultCode;
		}

		public SvnTaskResult(int resultCode, String errorMsg)
		{
			this.resultCode = resultCode;
			this.errorMsg = errorMsg;
		}
	}

	public interface OnCallback
	{
		void over(SvnTaskResult result);
	}
}

package com.lys.adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lys.activity.ActivityMarket;
import com.lys.app.R;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.HttpUtils;
import com.lys.base.utils.LOG;
import com.lys.base.utils.SysUtils;
import com.lys.kit.utils.ImageLoad;
import com.lys.protobuf.SApp;

import java.io.File;
import java.util.List;
import java.util.Map;

public class AdapterAppMarket extends RecyclerView.Adapter<AdapterAppMarket.Holder>
{
	private ActivityMarket owner = null;
	private Map<String, String> installedMap;
	private List<SApp> apps = null;

	public AdapterAppMarket(ActivityMarket owner, Map<String, String> installedMap)
	{
		this.owner = owner;
		this.installedMap = installedMap;
	}

	public void setData(List<SApp> apps)
	{
		this.apps = apps;
		notifyDataSetChanged();
	}

	public void flush(String packageName)
	{
		for (int i = 0; i < apps.size(); i++)
		{
			if (apps.get(i).pkgName.equals(packageName))
			{
				notifyItemChanged(i);
				break;
			}
		}
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_market, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, final int position)
	{
		final SApp app = apps.get(position);
		final Context context = holder.itemView.getContext();

		final String newVersion = app.versionName + "+" + app.versionCode;

		// ----------------- 显示UI -------------------

		ImageLoad.displayImage(context, app.icoUrl, holder.icon, R.drawable.img_default, null);
		holder.name.setText(app.name);
		holder.size.setText(CommonUtils.formatSize(app.size));
		holder.version.setText(String.format("(%d)", app.versionCode));
		holder.des.setText(app.des);

		// ----------------- 清理下载监听 -------------------

		String filePath = (String) holder.btn.getTag();
		if (!TextUtils.isEmpty(filePath))
		{
			LOG.v("clear download listener : " + filePath);
			File file = new File(filePath);
			HttpUtils.DownloadInfo downloadInfo;
			if ((downloadInfo = HttpUtils.getWaitDownloadInfo(file)) != null)
			{
				HttpUtils.downloadSetListener(downloadInfo, null);
			}
			else if ((downloadInfo = HttpUtils.getRunningDownloadInfo(file)) != null)
			{
				HttpUtils.downloadSetListener(downloadInfo, null);
			}
			holder.btn.setTag("");
		}

		// ----------------- 创建下载监听 -------------------

		final HttpUtils.OnDownloadListener downloadListener = new HttpUtils.OnDownloadListener()
		{
			@Override
			public void onWait()
			{
				holder.btn.setText("等待");
			}

			@Override
			public void onFail()
			{
				updateBtnText(context, holder, app.pkgName, newVersion);
			}

			@Override
			public void onProgress(int alreadyDownloadSize)
			{
				int progress = (int) (alreadyDownloadSize / app.size * 100);
				setProgress(holder, progress);
				holder.btn.setText(String.format("%d", progress) + "%");
			}

			@Override
			public void onSuccess()
			{
				updateBtnText(context, holder, app.pkgName, newVersion);
				File file = new File(getDownloadPath(context, app.pkgName));
				owner.installApk(file);
			}
		};

		// ----------------- 更新状态 -------------------

		File file = new File(getDownloadPath(context, app.pkgName));
		HttpUtils.DownloadInfo downloadInfo;
		if ((downloadInfo = HttpUtils.getWaitDownloadInfo(file)) != null)
		{
			holder.newFlag.setVisibility(View.GONE);
			setProgress(holder, 0);
			holder.btn.setText("等待");
			HttpUtils.downloadSetListener(downloadInfo, downloadListener);
			holder.btn.setTag(file.getAbsolutePath());
		}
		else if ((downloadInfo = HttpUtils.getRunningDownloadInfo(file)) != null)
		{
			holder.newFlag.setVisibility(View.GONE);
			setProgress(holder, 0);
			holder.btn.setText("");
			HttpUtils.downloadSetListener(downloadInfo, downloadListener);
			holder.btn.setTag(file.getAbsolutePath());
		}
		else
		{
			updateBtnText(context, holder, app.pkgName, newVersion);
		}

		// ----------------- 点击处理 -------------------

		holder.btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (holder.btn.getText().equals("卸载"))
				{
//					SysUtils.launchApp(context, app.packageName);
					owner.uninstallApk(app.pkgName);
				}
				else if (holder.btn.getText().equals("安装"))
				{
					holder.newFlag.setVisibility(View.GONE);
					File downloadFile = getDownloadFile(context, app.pkgName, newVersion);
					if (downloadFile != null)
						owner.installApk(downloadFile);
					else
						Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
				}
				else if (holder.btn.getText().equals("下载") || holder.btn.getText().equals("更新"))
				{
					File file = new File(getDownloadPath(context, app.pkgName));
					holder.newFlag.setVisibility(View.GONE);
					HttpUtils.download(context, ImageLoad.checkUrl(app.apkUrl), file, downloadListener);
				}
			}
		});
	}

	private void setProgress(Holder holder, int progress)
	{
		LayerDrawable layerDrawable = (LayerDrawable) holder.btn.getBackground();
		ClipDrawable clipDrawable = (ClipDrawable) layerDrawable.findDrawableByLayerId(android.R.id.progress);
		clipDrawable.setLevel(progress * 100);
	}

	private void updateBtnText(Context context, Holder holder, String packageName, String newVersion)
	{
		holder.newFlag.setVisibility(View.GONE);
		setProgress(holder, 0);

		String installedVersion = installedMap.get(packageName);
		if (TextUtils.isEmpty(installedVersion))
		{
			File downloadFile = getDownloadFile(context, packageName, newVersion);
			if (downloadFile != null)
			{
				holder.btn.setText("安装");
				setProgress(holder, 100);
			}
			else
			{
				holder.btn.setText("下载");
			}
		}
		else
		{
			if (newVersion.equals(installedVersion))
			{
//				if (packageName.equals(Config.PackageNameDesktop) || //
//						packageName.equals(Config.PackageNameTask) || //
//						packageName.equals(Config.PackageNameTopic) || //
//						packageName.equals(Config.PackageNameNote) || //
//						packageName.equals(Config.PackageNameWrong) || //
//						packageName.equals(Config.PackageNameBook) || //
//						packageName.equals(Config.PackageNameMarket))
//					holder.btn.setText("已安装");
//				else
				holder.btn.setText("卸载");
			}
			else
			{
				File downloadFile = getDownloadFile(context, packageName, newVersion);
				if (downloadFile != null)
				{
					holder.btn.setText("安装");
					holder.newFlag.setVisibility(View.VISIBLE);
					setProgress(holder, 100);
				}
				else
				{
					holder.btn.setText("更新");
					holder.newFlag.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	private String getDownloadPath(Context context, String packageName)
	{
		String APK_DIR = FsUtils.SD_CARD + "/" + context.getPackageName() + "/apk";
		new File(APK_DIR).mkdirs();
		return APK_DIR + "/" + packageName.replace('.', '_') + ".apk";
	}

	private File getDownloadFile(Context context, String packageName, String newVersion)
	{
		String apkPath = getDownloadPath(context, packageName);
		File file = new File(apkPath);
		if (file.exists())
		{
			PackageInfo packageInfo = SysUtils.getApkPackageInfo(context, apkPath);
			if (packageInfo != null && newVersion.equals(packageInfo.versionName + "+" + packageInfo.versionCode))
				return file;
			else
				return null;
		}
		else
			return null;
	}

	@Override
	public int getItemCount()
	{
		if (apps != null)
			return apps.size();
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ImageView icon;
		public TextView name;
		public TextView size;
		public TextView version;
		public TextView des;
		public TextView btn;
		public TextView newFlag;

		public Holder(View itemView)
		{
			super(itemView);
			icon = itemView.findViewById(R.id.icon);
			name = itemView.findViewById(R.id.name);
			size = itemView.findViewById(R.id.size);
			version = itemView.findViewById(R.id.version);
			des = itemView.findViewById(R.id.des);
			btn = itemView.findViewById(R.id.btn);
			newFlag = itemView.findViewById(R.id.newFlag);
		}
	}
}
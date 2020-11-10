package com.lys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.king.zxing.CaptureActivity;
import com.lys.app.R;
import com.lys.base.activity.BaseActivity;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.LOG;
import com.lys.dialog.DialogSelectTask;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SUser;
import com.lys.protobuf.SUserType;
import com.lys.utils.LysUpload;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityMasterTools extends AppActivity implements View.OnClickListener
{
	public static final int ScanForBindTask = 0x157;

	private class Holder
	{
//		private ViewGroup tabCon;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
//		holder.tabCon = findViewById(R.id.tabCon);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_master_tools);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		findViewById(R.id.scanToBindTask).setOnClickListener(this);
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.scanToBindTask)
		{
			scanToBindTask();
		}
	}

	private void scanToBindTask()
	{
		Intent intent = new Intent(context, CaptureActivityLandscape.class);
		startActivityForResult(intent, ScanForBindTask);
	}

	private void bindTask(final String url)
	{
		if (url.matches("http://k12-eco\\.com/\\w+\\.htm"))
		{
			DialogAlert.show(context, "绑定提示：", url, new DialogAlert.OnClickListener()
			{
				@Override
				public void onClick(int which)
				{
					if (which == 1)
					{
						Matcher matcher = null;
						if ((matcher = Pattern.compile("http://k12-eco\\.com/(\\w+)\\.htm").matcher(url)).find())
						{
							final String name = matcher.group(1).trim();
							selectUser(new BaseActivity.OnImageListener()
							{
								@Override
								public void onResult(String userStr)
								{
									SUser user = SUser.load(userStr);
									DialogSelectTask.show(context, user.id, new DialogSelectTask.OnListener()
									{
										@Override
										public void onSelect(List<SPTask> selectedList, String taskText)
										{
											if (selectedList.size() == 1)
											{
												SPTask task = selectedList.get(0);

												File htmDir = new File(FsUtils.SD_CARD, "htm");
												FsUtils.createFolder(htmDir);
												File file = new File(htmDir, String.format("%s.htm", name));

												String text = genHtm(task.id);
												FsUtils.writeText(file, text);

//												String path = String.format("/zjyk/apache-tomcat-8.5.43/webapps/ROOT/%s.htm", name);
												String path = String.format("/../../%s.htm", name);
												LysUpload.doUpload(context, file, path, new Protocol.OnCallback()
												{
													@Override
													public void onResponse(int code, String data, String msg)
													{
														if (code == 200)
														{
															LOG.toast(context, "绑定成功");
														}
													}
												});
											}
											else
											{
												LOG.toast(context, "错误的个数：" + selectedList.size());
											}
										}
									});
								}
							}, SUserType.Master, SUserType.Teacher);
						}
						else
						{
							LOG.toast(context, "错误2：" + url);
						}
					}
				}
			}, "取消", "绑定");
		}
		else
		{
			LOG.toast(context, "错误：" + url);
		}
	}

	private String genHtm(String taskId)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("<!DOCTYPE html>") + "\r\n");
		sb.append(String.format("<html>") + "\r\n");
		sb.append(String.format("<head>") + "\r\n");
		sb.append(String.format("<meta charset=\"UTF-8\">") + "\r\n");
		sb.append(String.format("<script>") + "\r\n");
		sb.append(String.format("\twindow.location = 'http://k12-eco.com/pair/task.html?id=%s';", taskId) + "\r\n");
		sb.append(String.format("</script>") + "\r\n");
		sb.append(String.format("</head>") + "\r\n");
		sb.append(String.format("<body>") + "\r\n");
		sb.append(String.format("</body>") + "\r\n");
		sb.append(String.format("</html>") + "\r\n");
		return sb.toString();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ScanForBindTask)
		{
			if (resultCode == RESULT_OK)
			{
				Bundle bundle = data.getExtras();
				String url = bundle.getString(CaptureActivity.KEY_RESULT);
				bindTask(url);
			}
		}
	}
}

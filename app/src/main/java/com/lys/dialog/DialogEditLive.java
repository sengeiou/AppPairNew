package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lys.App;
import com.lys.activity.ActivityLivePlay;
import com.lys.activity.AppActivity;
import com.lys.adapter.AdapterUserGroupList;
import com.lys.app.R;
import com.lys.base.activity.BaseActivity;
import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.JsonHelper;
import com.lys.base.utils.LOG;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.dialog.DialogWait;
import com.lys.kit.module.OssHelper;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SLiveTask;
import com.lys.protobuf.SLiveType;
import com.lys.protobuf.SRequest_GetUser;
import com.lys.protobuf.SRequest_UserGroupAddModify;
import com.lys.protobuf.SRequest_UserGroupGetAll;
import com.lys.protobuf.SResponse_GetUser;
import com.lys.protobuf.SResponse_UserGroupGetAll;
import com.lys.protobuf.SUser;
import com.lys.protobuf.SUserGroup;
import com.lys.protobuf.SUserType;
import com.lys.utils.UserCacheManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DialogEditLive extends Dialog implements View.OnClickListener
{
	public interface OnResultListener
	{
		void onResult(SLiveTask live);
	}

	private OnResultListener listener = null;

	private void setListener(OnResultListener listener)
	{
		this.listener = listener;
	}

	private class Holder
	{
		private TextView id;
		private EditText name;
		private EditText des;
		private TextView actorId;
		private TextView startTime;
		private TextView userIds;

		private RadioButton typePrivate;
		private RadioButton typePublic;
		private RadioButton typePrivateRecord;
		private RadioButton typePublicRecord;

		private ImageView cover;
		private ImageView video;
		private TextView duration;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.id = findViewById(R.id.id);
		holder.name = findViewById(R.id.name);
		holder.des = findViewById(R.id.des);
		holder.actorId = findViewById(R.id.actorId);
		holder.startTime = findViewById(R.id.startTime);
		holder.userIds = findViewById(R.id.userIds);

		holder.typePrivate = findViewById(R.id.typePrivate);
		holder.typePublic = findViewById(R.id.typePublic);
		holder.typePrivateRecord = findViewById(R.id.typePrivateRecord);
		holder.typePublicRecord = findViewById(R.id.typePublicRecord);

		holder.cover = findViewById(R.id.cover);
		holder.video = findViewById(R.id.video);
		holder.duration = findViewById(R.id.duration);
	}

	public AppActivity context = null;

	private RecyclerView recyclerView;
	private AdapterUserGroupList adapter;

	private DialogEditLive(@NonNull Context context, SLiveTask live)
	{
		super(context, R.style.FullDialog);
		this.context = (AppActivity) context;
//		setCancelable(false);
		setContentView(R.layout.dialog_edit_live);
		initHolder();

		applyToUI(live);

		findViewById(R.id.close).setOnClickListener(this);
		findViewById(R.id.ok).setOnClickListener(this);
		findViewById(R.id.add).setOnClickListener(this);

		holder.actorId.setOnClickListener(this);
		holder.startTime.setOnClickListener(this);
		holder.userIds.setOnClickListener(this);
		holder.cover.setOnClickListener(this);
		holder.video.setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterUserGroupList(this);
		recyclerView.setAdapter(adapter);

		requestUserGroup();
	}

	@Override
	public void dismiss()
	{
		super.dismiss();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.close)
		{
			dismiss();
		}
		else if (view.getId() == R.id.ok)
		{
			ok();
		}
		else if (view.getId() == R.id.add)
		{
			DialogAlert.showInput(context, "添加用户组：", null, new DialogAlert.OnInputListener()
			{
				@Override
				public void onInput(String text)
				{
					if (!TextUtils.isEmpty(text))
					{
						SUserGroup userGroup = new SUserGroup();
						userGroup.name = text;

						SRequest_UserGroupAddModify request = new SRequest_UserGroupAddModify();
						request.userGroup = userGroup;
						Protocol.doPost(context, App.getApi(), SHandleId.UserGroupAddModify, request.saveToStr(), new Protocol.OnCallback()
						{
							@Override
							public void onResponse(int code, String data, String msg)
							{
								if (code == 200)
								{
									requestUserGroup();
								}
							}
						});
					}
				}
			});
		}
		else if (view.getId() == R.id.actorId)
		{
			context.selectUser(new BaseActivity.OnImageListener()
			{
				@Override
				public void onResult(String userStr)
				{
					SUser user = SUser.load(userStr);
					holder.actorId.setTag(user.id);
					holder.actorId.setText(user.name);
				}
			}, SUserType.Master, SUserType.Teacher);
		}
		else if (view.getId() == R.id.startTime)
		{
			DialogSelectDate.show(context, (Long) holder.startTime.getTag(), new DialogSelectDate.OnListener()
			{
				@Override
				public void onResult(long time)
				{
					holder.startTime.setTag(time);
					holder.startTime.setText(formatDate.format(new Date(time)));
				}
			});
		}
		else if (view.getId() == R.id.userIds)
		{
			List<String> userIds = (List<String>) holder.userIds.getTag();
			context.selectUsers(new BaseActivity.OnImageListener()
			{
				@Override
				public void onResult(String userStr)
				{
					bindUserIds(AppDataTool.loadStringList(JsonHelper.getJSONArray(userStr)));
				}
			}, userIds, SUserType.Student, SUserType.Master, SUserType.Teacher);
		}
		else if (view.getId() == R.id.cover)
		{
			context.selectCustomImage(new BaseActivity.OnImageListener()
			{
				@Override
				public void onResult(String filepath)
				{
					Bitmap bitmap = CommonUtils.readBitmap(filepath, 400);
					CommonUtils.saveBitmap(bitmap, Config.tmpPngFile);
					bitmap.recycle();
					OssHelper.instance().doUploadMd5FileWithProgress(OssHelper.ZjykFile, Config.tmpPngFile, OssHelper.DirLive(), new OssHelper.OnProgressListener()
					{
						@Override
						public void onProgress(long currentSize, long totalSize)
						{
							LOG.v(String.format("currentSize = %s, totalSize = %s", currentSize, totalSize));
						}

						@Override
						public void onSuccess(final String url)
						{
							Config.tmpPngFile.delete();
							holder.cover.setTag(url);
							ImageLoad.displayImage(getContext(), url, holder.cover, R.drawable.img_bad, null);
						}

						@Override
						public void onFail()
						{
							Config.tmpPngFile.delete();
							LOG.toast(context, "上传失败");
						}
					});
				}
			});
		}
		else if (view.getId() == R.id.video)
		{
			context.selectCustomVideo(new BaseActivity.OnImageListener()
			{
				@Override
				public void onResult(final String filepath)
				{
					DialogWait.show(context, "上传中。。。");
					new Handler().post(new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								MediaMetadataRetriever mmr = new MediaMetadataRetriever();
								mmr.setDataSource(filepath);
								final int duration = Integer.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
								mmr.release();
								OssHelper.instance().doUploadMd5FileWithProgress(OssHelper.ZjykFile, new File(filepath), OssHelper.DirLive(), new OssHelper.OnProgressListener()
								{
									@Override
									public void onProgress(long currentSize, long totalSize)
									{
//										LOG.v(String.format("currentSize = %s, totalSize = %s", currentSize, totalSize));
										int percent = (int) (currentSize * 100.0 / totalSize);
										DialogWait.message(String.format("上传中：%s%%（%s/%s）", percent, CommonUtils.formatSize2(currentSize), CommonUtils.formatSize2(totalSize)));
									}

									@Override
									public void onSuccess(final String url)
									{
										holder.video.setTag(url);
										holder.video.setImageResource(R.drawable.img_video_has);

										holder.duration.setTag(duration);
										holder.duration.setText(ActivityLivePlay.formatTime(duration));

										DialogWait.close();
									}

									@Override
									public void onFail()
									{
										DialogWait.close();
										LOG.toast(context, "上传视频失败");
									}
								});
							}
							catch (Exception e)
							{
								e.printStackTrace();
								DialogWait.close();
								LOG.toast(context, "上传失败");
							}
						}
					});
				}
			});
		}
	}

	public void requestUserGroup()
	{
		SRequest_UserGroupGetAll request = new SRequest_UserGroupGetAll();
		Protocol.doPost(context, App.getApi(), SHandleId.UserGroupGetAll, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_UserGroupGetAll response = SResponse_UserGroupGetAll.load(data);
					adapter.setData(response.userGroups);
				}
			}
		});
	}

	public void useUserGroup(List<String> userIds)
	{
		bindUserIds(userIds);
	}

	private static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private void bindUserIds(final List<String> userIds)
	{
		holder.userIds.setTag(userIds);
		if (userIds.size() == 0)
		{
			holder.userIds.setText("未指定");
		}
		else
		{
			holder.userIds.setText("");
			UserCacheManager.instance().getUsers(userIds, new UserCacheManager.OnResults()
			{
				@Override
				public void result(List<SUser> users)
				{
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < users.size(); i++)
					{
						if (i != 0)
							sb.append("，");
						SUser user = users.get(i);
						if (user != null)
							sb.append(user.name);
						else
							sb.append("[" + userIds.get(i) + "]");
					}
					holder.userIds.setText(sb.toString());
				}
			});
		}
	}

	private void applyToUI(SLiveTask data)
	{
		if (data == null)
			data = new SLiveTask();

		holder.id.setText(data.id);
		holder.name.setText(data.name);
		holder.des.setText(data.des);

		holder.actorId.setTag(data.actorId);
		if (TextUtils.isEmpty(data.actorId))
		{
			holder.actorId.setText("未指定");
		}
		else
		{
			holder.actorId.setText("");
			final SRequest_GetUser request = new SRequest_GetUser();
			request.userId = data.actorId;
			Protocol.doPost(getContext(), App.getApi(), SHandleId.GetUser, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_GetUser response = SResponse_GetUser.load(data);
						if (response.user != null)
						{
							holder.actorId.setText(response.user.name);
						}
						else
						{
							holder.actorId.setText(String.format("用户 %s 不存在", request.userId));
						}
					}
					else
					{
						holder.actorId.setText("加载失败");
					}
				}
			});
		}

		holder.startTime.setTag(data.startTime);
		holder.startTime.setText(formatDate.format(new Date(data.startTime)));

		bindUserIds(data.userIds);

		if (data.type == SLiveType.Private)
			holder.typePrivate.setChecked(true);
		else if (data.type == SLiveType.Public)
			holder.typePublic.setChecked(true);
		else if (data.type == SLiveType.PrivateRecord)
			holder.typePrivateRecord.setChecked(true);
		else if (data.type == SLiveType.PublicRecord)
			holder.typePublicRecord.setChecked(true);

		holder.cover.setTag(data.cover);
		if (!TextUtils.isEmpty(data.cover))
			ImageLoad.displayImage(getContext(), data.cover, holder.cover, R.drawable.img_bad, null);
		else
			holder.cover.setImageResource(R.drawable.img_default);

		holder.video.setTag(data.video);
		if (!TextUtils.isEmpty(data.video))
			holder.video.setImageResource(R.drawable.img_video_has);
		else
			holder.video.setImageResource(R.drawable.img_default);

		holder.duration.setTag(data.duration);
		holder.duration.setText(ActivityLivePlay.formatTime(data.duration));
	}

	private SLiveTask genData()
	{
		SLiveTask data = new SLiveTask();

		data.id = holder.id.getText().toString();
		data.name = holder.name.getText().toString();
		data.des = holder.des.getText().toString();

		data.actorId = (String) holder.actorId.getTag();
		data.startTime = (Long) holder.startTime.getTag();
		data.userIds = (List<String>) holder.userIds.getTag();

		if (holder.typePrivate.isChecked())
			data.type = SLiveType.Private;
		else if (holder.typePublic.isChecked())
			data.type = SLiveType.Public;
		else if (holder.typePrivateRecord.isChecked())
			data.type = SLiveType.PrivateRecord;
		else if (holder.typePublicRecord.isChecked())
			data.type = SLiveType.PublicRecord;

		data.cover = (String) holder.cover.getTag();
		data.video = (String) holder.video.getTag();

		data.duration = (Integer) holder.duration.getTag();

		return data;
	}

	private boolean check(SLiveTask live)
	{
		if (TextUtils.isEmpty(live.cover))
		{
			LOG.toast(context, "请选择封面");
			return false;
		}
		if (TextUtils.isEmpty(live.video))
		{
			LOG.toast(context, "请选择视频");
			return false;
		}
		return true;
	}

	private void ok()
	{
		SLiveTask live = genData();
		if (check(live))
		{
			dismiss();
			if (listener != null)
				listener.onResult(live);
		}
	}

	public static void show(Context context, SLiveTask live, OnResultListener listener)
	{
		DialogEditLive dialog = new DialogEditLive(context, live);
		dialog.setListener(listener);
		dialog.show();
	}

}
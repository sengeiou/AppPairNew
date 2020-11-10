package com.lys.utils;

import android.content.Context;

import com.lys.App;
import com.lys.base.utils.LOG;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.dialog.DialogWait;
import com.lys.kit.utils.Protocol;
import com.lys.message.TaskMessage;
import com.lys.protobuf.SEvent;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_AddEvent;
import com.lys.protobuf.SRequest_GetFriendList;
import com.lys.protobuf.SRequest_GetUserList;
import com.lys.protobuf.SRequest_SendTask;
import com.lys.protobuf.SResponse_AddEvent;
import com.lys.protobuf.SResponse_GetFriendList;
import com.lys.protobuf.SResponse_GetUserList;
import com.lys.protobuf.SResponse_SendTask;
import com.lys.protobuf.SUser;
import com.lys.protobuf.SUserType;

import java.util.List;

public class Helper
{
	public interface OnUserListCallback
	{
		void onResult(List<SUser> users);
	}

	private static void requestTeacherAndStudentList(final Context context, final OnUserListCallback callback)
	{
		requestUserList(context, SUserType.Teacher, new OnUserListCallback()
		{
			@Override
			public void onResult(final List<SUser> teacherList)
			{
				requestUserList(context, SUserType.Student, new OnUserListCallback()
				{
					@Override
					public void onResult(List<SUser> studentList)
					{
						teacherList.addAll(studentList);
						if (callback != null)
							callback.onResult(teacherList);
					}
				});
			}
		});
	}

	private static void requestUserList(Context context, int userType, final OnUserListCallback callback)
	{
		SRequest_GetUserList request = new SRequest_GetUserList();
		request.userType = userType;
		Protocol.doPost(context, App.getApi(), SHandleId.GetUserList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetUserList response = SResponse_GetUserList.load(data);
					if (callback != null)
						callback.onResult(response.users);
				}
			}
		});
	}

	public static void requestFriendList(Context context, String userId, final OnUserListCallback callback)
	{
		requestFriendList(context, userId, false, callback);
	}

	public static void requestFriendList(Context context, String userId, boolean checkOwnerId, final OnUserListCallback callback)
	{
		SRequest_GetFriendList request = new SRequest_GetFriendList();
		request.userId = userId;
		request.checkOwnerId = checkOwnerId;
		Protocol.doPost(context, App.getApi(), SHandleId.GetFriendList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetFriendList response = SResponse_GetFriendList.load(data);
					if (callback != null)
						callback.onResult(response.friends);
				}
			}
		});
	}

	//-----------------------------------

	private static void sendTask(final Context context, final List<SUser> users, final int index, final String taskId, final String text, final String sendUserId)
	{
		if (index < users.size())
		{
			DialogWait.message(String.format("正在发送（%s/%s）", index + 1, users.size()));
			SRequest_SendTask request = new SRequest_SendTask();
			request.userIds.add(users.get(index).id);
			request.taskIds.add(taskId);
			request.text = text;
			request.sendUserId = sendUserId;
			Protocol.doPost(context, App.getApi(), SHandleId.SendTask, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_SendTask response = SResponse_SendTask.load(data);
						TaskMessage.sendTasks(response.tasks);
						sendTask(context, users, index + 1, taskId, text, sendUserId);
					}
					else
					{
						DialogWait.close();
						DialogAlert.show(context, "", String.format("发送失败，已发送（%s/%s）", index, users.size()), null);
					}
				}
			});
		}
		else
		{
			DialogWait.close();
			LOG.toast(context, "发送成功");
		}
	}

	public static void sendTask(Context context, List<SUser> users, String taskId, String text, String sendUserId)
	{
		if (users.size() > 0)
		{
			DialogWait.show(context, "发送中。。。");
			sendTask(context, users, 0, taskId, text, sendUserId);
		}
	}

	//-----------------------------------

	public static void addEvent(Context context, String userId, String action, String target, String des)
	{
		SRequest_AddEvent request = new SRequest_AddEvent();
		request.event = new SEvent();
		request.event.userId = userId;
		request.event.action = action;
		request.event.target = target;
		request.event.des = des;
		Protocol.doPost(context.getApplicationContext(), App.getApi(), SHandleId.AddEvent, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_AddEvent response = SResponse_AddEvent.load(data);
				}
			}
		});
	}

	//-----------------------------------

//	public interface OnCreateTaskListener
//	{
//		void onResult(String group, String name);
//	}
//
//	public static void createTask(Context context, String title, String groupText, String nameText, final OnCreateTaskListener listener)
//	{
//		final View view = LayoutInflater.from(context).inflate(R.layout.dialog_create_task, null);
//		final EditText group = view.findViewById(R.id.group);
//		final EditText name = view.findViewById(R.id.name);
//		group.setText(groupText);
//		name.setText(nameText);
//		AlertDialog.Builder builder = new AlertDialog.Builder(context);
//		builder.setTitle(title);
//		builder.setView(view);
//		builder.setNeutralButton("取消", null);
//		builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
//		{
//			@Override
//			public void onClick(DialogInterface dialogInterface, int which)
//			{
//				if (listener != null)
//					listener.onResult(group.getText().toString(), name.getText().toString());
//			}
//		});
//		builder.show();
//	}

}

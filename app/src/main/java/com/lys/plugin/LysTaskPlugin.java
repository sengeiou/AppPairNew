package com.lys.plugin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.lys.App;
import com.lys.app.R;
import com.lys.dialog.DialogSelectTask;
import com.lys.fragment.LysConversationFragment;
import com.lys.kit.utils.Protocol;
import com.lys.message.TaskMessage;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SRequest_SendTask;
import com.lys.protobuf.SResponse_SendTask;

import java.util.List;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.IPluginRequestPermissionResultCallback;
import io.rong.imlib.model.Conversation;

public class LysTaskPlugin implements IPluginModule, IPluginRequestPermissionResultCallback
{
	private Conversation.ConversationType conversationType;
	private String targetId;
	private LysConversationFragment fragment;
	private RongExtension extension;

	@Override
	public Drawable obtainDrawable(Context context)
	{
		return ContextCompat.getDrawable(context, R.drawable.plugin_task_selector);
	}

	@Override
	public String obtainTitle(Context context)
	{
		return "任务";
	}

	@Override
	public void onClick(Fragment fragment, RongExtension extension)
	{
		this.conversationType = extension.getConversationType();
		this.targetId = extension.getTargetId();
		this.fragment = (LysConversationFragment) fragment;
		this.extension = extension;
		final Context context = fragment.getContext();
		DialogSelectTask.show(context, App.userId(), new DialogSelectTask.OnListener()
		{
			@Override
			public void onSelect(List<SPTask> selectedList, String taskText)
			{
				if (selectedList.size() > 0)
				{
					SRequest_SendTask request = new SRequest_SendTask();
					request.userIds.add(targetId);
					for (SPTask task : selectedList)
						request.taskIds.add(task.id);
					request.text = taskText;
					Protocol.doPost(context, App.getApi(), SHandleId.SendTask, request.saveToStr(), new Protocol.OnCallback()
					{
						@Override
						public void onResponse(int code, String data, String msg)
						{
							if (code == 200)
							{
								SResponse_SendTask response = SResponse_SendTask.load(data);
								TaskMessage.sendTasks(response.tasks);
							}
						}
					});
				}
			}
		});
	}

	@Override
	public void onActivityResult(int i, int i1, Intent intent)
	{

	}

	@Override
	public boolean onRequestPermissionResult(Fragment fragment, RongExtension rongExtension, int i, @NonNull String[] strings, @NonNull int[] ints)
	{
		return false;
	}
}

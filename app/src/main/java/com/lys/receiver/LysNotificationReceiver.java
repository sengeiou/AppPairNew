package com.lys.receiver;

import android.content.Context;
import android.content.Intent;

import com.lys.activity.ActivityMain;
import com.lys.base.utils.LOG;

import io.rong.push.PushType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

public class LysNotificationReceiver extends PushMessageReceiver
{
	private void showMessage(PushType pushType, PushNotificationMessage message)
	{
		LOG.v("pushType = " + pushType);
		LOG.v("getPushId = " + message.getPushId());
		LOG.v("getConversationType = " + message.getConversationType());
		LOG.v("getTargetId = " + message.getTargetId());
		LOG.v("getTargetUserName = " + message.getTargetUserName());
		LOG.v("getToId = " + message.getToId());
		LOG.v("getReceivedTime = " + message.getReceivedTime());
		LOG.v("getObjectName = " + message.getObjectName());
		LOG.v("getSenderId = " + message.getSenderId());
		LOG.v("getSenderName = " + message.getSenderName());
		LOG.v("getSenderPortrait = " + message.getSenderPortrait());
		LOG.v("getPushTitle = " + message.getPushTitle());
		LOG.v("getPushContent = " + message.getPushContent());
		LOG.v("getPushData = " + message.getPushData());
		LOG.v("getExtra = " + message.getExtra());
		LOG.v("getPushFlag = " + message.getPushFlag());
		LOG.v("getSourceType = " + message.getSourceType());
	}

	@Override
	public boolean onNotificationMessageArrived(Context context, PushType pushType, PushNotificationMessage message)
	{
		LOG.v("Arrived:" + message);
		showMessage(pushType, message);
		return false;
	}

	@Override
	public boolean onNotificationMessageClicked(Context context, PushType pushType, PushNotificationMessage message)
	{
		LOG.v("Clicked:" + message);
		showMessage(pushType, message);
		Intent intent = new Intent(context, ActivityMain.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
		return false;
	}

	@Override
	public void onThirdPartyPushState(PushType pushType, String action, long resultCode)
	{
		super.onThirdPartyPushState(pushType, action, resultCode);
	}
}

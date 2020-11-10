package com.lys.provider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.TextMessageItemProvider;
import io.rong.message.TextMessage;

@ProviderTag(messageContent = TextMessage.class, showReadState = true, showSummaryWithName = false)
public class LysTextMessageItemProvider extends TextMessageItemProvider
{
	@Override
	public View newView(Context context, ViewGroup group)
	{
		return super.newView(context, group);
	}

	@Override
	public void onItemClick(View view, int position, TextMessage textMessage, UIMessage message)
	{
		super.onItemClick(view, position, textMessage, message);
	}

	@Override
	public void bindView(View view, int position, TextMessage textMessage, UIMessage message)
	{
		super.bindView(view, position, textMessage, message);
//		if (message.getConversationType().equals(Conversation.ConversationType.SYSTEM))
//		{
//		}
	}
}

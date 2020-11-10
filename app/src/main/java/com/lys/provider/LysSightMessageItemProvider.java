package com.lys.provider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.SightMessageItemProvider;
import io.rong.message.SightMessage;

@ProviderTag(messageContent = SightMessage.class, showProgress = false, showReadState = true)
public class LysSightMessageItemProvider extends SightMessageItemProvider
{
	@Override
	public View newView(Context context, ViewGroup group)
	{
		return super.newView(context, group);
	}

	@Override
	public void onItemClick(View view, int position, SightMessage sightMessage, UIMessage uiMessage)
	{
		super.onItemClick(view, position, sightMessage, uiMessage);
	}

	@Override
	public void bindView(View view, int position, SightMessage sightMessage, UIMessage uiMessage)
	{
		super.bindView(view, position, sightMessage, uiMessage);
	}
}

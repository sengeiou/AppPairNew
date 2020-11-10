package com.lys.provider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.VoiceMessageItemProvider;
import io.rong.message.VoiceMessage;

@ProviderTag(messageContent = VoiceMessage.class, showReadState = true)
public class LysVoiceMessageItemProvider extends VoiceMessageItemProvider
{
	public LysVoiceMessageItemProvider(Context context)
	{
		super(context);
	}

	@Override
	public View newView(Context context, ViewGroup group)
	{
		return super.newView(context, group);
	}

	@Override
	public void onItemClick(View view, int position, VoiceMessage voiceMessage, UIMessage message)
	{
		super.onItemClick(view, position, voiceMessage, message);
	}

	@Override
	public void bindView(View view, int position, VoiceMessage voiceMessage, UIMessage message)
	{
		super.bindView(view, position, voiceMessage, message);
	}
}

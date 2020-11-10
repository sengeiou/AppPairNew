package com.lys.provider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.FileMessageItemProvider;
import io.rong.message.FileMessage;

@ProviderTag(messageContent = FileMessage.class, showProgress = false, showReadState = true)
public class LysFileMessageItemProvider extends FileMessageItemProvider
{
	@Override
	public View newView(Context context, ViewGroup group)
	{
		return super.newView(context, group);
	}

	@Override
	public void onItemClick(View view, int position, FileMessage fileMessage, UIMessage message)
	{
		super.onItemClick(view, position, fileMessage, message);
	}

	@Override
	public void bindView(View view, int position, FileMessage fileMessage, UIMessage message)
	{
		super.bindView(view, position, fileMessage, message);
	}
}

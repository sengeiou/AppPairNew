package com.lys.provider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.sticker.message.StickerMessage;
import io.rong.sticker.message.StickerMessageItemProvider;

@ProviderTag(messageContent = StickerMessage.class, showReadState = true)
public class LysStickerMessageItemProvider extends StickerMessageItemProvider
{
	@Override
	public View newView(Context context, ViewGroup group)
	{
		return super.newView(context, group);
	}

	@Override
	public void onItemClick(View view, int position, StickerMessage stickerMessage, UIMessage message)
	{
		super.onItemClick(view, position, stickerMessage, message);
	}

	@Override
	public void bindView(View view, int position, StickerMessage stickerMessage, UIMessage message)
	{
		super.bindView(view, position, stickerMessage, message);
	}
}

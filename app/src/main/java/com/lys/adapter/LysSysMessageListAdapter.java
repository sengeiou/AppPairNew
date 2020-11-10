package com.lys.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.adapter.MessageListAdapter;

public class LysSysMessageListAdapter extends MessageListAdapter
{
	public LysSysMessageListAdapter(Context context)
	{
		super(context);
	}

	@Override
	protected View newView(Context context, int position, ViewGroup group)
	{
		View view = super.newView(context, position, group);
		MessageListAdapter.ViewHolder holder = (MessageListAdapter.ViewHolder) view.getTag();
		return view;
	}

	@Override
	protected void bindView(View view, int position, UIMessage data)
	{
		super.bindView(view, position, data);
		MessageListAdapter.ViewHolder holder = (MessageListAdapter.ViewHolder) view.getTag();
		holder.leftIconView.setVisibility(View.GONE);
		holder.rightIconView.setVisibility(View.GONE);
	}
}
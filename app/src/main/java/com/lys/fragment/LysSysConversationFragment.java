package com.lys.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lys.adapter.LysSysMessageListAdapter;
import com.lys.app.R;

import java.util.LinkedHashMap;

import io.rong.imkit.RongExtension;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.widget.AutoRefreshListView;
import io.rong.imkit.widget.adapter.MessageListAdapter;

public class LysSysConversationFragment extends ConversationFragment
{
	private RongExtension mRongExtension;
	private AutoRefreshListView mList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);

		mRongExtension = view.findViewById(R.id.rc_extension);
		mRongExtension.setVisibility(View.GONE);

		mList = view.findViewById(R.id.rc_layout_msg_list).findViewById(R.id.rc_list);
		mList.setVerticalScrollBarEnabled(false);
		mList.setOverScrollMode(View.OVER_SCROLL_NEVER);

		return view;
	}

	@Override
	public void onImageResult(LinkedHashMap<String, Integer> selectedMedias, boolean origin)
	{
		super.onImageResult(selectedMedias, origin);
	}

	@Override
	public MessageListAdapter onResolveAdapter(Context context)
	{
		return new LysSysMessageListAdapter(context);
	}
}

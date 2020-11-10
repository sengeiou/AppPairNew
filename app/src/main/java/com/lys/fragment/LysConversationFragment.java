package com.lys.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.lys.App;
import com.lys.adapter.LysMessageListAdapter;
import com.lys.app.R;
import com.lys.kit.utils.InvokeHelper;

import java.util.LinkedHashMap;

import io.rong.imkit.LysHackyViewPager;
import io.rong.imkit.RongExtension;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.widget.AutoRefreshListView;
import io.rong.imkit.widget.adapter.MessageListAdapter;

public class LysConversationFragment extends ConversationFragment
{
	private RongExtension mRongExtension;
	private ImageView mPluginToggle;

	private AutoRefreshListView mList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);

		mRongExtension = view.findViewById(R.id.rc_extension);
		mPluginToggle = mRongExtension.findViewById(R.id.rc_plugin_toggle);
		mPluginToggle.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				InvokeHelper.invokeMethod(RongExtension.class, mRongExtension, "setPluginBoard", new Class[]{}, new Object[]{});

				ViewGroup mPluginPager = mRongExtension.findViewById(R.id.my_ext_plugin_pager);
				ViewGroup.LayoutParams layoutParams = mPluginPager.getLayoutParams();
				layoutParams.height = 300;
				mPluginPager.setLayoutParams(layoutParams);

				LysHackyViewPager viewPager = mPluginPager.findViewById(R.id.rc_view_pager);
				viewPager.setOnAddViewListener(new LysHackyViewPager.OnAddViewListener()
				{
					@Override
					public void onAddView(View child)
					{
						if (child instanceof GridView)
						{
							GridView gridView = (GridView) child;
							if (!App.isStudent())
								gridView.setNumColumns(8);
							else
								gridView.setNumColumns(6);
						}
					}
				});
			}
		});

		mList = view.findViewById(R.id.rc_layout_msg_list).findViewById(R.id.rc_list);
		mList.setBackgroundColor(0xfff3f3f3);

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
		return new LysMessageListAdapter(context);
	}
}

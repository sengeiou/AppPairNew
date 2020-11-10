package com.lys.kit.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lys.base.fragment.BaseFragment;
import com.lys.base.utils.HttpUtils;
import com.lys.base.utils.JsonHelper;
import com.lys.base.utils.LOG;
import com.lys.kit.R;
import com.lys.kit.activity.ActivitySelectNetImage;
import com.lys.kit.adapter.AdapterImageNet;
import com.lys.protobuf.SNetPicInfo;

import java.util.ArrayList;
import java.util.List;

public class FragmentSelectImageNet extends BaseFragment implements View.OnClickListener
{
	private class Holder
	{
//		private ViewGroup menuBar;
	}

	private Holder holder = new Holder();

	private void initHolder(View view)
	{
//		holder.menuBar = view.findViewById(R.id.menuBar);
	}

	private View view = null;
	private String root;
	private String[] types;

	private RecyclerView recyclerView;
	private AdapterImageNet adapter;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		if (view == null)
		{
			view = inflater.inflate(R.layout.fragment_select_image_net, container, false);
			initHolder(view);

			root = getArguments().getString("root");
			types = getArguments().getString("types").split(";");

			recyclerView = view.findViewById(R.id.recyclerView);
			recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
//			recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
			adapter = new AdapterImageNet(this);
			recyclerView.setAdapter(adapter);

			request();
		}
		return view;
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
	}

	@Override
	public void onClick(View view)
	{
	}

	private boolean filterType(SNetPicInfo netPic)
	{
		for (String type : types)
		{
			if (netPic.type.toLowerCase().equals(type.toLowerCase()))
				return true;
		}
		return false;
	}

	private void request()
	{
		HttpUtils.doHttpGet(context, root, new HttpUtils.OnCallback()
		{
			@Override
			public void onResponse(String text)
			{
				if (!TextUtils.isEmpty(text))
				{
					List<SNetPicInfo> netPics = SNetPicInfo.loadList(JsonHelper.getJSONArray(text));
					List<SNetPicInfo> netPicsFilter = new ArrayList<>();
					for (SNetPicInfo netPic : netPics)
					{
						if (filterType(netPic))
							netPicsFilter.add(netPic);
					}
					adapter.setData(netPicsFilter);
				}
				else
				{
					LOG.toast(context, "获取插图失败");
				}
			}
		});
	}

	public void select(SNetPicInfo netPic)
	{
		ActivitySelectNetImage activity = (ActivitySelectNetImage) getBaseActivity();
		activity.selectNetPic(netPic);
	}
}

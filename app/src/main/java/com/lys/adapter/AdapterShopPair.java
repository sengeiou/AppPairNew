package com.lys.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lys.App;
import com.lys.activity.ActivityShopPair;
import com.lys.app.R;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SMatter;
import com.lys.protobuf.SRequest_SwapMatter;

import java.util.ArrayList;
import java.util.List;

public class AdapterShopPair extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	private ActivityShopPair owner = null;

	private List<List<SMatter>> matterGroups = new ArrayList<>();

	public AdapterShopPair(ActivityShopPair owner)
	{
		this.owner = owner;
	}

	public void setData(List<SMatter> matters)
	{
		matterGroups.clear();
		for (SMatter matter : matters)
		{
			if (matterGroups.size() == 0)
			{
				matterGroups.add(new ArrayList<SMatter>());
			}
			List<SMatter> lastGroup = matterGroups.get(matterGroups.size() - 1);
			if (lastGroup.size() < 4)
			{
				lastGroup.add(matter);
			}
			else
			{
				matterGroups.add(new ArrayList<SMatter>());
				lastGroup = matterGroups.get(matterGroups.size() - 1);
				lastGroup.add(matter);
			}
		}
		notifyDataSetChanged();
	}

	public SMatter getLast()
	{
		if (matterGroups.size() > 0)
		{
			List<SMatter> lastGroup = matterGroups.get(matterGroups.size() - 1);
			return lastGroup.get(lastGroup.size() - 1);
		}
		else
		{
			return null;
		}
	}

	private SMatter findPrev(String matterId)
	{
		SMatter prev = null;
		for (List<SMatter> matterGroup : matterGroups)
		{
			for (SMatter matter : matterGroup)
			{
				if (matter.id.equals(matterId))
					return prev;
				else
					prev = matter;
			}
		}
		return null;
	}

	private SMatter findNext(String matterId)
	{
		boolean findIt = false;
		for (List<SMatter> matterGroup : matterGroups)
		{
			for (SMatter matter : matterGroup)
			{
				if (findIt)
					return matter;
				if (matter.id.equals(matterId))
					findIt = true;
			}
		}
		return null;
	}

	private void swap(Context context, SMatter matter1, SMatter matter2)
	{
		SRequest_SwapMatter request = new SRequest_SwapMatter();
		request.matter1 = matter1;
		request.matter2 = matter2;
		Protocol.doPost(context, App.getApi(), SHandleId.SwapMatter, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					owner.request();
				}
			}
		});
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		switch (viewType)
		{
		case AdapterShopHome.Type_MatterGroupPair:
			return new AdapterShopHome.HolderMatterGroupPair(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matter_group_pair, parent, false));
		}
		return null;
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position)
	{
		final Context context = viewHolder.itemView.getContext();
		if (getItemViewType(position) == AdapterShopHome.Type_MatterGroupPair)
		{
			AdapterShopHome.HolderMatterGroupPair holderGroup = (AdapterShopHome.HolderMatterGroupPair) viewHolder;
			List<SMatter> matterGroup = matterGroups.get(position);
			holderGroup.bindView(context, matterGroup, false, new AdapterShopHome.OnMatterOperateListener()
			{
				@Override
				public void onModify()
				{
					owner.request();
				}

				@Override
				public void onMoveUp(SMatter matter)
				{
					SMatter prev = findPrev(matter.id);
					if (prev != null)
						swap(context, matter, prev);
				}

				@Override
				public void onMoveDown(SMatter matter)
				{
					SMatter next = findNext(matter.id);
					if (next != null)
						swap(context, matter, next);
				}

				@Override
				public void onDelete()
				{
					owner.request();
				}
			});
		}
	}

	@Override
	public int getItemCount()
	{
		return matterGroups.size();
	}

	@Override
	public int getItemViewType(int position)
	{
		return AdapterShopHome.Type_MatterGroupPair;
	}

}
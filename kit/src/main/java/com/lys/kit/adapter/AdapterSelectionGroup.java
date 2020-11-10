package com.lys.kit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lys.kit.R;
import com.lys.kit.activity.ActivitySelectSelectionGroup;
import com.lys.kit.view.SelectionGroup;
import com.lys.protobuf.SSelectionGroup;

import java.util.List;

public class AdapterSelectionGroup extends RecyclerView.Adapter<AdapterSelectionGroup.Holder>
{
	private ActivitySelectSelectionGroup owner = null;
	private List<SSelectionGroup> selectionGroupList = null;

	public AdapterSelectionGroup(ActivitySelectSelectionGroup owner)
	{
		this.owner = owner;
	}

	public void setData(List<SSelectionGroup> selectionGroupList)
	{
		this.selectionGroupList = selectionGroupList;
		notifyDataSetChanged();
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selection_group, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, final int position)
	{
		final SSelectionGroup selectionGroup = selectionGroupList.get(position);
		final Context context = holder.itemView.getContext();

		holder.selectionGroup.lockSelections();
		holder.selectionGroup.setSelectionGroup(selectionGroup);
		holder.selectionGroup.updateSelections(true);

		holder.selectionGroup.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				owner.select(selectionGroup);
			}
		});
	}

	@Override
	public int getItemCount()
	{
		if (selectionGroupList != null)
			return selectionGroupList.size();
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public SelectionGroup selectionGroup;

		public Holder(View itemView)
		{
			super(itemView);
			selectionGroup = itemView.findViewById(R.id.selectionGroup);
		}
	}
}
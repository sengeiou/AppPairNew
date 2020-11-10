package com.lys.kit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lys.kit.R;
import com.lys.protobuf.SProblemDiff;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdapterTopicFilterDiff extends RecyclerView.Adapter<AdapterTopicFilterDiff.Holder>
{
	//	private ActivityTopicFilter owner = null;
	private List<SProblemDiff> diffs = null;
	public Map<Integer, SProblemDiff> selectedMap = null;

//	public AdapterTopicFilterDiff(ActivityTopicFilter owner)
//	{
//		this.owner = owner;
//	}

	public void setData(List<SProblemDiff> diffs, Map<Integer, SProblemDiff> selectedMap)
	{
		this.diffs = diffs;
		this.selectedMap = selectedMap;
		notifyDataSetChanged();
	}

	public boolean isReady()
	{
		return diffs != null;
	}

	public List<Integer> getSelectedDiffs()
	{
		List<Integer> diffs = new ArrayList<>();
		for (SProblemDiff diff : selectedMap.values())
		{
			diffs.add(diff.diff);
		}
		return diffs;
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_filter_diff, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, final int position)
	{
		final SProblemDiff diff = diffs.get(position);
		final Context context = holder.itemView.getContext();

		if (selectedMap.containsKey(diff.diff))
			holder.selectStates.setImageResource(R.drawable.img_select_yes);
		else
			holder.selectStates.setImageResource(R.drawable.img_select_no);

		holder.name.setText(diff.name);

		holder.con.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (selectedMap.containsKey(diff.diff))
					selectedMap.remove(diff.diff);
				else
					selectedMap.put(diff.diff, diff);

				if (selectedMap.containsKey(diff.diff))
					holder.selectStates.setImageResource(R.drawable.img_select_yes);
				else
					holder.selectStates.setImageResource(R.drawable.img_select_no);
			}
		});

	}

	@Override
	public int getItemCount()
	{
		if (diffs != null)
			return diffs.size();
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ViewGroup con;
		public ImageView selectStates;
		public TextView name;

		public Holder(View itemView)
		{
			super(itemView);
			con = itemView.findViewById(R.id.con);
			selectStates = itemView.findViewById(R.id.selectStates);
			name = itemView.findViewById(R.id.name);
		}
	}
}
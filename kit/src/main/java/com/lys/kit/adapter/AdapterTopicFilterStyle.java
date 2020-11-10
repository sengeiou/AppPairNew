package com.lys.kit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lys.kit.R;
import com.lys.protobuf.SProblemStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdapterTopicFilterStyle extends RecyclerView.Adapter<AdapterTopicFilterStyle.Holder>
{
	//	private ActivityTopicFilter owner = null;
	private List<SProblemStyle> styles = null;
	public Map<String, SProblemStyle> selectedMap = null;

//	public AdapterTopicFilterStyle(ActivityTopicFilter owner)
//	{
//		this.owner = owner;
//	}

	public void setData(List<SProblemStyle> styles, Map<String, SProblemStyle> selectedMap)
	{
		this.styles = styles;
		this.selectedMap = selectedMap;
		notifyDataSetChanged();
	}

	public boolean isReady()
	{
		return styles != null;
	}

	public List<String> getSelectedStyles()
	{
		List<String> styles = new ArrayList<>();
		for (SProblemStyle style : selectedMap.values())
		{
			styles.add(style.name);
		}
		return styles;
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_filter_style, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, final int position)
	{
		final SProblemStyle style = styles.get(position);
		final Context context = holder.itemView.getContext();

		if (selectedMap.containsKey(style.name))
			holder.selectStates.setImageResource(R.drawable.img_select_yes);
		else
			holder.selectStates.setImageResource(R.drawable.img_select_no);

		holder.name.setText(style.name);

		holder.con.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (selectedMap.containsKey(style.name))
					selectedMap.remove(style.name);
				else
					selectedMap.put(style.name, style);

				if (selectedMap.containsKey(style.name))
					holder.selectStates.setImageResource(R.drawable.img_select_yes);
				else
					holder.selectStates.setImageResource(R.drawable.img_select_no);
			}
		});

	}

	@Override
	public int getItemCount()
	{
		if (styles != null)
			return styles.size();
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
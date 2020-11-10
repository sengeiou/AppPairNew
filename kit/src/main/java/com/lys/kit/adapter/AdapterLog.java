package com.lys.kit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lys.kit.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterLog extends RecyclerView.Adapter<AdapterLog.Holder>
{
	private List<String> msgs = new ArrayList<>();

	public void addData(String msg)
	{
		this.msgs.add(msg);
//		notifyDataSetChanged();
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, final int position)
	{
		final String msg = msgs.get(position);
		final Context context = holder.itemView.getContext();
		holder.text.setText(msg);
	}

	@Override
	public int getItemCount()
	{
		if (msgs != null)
			return msgs.size();
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public TextView text;

		public Holder(View itemView)
		{
			super(itemView);
			text = itemView.findViewById(R.id.text);
		}
	}
}
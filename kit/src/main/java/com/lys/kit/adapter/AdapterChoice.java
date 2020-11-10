package com.lys.kit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lys.kit.R;
import com.lys.kit.pop.PopChoice;
import com.lys.protobuf.SChoiceItem;

import java.util.List;

public class AdapterChoice extends RecyclerView.Adapter<AdapterChoice.Holder>
{
	private PopChoice popPage = null;
	private List<SChoiceItem> choiceItems = null;

	public AdapterChoice(PopChoice popPage)
	{
		this.popPage = popPage;
	}

	public void setData(List<SChoiceItem> choiceItems)
	{
		this.choiceItems = choiceItems;
		notifyDataSetChanged();
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choice, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, int position)
	{
		final SChoiceItem choiceItem = choiceItems.get(position);
		final Context context = holder.itemView.getContext();

		holder.name.setText(choiceItem.name);
		holder.number.setText(String.valueOf(choiceItem.number));

		holder.con.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				popPage.select(choiceItem);
			}
		});
	}

	@Override
	public int getItemCount()
	{
		if (choiceItems != null)
			return choiceItems.size();
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ViewGroup con;
		public TextView name;
		public TextView number;

		public Holder(View itemView)
		{
			super(itemView);
			con = itemView.findViewById(R.id.con);
			name = itemView.findViewById(R.id.name);
			number = itemView.findViewById(R.id.number);
		}
	}
}
package com.lys.kit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lys.kit.R;
import com.lys.kit.pop.PopChoiceChapter;
import com.lys.protobuf.SMaterial;

import java.util.List;

public class AdapterChoiceMaterial extends RecyclerView.Adapter<AdapterChoiceMaterial.Holder>
{
	private PopChoiceChapter popPage = null;
	private List<SMaterial> materials = null;
	private int selectIndex = -1;

	public AdapterChoiceMaterial(PopChoiceChapter popPage)
	{
		this.popPage = popPage;
	}

	public void setData(List<SMaterial> materials)
	{
		this.materials = materials;
		notifyDataSetChanged();
	}

	public void setSelectIndex(int selectIndex)
	{
		this.selectIndex = selectIndex;
		notifyDataSetChanged();
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choice_material, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, final int position)
	{
		final SMaterial material = materials.get(position);
		final Context context = holder.itemView.getContext();

		if (selectIndex == position)
		{
			holder.itemView.setBackgroundColor(0xff1f2123);
			holder.name.setTextColor(0xffffbd01);
		}
		else
		{
			holder.itemView.setBackgroundColor(0xff343537);
			holder.name.setTextColor(0xffffffff);
		}

		holder.name.setText(material.name);

		holder.con.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				popPage.select(position);
			}
		});

	}

	@Override
	public int getItemCount()
	{
		if (materials != null)
			return materials.size();
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ViewGroup con;
		public TextView name;

		public Holder(View itemView)
		{
			super(itemView);
			con = itemView.findViewById(R.id.con);
			name = itemView.findViewById(R.id.name);
		}
	}
}
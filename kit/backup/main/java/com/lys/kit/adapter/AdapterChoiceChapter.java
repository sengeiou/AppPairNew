package com.lys.kit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lys.kit.R;
import com.lys.kit.pop.PopChoiceChapter;
import com.lys.protobuf.SChapter;

import java.util.List;

public class AdapterChoiceChapter extends RecyclerView.Adapter<AdapterChoiceChapter.Holder>
{
	private PopChoiceChapter popPage = null;
	private List<SChapter> chapters = null;

	public AdapterChoiceChapter(PopChoiceChapter popPage)
	{
		this.popPage = popPage;
	}

	public void setData(List<SChapter> chapters)
	{
		this.chapters = chapters;
		notifyDataSetChanged();
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choice_chapter, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, int position)
	{
		final SChapter chapter = chapters.get(position);
		final Context context = holder.itemView.getContext();

		if (chapter.level == 0)
		{
			holder.name.setTextSize(15.33f);
		}
		else
		{
			holder.name.setTextSize(14f);
		}

		holder.name.setText(getIndentStr(chapter.level) + chapter.unit);
		holder.id.setText(chapter.id);

		holder.con.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				popPage.selectChapter(chapter);
			}
		});
	}

	@Override
	public int getItemCount()
	{
		if (chapters != null)
			return chapters.size();
		else
			return 0;
	}

	private String getIndentStr(int level)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < level; i++)
		{
			sb.append("      ");
		}
		return sb.toString();
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ViewGroup con;
		public TextView name;
		public TextView id;

		public Holder(View itemView)
		{
			super(itemView);
			con = itemView.findViewById(R.id.con);
			name = itemView.findViewById(R.id.name);
			id = itemView.findViewById(R.id.id);
		}
	}
}
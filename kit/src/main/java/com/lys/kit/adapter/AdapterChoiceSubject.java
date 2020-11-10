package com.lys.kit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lys.kit.R;
import com.lys.kit.config.Config;
import com.lys.kit.pop.PopChoiceSubject;
import com.lys.protobuf.SSubjectCount;

import java.util.List;

public class AdapterChoiceSubject extends RecyclerView.Adapter<AdapterChoiceSubject.Holder>
{
	private PopChoiceSubject popPage = null;
	private List<SSubjectCount> subjectCounts = null;

	public AdapterChoiceSubject(PopChoiceSubject popPage)
	{
		this.popPage = popPage;
	}

	public void setData(List<SSubjectCount> subjectCounts)
	{
		this.subjectCounts = subjectCounts;
		notifyDataSetChanged();
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choice_subject, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, int position)
	{
		final SSubjectCount subjectCount = subjectCounts.get(position);
		final Context context = holder.itemView.getContext();

		holder.name.setText(Config.getSubjectName(subjectCount.subject));
		holder.number.setText(String.valueOf(subjectCount.number));

		holder.con.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				popPage.select(subjectCount);
			}
		});

	}

	@Override
	public int getItemCount()
	{
		if (subjectCounts != null)
			return subjectCounts.size();
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
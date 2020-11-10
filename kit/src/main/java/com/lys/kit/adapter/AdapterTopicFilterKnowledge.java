package com.lys.kit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lys.kit.R;
import com.lys.kit.utils.KnowledgeTreeUtils;
import com.lys.protobuf.SKnowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterTopicFilterKnowledge extends RecyclerView.Adapter<AdapterTopicFilterKnowledge.Holder>
{
	//	private ActivityTopicFilter page = null;
	private List<SKnowledge> knowledges = null;

//	public AdapterTopicFilterKnowledge(ActivityTopicFilter page)
//	{
//		this.page = page;
//	}

	public void setData(List<SKnowledge> knowledges, List<SKnowledge> selectKnowledges)
	{
		this.knowledges = knowledges;
		updateData(selectKnowledges);
	}

	public void updateData(List<SKnowledge> selectKnowledges)
	{
		if (knowledges != null)
		{
			KnowledgeTreeUtils.checkKnowledges(knowledges);
			if (selectKnowledges.size() > 0)
			{
				Map<String, SKnowledge> knowledgeMap = new HashMap<>();
				for (SKnowledge knowledge : selectKnowledges)
				{
					knowledgeMap.put(knowledge.code, knowledge);
				}
				KnowledgeTreeUtils.checkState(knowledges, knowledgeMap);
			}
		}
		notifyDataSetChanged();
	}

	public void reset()
	{
		if (knowledges != null)
		{
			KnowledgeTreeUtils.checkKnowledges(knowledges);
			notifyDataSetChanged();
		}
	}

	public boolean isReady()
	{
		return knowledges != null;
	}

	private void getSelectedKnowledges(List<SKnowledge> nodes, List<SKnowledge> selectedKnowledges)
	{
		for (SKnowledge knowledge : nodes)
		{
			if (KnowledgeTreeUtils.isLeaf(knowledge) && knowledge.state == KnowledgeTreeUtils.StateYes)
				selectedKnowledges.add(knowledge);
			getSelectedKnowledges(knowledge.nodes, selectedKnowledges);
		}
	}

	public List<SKnowledge> getSelectedKnowledges()
	{
		List<SKnowledge> selectedKnowledges = new ArrayList<>();
		getSelectedKnowledges(knowledges, selectedKnowledges);
		return selectedKnowledges;
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_filter_knowledge, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, int position)
	{
		final SKnowledge knowledge = KnowledgeTreeUtils.getShowNode(knowledges, position);
		final Context context = holder.itemView.getContext();

		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.oper.getLayoutParams();
		layoutParams.leftMargin = 70 + KnowledgeTreeUtils.getLevel(knowledge) * 40;
		holder.oper.setLayoutParams(layoutParams);

		holder.name.setText(knowledge.name);
		holder.count.setText(String.valueOf(knowledge.topicCount));

		View.OnClickListener selectClick = new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				switch (knowledge.state)
				{
				case KnowledgeTreeUtils.StateNo:
					KnowledgeTreeUtils.setState(knowledge, KnowledgeTreeUtils.StateYes);
					break;
				case KnowledgeTreeUtils.StateHalf:
					KnowledgeTreeUtils.setState(knowledge, KnowledgeTreeUtils.StateYes);
					break;
				case KnowledgeTreeUtils.StateYes:
					KnowledgeTreeUtils.setState(knowledge, KnowledgeTreeUtils.StateNo);
					break;
				}
				KnowledgeTreeUtils.checkParentState(knowledge);
				notifyDataSetChanged();
			}
		};

		if (KnowledgeTreeUtils.isLeaf(knowledge))
		{
			holder.count.setVisibility(View.VISIBLE);
//			holder.oper.setImageResource(R.drawable.img_tree_point);
			holder.oper.setImageDrawable(null);
			holder.itemView.setOnClickListener(selectClick);
		}
		else
		{
			holder.count.setVisibility(View.GONE);
			if (knowledge.isOpen)
				holder.oper.setImageResource(R.drawable.img_tree_sub);
			else
				holder.oper.setImageResource(R.drawable.img_tree_add);
			holder.itemView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					knowledge.isOpen = !knowledge.isOpen;
					notifyDataSetChanged();
				}
			});
		}
		holder.check.setOnClickListener(selectClick);

		switch (knowledge.state)
		{
		case KnowledgeTreeUtils.StateNo:
			holder.check.setImageResource(R.drawable.img_select_no);
			break;
		case KnowledgeTreeUtils.StateHalf:
			holder.check.setImageResource(R.drawable.img_select_half);
			break;
		case KnowledgeTreeUtils.StateYes:
			holder.check.setImageResource(R.drawable.img_select_yes);
			break;
		}
	}

	@Override
	public int getItemCount()
	{
		if (knowledges != null)
			return KnowledgeTreeUtils.getShowCount(knowledges);
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ImageView oper;
		public TextView name;
		public TextView count;
		public ImageView check;

		public Holder(View itemView)
		{
			super(itemView);
			oper = itemView.findViewById(R.id.oper);
			name = itemView.findViewById(R.id.name);
			count = itemView.findViewById(R.id.count);
			check = itemView.findViewById(R.id.check);
		}
	}

}
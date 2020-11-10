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
import com.lys.kit.utils.ChapterTreeUtils;
import com.lys.protobuf.SChapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterTopicFilterChapter extends RecyclerView.Adapter<AdapterTopicFilterChapter.Holder>
{
	//	private ActivityTopicFilter page = null;
	private List<SChapter> chapters = null;

//	public AdapterTopicFilterChapter(ActivityTopicFilter page)
//	{
//		this.page = page;
//	}

	public void setData(List<SChapter> chapters, List<SChapter> selectChapters)
	{
		this.chapters = chapters;
		updateData(selectChapters);
	}

	public void updateData(List<SChapter> selectChapters)
	{
		if (chapters != null)
		{
			ChapterTreeUtils.checkChapters(chapters);
			if (selectChapters.size() > 0)
			{
				Map<String, SChapter> chapterMap = new HashMap<>();
				for (SChapter chapter : selectChapters)
				{
					chapterMap.put(chapter.code, chapter);
				}
				ChapterTreeUtils.checkState(chapters, chapterMap);
			}
		}
		notifyDataSetChanged();
	}

	public void reset()
	{
		if (chapters != null)
		{
			ChapterTreeUtils.checkChapters(chapters);
			notifyDataSetChanged();
		}
	}

	public boolean isReady()
	{
		return chapters != null;
	}

	private void getSelectedChapters(List<SChapter> nodes, List<SChapter> selectedChapters)
	{
		for (SChapter chapter : nodes)
		{
			if (chapter.state == ChapterTreeUtils.StateYes)
				selectedChapters.add(chapter);
			else if (chapter.state == ChapterTreeUtils.StateHalf)
				getSelectedChapters(chapter.nodes, selectedChapters);
		}
	}

	public List<SChapter> getSelectedChapters()
	{
		List<SChapter> selectedChapters = new ArrayList<>();
		getSelectedChapters(chapters, selectedChapters);
		return selectedChapters;
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_filter_chapter, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, int position)
	{
		final SChapter chapter = ChapterTreeUtils.getShowNode(chapters, position);
		final Context context = holder.itemView.getContext();

		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.oper.getLayoutParams();
		layoutParams.leftMargin = 30 + ChapterTreeUtils.getLevel(chapter) * 45;
		holder.oper.setLayoutParams(layoutParams);

		holder.name.setText(chapter.name);
		holder.count.setText(String.valueOf(chapter.topicCount));

		View.OnClickListener selectClick = new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				switch (chapter.state)
				{
				case ChapterTreeUtils.StateNo:
					ChapterTreeUtils.setState(chapter, ChapterTreeUtils.StateYes);
					break;
				case ChapterTreeUtils.StateHalf:
					ChapterTreeUtils.setState(chapter, ChapterTreeUtils.StateYes);
					break;
				case ChapterTreeUtils.StateYes:
					ChapterTreeUtils.setState(chapter, ChapterTreeUtils.StateNo);
					break;
				}
				ChapterTreeUtils.checkParentState(chapter);
				notifyDataSetChanged();
			}
		};

		if (ChapterTreeUtils.isLeaf(chapter))
		{
//			holder.oper.setImageResource(R.drawable.img_tree_point);
			holder.oper.setImageDrawable(null);
			holder.itemView.setOnClickListener(selectClick);
		}
		else
		{
			if (chapter.isOpen)
				holder.oper.setImageResource(R.drawable.img_tree_sub);
			else
				holder.oper.setImageResource(R.drawable.img_tree_add);
			holder.itemView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					chapter.isOpen = !chapter.isOpen;
					notifyDataSetChanged();
				}
			});
		}
		holder.check.setOnClickListener(selectClick);

		switch (chapter.state)
		{
		case ChapterTreeUtils.StateNo:
			holder.check.setImageResource(R.drawable.img_select_no);
			break;
		case ChapterTreeUtils.StateHalf:
			holder.check.setImageResource(R.drawable.img_select_half);
			break;
		case ChapterTreeUtils.StateYes:
			holder.check.setImageResource(R.drawable.img_select_yes);
			break;
		}
	}

	@Override
	public int getItemCount()
	{
		if (chapters != null)
			return ChapterTreeUtils.getShowCount(chapters);
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
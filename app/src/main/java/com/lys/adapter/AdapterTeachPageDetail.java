package com.lys.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lys.app.R;
import com.lys.protobuf.STeachPage;

import java.util.List;

public class AdapterTeachPageDetail extends RecyclerView.Adapter<AdapterTeachPageDetail.Holder>
{
	private List<STeachPage> teachPages = null;
	private long maxTime = 0;

	public AdapterTeachPageDetail(List<STeachPage> teachPages, long maxTime)
	{
		this.teachPages = teachPages;
		this.maxTime = maxTime;
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teach_page_detail, parent, false));
	}

	public static String formatTime(long ms)
	{
		long second = ms / 1000;
		long minute = second / 60;
		second = second % 60;
		long hour = minute / 60;
		minute = minute % 60;
		if (hour > 0)
			return String.format("%d 小时 %02d 分钟 %02d 秒", hour, minute, second);
		if (minute > 0)
			return String.format("%d 分钟 %02d 秒", minute, second);
		return String.format("%d 秒", second);
	}

	@Override
	public void onBindViewHolder(final Holder holder, int position)
	{
		final STeachPage teachPage = teachPages.get(position);
		final Context context = holder.itemView.getContext();

		if (position % 2 == 0)
			holder.con.setBackgroundColor(0xfff5f5dc);
		else
			holder.con.setBackgroundColor(0xfff0ffff);

		holder.index.setText(String.format("第 %03d 页 ： ", teachPage.index + 1));

		holder.progress.setMax((int) (maxTime / 1000));
		holder.progress.setProgress((int) (teachPage.time / 1000));

		holder.time.setText(formatTime(teachPage.time));
	}

	@Override
	public int getItemCount()
	{
		if (teachPages != null)
			return teachPages.size();
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public LinearLayout con;
		public TextView index;
		public ProgressBar progress;
		public TextView time;

		public Holder(View itemView)
		{
			super(itemView);
			con = itemView.findViewById(R.id.con);
			index = itemView.findViewById(R.id.index);
			progress = itemView.findViewById(R.id.progress);
			time = itemView.findViewById(R.id.time);
		}
	}
}
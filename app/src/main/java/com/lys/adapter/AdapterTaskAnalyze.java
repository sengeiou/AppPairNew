package com.lys.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lys.App;
import com.lys.activity.ActivityTaskAnalyze;
import com.lys.app.R;
import com.lys.base.utils.CommonUtils;
import com.lys.config.AppConfig;
import com.lys.kit.dialog.DialogAlert;
import com.lys.protobuf.SEvent;
import com.lys.protobuf.SUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterTaskAnalyze extends RecyclerView.Adapter<AdapterTaskAnalyze.Holder>
{
	private ActivityTaskAnalyze owner = null;
	private List<SUser> userList = null;

	public AdapterTaskAnalyze(ActivityTaskAnalyze owner)
	{
		this.owner = owner;
	}

	public void setData(List<SUser> userList)
	{
		this.userList = userList;
		checkMinMax();
		notifyDataSetChanged();
	}

	public long minTime;
	public long maxTime;

//	private long timeLen;

	private void checkMinMax()
	{
		minTime = Long.MAX_VALUE;
		maxTime = Long.MIN_VALUE;
		for (SUser user : userList)
		{
			List<SEvent> eventList = owner.eventMap.get(user.id);
			for (SEvent event : eventList)
			{
				minTime = Math.min(minTime, event.time);
				maxTime = Math.max(maxTime, event.time);
			}
		}
		if (minTime <= maxTime)
		{
			long now = System.currentTimeMillis() - App.TimeOffset;
			maxTime = Math.max(maxTime, now);
//			timeLen = maxTime - minTime; // 这里有可能>=0，但BIND的时候只处理>0的情况
		}
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_analyze, parent, false));
	}

	private static final SimpleDateFormat formatDate = new SimpleDateFormat("MM-dd HH:mm");

	@Override
	public void onBindViewHolder(final Holder holder, int position)
	{
		final SUser user = userList.get(position);
		final Context context = holder.itemView.getContext();

		final List<SEvent> eventList = owner.eventMap.get(user.id);

		if (position % 3 == 2)
			holder.con.setBackgroundColor(0xfff5f5dc);
		else
			holder.con.setBackgroundColor(0xfff0ffff);

		holder.name.setText(user.name);
		holder.name.setTextColor(0xff202122);

		holder.commitFlag.setVisibility(View.INVISIBLE);

		holder.eventCon.removeAllViews();
		holder.commitCon.removeAllViews();

		if (minTime < maxTime)
		{
			if (true) // eventCon
			{
				long timePos = minTime;

				long lastInTime = -1;
				for (int i = 0; i < eventList.size(); i++)
				{
					SEvent event = eventList.get(i);
					if (event.action.equals(AppConfig.EventAction_InTask))
					{
						lastInTime = event.time;
					}
					else if (event.action.equals(AppConfig.EventAction_OutTask))
					{
						if (lastInTime != -1)
						{
							addSpace(context, holder.eventCon, lastInTime - timePos);
							timePos = lastInTime;

							addBlock(context, holder.eventCon, event.time - timePos);
							timePos = event.time;

							holder.name.setTextColor(Color.GREEN);
						}
						lastInTime = -1;
					}
				}

				if (lastInTime != -1)
				{
					addSpace(context, holder.eventCon, lastInTime - timePos);
					timePos = lastInTime;

					addBlock(context, holder.eventCon, maxTime - timePos);
					holder.name.setTextColor(Color.GREEN);
				}
				else
				{
					addSpace(context, holder.eventCon, maxTime - timePos);
				}
			}

			if (true) // commitCon
			{
				long timePos = minTime;

				for (int i = 0; i < eventList.size(); i++)
				{
					SEvent event = eventList.get(i);
					if (event.action.equals(AppConfig.EventAction_CommitJob))
					{
						addSpace(context, holder.commitCon, event.time - timePos);
						timePos = event.time;
						addCommitBlock(context, holder.commitCon);

						holder.commitFlag.setVisibility(View.VISIBLE);
					}
				}

				addSpace(context, holder.commitCon, maxTime - timePos);
			}
		}

		holder.con.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				long lastInTime = -1;
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < eventList.size(); i++)
				{
					SEvent event = eventList.get(i);

					String timeStr = "";
					if (event.action.equals(AppConfig.EventAction_InTask))
					{
						lastInTime = event.time;
					}
					else if (event.action.equals(AppConfig.EventAction_OutTask))
					{
						if (lastInTime != -1)
							timeStr = "    " + CommonUtils.formatTime(event.time - lastInTime);
						lastInTime = -1;
					}

					sb.append(String.format("%s  %s%s\r\n", formatDate.format(new Date(event.time)), event.des, timeStr));
				}
				DialogAlert.show(context, user.name, sb.toString(), null);
			}
		});

	}

	private void addSpace(Context context, ViewGroup con, long time)
	{
		{
			View view = new View(context);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
			view.setLayoutParams(layoutParams);
			con.addView(view);
		}
		{
			View view = new View(context);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
			layoutParams.weight = time;
			view.setLayoutParams(layoutParams);
			con.addView(view);
		}
		{
			View view = new View(context);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
			view.setLayoutParams(layoutParams);
			con.addView(view);
		}
	}

	private void addBlock(Context context, ViewGroup con, long time)
	{
		{
			View view = new View(context);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
			view.setLayoutParams(layoutParams);
			view.setBackgroundColor(Color.GREEN);
			con.addView(view);
		}
		{
			View view = new View(context);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
			layoutParams.weight = time;
			view.setLayoutParams(layoutParams);
			view.setBackgroundColor(Color.GREEN);
			con.addView(view);
		}
		{
			View view = new View(context);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
			view.setLayoutParams(layoutParams);
			view.setBackgroundColor(Color.GREEN);
			con.addView(view);
		}
	}

	private void addCommitBlock(Context context, ViewGroup con)
	{
		{
			View view = new View(context);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10, ViewGroup.LayoutParams.MATCH_PARENT);
			view.setLayoutParams(layoutParams);
			view.setBackgroundColor(Color.RED);
			con.addView(view);
		}
	}

	@Override
	public int getItemCount()
	{
		if (userList != null)
			return userList.size();
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ViewGroup con;
		public View commitFlag;
		public TextView name;
		public ViewGroup eventCon;
		public ViewGroup commitCon;

		public Holder(View itemView)
		{
			super(itemView);
			con = itemView.findViewById(R.id.con);
			commitFlag = itemView.findViewById(R.id.commitFlag);
			name = itemView.findViewById(R.id.name);
			eventCon = itemView.findViewById(R.id.eventCon);
			commitCon = itemView.findViewById(R.id.commitCon);
		}
	}

}
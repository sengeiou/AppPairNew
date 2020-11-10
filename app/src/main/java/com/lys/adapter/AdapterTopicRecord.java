package com.lys.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lys.activity.ActivityTopicRecord;
import com.lys.app.R;
import com.lys.base.utils.CommonUtils;
import com.lys.config.AppConfig;
import com.lys.protobuf.STopicRecord;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterTopicRecord extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	public static final int Type_LoadInfo = 1;
	public static final int Type_TopicRecord = 2;

	private ActivityTopicRecord owner = null;
	private List<STopicRecord> topicRecords = new ArrayList<>();

	public AdapterTopicRecord(ActivityTopicRecord owner)
	{
		this.owner = owner;
	}

//	public void clearData()
//	{
//		this.topicRecords.clear();
//		notifyDataSetChanged();
//	}

	public void addData(List<STopicRecord> topicRecords)
	{
		this.topicRecords.addAll(topicRecords);
		notifyDataSetChanged();
	}

	public STopicRecord getLast()
	{
		if (topicRecords.size() > 0)
			return topicRecords.get(topicRecords.size() - 1);
		else
			return null;
	}

	public static final int State_Normal = 1;
	public static final int State_Loading = 2;
	public static final int State_LoadFail = 3;
	public static final int State_NotMore = 4;

	public int state = State_Normal;
	private String msg = "";

	public void setState(int state, String msg)
	{
		this.state = state;
		this.msg = msg;
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		switch (viewType)
		{
		case Type_TopicRecord:
			return new HolderTopicRecord(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_record, parent, false));
		case Type_LoadInfo:
			return new HolderLoadInfo(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_info, parent, false));
		}
		return null;
	}

	private static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy - MM - dd   HH : mm : ss");

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position)
	{
		final Context context = viewHolder.itemView.getContext();
		if (getItemViewType(position) == Type_TopicRecord)
		{
			final STopicRecord topicRecord = topicRecords.get(position);
			final HolderTopicRecord holder = (HolderTopicRecord) viewHolder;

			holder.date.setText(formatDate.format(new Date(topicRecord.time)));
			holder.time.setText(CommonUtils.formatTime2(System.currentTimeMillis() - topicRecord.time));

			File file = new File(AppConfig.getTopicDir(topicRecord.topicId), AppConfig.TopicContentName + ".png");
			if (file.exists())
			{
				ViewGroup.LayoutParams layoutParams = holder.content.getLayoutParams();
				Bitmap bitmap = CommonUtils.readBitmap(file.toString(), layoutParams.width);
				layoutParams.height = layoutParams.width * bitmap.getHeight() / bitmap.getWidth();
				holder.content.setLayoutParams(layoutParams);
				holder.content.setImageBitmap(bitmap);
			}
			else
			{
				holder.content.setImageResource(R.drawable.img_default);
				ViewGroup.LayoutParams layoutParams = holder.content.getLayoutParams();
				layoutParams.height = 300;
				holder.content.setLayoutParams(layoutParams);
			}

			holder.content.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					owner.goin(topicRecord);
				}
			});

			if (topicRecord.result.equals(1))
			{
				holder.point.setImageResource(R.drawable.img_topic_point_error);
			}
			else if (topicRecord.result.equals(3))
			{
				holder.point.setImageResource(R.drawable.img_topic_point_right);
			}
			else
			{
				holder.point.setImageResource(R.drawable.img_topic_point_normal);
			}
		}
		else if (getItemViewType(position) == Type_LoadInfo)
		{
			final HolderLoadInfo holder = (HolderLoadInfo) viewHolder;
			holder.info.setText(msg);
		}
	}

	@Override
	public int getItemCount()
	{
		if (topicRecords != null)
			return topicRecords.size() + 1;
		else
			return 0 + 1;
	}

	@Override
	public int getItemViewType(int position)
	{
		if (position == getItemCount() - 1)
			return Type_LoadInfo;
		else
			return Type_TopicRecord;
	}

	protected class HolderTopicRecord extends RecyclerView.ViewHolder
	{
		public TextView date;
		public TextView time;
		public ImageView point;
		public ViewGroup con;
		public ImageView content;

		public HolderTopicRecord(View itemView)
		{
			super(itemView);
			date = itemView.findViewById(R.id.date);
			time = itemView.findViewById(R.id.time);
			point = itemView.findViewById(R.id.point);
			con = itemView.findViewById(R.id.con);
			content = itemView.findViewById(R.id.content);
		}
	}

	protected class HolderLoadInfo extends RecyclerView.ViewHolder
	{
		public TextView info;

		public HolderLoadInfo(View itemView)
		{
			super(itemView);
			info = itemView.findViewById(R.id.info);
		}
	}

}
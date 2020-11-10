package com.lys.kit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lys.kit.R;
import com.lys.kit.fragment.FragmentSelectImageNet;
import com.lys.kit.utils.ImageLoad;
import com.lys.protobuf.SNetPicInfo;

import java.util.List;

public class AdapterImageNet extends RecyclerView.Adapter<AdapterImageNet.Holder>
{
	private FragmentSelectImageNet owner = null;
	private List<SNetPicInfo> netPics = null;

	public AdapterImageNet(FragmentSelectImageNet owner)
	{
		this.owner = owner;
	}

	public void setData(List<SNetPicInfo> netPics)
	{
		this.netPics = netPics;
		notifyDataSetChanged();
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_net, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, final int position)
	{
		final SNetPicInfo netPic = netPics.get(position);
		final Context context = holder.itemView.getContext();

		final int imageWidth = 435;

		if (netPic.isMovie)
		{
			holder.videoFlag.setVisibility(View.VISIBLE);
			holder.durationText.setVisibility(View.VISIBLE);
			holder.durationText.setText(formatTime(netPic.duration));
		}
		else
		{
			holder.videoFlag.setVisibility(View.GONE);
			holder.durationText.setVisibility(View.GONE);
		}

		ViewGroup.LayoutParams layoutParams = holder.con.getLayoutParams();
		layoutParams.height = imageWidth * netPic.smallHeight / netPic.smallWidth;
		holder.con.setLayoutParams(layoutParams);

		ImageLoad.displayImage(context, netPic.smallUrl, 0, holder.image, R.drawable.img_default, null);

		holder.image.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				owner.select(netPic);
			}
		});
	}

	private String formatTime(long ms)
	{
		int second = (int) (ms / 1000);
		int minute = second / 60;
		second = second % 60;
		int hour = minute / 60;
		minute = minute % 60;
		if (hour == 0)
			return String.format("%d:%02d", minute, second);
		else
			return String.format("%d:%02d:%02d", hour, minute, second);
	}

	@Override
	public int getItemCount()
	{
		if (netPics != null)
			return netPics.size();
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ViewGroup con;
		public ImageView image;
		public ImageView videoFlag;
		public TextView durationText;

		public Holder(View itemView)
		{
			super(itemView);
			con = itemView.findViewById(R.id.con);
			image = itemView.findViewById(R.id.image);
			videoFlag = itemView.findViewById(R.id.videoFlag);
			durationText = itemView.findViewById(R.id.durationText);
		}
	}
}
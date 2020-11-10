package com.lys.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.lys.activity.ActivityTaskBook;
import com.lys.app.R;
import com.lys.config.AppConfig;
import com.lys.kit.utils.ImageLoad;
import com.lys.message.TransMessage;
import com.lys.protobuf.SCheckState;
import com.lys.protobuf.SUser;

import java.util.List;

public class AdapterRoomUser extends RecyclerView.Adapter<AdapterRoomUser.Holder>
{
	private ActivityTaskBook owner = null;
	public List<String> roomUsers = null;

	public AdapterRoomUser(ActivityTaskBook owner)
	{
		this.owner = owner;
	}

	public void setData(List<String> roomUsers)
	{
		this.roomUsers = roomUsers;
		notifyDataSetChanged();
	}

//	public void addData(String roomUser)
//	{
//		this.roomUsers.add(roomUser);
//		notifyDataSetChanged();
//	}
//
//	public void removeData(String roomUser)
//	{
//		this.roomUsers.remove(roomUser);
//		notifyDataSetChanged();
//	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_user, parent, false));
	}

	@Override
	public void onBindViewHolder(Holder holder, int position)
	{
		final String targetId = roomUsers.get(position);
		final Context context = holder.itemView.getContext();

		final SUser user = owner.getTarget(targetId);
		if (user != null)
		{
			ImageLoad.displayImage(context, user.head, holder.head, R.drawable.img_default_head, null);
			holder.name.setText(user.name);

			holder.muteAudio.setChecked(user.isMuteAudio);
			holder.muteAudio.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					TransMessage.send(targetId, TransMessage.obtain(AppConfig.TransEvt_TeachNotify_MuteAudio, String.valueOf(!user.isMuteAudio)), null);
				}
			});

			holder.lockWrite.setChecked(user.isLockWrite);
			holder.lockWrite.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					TransMessage.send(targetId, TransMessage.obtain(AppConfig.TransEvt_TeachNotify_LockWrite, String.valueOf(!user.isLockWrite)), null);
				}
			});

			if (user.checkState.equals(SCheckState.None))
			{
				holder.check.setText("同步检查");
				holder.check.setTextColor(0xff13121a);
			}
			else if (user.checkState.equals(SCheckState.Refresh))
			{
				holder.check.setText("检查中。。。");
				holder.check.setTextColor(0xff13121a);
			}
			else if (user.checkState.equals(SCheckState.Diff))
			{
				holder.check.setText("有差异");
				holder.check.setTextColor(0xffff4034);
			}
			else if (user.checkState.equals(SCheckState.Equal))
			{
				holder.check.setText("同步正确");
				holder.check.setTextColor(0xff81cc86);
			}
			else
			{
				holder.check.setText("状态错误");
				holder.check.setTextColor(0xff13121a);
			}

			holder.check.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					owner.readyCheckDir();
					user.checkState = SCheckState.Refresh;
					TransMessage.send(targetId, TransMessage.obtain(AppConfig.TransEvt_TeachNotify_Check, null), null);
					notifyDataSetChanged();
				}
			});
		}
		else
		{
			holder.head.setImageResource(R.drawable.img_default_head);
			holder.name.setText("未知用户:" + targetId);
		}
	}

	@Override
	public int getItemCount()
	{
		if (roomUsers != null)
			return roomUsers.size();
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ImageView head;
		public TextView name;
		public CheckBox muteAudio;
		public CheckBox lockWrite;
		public TextView check;

		public Holder(View itemView)
		{
			super(itemView);
			head = itemView.findViewById(R.id.head);
			name = itemView.findViewById(R.id.name);
			muteAudio = itemView.findViewById(R.id.muteAudio);
			lockWrite = itemView.findViewById(R.id.lockWrite);
			check = itemView.findViewById(R.id.check);
		}
	}
}
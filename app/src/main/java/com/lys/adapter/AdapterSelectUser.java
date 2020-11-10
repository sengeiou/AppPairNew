package com.lys.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lys.app.R;
import com.lys.fragment.FragmentSelectUser;
import com.lys.kit.utils.ImageLoad;
import com.lys.protobuf.SSex;
import com.lys.protobuf.SUser;

import java.util.ArrayList;
import java.util.List;

public class AdapterSelectUser extends RecyclerView.Adapter<AdapterSelectUser.Holder>
{
	private FragmentSelectUser owner = null;
	private List<SUser> rawUsers = null;
	private List<SUser> filterUsers = null;
	private String filterText = null;

	public AdapterSelectUser(FragmentSelectUser owner)
	{
		this.owner = owner;
	}

	public void setData(List<SUser> users)
	{
		this.rawUsers = users;
		flush();
	}

	public void setFilterText(String filterText)
	{
		this.filterText = filterText;
		flush();
	}

	private void flush()
	{
		if (rawUsers != null && rawUsers.size() > 0 && !TextUtils.isEmpty(filterText))
		{
			List<SUser> filterUsers = new ArrayList<>();
			for (SUser user : rawUsers)
			{
				if (user.name.contains(filterText) || user.id.contains(filterText))
				{
					filterUsers.add(user);
				}
			}
			this.filterUsers = filterUsers;
		}
		else
		{
			this.filterUsers = rawUsers;
		}
		notifyDataSetChanged();
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_user, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, int position)
	{
		final SUser user = filterUsers.get(position);
		final Context context = holder.itemView.getContext();

		ImageLoad.displayImage(context, user.head, holder.head, R.drawable.img_default_head, null);

		holder.name.setText(user.name);
		holder.account.setText(user.id);
		holder.sex.setText(user.sex.equals(SSex.Girl) ? "女" : "男");

		if (owner.owner.multi)
		{
			holder.check.setVisibility(View.VISIBLE);
			if (owner.owner.userIds.contains(user.id))
				holder.check.setImageResource(R.drawable.img_select_yes);
			else
				holder.check.setImageResource(R.drawable.img_select_no);
		}
		else
		{
			holder.check.setVisibility(View.GONE);
		}

		holder.con.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (owner.owner.multi)
				{
					if (owner.owner.userIds.contains(user.id))
						owner.owner.userIds.remove(user.id);
					else
						owner.owner.userIds.add(user.id);

					if (owner.owner.userIds.contains(user.id))
						holder.check.setImageResource(R.drawable.img_select_yes);
					else
						holder.check.setImageResource(R.drawable.img_select_no);
				}
				else
				{
					owner.select(user);
				}
			}
		});

	}

	@Override
	public int getItemCount()
	{
		if (filterUsers != null)
			return filterUsers.size();
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ViewGroup con;
		public ImageView head;
		public TextView name;
		public TextView account;
		public TextView sex;
		public ImageView check;

		public Holder(View itemView)
		{
			super(itemView);
			con = itemView.findViewById(R.id.con);
			head = itemView.findViewById(R.id.head);
			name = itemView.findViewById(R.id.name);
			account = itemView.findViewById(R.id.account);
			sex = itemView.findViewById(R.id.sex);
			check = itemView.findViewById(R.id.check);
		}
	}
}
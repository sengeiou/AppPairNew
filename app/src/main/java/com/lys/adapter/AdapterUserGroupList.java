package com.lys.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lys.App;
import com.lys.app.R;
import com.lys.base.activity.BaseActivity;
import com.lys.base.utils.AppDataTool;
import com.lys.base.utils.JsonHelper;
import com.lys.base.utils.LOG;
import com.lys.dialog.DialogEditLive;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_UserGroupAddModify;
import com.lys.protobuf.SRequest_UserGroupDelete;
import com.lys.protobuf.SUser;
import com.lys.protobuf.SUserGroup;
import com.lys.protobuf.SUserType;
import com.lys.utils.UserCacheManager;

import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterUserGroupList extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	public static final int Type_UserGroup = 1;

	private DialogEditLive owner = null;
	private List<SUserGroup> userGroups = null;

	public AdapterUserGroupList(DialogEditLive owner)
	{
		this.owner = owner;
	}

	public void setData(List<SUserGroup> userGroups)
	{
		this.userGroups = userGroups;
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		switch (viewType)
		{
		case Type_UserGroup:
			return new HolderUserGroup(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_group_list_one, parent, false));
		}
		return null;
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position)
	{
		final Context context = viewHolder.itemView.getContext();
		final SUserGroup userGroup = userGroups.get(position);
		if (getItemViewType(position) == Type_UserGroup)
		{
			final HolderUserGroup holder = (HolderUserGroup) viewHolder;

			holder.name.setText(userGroup.name);
			holder.name.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					DialogAlert.showInput(context, "修改用户组名字：", userGroup.name, new DialogAlert.OnInputListener()
					{
						@Override
						public void onInput(String text)
						{
							if (!TextUtils.isEmpty(text))
							{
								userGroup.name = text;
								holder.name.setText(userGroup.name);

								SRequest_UserGroupAddModify request = new SRequest_UserGroupAddModify();
								request.userGroup = userGroup;
								Protocol.doPost(context, App.getApi(), SHandleId.UserGroupAddModify, request.saveToStr(), new Protocol.OnCallback()
								{
									@Override
									public void onResponse(int code, String data, String msg)
									{
										if (code != 200)
										{
											LOG.toast(context, "保存失败");
										}
									}
								});
							}
						}
					});
				}
			});

			holder.delete.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					DialogAlert.show(context, "确定要删除吗？", null, new DialogAlert.OnClickListener()
					{
						@Override
						public void onClick(int which)
						{
							if (which == 1)
							{
								SRequest_UserGroupDelete request = new SRequest_UserGroupDelete();
								request.id = userGroup.id;
								Protocol.doPost(context, App.getApi(), SHandleId.UserGroupDelete, request.saveToStr(), new Protocol.OnCallback()
								{
									@Override
									public void onResponse(int code, String data, String msg)
									{
										if (code == 200)
										{
											owner.requestUserGroup();
										}
									}
								});
							}
						}
					}, "取消", "删除");
				}
			});

			holder.use.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					DialogAlert.show(context, "确定要使用吗？", null, new DialogAlert.OnClickListener()
					{
						@Override
						public void onClick(int which)
						{
							if (which == 1)
							{
								owner.useUserGroup(userGroup.userIds);
							}
						}
					}, "取消", "使用");
				}
			});

			bindUserIds(holder, userGroup.userIds);
			holder.userIds.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					owner.context.selectUsers(new BaseActivity.OnImageListener()
					{
						@Override
						public void onResult(String userStr)
						{
							userGroup.userIds = AppDataTool.loadStringList(JsonHelper.getJSONArray(userStr));
							bindUserIds(holder, userGroup.userIds);

							SRequest_UserGroupAddModify request = new SRequest_UserGroupAddModify();
							request.userGroup = userGroup;
							Protocol.doPost(context, App.getApi(), SHandleId.UserGroupAddModify, request.saveToStr(), new Protocol.OnCallback()
							{
								@Override
								public void onResponse(int code, String data, String msg)
								{
									if (code != 200)
									{
										LOG.toast(context, "保存失败");
									}
								}
							});
						}
					}, userGroup.userIds, SUserType.Student, SUserType.Master, SUserType.Teacher);
				}
			});

		}
	}

	private void bindUserIds(final HolderUserGroup holder, final List<String> userIds)
	{
		if (userIds.size() == 0)
		{
			holder.userIds.setText("未指定");
		}
		else
		{
			holder.userIds.setText("");
			UserCacheManager.instance().getUsers(userIds, new UserCacheManager.OnResults()
			{
				@Override
				public void result(List<SUser> users)
				{
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < users.size(); i++)
					{
						if (i != 0)
							sb.append("，");
						SUser user = users.get(i);
						if (user != null)
							sb.append(user.name);
						else
							sb.append("[" + userIds.get(i) + "]");
					}
					holder.userIds.setText(sb.toString());
				}
			});
		}
	}

	@Override
	public int getItemCount()
	{
		if (userGroups != null)
			return userGroups.size();
		else
			return 0;
	}

	@Override
	public int getItemViewType(int position)
	{
		return Type_UserGroup;
	}

	protected class HolderUserGroup extends RecyclerView.ViewHolder
	{
		public TextView name;
		public TextView delete;
		public TextView use;
		public TextView userIds;

		public HolderUserGroup(View itemView)
		{
			super(itemView);
			name = itemView.findViewById(R.id.name);
			delete = itemView.findViewById(R.id.delete);
			use = itemView.findViewById(R.id.use);
			userIds = itemView.findViewById(R.id.userIds);
		}
	}

}
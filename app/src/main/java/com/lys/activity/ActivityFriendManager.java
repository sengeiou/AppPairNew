package com.lys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lys.App;
import com.lys.adapter.AdapterFriendManager;
import com.lys.app.R;
import com.lys.base.activity.BaseActivity;
import com.lys.config.AppConfig;
import com.lys.kit.utils.Protocol;
import com.lys.message.TransMessage;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_AddFriend;
import com.lys.protobuf.SResponse_AddFriend;
import com.lys.protobuf.SUser;
import com.lys.protobuf.SUserType;
import com.lys.utils.UserHelper;
import com.lys.utils.UserTreeNode;

import java.util.List;

public class ActivityFriendManager extends AppActivity implements View.OnClickListener
{
	private class Holder
	{
		private EditText keyword;
		private TextView count;
		private Button addFriend;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.keyword = findViewById(R.id.keyword);
		holder.count = findViewById(R.id.count);
		holder.addFriend = findViewById(R.id.addFriend);
	}

	public String userId;

	private RecyclerView recyclerView;
	private AdapterFriendManager adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_manager);
		initHolder();
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		userId = getIntent().getStringExtra("userId");

		if (App.isSupterMaster())
			holder.addFriend.setVisibility(View.VISIBLE);

		findViewById(R.id.addFriend).setOnClickListener(this);
		findViewById(R.id.teachRecord).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterFriendManager(this);
		recyclerView.setAdapter(adapter);

		holder.keyword.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				adapter.setFilterText(s.toString());
				holder.count.setText(String.valueOf(adapter.getShowTaskCount()));
			}

			@Override
			public void afterTextChanged(Editable s)
			{
			}
		});

		refreshFriends();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.addFriend)
		{
			selectUser(new BaseActivity.OnImageListener()
			{
				@Override
				public void onResult(String userStr)
				{
					SUser user = SUser.load(userStr);
					final SRequest_AddFriend request = new SRequest_AddFriend();
					request.userId = userId;
					request.friendId = user.id;
					Protocol.doPost(context, App.getApi(), SHandleId.AddFriend, request.saveToStr(), new Protocol.OnCallback()
					{
						@Override
						public void onResponse(int code, String data, String msg)
						{
							if (code == 200)
							{
								SResponse_AddFriend response = SResponse_AddFriend.load(data);
								TransMessage.send(request.userId, TransMessage.obtain(AppConfig.TransEvt_FriendChange, null), null);
								TransMessage.send(request.friendId, TransMessage.obtain(AppConfig.TransEvt_FriendChange, null), null);
								refreshFriends();
							}
						}
					});
				}
			}, SUserType.Master, SUserType.Teacher, SUserType.Student);
		}
		else if (view.getId() == R.id.teachRecord)
		{
			Intent intent = new Intent(context, ActivityTeachRecord.class);
			intent.putExtra("userId", userId);
			startActivity(intent);
		}
	}

	private void refreshFriends()
	{
		UserHelper.requestUserTree(context, userId, new UserHelper.onUserTreeCallback()
		{
			@Override
			public void onResult(List<UserTreeNode> treeNodes)
			{
				if (treeNodes != null)
				{
					adapter.setData(treeNodes);
					holder.count.setText(String.valueOf(adapter.getShowTaskCount()));
				}
			}
		});
//		Helper.requestFriendList(context, userId, new Helper.OnUserListCallback()
//		{
//			@Override
//			public void onResult(List<SUser> users)
//			{
//				List<SUser> students = new ArrayList<>();
//				for (SUser user : users)
//				{
//					if (user.userType.equals(SUserType.Student))
//						students.add(user);
//				}
//				adapter.setData(students);
//			}
//		});
	}

}

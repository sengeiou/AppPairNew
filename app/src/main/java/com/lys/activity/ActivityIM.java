package com.lys.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.king.zxing.CaptureActivity;
import com.lys.App;
import com.lys.adapter.AdapterFriend;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.config.AppConfig;
import com.lys.dialog.DialogAddFriend;
import com.lys.fragment.LysConversationFragment;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.utils.Protocol;
import com.lys.message.TransMessage;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_AddFriend;
import com.lys.protobuf.SRequest_DeleteFriend;
import com.lys.protobuf.SResponse_AddFriend;
import com.lys.protobuf.SResponse_DeleteFriend;
import com.lys.protobuf.SUser;
import com.lys.utils.LysIM;
import com.lys.utils.UserHelper;
import com.lys.utils.UserTreeNode;

import java.util.List;

import io.rong.imlib.model.Conversation;

public class ActivityIM extends KitActivity implements View.OnClickListener, LysIM.OnUnReadMessageObserver
{
	public static final int ScanForAddFriend = 0x226;

	private RecyclerView recyclerView;
	private AdapterFriend adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_im);
		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		String selectUserId = null;
		if (getIntent().hasExtra("selectUserId"))
			selectUserId = getIntent().getStringExtra("selectUserId");

		findViewById(R.id.addFriend).setOnClickListener(this);
		findViewById(R.id.friendCode).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterFriend(this);
		recyclerView.setAdapter(adapter);

		LysIM.instance().addUnReadMessageCountChangedObserver(this);

		IntentFilter filter = new IntentFilter();
		filter.addAction(LysIM.ActionTrans(context, AppConfig.TransEvt_FriendChange));
		registerReceiver(mReceiver, filter);

		refreshFriends(selectUserId);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		LysIM.instance().removeUnReadMessageCountChangedObserver(this);
		unregisterReceiver(mReceiver);
	}

	private SUser findUser(List<UserTreeNode> treeNodes, String selectUserId)
	{
		for (UserTreeNode treeNode : treeNodes)
		{
			if (treeNode.isUser)
			{
				if (treeNode.user.id.equals(selectUserId))
				{
					return treeNode.user;
				}
			}
			else
			{
				for (UserTreeNode child : treeNode.children)
				{
					if (child.user.id.equals(selectUserId))
					{
						return child.user;
					}
				}
			}
		}
		return null;
	}

	private void refreshFriends(final String selectUserId)
	{
		UserHelper.requestUserTree(context, App.userId(), new UserHelper.onUserTreeCallback()
		{
			@Override
			public void onResult(List<UserTreeNode> treeNodes)
			{
				if (treeNodes != null)
				{
					adapter.setData(treeNodes);
					if (treeNodes.size() > 0)
					{
						UserTreeNode firstTreeNode = treeNodes.get(0);
						SUser selectUser = null;
						if (firstTreeNode.isUser)
							selectUser = firstTreeNode.user;
						else
							selectUser = firstTreeNode.children.get(0).user;
						if (!TextUtils.isEmpty(selectUserId))
						{
							SUser findUser = findUser(treeNodes, selectUserId);
							if (findUser != null)
								selectUser = findUser;
						}
						selectConversation(selectUser);
					}
				}
			}
		});
//		Helper.requestFriendList(context, App.userId(), new Helper.OnUserListCallback()
//		{
//			@Override
//			public void onResult(List<SUser> users)
//			{
//				Collections.sort(users, new Comparator<SUser>()
//				{
//					@Override
//					public int compare(SUser user1, SUser user2)
//					{
//						int ret = user2.userType.compareTo(user1.userType);
//						if (ret != 0)
//							return ret;
//						else
//							return user2.name.compareTo(user1.name);
//					}
//				});
//				adapter.setData(users);
//				if (users.size() > 0)
//				{
//					SUser selectUser = users.get(0);
//					if (!TextUtils.isEmpty(selectUserId))
//					{
//						for (SUser user : users)
//						{
//							if (user.id.equals(selectUserId))
//							{
//								selectUser = user;
//								break;
//							}
//						}
//					}
//					selectConversation(selectUser);
//				}
//			}
//		});
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.addFriend)
		{
//			DialogMenu.show(context, new DialogMenu.OnClickListener()
//			{
//				@Override
//				public void onClick(int which)
//				{
//					switch (which)
//					{
//					case 0:
			Intent intent = new Intent(context, CaptureActivityLandscape.class);
			startActivityForResult(intent, ScanForAddFriend);
//						break;
//					case 1:
//						LOG.toast(context, "暂未开放");
//						break;
//					}
//				}
//			}, "扫码添加", "好友码添加");
		}
		else if (view.getId() == R.id.friendCode)
		{
			DialogAddFriend.show(context, AppConfig.readAccount());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ScanForAddFriend)
		{
			if (resultCode == RESULT_OK)
			{
				Bundle bundle = data.getExtras();
				String friendId = bundle.getString(CaptureActivity.KEY_RESULT);
				addFriend(friendId);
			}
		}
	}

	private void addFriend(final String friendId)
	{
		SRequest_AddFriend request = new SRequest_AddFriend();
		request.userId = App.userId();
		request.friendId = friendId;
		Protocol.doPost(context, App.getApi(), SHandleId.AddFriend, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_AddFriend response = SResponse_AddFriend.load(data);
					TransMessage.send(friendId, TransMessage.obtain(AppConfig.TransEvt_FriendChange, null), null);
					refreshFriends(currUser != null ? currUser.id : null);
				}
			}
		});
	}

	public void deleteFriend(final String friendId)
	{
		SRequest_DeleteFriend request = new SRequest_DeleteFriend();
		request.userId = App.userId();
		request.friendId = friendId;
		Protocol.doPost(context, App.getApi(), SHandleId.DeleteFriend, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_DeleteFriend response = SResponse_DeleteFriend.load(data);
					TransMessage.send(friendId, TransMessage.obtain(AppConfig.TransEvt_FriendChange, null), null);
					refreshFriends(currUser != null ? currUser.id : null);
				}
			}
		});
	}

	private SUser currUser = null;

	public boolean isCurrUser(SUser user)
	{
		try
		{
			return user.id.equals(currUser.id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			LOG.toast(context, "好友不存在");
		}
		return false;
	}

	public void selectConversation(SUser user)
	{
		if (currUser == null || !currUser.id.equals(user.id))
		{
			currUser = user;
			LysConversationFragment fragment = new LysConversationFragment();
			Uri uri = Uri.parse("rong://" + getPackageName()).buildUpon() //
					.appendPath("conversation") //
					.appendPath(Conversation.ConversationType.PRIVATE.getName().toLowerCase()) //
					.appendQueryParameter("targetId", user.id) //
					.build();
			fragment.setUri(uri);
			setup(fragment);
		}
	}

	private void setup(Fragment fragment)
	{
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.conversation, fragment);
		ft.commitAllowingStateLoss();
	}

	@Override
	public void onCountChanged(int count)
	{
		LOG.v("count : " + count);
		adapter.flush();
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			LOG.v("Action:" + intent.getAction());
			if (intent.getAction().equals(LysIM.ActionTrans(context, AppConfig.TransEvt_FriendChange)))
			{
				refreshFriends(currUser != null ? currUser.id : null);
			}
		}
	};

}

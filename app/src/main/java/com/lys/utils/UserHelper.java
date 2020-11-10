package com.lys.utils;

import android.content.Context;
import android.text.TextUtils;

import com.github.promeg.pinyinhelper.Pinyin;
import com.lys.App;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_GetFriendList;
import com.lys.protobuf.SResponse_GetFriendList;
import com.lys.protobuf.SUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserHelper
{

	//-----------------------------------

	public interface onUserTreeCallback
	{
		void onResult(List<UserTreeNode> treeNodes);
	}

	// 构建目录节点
	private static UserTreeNode genTreeNode(String group)
	{
		UserTreeNode treeNode = new UserTreeNode();
		treeNode.isUser = false;
		treeNode.group = group;
		treeNode.pinyin = Pinyin.toPinyin(group, "");
//		LOG.v(group + " : " + treeNode.pinyin);
		treeNode.isOpen = false;
		return treeNode;
	}

	// 包装用户节点
	private static UserTreeNode packTreeNode(SUser user)
	{
		UserTreeNode treeNode = new UserTreeNode();
		treeNode.isUser = true;
		treeNode.user = user;
		treeNode.pinyin = Pinyin.toPinyin(user.name, "");
//		LOG.v(user.name + " : " + treeNode.pinyin);
		return treeNode;
	}

	// 构建用户树
	private static List<UserTreeNode> buildUserTree(List<SUser> users)
	{
		Map<String, UserTreeNode> treeNodeMap = new HashMap<>();
		List<UserTreeNode> treeNodes = new ArrayList<>();
		for (SUser user : users)
		{
			if (TextUtils.isEmpty(user.group))
				user.group = "[未分组]";
			if (TextUtils.isEmpty(user.group))
			{
				UserTreeNode userNode = packTreeNode(user);
				treeNodes.add(userNode);
			}
			else
			{
				if (!treeNodeMap.containsKey(user.group))
				{
					UserTreeNode treeNode = genTreeNode(user.group);
					treeNodeMap.put(user.group, treeNode);
					treeNodes.add(treeNode);
				}
				UserTreeNode parent = treeNodeMap.get(user.group);
				UserTreeNode userNode = packTreeNode(user);
				parent.children.add(userNode);
				userNode.parent = parent;
			}
		}

		Collections.sort(treeNodes, new Comparator<UserTreeNode>()
		{
			@Override
			public int compare(UserTreeNode treeNode1, UserTreeNode treeNode2)
			{
				int ret = treeNode1.isUser.compareTo(treeNode2.isUser);
				if (ret != 0)
					return ret;
				else
				{
					return treeNode1.pinyin.compareTo(treeNode2.pinyin);
//					if (treeNode1.isUser)
//						return treeNode1.user.name.compareTo(treeNode2.user.name);
//					else
//						return treeNode1.group.compareTo(treeNode2.group);
				}
			}
		});

		for (UserTreeNode treeNode : treeNodes)
		{
			if (!treeNode.isUser)
			{
				Collections.sort(treeNode.children, new Comparator<UserTreeNode>()
				{
					@Override
					public int compare(UserTreeNode treeNode1, UserTreeNode treeNode2)
					{
						return treeNode1.pinyin.compareTo(treeNode2.pinyin);
//						return treeNode1.user.name.compareTo(treeNode2.user.name);
					}
				});
			}
		}

//		for (UserTreeNode treeNode : treeNodes)
//		{
//			Collections.sort(treeNode.children, new Comparator<UserTreeNode>()
//			{
//				@Override
//				public int compare(UserTreeNode treeNode1, UserTreeNode treeNode2)
//				{
//					return treeNode1.user.createTime.compareTo(treeNode2.user.createTime);
//				}
//			});
//		}

		return treeNodes;
	}

	// 在现有树上添加一个用户
//	public static void addUserNode(List<UserTreeNode> treeNodes, SUser user)
//	{
//		UserTreeNode parent = null;
//		for (UserTreeNode treeNode : treeNodes)
//		{
//			if (treeNode.group.equals(user.group))
//			{
//				parent = treeNode;
//				break;
//			}
//		}
//		if (parent == null)
//		{
//			parent = genTreeNode(user.group);
//			treeNodes.add(parent);
//			Collections.sort(treeNodes, new Comparator<UserTreeNode>()
//			{
//				@Override
//				public int compare(UserTreeNode treeNode1, UserTreeNode treeNode2)
//				{
//					return treeNode1.group.compareTo(treeNode2.group);
//				}
//			});
//		}
//		UserTreeNode userNode = packTreeNode(user);
//		parent.children.add(userNode);
//		userNode.parent = parent;
//	}

	// 请求并构建用户树
	public static void requestUserTree(Context context, String userId, final onUserTreeCallback callback)
	{
		SRequest_GetFriendList request = new SRequest_GetFriendList();
		request.userId = userId;
		Protocol.doPost(context, App.getApi(), SHandleId.GetFriendList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetFriendList response = SResponse_GetFriendList.load(data);
					List<UserTreeNode> treeNodes = buildUserTree(response.friends);
					if (callback != null)
						callback.onResult(treeNodes);
				}
				else
				{
					if (callback != null)
						callback.onResult(null);
				}
			}
		});
	}

	//-----------------------------------

}

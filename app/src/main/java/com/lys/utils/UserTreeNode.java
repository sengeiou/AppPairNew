package com.lys.utils;

import com.lys.protobuf.SUser;

import java.util.ArrayList;
import java.util.List;

public class UserTreeNode
{
	public Boolean isUser = false; // 节点类型
	public String group = null;
	public SUser user = null;
	public String pinyin = null;
	public Boolean isOpen = false; // 打开状态
	public Boolean isShow = true;
	public Integer state = 0; // 选中状态
	public Integer msgCount = 0;
	public UserTreeNode parent = null;
	public List<UserTreeNode> children = new ArrayList<>();
}

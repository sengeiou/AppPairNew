package com.lys.utils;

import com.lys.protobuf.SPTask;

import java.util.ArrayList;
import java.util.List;

public class TaskTreeNode
{
	public Boolean isTask = false; // 节点类型
	public String group = null;
	public SPTask task = null;
	public Boolean isOpen = false; // 打开状态
	public Boolean isShow = true;
	public Integer state = 0; // 选中状态
	public TaskTreeNode parent = null;
	public List<TaskTreeNode> children = new ArrayList<>();
}

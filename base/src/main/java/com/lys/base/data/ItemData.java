package com.lys.base.data;

public class ItemData
{
	public int type = 0;
	public Object data = null;

	public ItemData(int type)
	{
		this.type = type;
	}

	public ItemData(int type, Object data)
	{
		this.type = type;
		this.data = data;
	}
}

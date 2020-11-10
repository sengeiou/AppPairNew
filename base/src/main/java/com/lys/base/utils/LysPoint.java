package com.lys.base.utils;

public class LysPoint
{
	public Float x = 0F;
	public Float y = 0F;
	public Long timestamp = 0L;

	public LysPoint(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public LysPoint(float x, float y, long timestamp)
	{
		this.x = x;
		this.y = y;
		this.timestamp = timestamp;
	}

	public LysPoint(LysPoint other)
	{
		this.x = other.x;
		this.y = other.y;
		this.timestamp = other.timestamp;
	}

	public boolean equals(LysPoint other)
	{
		return this.x.equals(other.x) && this.y.equals(other.y);
	}

	public float distance(LysPoint other)
	{
		return (float) Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
	}

	public boolean leftOf(LysPoint other)
	{
		return this.x < other.x && this.y == other.y;
	}

	public boolean rightOf(LysPoint other)
	{
		return this.x > other.x && this.y == other.y;
	}

	public boolean topOf(LysPoint other)
	{
		return this.x == other.x && this.y < other.y;
	}

	public boolean bottomOf(LysPoint other)
	{
		return this.x == other.x && this.y > other.y;
	}

	public boolean leftTopOf(LysPoint other)
	{
		return this.x < other.x && this.y < other.y;
	}

	public boolean leftBottomOf(LysPoint other)
	{
		return this.x < other.x && this.y > other.y;
	}

	public boolean rightTopOf(LysPoint other)
	{
		return this.x > other.x && this.y < other.y;
	}

	public boolean rightBottomOf(LysPoint other)
	{
		return this.x > other.x && this.y > other.y;
	}

	@Override
	public String toString()
	{
		return String.format("(%f,%f)", x, y);
	}
}

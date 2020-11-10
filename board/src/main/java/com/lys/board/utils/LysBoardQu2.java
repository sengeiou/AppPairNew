package com.lys.board.utils;

import java.util.ArrayList;
import java.util.List;

// 曲线平滑算法
// https://blog.csdn.net/u011284073/article/details/81385922
// https://blog.csdn.net/liumangmao1314/article/details/54586761
public class LysBoardQu2
{

	public static LysBoardPoint[] smooth(List<LysBoardPoint> ins)
	{
		if (ins == null || ins.size() < 2)
			return null;

		List<LysBoardPoint> outs = new ArrayList<>();

		outs.add(new LysBoardPoint(ins.get(0).x, ins.get(0).y));

		for (int n = 0; n < ins.size(); n++)
		{
			if (n <= ins.size() - 4)
			{
				for (double t = 0.0; t <= 1.0; t += 0.1)
				{
					double a1 = Math.pow((1 - t), 3) / 6;
					double a2 = (3 * Math.pow(t, 3) - 6 * Math.pow(t, 2) + 4) / 6;
					double a3 = (-3 * Math.pow(t, 3) + 3 * Math.pow(t, 2) + 3 * t + 1) / 6;
					double a4 = Math.pow(t, 3) / 6;

					double x = a1 * ins.get(n).x + a2 * ins.get(n + 1).x + a3 * ins.get(n + 2).x + a4 * ins.get(n + 3).x;
					double y = a1 * ins.get(n).y + a2 * ins.get(n + 1).y + a3 * ins.get(n + 2).y + a4 * ins.get(n + 3).y;
					outs.add(new LysBoardPoint((float) x, (float) y));
				}
			}
		}

		outs.add(new LysBoardPoint(ins.get(ins.size() - 1).x, ins.get(ins.size() - 1).y));

		return outs.toArray(new LysBoardPoint[outs.size()]);
	}

}

package com.lys.kit.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lys.board.dawing.LysBoardDrawingType;
import com.lys.kit.R;
import com.lys.kit.utils.KitUtils;

public class PopPen extends PopupWindow
{

	public static int getPenRes(int penType)
	{
		if (penType == LysBoardDrawingType.Stroke)
			return R.drawable.drawing_stroke;
		else if (penType == LysBoardDrawingType.Brush)
			return R.drawable.drawing_brush;
		else if (penType == LysBoardDrawingType.Mark)
			return R.drawable.drawing_mark;
		else if (penType == LysBoardDrawingType.Water)
			return R.drawable.drawing_water;
		else if (penType == LysBoardDrawingType.FullLine)
			return R.drawable.drawing_full_line;
		else if (penType == LysBoardDrawingType.ImaginaryLine)
			return R.drawable.drawing_imaginary_line;
		else if (penType == LysBoardDrawingType.ArrowsLine)
			return R.drawable.drawing_arrows_line;
		else if (penType == LysBoardDrawingType.Coord)
			return R.drawable.drawing_coord;

		else if (penType == LysBoardDrawingType.Triangle)
			return R.drawable.drawing_triangle;
		else if (penType == LysBoardDrawingType.RightAngled)
			return R.drawable.drawing_right_angled;
		else if (penType == LysBoardDrawingType.Isosceles)
			return R.drawable.drawing_isosceles;
		else if (penType == LysBoardDrawingType.EquilateralTriangle)
			return R.drawable.drawing_equilateral_triangle;
		else if (penType == LysBoardDrawingType.Square)
			return R.drawable.drawing_square;
		else if (penType == LysBoardDrawingType.Rectangle)
			return R.drawable.drawing_rectangle;

		else if (penType == LysBoardDrawingType.Circle)
			return R.drawable.drawing_circle;
		else if (penType == LysBoardDrawingType.Oval)
			return R.drawable.drawing_oval;
		else if (penType == LysBoardDrawingType.Hyperbola)
			return R.drawable.drawing_hyperbola;
		else if (penType == LysBoardDrawingType.Hyperbola2)
			return R.drawable.drawing_hyperbola2;
		else if (penType == LysBoardDrawingType.LrParabola)
			return R.drawable.drawing_lr_parabola;
		else if (penType == LysBoardDrawingType.Dummy)
			return R.drawable.drawing_dummy;

		else if (penType == LysBoardDrawingType.UdParabola)
			return R.drawable.drawing_ud_parabola;
		else if (penType == LysBoardDrawingType.Sine)
			return R.drawable.drawing_sine;
		else if (penType == LysBoardDrawingType.Tan)
			return R.drawable.drawing_tan;
		else if (penType == LysBoardDrawingType.Dummy)
			return R.drawable.drawing_dummy;
		else if (penType == LysBoardDrawingType.Dummy)
			return R.drawable.drawing_dummy;
		else if (penType == LysBoardDrawingType.Dummy)
			return R.drawable.drawing_dummy;

		else if (penType == LysBoardDrawingType.Pyramid)
			return R.drawable.drawing_pyramid;
		else if (penType == LysBoardDrawingType.Cube)
			return R.drawable.drawing_cube;
		else if (penType == LysBoardDrawingType.Cone)
			return R.drawable.drawing_cone;
		else if (penType == LysBoardDrawingType.Cylinder)
			return R.drawable.drawing_cylinder;
		else if (penType == LysBoardDrawingType.Ball)
			return R.drawable.drawing_ball;
		else if (penType == LysBoardDrawingType.Dummy)
			return R.drawable.drawing_dummy;

		else
			return R.drawable.drawing_dummy;
	}

	public interface OnClickListener
	{
		void onClick(int penType);
	}

	private OnClickListener clickListener = null;

	public void setClickListener(OnClickListener clickListener)
	{
		this.clickListener = clickListener;
	}

	public PopPen(Context context, int... penTypes)
	{
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.pop_pen, null);
		setContentView(view);
		setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new ColorDrawable(0x00000000)); // 点击外面消失
		setFocusable(true);

		GridLayout gridLayout = view.findViewById(R.id.gridLayout);

		for (final int penType : penTypes)
		{
			View item = LayoutInflater.from(context).inflate(R.layout.view_pen, null);
			gridLayout.addView(item, 120, 120);
//			item.setBackgroundResource(R.drawable.board_photo_bg);
			ImageView itemPenType = item.findViewById(R.id.penType);
			TextView itemPenName = item.findViewById(R.id.penName);
			if (penType == LysBoardDrawingType.Dummy)
			{
				itemPenType.setImageDrawable(null);
				itemPenName.setText("");
			}
			else
			{
				itemPenType.setImageResource(PopPen.getPenRes(penType));
				itemPenName.setText(LysBoardDrawingType.getName(penType));
				item.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View view)
					{
						dismiss();
						if (clickListener != null)
							clickListener.onClick(penType);
					}
				});
			}
		}

	}

	public static void show(Context context, View view, OnClickListener clickListener)
	{
		show(context, view, false, clickListener);
	}

	public static void show(Context context, View view, boolean debug, OnClickListener clickListener)
	{
		PopPen pop = new PopPen(context, //
				KitUtils.getPenType(), LysBoardDrawingType.Mark, LysBoardDrawingType.Water, LysBoardDrawingType.FullLine, LysBoardDrawingType.ImaginaryLine, LysBoardDrawingType.ArrowsLine, //
				LysBoardDrawingType.Triangle, LysBoardDrawingType.RightAngled, LysBoardDrawingType.Isosceles, LysBoardDrawingType.EquilateralTriangle, LysBoardDrawingType.Square, LysBoardDrawingType.Rectangle, //
				LysBoardDrawingType.Circle, LysBoardDrawingType.Oval, LysBoardDrawingType.Hyperbola2, LysBoardDrawingType.LrParabola, LysBoardDrawingType.Coord, LysBoardDrawingType.Dummy, //
				LysBoardDrawingType.UdParabola, LysBoardDrawingType.Hyperbola, LysBoardDrawingType.Sine, LysBoardDrawingType.Tan, LysBoardDrawingType.Dummy, debug ? LysBoardDrawingType.Stroke : LysBoardDrawingType.Dummy, //
				LysBoardDrawingType.Pyramid, LysBoardDrawingType.Cube, LysBoardDrawingType.Cone, LysBoardDrawingType.Cylinder, LysBoardDrawingType.Ball, debug ? LysBoardDrawingType.Brush : LysBoardDrawingType.Dummy);
		pop.setClickListener(clickListener);
		pop.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		int windowHeight = pop.getContentView().getMeasuredHeight();
		pop.showAsDropDown(view, view.getWidth() + 18, -(view.getHeight() + windowHeight) / 2);
	}

}
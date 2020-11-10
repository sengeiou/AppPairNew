package com.lys.base.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

public class DrawableFactory
{
	public static int getColor(Context context, @ColorRes int id)
	{
		return ContextCompat.getColor(context, id);
	}

	public static Drawable getDrawable(Context context, @DrawableRes int id)
	{
		return ContextCompat.getDrawable(context, id);
//		return App.getContext().getResources().getDrawable(id);
	}

	public static Drawable getTintDrawable(Context context, @DrawableRes int id, int tintColor)
	{
		Drawable drawable = getDrawable(context, id);
		Drawable.ConstantState state = drawable.getConstantState();
		Drawable drawableNew = DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
		drawableNew.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		DrawableCompat.setTint(drawableNew, tintColor);
		return drawableNew;
	}

	public static Drawable getAlphaDrawable(Context context, @DrawableRes int id, int alpha)
	{
		Drawable drawable = getDrawable(context, id).mutate();
		drawable.setAlpha(alpha);
		return drawable;
	}

	public static ColorDrawable createColorDrawable(@ColorInt int color)
	{
		return new ColorDrawable(color);
	}

	public static GradientDrawable createRoundFillStroke(int roundRadius, int fillColor, int strokeColor, int strokeWidth)
	{
		GradientDrawable gd = new GradientDrawable();
		gd.setCornerRadius(roundRadius);
		gd.setColor(fillColor);
		gd.setStroke(strokeWidth, strokeColor);
		return gd;
	}

	public static GradientDrawable createRoundFill(int roundRadius, int fillColor)
	{
		GradientDrawable gd = new GradientDrawable();
		gd.setCornerRadius(roundRadius);
		gd.setColor(fillColor);
		return gd;
	}

	public static GradientDrawable createRoundStroke(int roundRadius, int strokeColor, int strokeWidth)
	{
		GradientDrawable gd = new GradientDrawable();
		gd.setCornerRadius(roundRadius);
		gd.setStroke(strokeWidth, strokeColor);
		return gd;
	}

	public static GradientDrawable createRectFillStroke(int fillColor, int strokeColor, int strokeWidth)
	{
		GradientDrawable gd = new GradientDrawable();
		gd.setColor(fillColor);
		gd.setStroke(strokeWidth, strokeColor);
		return gd;
	}

	public static GradientDrawable createRectFill(int fillColor)
	{
		GradientDrawable gd = new GradientDrawable();
		gd.setColor(fillColor);
		return gd;
	}

	public static GradientDrawable createRectStroke(int strokeColor, int strokeWidth)
	{
		GradientDrawable gd = new GradientDrawable();
		gd.setStroke(strokeWidth, strokeColor);
		return gd;
	}

	public static ShapeDrawable createCircleFill(int fillColor)
	{
		ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
		drawable.getPaint().setColor(fillColor);
		return drawable;
	}

	public static ShapeDrawable createCircleStroke(@ColorInt int strokeColor, int strokeWidth)
	{
		ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
		drawable.getPaint().setColor(strokeColor);
		drawable.getPaint().setStrokeWidth(strokeWidth);
		drawable.getPaint().setStyle(Paint.Style.STROKE);
		return drawable;
	}

	public static StateListDrawable createStateColorDrawable(@ColorInt int normal, @ColorInt int select)
	{
		return createStateDrawable(createColorDrawable(normal), createColorDrawable(select));
	}

	public static StateListDrawable createStateDrawable(Context context, @DrawableRes int normalResId, @DrawableRes int selectResId)
	{
		return createStateDrawable(getDrawable(context, normalResId), getDrawable(context, selectResId));
	}

	public static StateListDrawable createStateDrawable(Drawable normal, Drawable select)
	{
		StateListDrawable drawable = new StateListDrawable();
		drawable.addState(new int[]{android.R.attr.state_selected}, select);
		drawable.addState(new int[]{android.R.attr.state_pressed}, select);
		drawable.addState(new int[]{android.R.attr.state_checked}, select);
		drawable.addState(new int[]{}, normal); // normal必须放到最后
		return drawable;
	}

	public static ColorStateList createStateColor(@ColorInt int normal, @ColorInt int select)
	{
		int[] colors = new int[]{select, select, select, normal};
		int[][] states = new int[4][];
		states[0] = new int[]{android.R.attr.state_selected};
		states[1] = new int[]{android.R.attr.state_pressed};
		states[2] = new int[]{android.R.attr.state_checked};
		states[3] = new int[]{};
		ColorStateList colorList = new ColorStateList(states, colors);
		return colorList;
	}

	public static AnimationDrawable createFrameAnimation(Context context, int duration, boolean loop, int... ids)
	{
		AnimationDrawable drawable = new AnimationDrawable();
		for (int id : ids)
			drawable.addFrame(getDrawable(context, id), duration);
		drawable.setOneShot(!loop);
		return drawable;
	}
}

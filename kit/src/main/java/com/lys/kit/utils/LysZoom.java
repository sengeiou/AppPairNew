package com.lys.kit.utils;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

public class LysZoom implements View.OnTouchListener
{
	public interface OnTip
	{
		void onTip();
	}

	private OnTip mOnTip = null;

	public void setOnTip(OnTip onTip)
	{
		mOnTip = onTip;
	}

	private View target;

	public LysZoom(View con, View target)
	{
		con.setOnTouchListener(this);
		this.target = target;
	}

	public LysZoom(View target)
	{
		this.target = target;
	}

	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	private int mode = NONE;

	// 第一个按下的手指的点
	private PointF startPoint = new PointF();
	// 两个按下的手指的触摸点的中点
	private PointF midPoint = new PointF();
	// 初始的两个手指按下的触摸点的距离
	private float initDis = 1f;

	private MyMatrix matrix = new MyMatrix();
	private MyMatrix savedMatrix = new MyMatrix();

	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		return onTouch(event);
	}

	private boolean isTip = false;

	public boolean onTouch(MotionEvent event)
	{
		// 进行与操作是为了判断多点触摸
		switch (event.getAction() & MotionEvent.ACTION_MASK)
		{
		case MotionEvent.ACTION_DOWN:
			// 第一个手指按下事件
			isTip = true;
			matrix.set(target);
			savedMatrix.set(matrix);
			startPoint.set(event.getX(), event.getY());
			mode = DRAG;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			// 第二个手指按下事件
			isTip = false;
			initDis = distance(event);
			savedMatrix.set(matrix);
			midPoint = middle(event);
			savedMatrix.setPivot(savedMatrix.getPoint(midPoint));
			mode = ZOOM;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			// 手指放开事件
			mode = NONE;
			if (isTip)
			{
				if (mOnTip != null)
					mOnTip.onTip();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			// 手指滑动事件
			if (isTip && distance(startPoint, new PointF(event.getX(), event.getY())) >= 1)
				isTip = false;
			if (mode == DRAG)
			{
				// 是一个手指拖动
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
			}
			else if (mode == ZOOM)
			{
				// 两个手指滑动
				float newDist = distance(event);
				matrix.set(savedMatrix);
				float scale = newDist / initDis;
				matrix.postScale(scale, scale);
				matrix.checkScale(0.2f, 6, 0.2f, 6);
			}
			break;
		}
		matrix.apply(target);
		return true;
	}

	private float distance(PointF p1, PointF p2)
	{
		float x = p1.x - p2.x;
		float y = p1.y - p2.y;
		return (float) Math.sqrt(x * x + y * y);
	}

	// 计算两个触摸点之间的距离
	private float distance(MotionEvent event)
	{
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

	// 计算两个触摸点的中点
	private PointF middle(MotionEvent event)
	{
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		return new PointF(x / 2, y / 2);
	}

	private class MyMatrix
	{
		private float translationX;
		private float translationY;
		private float scaleX = 1;
		private float scaleY = 1;
		private float pivotX;
		private float pivotY;

		private void set(View view)
		{
			translationX = view.getTranslationX();
			translationY = view.getTranslationY();
			scaleX = view.getScaleX();
			scaleY = view.getScaleY();
			pivotX = view.getPivotX();
			pivotY = view.getPivotY();
		}

		private void set(MyMatrix matrix)
		{
			translationX = matrix.translationX;
			translationY = matrix.translationY;
			scaleX = matrix.scaleX;
			scaleY = matrix.scaleY;
			pivotX = matrix.pivotX;
			pivotY = matrix.pivotY;
		}

		private PointF getPoint(PointF p)
		{
			float x = (p.x - pivotX * (1 - scaleX) - translationX) / scaleX;
			float y = (p.y - pivotY * (1 - scaleY) - translationY) / scaleY;
			return new PointF(x, y);
		}

		private void postPivot(PointF pivot)
		{
			postPivot(pivot.x, pivot.y);
		}

		private void postPivot(float px, float py)
		{
			pivotX += px;
			pivotY += py;
			translationX -= px * (1 - scaleX);
			translationY -= py * (1 - scaleY);
		}

		private void setPivot(PointF pivot)
		{
			setPivot(pivot.x, pivot.y);
		}

		private void setPivot(float px, float py)
		{
			postPivot(px - pivotX, py - pivotY);
		}

		private void postTranslate(float dx, float dy)
		{
			translationX += dx;
			translationY += dy;
		}

		private void setTranslate(float dx, float dy)
		{
			translationX = dx;
			translationY = dy;
		}

		private void postScale(float sx, float sy)
		{
			scaleX *= sx;
			scaleY *= sy;
		}

		private void setScale(float sx, float sy)
		{
			scaleX = sx;
			scaleY = sy;
		}

		private void checkScale(float sxMin, float sxMax, float syMin, float syMax)
		{
			scaleX = Math.max(scaleX, sxMin);
			scaleX = Math.min(scaleX, sxMax);
			scaleY = Math.max(scaleY, syMin);
			scaleY = Math.min(scaleY, syMax);
		}

		private void apply(View view)
		{
			view.setTranslationX(translationX);
			view.setTranslationY(translationY);
			view.setScaleX(scaleX);
			view.setScaleY(scaleY);
			view.setPivotX(pivotX);
			view.setPivotY(pivotY);
		}
	}
}

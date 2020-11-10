package com.lys.kit.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.lys.base.utils.LysPoint;
import com.lys.base.view.ClickImageView;

import java.util.ArrayList;

@SuppressLint("AppCompatCustomView")
public class HeadImageView extends ClickImageView
{
	public HeadImageView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
	}

	private Bitmap genBitmap(Bitmap bitmapRaw)
	{
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setStyle(Paint.Style.FILL);

		ArrayList<LysPoint> points = new ArrayList<>();
		points.add(new LysPoint(0, getHeight() / 2));
		points.add(new LysPoint(getWidth() / 4, 0));
		points.add(new LysPoint(getWidth() * 3 / 4, 0));
		points.add(new LysPoint(getWidth(), getHeight() / 2));
		points.add(new LysPoint(getWidth() * 3 / 4, getHeight()));
		points.add(new LysPoint(getWidth() / 4, getHeight()));

		Path path = new Path();
		for (int i = 0; i < points.size(); i++)
		{
			LysPoint point = points.get(i);
			if (i == 0)
				path.moveTo(point.x, point.y);
			else
				path.lineTo(point.x, point.y);
		}
		path.close();
		canvas.drawPath(path, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

		canvas.drawBitmap(bitmapRaw, 0, 0, paint);
		return bitmap;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		Bitmap bitmapRaw = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvasRaw = new Canvas(bitmapRaw);
		super.onDraw(canvasRaw);

		Bitmap bitmap = genBitmap(bitmapRaw);
		canvas.drawBitmap(bitmap, 0, 0, null);
		bitmap.recycle();

		bitmapRaw.recycle();
	}

}

package com.lys.kit.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.lys.base.view.ClickImageView;

@SuppressLint("AppCompatCustomView")
public class IconImageView extends ClickImageView
{
	public IconImageView(Context context, @Nullable AttributeSet attrs)
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

		canvas.drawRoundRect(0, 0, getWidth(), getHeight(), getWidth() * 18 / 100, getHeight() * 18 / 100, paint);

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

package com.lys.kit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lys.base.utils.ImageLoader;
import com.lys.base.utils.SysUtils;
import com.lys.kit.R;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.utils.LysZoom;

public class DialogPic extends Dialog
{
	private class Holder
	{
		private FrameLayout picCon;
		private ImageView pic;
		private ProgressBar loading;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.picCon = findViewById(R.id.picCon);
		holder.pic = findViewById(R.id.pic);
		holder.loading = findViewById(R.id.loading);
	}

	private DialogPic(@NonNull Context context)
	{
		super(context, R.style.FullDialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_pic);
		initHolder();
		LysZoom zoom = new LysZoom(holder.picCon, holder.pic);
		zoom.setOnTip(new LysZoom.OnTip()
		{
			@Override
			public void onTip()
			{
				dismiss();
			}
		});
	}

	@Override
	public void dismiss()
	{
		super.dismiss();
	}

	private void loadPic(String url)
	{
		holder.loading.setVisibility(View.VISIBLE);
		ImageLoad.displayImage(getContext(), url, holder.pic, R.drawable.img_default, new ImageLoader.OnDisplay()
		{
			@Override
			public void success(Bitmap bitmap, String url)
			{
				FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.pic.getLayoutParams();
				layoutParams.width = bitmap.getWidth();
				layoutParams.height = bitmap.getHeight();
				holder.pic.setLayoutParams(layoutParams);
				holder.pic.setTranslationX((SysUtils.screenWidth(getContext()) - bitmap.getWidth()) / 2);
				holder.pic.setTranslationY(Math.max(0, (SysUtils.screenHeight(getContext()) - bitmap.getHeight()) / 2));
				holder.loading.setVisibility(View.GONE);
			}
		});
	}

	public static void show(Context context, String url)
	{
		DialogPic dialog = new DialogPic(context);
		dialog.loadPic(url);
		dialog.show();
	}

}
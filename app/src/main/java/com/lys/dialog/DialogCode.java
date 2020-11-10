package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.king.zxing.util.CodeUtils;
import com.lys.app.R;
import com.lys.base.utils.LOG;

public class DialogCode extends Dialog
{
	private class Holder
	{
		private ImageView qrcode;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.qrcode = findViewById(R.id.qrcode);
	}

	private DialogCode(@NonNull Context context)
	{
		super(context, R.style.SimpleDialog);
		setContentView(R.layout.dialog_code);
		initHolder();
	}

	@Override
	public void dismiss()
	{
		super.dismiss();
	}

	private void setCode(String code)
	{
		LOG.v("code:" + code);
		try
		{
			Bitmap bitmap = CodeUtils.createQRCode(code, 750);
//			Bitmap bitmap = EncodingHandler.createQRCode(code, 750);
			holder.qrcode.setImageBitmap(bitmap);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void show(Context context, String code)
	{
		DialogCode dialog = new DialogCode(context);
		dialog.setCode(code);
		dialog.show();
	}
}
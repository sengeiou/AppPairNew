package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.king.zxing.util.CodeUtils;
import com.lys.app.R;
import com.lys.base.utils.LOG;

public class DialogAddFriend extends Dialog implements View.OnClickListener
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

	private DialogAddFriend(@NonNull Context context)
	{
		super(context, R.style.FullDialog);
		setContentView(R.layout.dialog_add_friend);
		initHolder();
		findViewById(R.id.con).setOnClickListener(this);
	}

	@Override
	public void dismiss()
	{
		super.dismiss();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.con)
		{
			dismiss();
		}
	}

	private void setCode(String code)
	{
		LOG.v("code:" + code);
		try
		{
			Bitmap bitmap = CodeUtils.createQRCode(code, 460);
//			Bitmap bitmap = EncodingHandler.createQRCode(code, 460);
			holder.qrcode.setImageBitmap(bitmap);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void show(Context context, String code)
	{
		DialogAddFriend dialog = new DialogAddFriend(context);
		dialog.setCode(code);
		dialog.show();
	}
}
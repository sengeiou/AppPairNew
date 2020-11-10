package com.lys.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.king.zxing.CaptureActivity;

// 扫描二维码
public class CaptureActivityLandscape extends CaptureActivity
{
	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}

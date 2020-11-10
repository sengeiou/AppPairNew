package com.lys.kit.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.lys.kit.AppKit;
import com.lys.kit.R;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.manager.CacheManager;
import com.lys.kit.pop.PopInsert;
import com.lys.kit.view.BoardToolBar;
import com.lys.kit.view.BoardView;
import com.lys.protobuf.SPhotoAddParam;

import java.io.File;

public class ActivityBoard extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
		private BoardToolBar toolBar;
		private BoardView board;
		private TextView btnResult;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.toolBar = findViewById(R.id.toolBar);
		holder.board = findViewById(R.id.board);
		holder.btnResult = findViewById(R.id.btnResult);
	}

	private File dir;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void init()
	{
		super.init();
		setContentView(R.layout.activity_board);

		initHolder();

//		findViewById(R.id.close).setVisibility(shouldShowClose() ? View.VISIBLE : View.GONE);
//		findViewById(R.id.close).setOnClickListener(this);

		if (AppKit.isStudent())
		{
			holder.toolBar.setInsert(true, PopInsert.IconImageScreen, PopInsert.IconImageVideo, PopInsert.IconImageTopic, PopInsert.IconImageSelectionGroup);
		}
		else
		{
			holder.toolBar.setInsert(true, PopInsert.IconImageScreen, PopInsert.IconImageVideo);
		}
		holder.toolBar.setWeike(false);

		holder.toolBar.hideIconSend();
		holder.toolBar.hideIconGrid();
		holder.toolBar.hideIconAddPage();

		holder.toolBar.justSelectImage();

		holder.toolBar.setListener(toolBarListener);

		dir = Config.tmpRecordBoardDir;

		if (getIntent().hasExtra("btnText"))
			holder.btnResult.setText(getIntent().getStringExtra("btnText"));

		findViewById(R.id.btnResult).setOnClickListener(this);

		holder.board.setMenu(holder.toolBar);
		holder.toolBar.bindBoard(holder.board);
		holder.board.setListener(new BoardView.OnListener()
		{
			@Override
			public void onLockChanged(boolean isLock)
			{
				holder.toolBar.photoLockSetChecked(isLock);
			}

			@Override
			public void onPaste()
			{
				holder.toolBar.onPaste();
			}
		});

		holder.board.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
		{
			@Override
			public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
			{
				view.removeOnLayoutChangeListener(this);
				holder.board.loadBoard(dir, new BoardView.OnLoadBoardCallback()
				{
					@Override
					public void onLoadOver()
					{
						if (getIntent().hasExtra("bitmapData"))
						{
							byte[] bitmapData = CacheManager.instance().getAndRemoveCache(getIntent().getStringExtra("bitmapData"));
							SPhotoAddParam param = new SPhotoAddParam();
							param.notEye = true;
							param.doNotActive = true;
							holder.board.addPhoto(bitmapData, param);
							holder.board.setNoModify();
						}
					}
				});
			}
		});

//		holder.board.setBoardHeight(SysUtils.screenHeight(context));
	}

	private boolean toClose()
	{
		if (holder.board.hasModify())
		{
			DialogAlert.show(context, "是否退出？", null, new DialogAlert.OnClickListener()
			{
				@Override
				public void onClick(int which)
				{
					if (which == 1)
					{
						finish();
					}
				}
			}, "否", "退出");
			return true;
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (toClose())
				return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	//------------------- 点击事件处理（开始） --------------------------

	private BoardToolBar.OnListener toolBarListener = new BoardToolBar.OnListener() {};

	@Override
	public void onClick(View view)
	{
//		if (view.getId() == R.id.close)
//		{
//			if (!toClose())
//				finish();
//		}
		if (view.getId() == R.id.btnResult)
		{
			holder.board.genAnswer(Color.WHITE);
			Intent intent = new Intent();
			intent.putExtra("path", BoardView.getAnswerFile(dir).toString());
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	}

	//------------------- 点击事件处理（结束） --------------------------

}

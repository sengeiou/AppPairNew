package com.lys.board.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.lys.board.R;
import com.lys.board.config.BoardConfig;

public class DialogSelectBoardBg extends Dialog implements View.OnClickListener
{
	public interface OnListener
	{
		void onSelect(int boardBg);
	}

	private OnListener listener = null;

	public void setListener(OnListener listener)
	{
		this.listener = listener;
	}

	public DialogSelectBoardBg(@NonNull Context context)
	{
		super(context, R.style.Dialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_select_board_bg);

		findViewById(R.id.bgDefault).setOnClickListener(this);
		findViewById(R.id.bgLine).setOnClickListener(this);
		findViewById(R.id.bgNone).setOnClickListener(this);

	}

	@Override
	public void dismiss()
	{
		super.dismiss();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.bgDefault)
		{
			dismiss();
			if (listener != null)
				listener.onSelect(BoardConfig.BoardBgDefault);
		}
		else if (view.getId() == R.id.bgLine)
		{
			dismiss();
			if (listener != null)
				listener.onSelect(BoardConfig.BoardBgLine);
		}
		else if (view.getId() == R.id.bgNone)
		{
			dismiss();
			if (listener != null)
				listener.onSelect(BoardConfig.BoardBgTransparent);
		}
	}

}
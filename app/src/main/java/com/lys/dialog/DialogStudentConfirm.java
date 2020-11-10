package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lys.App;
import com.lys.app.R;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_TeachConfirmByStudent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DialogStudentConfirm extends Dialog implements View.OnClickListener
{
	public interface OnListener
	{
		void onResult();
	}

	private OnListener listener = null;

	private void setListener(OnListener listener)
	{
		this.listener = listener;
	}

	private class Holder
	{
		private TextView text;
		private EditText inputMsg;
		private TextView btnCommit;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.text = findViewById(R.id.text);
		holder.inputMsg = findViewById(R.id.inputMsg);
		holder.btnCommit = findViewById(R.id.btnCommit);
	}

	private static final SimpleDateFormat formatDate = new SimpleDateFormat("HH点mm分");

	private String teachId;
	private String userId;
	private String targetId;

	private DialogStudentConfirm(@NonNull Context context, String teachId, String userId, String targetId, long startTime)
	{
		super(context, R.style.FullDialog);
		setCancelable(false);
		setContentView(R.layout.dialog_student_confirm);
		initHolder();

		this.teachId = teachId;
		this.userId = userId;
		this.targetId = targetId;

		findViewById(R.id.btnQuestion).setOnClickListener(this);
		findViewById(R.id.btnOk).setOnClickListener(this);
		findViewById(R.id.btnCommit).setOnClickListener(this);

		long currTime = System.currentTimeMillis();
		holder.text.setText(Html.fromHtml(String.format("&nbsp;&nbsp;&nbsp;&nbsp;<font color='#00ff00'>%s</font> 同学<br>&nbsp;&nbsp;&nbsp;&nbsp;本次课程从 <font color='#00ff00'>%s</font> 开始，至 <font color='#00ff00'>%s</font> 结束，共计 <font color='#00ff00'>%s</font>，请您确认。", //
				App.name(), formatDate.format(new Date(startTime)), formatDate.format(new Date(currTime)), formatTime(currTime - startTime))));

		holder.inputMsg.setVisibility(View.GONE);
		holder.btnCommit.setVisibility(View.GONE);
	}

	public static String formatTime(long ms)
	{
		long second = ms / 1000;
		long minute = second / 60;
		long hour = minute / 60;
		minute = minute % 60;
		if (hour == 0)
			return String.format("%d分钟", minute);
		else
			return String.format("%d小时%d分钟", hour, minute);
	}

	@Override
	public void dismiss()
	{
		super.dismiss();
		if (listener != null)
			listener.onResult();
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.btnQuestion:
			holder.inputMsg.setVisibility(View.VISIBLE);
			holder.btnCommit.setVisibility(View.VISIBLE);
			break;

		case R.id.btnOk:
			doConfirm("");
			break;

		case R.id.btnCommit:
			doConfirm(holder.inputMsg.getText().toString().trim());
			break;
		}
	}

	public void doConfirm(String msg)
	{
		SRequest_TeachConfirmByStudent request = new SRequest_TeachConfirmByStudent();
		request.teachId = teachId;
		request.userId = userId;
		request.targetId = targetId;
		request.confirmMsg = msg;
		Protocol.doPost(getContext(), App.getApi(), SHandleId.TeachConfirmByStudent, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					dismiss();
				}
			}
		});
	}

	public static void show(Context context, String teachId, String userId, String targetId, long startTime, OnListener listener)
	{
		DialogStudentConfirm dialog = new DialogStudentConfirm(context, teachId, userId, targetId, startTime);
		dialog.setListener(listener);
		dialog.show();
	}

}
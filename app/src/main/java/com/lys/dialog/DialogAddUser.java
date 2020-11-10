package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.protobuf.SRequest_AddUser;
import com.lys.protobuf.SSex;
import com.lys.protobuf.SUserType;

public class DialogAddUser extends Dialog implements View.OnClickListener
{
	public interface OnAddUserListener
	{
		void onResult(SRequest_AddUser request);
	}

	private OnAddUserListener listener = null;

	private void setListener(OnAddUserListener listener)
	{
		this.listener = listener;
	}

	private class Holder
	{
		private EditText account;
		private EditText psw;
		private EditText userName;

		private RadioButton typeMaster;
		private RadioButton typeTeacher;
		private RadioButton typeStudent;

		private RadioButton sexGirl;
		private RadioButton sexBoy;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.account = findViewById(R.id.account);
		holder.psw = findViewById(R.id.psw);
		holder.userName = findViewById(R.id.userName);

		holder.typeMaster = findViewById(R.id.typeMaster);
		holder.typeTeacher = findViewById(R.id.typeTeacher);
		holder.typeStudent = findViewById(R.id.typeStudent);

		holder.sexGirl = findViewById(R.id.sexGirl);
		holder.sexBoy = findViewById(R.id.sexBoy);
	}

	private DialogAddUser(@NonNull Context context)
	{
		super(context, R.style.Dialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_add_user);
		initHolder();
		findViewById(R.id.add).setOnClickListener(this);
	}

	@Override
	public void dismiss()
	{
		super.dismiss();
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.add:
			add();
			break;
		}
	}

	private void add()
	{
		SRequest_AddUser request = new SRequest_AddUser();
		request.userId = holder.account.getText().toString().trim();
		request.name = holder.userName.getText().toString().trim();
		request.psw = holder.psw.getText().toString().trim();

		request.head = "";

		if (TextUtils.isEmpty(request.userId))
		{
			LOG.toast(getContext(), "账号不能为空");
			return;
		}
		if (TextUtils.isEmpty(request.psw))
		{
			LOG.toast(getContext(), "密码不能为空");
			return;
		}
		if (TextUtils.isEmpty(request.name))
		{
			LOG.toast(getContext(), "用户名不能为空");
			return;
		}

		if (holder.typeMaster.isChecked())
			request.userType = SUserType.Master;
		else if (holder.typeTeacher.isChecked())
			request.userType = SUserType.Teacher;
		else if (holder.typeStudent.isChecked())
			request.userType = SUserType.Student;
		else
		{
			LOG.toast(getContext(), "请选择用户类型");
			return;
		}

		if (holder.sexGirl.isChecked())
			request.sex = SSex.Girl;
		else if (holder.sexBoy.isChecked())
			request.sex = SSex.Boy;
		else
		{
			LOG.toast(getContext(), "请选择性别");
			return;
		}

		dismiss();
		if (listener != null)
			listener.onResult(request);
	}

	public static void show(Context context, OnAddUserListener listener)
	{
		DialogAddUser dialog = new DialogAddUser(context);
		dialog.setListener(listener);
		dialog.show();
	}

}
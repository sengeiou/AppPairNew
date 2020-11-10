package com.lys.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lys.App;
import com.lys.activity.ActivityMain;
import com.lys.app.R;
import com.lys.base.fragment.BaseFragment;
import com.lys.base.utils.DrawableFactory;
import com.lys.base.utils.LOG;
import com.lys.base.utils.SysUtils;
import com.lys.dialog.DialogLogin;
import com.lys.kit.AppKit;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_AddUser;
import com.lys.protobuf.SResponse_AddUser;
import com.lys.protobuf.SUserType;

public class FragmentNewUser extends BaseFragment implements View.OnClickListener
{
	private class Holder
	{
		//		private TextView deviceId;
//		private ImageView deviceCode;
		private EditText userName;

//		private RadioButton typeStudent;
//		private RadioButton typeTeacher;

	}

	private Holder holder = new Holder();

	private void initHolder(View view)
	{
//		holder.deviceId = view.findViewById(R.id.deviceId);
//		holder.deviceCode = view.findViewById(R.id.deviceCode);
		holder.userName = view.findViewById(R.id.userName);

//		holder.typeStudent = view.findViewById(R.id.typeStudent);
//		holder.typeTeacher = view.findViewById(R.id.typeTeacher);

	}

	private ActivityMain getMainActivity()
	{
		return (ActivityMain) getActivity();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActivity().getWindow().setBackgroundDrawable(DrawableFactory.createColorDrawable(0xfffcfcfc));
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_new_user, container, false);
		initHolder(view);

//		holder.deviceId.setText(App.Account);

//		Bitmap bitmap = CodeUtils.createQRCode(App.Account, 470);
//		holder.deviceCode.setImageBitmap(bitmap);

		view.findViewById(R.id.submit).setOnClickListener(this);
		view.findViewById(R.id.teacherLogin).setOnClickListener(this);

		return view;
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.submit)
		{
			doSubmit();
//			getMainActivity().gotoUser(false);
		}
		else if (view.getId() == R.id.teacherLogin)
		{
//			Intent intent = new Intent(context, ActivityLogin.class);
//			startActivity(intent);
			DialogLogin.show(context, new DialogLogin.OnLoginListener()
			{
				@Override
				public void onLogin(String account, String psw)
				{
					getMainActivity().doLogin(account, psw);
				}
			});
		}
	}

	private void doSubmit()
	{
		String userName = holder.userName.getText().toString().trim();
		if (TextUtils.isEmpty(userName))
		{
			LOG.toast(context, "用户名不能为空");
			return;
		}

//		Integer userType = 0;
//		if (holder.typeStudent.isChecked())
//			userType = SUserType.Student;
//		else if (holder.typeTeacher.isChecked())
//			userType = SUserType.Teacher;
//		if (userType.equals(0))
//		{
//			LOG.toast(context, "请选择用户类型");
//			return;
//		}

		SysUtils.hideKeybord(getMainActivity());

		SRequest_AddUser request = new SRequest_AddUser();
		request.userId = AppKit.OnlyId;
		request.userType = SUserType.Student;
		request.name = userName;
		request.head = "";
		request.psw = "123";
		Protocol.doPost(context, App.getApi(), SHandleId.AddUser, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_AddUser response = SResponse_AddUser.load(data);
//					LOG.toast(context, "添加成功");
					getMainActivity().doLogin(AppKit.OnlyId, "123");
				}
			}
		});

	}

}

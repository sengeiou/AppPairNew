package com.lys.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;
import com.lys.App;
import com.lys.adapter.AdapterUserManager;
import com.lys.app.R;
import com.lys.base.fragment.BaseFragment;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_GetUserList;
import com.lys.protobuf.SResponse_GetUserList;
import com.lys.protobuf.SUser;

import java.util.Collections;
import java.util.Comparator;

public class FragmentUserManager extends BaseFragment
{
	private class Holder
	{
		private EditText keyword;
		private TextView count;
	}

	private Holder holder = new Holder();

	private void initHolder(View view)
	{
		holder.keyword = view.findViewById(R.id.keyword);
		holder.count = view.findViewById(R.id.count);
	}

	private View view = null;
	private int userType;

	private RecyclerView recyclerView;
	private AdapterUserManager adapter;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		if (view == null)
		{
			view = inflater.inflate(R.layout.fragment_user_manager, container, false);
			initHolder(view);

			userType = getArguments().getInt("userType");

			recyclerView = view.findViewById(R.id.recyclerView);
			recyclerView.setLayoutManager(new LinearLayoutManager(context));
			adapter = new AdapterUserManager(this);
			recyclerView.setAdapter(adapter);

			holder.keyword.addTextChangedListener(new TextWatcher()
			{
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after)
				{
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count)
				{
					adapter.setFilterText(s.toString());
					holder.count.setText(String.valueOf(adapter.getItemCount()));
				}

				@Override
				public void afterTextChanged(Editable s)
				{
				}
			});

			request();
		}
		return view;
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
	}

	public void request()
	{
		SRequest_GetUserList request = new SRequest_GetUserList();
		request.userType = userType;
		Protocol.doPost(context, App.getApi(), SHandleId.GetUserList, request.saveToStr(), new Protocol.OnCallback()
		{
			@Override
			public void onResponse(int code, String data, String msg)
			{
				if (code == 200)
				{
					SResponse_GetUserList response = SResponse_GetUserList.load(data);
					Collections.sort(response.users, new Comparator<SUser>()
					{
						@Override
						public int compare(SUser user1, SUser user2)
						{
							String pinyin1 = Pinyin.toPinyin(user1.name, "");
							String pinyin2 = Pinyin.toPinyin(user2.name, "");
							return pinyin1.compareTo(pinyin2);
						}
					});
					adapter.setData(response.users);
					holder.count.setText(String.valueOf(adapter.getItemCount()));
				}
			}
		});
	}

}

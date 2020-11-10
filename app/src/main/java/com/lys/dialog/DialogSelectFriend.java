package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lys.adapter.AdapterSelectFriend;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.protobuf.SUser;
import com.lys.utils.UserHelper;
import com.lys.utils.UserTreeNode;

import java.util.List;

public class DialogSelectFriend extends Dialog implements View.OnClickListener
{
	public interface OnListener
	{
		void onSelect(List<SUser> selectedList);
	}

	private OnListener listener = null;

	private void setListener(OnListener listener)
	{
		this.listener = listener;
	}

	private class Holder
	{
		private TextView selectCount;
		private EditText keyword;
		private TextView count;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.selectCount = findViewById(R.id.selectCount);
		holder.keyword = findViewById(R.id.keyword);
		holder.count = findViewById(R.id.count);
	}

	private RecyclerView recyclerView;
	private AdapterSelectFriend adapter;

	private DialogSelectFriend(@NonNull Context context, String userId)
	{
		super(context, R.style.FullDialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_select_friend);
		initHolder();

		findViewById(R.id.con).setOnClickListener(this);

		findViewById(R.id.cancel).setOnClickListener(this);
		findViewById(R.id.ok).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		adapter = new AdapterSelectFriend(new AdapterSelectFriend.OnChangeListener()
		{
			@Override
			public void onChange()
			{
				holder.selectCount.setText(String.format("已选 %s 人", adapter.getSelectedUsers().size()));
			}
		});
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
				holder.count.setText(String.valueOf(adapter.getShowTaskCount()));
			}

			@Override
			public void afterTextChanged(Editable s)
			{
			}
		});

		UserHelper.requestUserTree(context, userId, new UserHelper.onUserTreeCallback()
		{
			@Override
			public void onResult(List<UserTreeNode> treeNodes)
			{
				if (treeNodes != null)
				{
					adapter.setData(treeNodes);
					holder.count.setText(String.valueOf(adapter.getShowTaskCount()));
				}
			}
		});
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
		case R.id.con:
			dismiss();
			break;

		case R.id.cancel:
			dismiss();
			break;

		case R.id.ok:
			if (adapter.isReady())
			{
				if (adapter.getSelectedUsers().size() > 0)
				{
					dismiss();
					if (listener != null)
						listener.onSelect(adapter.getSelectedUsers());
				}
				else
				{
					LOG.toast(getContext(), "未选择学生");
				}
			}
			else
			{
				LOG.toast(getContext(), "数据未加载");
			}
			break;
		}
	}

	public static void show(Context context, String userId, OnListener listener)
	{
		DialogSelectFriend dialog = new DialogSelectFriend(context, userId);
		dialog.setListener(listener);
		dialog.show();
	}

}
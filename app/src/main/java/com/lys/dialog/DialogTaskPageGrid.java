package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lys.adapter.AdapterTaskSmall;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.config.AppConfig;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogAlert;
import com.lys.protobuf.SClipboard;
import com.lys.protobuf.SClipboardType;
import com.lys.protobuf.SNotePage;
import com.lys.protobuf.SPTask;

import java.util.List;
import java.util.Map;

public class DialogTaskPageGrid extends Dialog implements View.OnClickListener
{
	public interface OnListener
	{
		void onSelect(int index);

		void onDelete(Map<String, SNotePage> deleteMap);

		boolean onMove(Map<String, SNotePage> moveMap, boolean isNext);
	}

	private OnListener listener = null;

	public void setListener(OnListener listener)
	{
		this.listener = listener;
	}

	private class Holder
	{
		private ViewGroup menuBar;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.menuBar = findViewById(R.id.menuBar);
	}

	private RecyclerView recyclerView;
	private AdapterTaskSmall adapter;

	public DialogTaskPageGrid(@NonNull Context context, SPTask task, List<SNotePage> pages, boolean modeIsSync)
	{
		super(context, R.style.FullDialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_task_page_grid);
		initHolder();

		holder.menuBar.setVisibility(View.GONE);

		findViewById(R.id.close).setOnClickListener(this);
		findViewById(R.id.movePrev).setOnClickListener(this);
		findViewById(R.id.moveNext).setOnClickListener(this);
		findViewById(R.id.delete).setOnClickListener(this);
		findViewById(R.id.copy).setOnClickListener(this);
		findViewById(R.id.cancel).setOnClickListener(this);

		if (modeIsSync)
		{
			findViewById(R.id.movePrev).setVisibility(View.GONE);
			findViewById(R.id.moveNext).setVisibility(View.GONE);
			findViewById(R.id.copy).setVisibility(View.GONE);
		}

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
		adapter = new AdapterTaskSmall(this, task, pages);
		recyclerView.setAdapter(adapter);
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
		case R.id.close:
			dismiss();
			break;
		case R.id.movePrev:
			if (adapter.selectMap.size() == 0)
			{
				DialogAlert.show(getContext(), "", "请选择要移动的页！", null, "我知道了");
			}
			else
			{
				if (listener.onMove(adapter.selectMap, false))
				{
					adapter.notifyDataSetChanged();
				}
			}
			break;
		case R.id.moveNext:
			if (adapter.selectMap.size() == 0)
			{
				DialogAlert.show(getContext(), "", "请选择要移动的页！", null, "我知道了");
			}
			else
			{
				if (listener.onMove(adapter.selectMap, true))
				{
					adapter.notifyDataSetChanged();
				}
			}
			break;
		case R.id.delete:
			if (adapter.selectMap.size() == 0)
			{
				DialogAlert.show(getContext(), "", "请选择要删除的页！", null, "我知道了");
			}
			else if (adapter.selectMap.size() == adapter.pages.size())
			{
				DialogAlert.show(getContext(), "", "至少要保留一页！", null, "我知道了");
			}
			else
			{
				DialogAlert.show(getContext(), String.format("确定要删除 %d 页吗？", adapter.selectMap.size()), "删除后不可恢复！！！", new DialogAlert.OnClickListener()
				{
					@Override
					public void onClick(int which)
					{
						if (which == 1)
						{
							listener.onDelete(adapter.selectMap);
							adapter.selectMap.clear();
							adapter.notifyDataSetChanged();
						}
					}
				}, "取消", "删除");
			}
			break;
		case R.id.copy:
			if (adapter.selectMap.size() == 0)
			{
				DialogAlert.show(getContext(), "", "请选择要拷贝的页！", null, "我知道了");
			}
			else
			{
				SClipboard clipboard = new SClipboard();
				clipboard.type = SClipboardType.BoardPages;
				clipboard.data1 = AppConfig.getTaskDir(adapter.task);
				clipboard.data2 = SNotePage.saveList(adapter.getSelectPages()).toString();
				Config.writeClipboard(clipboard);
				LOG.toast(getContext(), String.format("已拷贝 %s 页到剪贴板", adapter.selectMap.size()));
			}
			break;
		case R.id.cancel:
			adapter.selectMap.clear();
			adapter.editMode = false;
			holder.menuBar.setVisibility(View.GONE);
			adapter.notifyDataSetChanged();
			break;
		}
	}

	public void select(int index)
	{
		dismiss();
		listener.onSelect(index);
	}

	public void editMode()
	{
		holder.menuBar.setVisibility(View.VISIBLE);
		adapter.notifyDataSetChanged();
//		for (int i = 0; i < adapter.getItemCount(); i++)
//		{
//			AdapterTaskSmall.Holder holder = (AdapterTaskSmall.Holder) recyclerView.findViewHolderForAdapterPosition(i);
//			if (holder != null)
//				adapter.updateCheck(holder, i);
//		}
	}
}
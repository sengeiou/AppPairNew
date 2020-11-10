package com.lys.adapter;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.lys.app.R;
import com.lys.base.utils.SysUtils;
import com.lys.config.AppConfig;
import com.lys.dialog.DialogTaskPageGrid;
import com.lys.fragment.FragmentTaskPage;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.view.BoardView;
import com.lys.protobuf.SNotePage;
import com.lys.protobuf.SPTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterTaskSmall extends RecyclerView.Adapter<AdapterTaskSmall.Holder>
{
	private DialogTaskPageGrid owner;
	public SPTask task;
	public List<SNotePage> pages;

	public boolean editMode = false;
	public Map<String, SNotePage> selectMap = new HashMap<>();

	public AdapterTaskSmall(DialogTaskPageGrid owner, SPTask task, List<SNotePage> pages)
	{
		this.owner = owner;
		this.task = task;
		this.pages = pages;
	}

	public List<SNotePage> getSelectPages()
	{
		List<SNotePage> selectPages = new ArrayList<>();
		for (int i = 0; i < pages.size(); i++)
		{
			SNotePage page = pages.get(i);
			if (selectMap.containsKey(page.pageDir))
			{
				selectPages.add(page);
			}
		}
		return selectPages;
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_small, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, final int position)
	{
		final SNotePage page = pages.get(position);
		final Context context = holder.itemView.getContext();

//		LOG.v("holder.small.getWidth():" + holder.small.getWidth()); // 根据这个值决定缩略图的生成宽度

		ViewGroup.LayoutParams layoutParams = holder.small.getLayoutParams();
		layoutParams.width = FragmentTaskPage.SMALL_WIDTH;
		layoutParams.height = SysUtils.screenHeight(context) * layoutParams.width / SysUtils.screenWidth(context);
		holder.small.setLayoutParams(layoutParams);

		File dir = new File(String.format("%s/%s", AppConfig.getTaskDir(task), page.pageDir));
		File file = BoardView.getSmallFile(dir);
		ImageLoad.displayImage(context, file.getAbsolutePath(), holder.small, 0, null);

		holder.small.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (editMode)
				{
					if (selectMap.containsKey(page.pageDir))
						selectMap.remove(page.pageDir);
					else
						selectMap.put(page.pageDir, page);
					holder.check.setChecked(selectMap.containsKey(page.pageDir));
				}
				else
				{
					owner.select(position);
				}
			}
		});

		updateCheck(holder, position);
	}

	private void updateCheck(final Holder holder, final int position)
	{
		final SNotePage page = pages.get(position);
		final Context context = holder.itemView.getContext();

		holder.check.setVisibility(editMode ? View.VISIBLE : View.GONE);
		holder.check.setChecked(selectMap.containsKey(page.pageDir));

		if (editMode)
		{
			holder.small.setLongClickable(false);
		}
		else
		{
			holder.small.setOnLongClickListener(new View.OnLongClickListener()
			{
				@Override
				public boolean onLongClick(View view)
				{
					selectMap.put(page.pageDir, page);
					editMode = true;
					Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
					vibrator.vibrate(60);
					owner.editMode();
					return true;
				}
			});
		}
	}

	@Override
	public int getItemCount()
	{
		if (pages != null)
			return pages.size();
		else
			return 0;
	}

	public class Holder extends RecyclerView.ViewHolder
	{
		public ImageView small;
		public CheckBox check;

		public Holder(View itemView)
		{
			super(itemView);
			small = itemView.findViewById(R.id.small);
			check = itemView.findViewById(R.id.check);
		}
	}
}
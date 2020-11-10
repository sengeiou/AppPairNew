package com.lys.kit.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lys.base.fragment.BaseFragment;
import com.lys.kit.R;
import com.lys.kit.activity.ActivitySelectImage;
import com.lys.kit.adapter.AdapterImage;
import com.lys.kit.dialog.DialogAlert;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentSelectImage extends BaseFragment implements View.OnClickListener
{
	private class Holder
	{
		private ViewGroup menuBar;
	}

	private Holder holder = new Holder();

	private void initHolder(View view)
	{
		holder.menuBar = view.findViewById(R.id.menuBar);
	}

	private View view = null;
	private String root;
	private String[] types;

	private RecyclerView recyclerView;
	private AdapterImage adapter;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		if (view == null)
		{
			view = inflater.inflate(R.layout.fragment_select_image, container, false);
			initHolder(view);

			root = getArguments().getString("root");
			types = getArguments().getString("types").split(";");

			holder.menuBar.setVisibility(View.GONE);

			view.findViewById(R.id.delete).setOnClickListener(this);
			view.findViewById(R.id.cancel).setOnClickListener(this);

			recyclerView = view.findViewById(R.id.recyclerView);
			recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
//			recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
			adapter = new AdapterImage(this);
			recyclerView.setAdapter(adapter);

			new Handler().post(new Runnable()
			{
				@Override
				public void run()
				{
					flushImages();
				}
			});
		}
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
		if (view.getId() == R.id.delete)
		{
			if (adapter.selectMap.size() == 0)
			{
				DialogAlert.show(context, "", "请选择要删除的项！", null, "我知道了");
			}
			else
			{
				DialogAlert.show(context, String.format("确定要删除 %d 项吗？", adapter.selectMap.size()), "删除后不可恢复！！！", new DialogAlert.OnClickListener()
				{
					@Override
					public void onClick(int which)
					{
						if (which == 1)
						{
							for (File image : adapter.selectMap.values())
							{
								image.delete();
								adapter.images.remove(image);
							}
							adapter.selectMap.clear();
							adapter.notifyDataSetChanged();
						}
					}
				}, "取消", "删除");
			}
		}
		else if (view.getId() == R.id.cancel)
		{
			adapter.selectMap.clear();
			adapter.editMode = false;
			holder.menuBar.setVisibility(View.GONE);
			adapter.notifyDataSetChanged();
		}
	}

	private void flushImages()
	{
		List<File> images = new ArrayList<>();
		searchImages(new File(root), images);
		adapter.setData(images);
	}

	private void searchImages(File dir, final List<File> images)
	{
		if (dir.exists())
		{
			File[] rawImages = dir.listFiles(new FileFilter()
			{
				@Override
				public boolean accept(File file)
				{
					if (file.isDirectory())
					{
						if (!file.getName().equals("small"))
							searchImages(file, images);
						return false;
					}
					else
					{
						String nameLower = file.getName().toLowerCase();
						for (String type : types)
						{
							if (nameLower.endsWith(type))
								return true;
						}
						return false;
					}
				}
			});
			if (rawImages != null)
			{
				List<File> imageList = Arrays.asList(rawImages);
				Collections.sort(imageList, new Comparator<File>()
				{
					@Override
					public int compare(File image1, File image2)
					{
						return image2.getName().compareTo(image1.getName());
//						return Long.valueOf(image2.lastModified()).compareTo(Long.valueOf(image1.lastModified()));
					}
				});
				images.addAll(imageList);
			}
		}
	}

	public void select(File image)
	{
		ActivitySelectImage activity = (ActivitySelectImage) getBaseActivity();
		activity.select(image);
	}

	public void editMode()
	{
		holder.menuBar.setVisibility(View.VISIBLE);
		adapter.notifyDataSetChanged();
	}
}

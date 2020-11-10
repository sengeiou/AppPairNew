package com.lys.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lys.App;
import com.lys.adapter.AdapterNotePage;
import com.lys.app.R;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.JsonHelper;
import com.lys.base.utils.LOG;
import com.lys.base.utils.LOGJson;
import com.lys.base.view.MyViewPager;
import com.lys.config.AppConfig;
import com.lys.dialog.DialogNotePageGrid;
import com.lys.fragment.FragmentNotePage;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.config.Config;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.pop.PopInsert;
import com.lys.kit.view.BoardToolBar;
import com.lys.protobuf.SClipboard;
import com.lys.protobuf.SClipboardType;
import com.lys.protobuf.SNoteBook;
import com.lys.protobuf.SNotePage;
import com.lys.protobuf.SNotePageSet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityNoteBook extends KitActivity implements View.OnClickListener, ViewPager.OnPageChangeListener
{
	public static int getPageCount(Context context, SNoteBook book)
	{
		File file = new File(String.format("%s/pageset.json", AppConfig.getNoteDir(context, book)));
		if (file.exists())
		{
			SNotePageSet pageset = SNotePageSet.load(FsUtils.readText(file));
			return pageset.pages.size();
		}
		else
		{
			return 0;
		}
	}

	private class Holder
	{
		private BoardToolBar toolBar;

		private TextView pageNumber;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.toolBar = findViewById(R.id.toolBar);

		holder.pageNumber = findViewById(R.id.pageNumber);
	}

	private SNoteBook book;

	private MyViewPager viewPager;
	private AdapterNotePage adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void init()
	{
		super.init();
		setContentView(R.layout.activity_note_book);

		initHolder();

//		findViewById(R.id.close).setVisibility(shouldShowClose() ? View.VISIBLE : View.GONE);
//		findViewById(R.id.close).setOnClickListener(this);

		book = SNoteBook.load(getIntent().getStringExtra("book"));

		if (App.isStudent())
		{
			holder.toolBar.setInsert(true, PopInsert.IconImageTopic, PopInsert.IconImageSelectionGroup);
			holder.toolBar.setWeike(false);
		}

		holder.toolBar.setListener(toolBarListener);

		viewPager = findViewById(R.id.viewPager);
		adapter = new AdapterNotePage(getSupportFragmentManager(), this, book);
		viewPager.setAdapter(adapter);

		viewPager.addOnPageChangeListener(this);

//		try
//		{
//			LOG.v("native_profileBegin");
//			IjkMediaPlayer.loadLibrariesOnce(null);
//			IjkMediaPlayer.native_profileBegin("libijkplayer.so");
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}

		loadPageSet();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
//		LOG.v("native_profileEnd");
//		IjkMediaPlayer.native_profileEnd();
	}

	private SNotePage getCurrNotePage()
	{
		return getNotePage(viewPager.getCurrentItem());
	}

	private SNotePage getNotePage(int position)
	{
		return pageset.pages.get(position);
	}

	private FragmentNotePage getCurrFragmentPage()
	{
		return getFragmentPage(viewPager.getCurrentItem());
	}

	private FragmentNotePage getFragmentPage(int position)
	{
		return adapter.fragmentMap.get(position);
	}

	public void removeFragmentPage(int position)
	{
		adapter.fragmentMap.remove(position);
	}

	private String genPageDir()
	{
		for (int i = 0; ; i++)
		{
			String pageDir = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date(System.currentTimeMillis() + i * 1000));
			File dir = new File(String.format("%s/%s", AppConfig.getNoteDir(context, book), pageDir));
			if (!dir.exists())
				return pageDir;
		}
	}

	private String addPage(int currPosition, String pageDir, File srcDir)
	{
		if (TextUtils.isEmpty(pageDir))
			pageDir = genPageDir();
		File dir = new File(String.format("%s/%s", AppConfig.getNoteDir(context, book), pageDir));
		dir.mkdirs();

		if (srcDir != null && srcDir.exists())
			FsUtils.copyPath(srcDir, dir, null);

		SNotePage page = new SNotePage();
		page.pageDir = pageDir;
		pageset.pages.add(currPosition + 1, page);

		savePageSet();

//			for (FragmentNotePage fragmentPage : adapter.fragmentMap.values())
//			{
//				fragmentPage.genSmall(true);
//			}
		adapter.fragmentMap.clear();
		lastPosition = -1;
		adapter.setData(pageset.pages);
		viewPager.setAdapter(adapter);
		showPage(currPosition + 1);

		return pageDir;
	}

	private void deletePageImpl(Map<String, SNotePage> deleteMap)
	{
		int currPosition = viewPager.getCurrentItem();
		String selectPageDir = null;
		if (TextUtils.isEmpty(selectPageDir))
		{
			for (int i = currPosition; i < pageset.pages.size(); i++)
			{
				SNotePage page = pageset.pages.get(i);
				if (!deleteMap.containsKey(page.pageDir))
				{
					selectPageDir = page.pageDir;
					break;
				}
			}
		}
		if (TextUtils.isEmpty(selectPageDir))
		{
			for (int i = currPosition - 1; i >= 0; i--)
			{
				SNotePage page = pageset.pages.get(i);
				if (!deleteMap.containsKey(page.pageDir))
				{
					selectPageDir = page.pageDir;
					break;
				}
			}
		}
		if (!TextUtils.isEmpty(selectPageDir))
		{
			for (SNotePage page : deleteMap.values())
			{
				File dir = new File(String.format("%s/%s", AppConfig.getNoteDir(context, book), page.pageDir));
				FsUtils.delete(dir);
				pageset.pages.remove(page);
			}

			savePageSet();
			adapter.fragmentMap.clear();
			lastPosition = -1;
			adapter.setData(pageset.pages);
			viewPager.setAdapter(adapter);
			showPage(selectPageDir);
		}
		else
		{
			LOG.v("删除页异常");
		}
	}

	private SNotePageSet pageset = null;

	private void loadPageSet()
	{
		File file = new File(String.format("%s/pageset.json", AppConfig.getNoteDir(context, book)));
		if (file.exists())
		{
			pageset = SNotePageSet.load(FsUtils.readText(file));
			adapter.setData(pageset.pages);
			showPage(0);
		}
		else
		{
			pageset = new SNotePageSet();
			addPage(-1, null, null);
		}
	}

	// 记录最后一次修改时间，只要有操作就调用
//	public void recordLastModifyTime()
//	{
//		File file = new File(String.format("%s/lastModifyTime.txt", AppConfig.getNoteDir(context, book)));
//		FsUtils.writeText(file, String.valueOf(System.currentTimeMillis()));
//	}

//	public long getLastModifyTime()
//	{
//		File file = new File(String.format("%s/lastModifyTime.txt", AppConfig.getNoteDir(context, book)));
//		if (file.exists())
//			return Long.valueOf(FsUtils.readText(file));
//		else
//			return 0;
//	}

	private void savePageSet()
	{
		File file = new File(String.format("%s/pageset.json", AppConfig.getNoteDir(context, book)));
		FsUtils.writeText(file, LOGJson.getStr(pageset.saveToStr()));
	}

	private boolean showPage(String pageDir)
	{
		for (int i = 0; i < pageset.pages.size(); i++)
		{
			SNotePage page = pageset.pages.get(i);
			if (pageDir.equals(page.pageDir))
			{
				showPage(i);
				return true;
			}
		}
		return false;
	}

	private void showPage(int index)
	{
		viewPager.setCurrentItem(index);
		pageSelected(index); // 如果只有一页的时候，OnPageChangeListener不会触发，所以这里需要手动调用一次
	}

	//------------------- 点击事件处理（开始） --------------------------

	private BoardToolBar.OnListener toolBarListener = new BoardToolBar.OnListener()
	{
		@Override
		public void onIconSend()
		{
			getCurrFragmentPage().onSend(null);
		}

		@Override
		public void onIconGrid()
		{
			openGrid();
		}

		@Override
		public void onIconDelete()
		{
			deletePage(viewPager.getCurrentItem());
		}

		@Override
		public void onDeleteBigVideoBefore()
		{
			getCurrFragmentPage().onDeleteBigVideoBefore();
		}

		@Override
		public void onAddBigVideoOver()
		{
			getCurrFragmentPage().onAddBigVideoOver();
		}

		@Override
		public void onIconAddPage()
		{
			getCurrFragmentPage().genSmallAndBig(true);
			String newPageDir = genPageDir();
			addPage(viewPager.getCurrentItem(), newPageDir, null);
		}
	};

	public void onPaste()
	{
		SClipboard clipboard = Config.readClipboard();
		if (clipboard != null && clipboard.type.equals(SClipboardType.BoardPages))
		{
			File srcTaskDir = new File(clipboard.data1);
			List<SNotePage> selectPages = SNotePage.loadList(JsonHelper.getJSONArray(clipboard.data2));
			for (SNotePage selectPage : selectPages)
			{
				addPage(viewPager.getCurrentItem(), null, new File(srcTaskDir, selectPage.pageDir));
			}
		}
	}

	@Override
	public void onClick(View view)
	{
//		if (view.getId() == R.id.close)
//		{
//			finish();
//		}
	}

	private void openGrid()
	{
		if (getCurrFragmentPage() != null)
			getCurrFragmentPage().genSmallAndBig(false);

		DialogNotePageGrid dialogPageGrid = new DialogNotePageGrid(context, book, pageset.pages);
		dialogPageGrid.setListener(new DialogNotePageGrid.OnListener()
		{
			@Override
			public void onSelect(int index)
			{
				showPage(index);
			}

			@Override
			public void onDelete(Map<String, SNotePage> deleteMap)
			{
				deletePageImpl(deleteMap);
			}

			@Override
			public boolean onMove(Map<String, SNotePage> moveMap, boolean isNext)
			{
//				if (moveMap.size() != 1)
//				{
//					DialogAlert.show(context, "", "只能移动一页！", null, "我知道了");
//					return false;
//				}

				String selectPageDir = getCurrNotePage().pageDir;

				boolean hasMove = false;

				if (isNext)
				{
					for (int i = pageset.pages.size() - 2; i >= 0; i--)
					{
						SNotePage page = pageset.pages.get(i);
						if (moveMap.containsKey(page.pageDir))
						{
							pageset.pages.remove(page);
							pageset.pages.add(i + 1, page);
							hasMove = true;
						}
					}
				}
				else
				{
					for (int i = 1; i < pageset.pages.size(); i++)
					{
						SNotePage page = pageset.pages.get(i);
						if (moveMap.containsKey(page.pageDir))
						{
							pageset.pages.remove(page);
							pageset.pages.add(i - 1, page);
							hasMove = true;
						}
					}
				}

				if (hasMove)
				{
					savePageSet();
					adapter.fragmentMap.clear();
					lastPosition = -1;
					adapter.setData(pageset.pages);
					viewPager.setAdapter(adapter);
					showPage(selectPageDir);
				}

				return hasMove;
			}
		});
		dialogPageGrid.show();
	}

	private void deletePage(final int currPosition)
	{
		if (pageset.pages.size() == 1)
		{
			DialogAlert.show(context, "", "至少要保留一页！", null, "我知道了");
		}
		else
		{
			DialogAlert.show(context, "确定要删除当前页吗？", "删除后不可恢复！！！", new DialogAlert.OnClickListener()
			{
				@Override
				public void onClick(int which)
				{
					if (which == 1)
					{
						Map<String, SNotePage> deleteMap = new HashMap<>();
						SNotePage page = getCurrNotePage();
						deleteMap.put(page.pageDir, page);
						deletePageImpl(deleteMap);
					}
				}
			}, "取消", "删除");
		}
	}

	//------------------- 点击事件处理（结束） --------------------------

	//------------------- ViewPager监听（开始） --------------------------

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{

	}

	private int lastPosition = -1;

	public boolean pageSelected(final int position)
	{
		if (lastPosition != position)
		{
			FragmentNotePage lastFragmentPage = getFragmentPage(lastPosition);
			if (lastFragmentPage != null)
			{
				LOG.v("---------------------- to gen small : " + lastPosition);
				lastFragmentPage.genSmallAndBig(true);
				lastFragmentPage.stop();
			}
			LOG.v(String.format("-------- %d ---> %d", lastPosition, position));
			lastPosition = position;
			holder.pageNumber.setText(String.format("%d / %d", position + 1, pageset.pages.size()));
			bindPage(position);
			return true;
		}
		return false;
	}

	private void bindPage(final int position)
	{
		new Handler().post(new Runnable()
		{
			@Override
			public void run()
			{
				FragmentNotePage fragmentPage = getFragmentPage(position);
				if (fragmentPage != null)
				{
					LOG.v("bindPage success " + position);
					fragmentPage.bindTool(holder.toolBar);
					fragmentPage.play();
				}
				else
				{
					LOG.v("bindPage is null and will retry ...");
					bindPage(position);
				}
			}
		});
	}

	@Override
	public void onPageSelected(int position)
	{
		pageSelected(position);
	}

	@Override
	public void onPageScrollStateChanged(int state)
	{

	}

	//------------------- ViewPager监听（结束） --------------------------

}

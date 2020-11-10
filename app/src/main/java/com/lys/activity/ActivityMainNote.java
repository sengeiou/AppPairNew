package com.lys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lys.adapter.AdapterNoteCover;
import com.lys.app.R;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.LOG;
import com.lys.base.utils.LOGJson;
import com.lys.config.AppConfig;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.dialog.DialogAlert;
import com.lys.protobuf.SNoteBook;
import com.lys.protobuf.SNoteBookSet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityMainNote extends KitActivity implements View.OnClickListener
{
	private class Holder
	{
//		private RadioButton btnShujia;
//		private RadioButton btnShuku;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
//		holder.btnShujia = findViewById(R.id.btnShujia);
//		holder.btnShuku = findViewById(R.id.btnShuku);
	}

	private RecyclerView recyclerView;
	private AdapterNoteCover adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_note);

		initHolder();

		requestPermission();
	}

	@Override
	public void permissionSuccess()
	{
		super.permissionSuccess();

		findViewById(R.id.add).setOnClickListener(this);

		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
		adapter = new AdapterNoteCover(this);
		recyclerView.setAdapter(adapter);

		loadBookSet();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		if (checkPermissions())
			adapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.add:
			DialogAlert.showInput(context, "请输入名字：", "新笔记", new DialogAlert.OnInputListener()
			{
				@Override
				public void onInput(String text)
				{
					if (addBook(text))
						recyclerView.smoothScrollToPosition(bookset.books.size() - 1);
				}
			});
			break;
		}
	}

	private boolean addBook(String name)
	{
		String bookDir = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		File dir = new File(AppConfig.getNoteDir(context, bookDir));
		if (dir.exists())
		{
			LOG.toast(context, "您操作的过快");
			return false;
		}
		else
		{
			dir.mkdirs();

			SNoteBook book = new SNoteBook();
			book.name = name;
			book.bookDir = bookDir;
			bookset.books.add(book);

			saveBookSet();

			adapter.setData(bookset.books);

			return true;
		}
	}

	private SNoteBookSet bookset = null;

	private void loadBookSet()
	{
		File file = new File(AppConfig.getBooksetFile(context));
		if (file.exists())
		{
			bookset = SNoteBookSet.load(FsUtils.readText(file));
			adapter.setData(bookset.books);
		}
		else
		{
			bookset = new SNoteBookSet();
			addBook("新笔记");
		}
	}

	private void saveBookSet()
	{
		File file = new File(AppConfig.getBooksetFile(context));
		FsUtils.writeText(file, LOGJson.getStr(bookset.saveToStr()));
	}

	public void openBook(SNoteBook book)
	{
		Intent intent = new Intent(this, ActivityNoteBook.class);
		intent.putExtra("book", book.saveToStr());
		startActivity(intent);
	}

	public void modifyName(SNoteBook book, String name)
	{
		book.name = name;
		saveBookSet();
		adapter.setData(bookset.books);
	}

	public void deleteBook(SNoteBook book)
	{
		File dir = new File(AppConfig.getNoteDir(context, book));
		FsUtils.delete(dir);
		bookset.books.remove(book);
		saveBookSet();
		adapter.setData(bookset.books);
	}

}

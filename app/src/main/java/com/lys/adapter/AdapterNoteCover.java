package com.lys.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lys.activity.ActivityMainNote;
import com.lys.activity.ActivityNoteBook;
import com.lys.app.R;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.dialog.DialogMenu;
import com.lys.protobuf.SNoteBook;

import java.util.List;

public class AdapterNoteCover extends RecyclerView.Adapter<AdapterNoteCover.Holder>
{
	private ActivityMainNote owner = null;
	private List<SNoteBook> books = null;

	public AdapterNoteCover(ActivityMainNote owner)
	{
		this.owner = owner;
	}

	public void setData(List<SNoteBook> books)
	{
		this.books = books;
		notifyDataSetChanged();
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_cover, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, final int position)
	{
		final SNoteBook book = books.get(position);
		final Context context = holder.itemView.getContext();

		holder.dir.setVisibility(View.GONE);

		holder.name.setText(book.name);
		holder.pageCount.setText(String.format("%d 页", ActivityNoteBook.getPageCount(context, book)));
		holder.dir.setText(book.bookDir);

		if (position % 3 == 0)
			holder.cover.setImageResource(R.drawable.img_cover_1);
		else if (position % 3 == 1)
			holder.cover.setImageResource(R.drawable.img_cover_2);
		else if (position % 3 == 2)
			holder.cover.setImageResource(R.drawable.img_cover_3);

		holder.cover.setOnLongClickListener(new View.OnLongClickListener()
		{
			@Override
			public boolean onLongClick(View view)
			{
				DialogMenu.show(context, new DialogMenu.OnClickListener()
				{
					@Override
					public void onClick(int which)
					{
						switch (which)
						{
						case 0:
							DialogAlert.showInput(context, "请输入名字：", book.name, new DialogAlert.OnInputListener()
							{
								@Override
								public void onInput(String text)
								{
									owner.modifyName(book, text);
								}
							});
							break;
						case 1:
							DialogAlert.show(context, String.format("确定要删除《%s》吗？", book.name), "删除后不可恢复！！！", new DialogAlert.OnClickListener()
							{
								@Override
								public void onClick(int which)
								{
									if (which == 1)
									{
										owner.deleteBook(book);
									}
								}
							}, "取消", "删除");
							break;
						}
					}
				}, "修改名字", "删除");
				return true;
			}
		});

		holder.cover.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				owner.openBook(book);
			}
		});
	}

	@Override
	public int getItemCount()
	{
		if (books != null)
			return books.size();
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ImageView cover;
		public TextView name;
		public TextView pageCount;
		public TextView dir;

		public Holder(View itemView)
		{
			super(itemView);
			cover = itemView.findViewById(R.id.cover);
			name = itemView.findViewById(R.id.name);
			pageCount = itemView.findViewById(R.id.pageCount);
			dir = itemView.findViewById(R.id.dir);
		}
	}
}
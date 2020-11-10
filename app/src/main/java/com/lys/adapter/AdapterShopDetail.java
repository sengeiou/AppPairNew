package com.lys.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lys.App;
import com.lys.activity.ActivityShopDetail;
import com.lys.activity.ActivityTaskBook;
import com.lys.app.R;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.LOG;
import com.lys.base.utils.SysUtils;
import com.lys.config.AppConfig;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.module.ModulePlayer;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.utils.Protocol;
import com.lys.kit.view.BoardView;
import com.lys.protobuf.SComment;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SMatterDetail;
import com.lys.protobuf.SMatterDetailType;
import com.lys.protobuf.SNotePage;
import com.lys.protobuf.SNotePageSet;
import com.lys.protobuf.SRequest_AddModifyComment;
import com.lys.protobuf.SRequest_DeleteComment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdapterShopDetail extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	public static final int Type_DetailImg = 1;
	public static final int Type_DetailVideo = 2;
	public static final int Type_DetailTask = 3;
	public static final int Type_CommentEdit = 4;
	public static final int Type_CommentItem = 5;

	private ActivityShopDetail owner = null;

	private List<SMatterDetail> details = new ArrayList<>();
	private List<SComment> comments = new ArrayList<>();

	public AdapterShopDetail(ActivityShopDetail owner)
	{
		this.owner = owner;
	}

	public void setDetailData(List<SMatterDetail> details)
	{
		this.details = details;
		notifyDataSetChanged();
	}

	public void setCommentData(List<SComment> comments)
	{
		this.comments = comments;
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		switch (viewType)
		{
		case Type_DetailImg:
			return new HolderDetailImg(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_img, parent, false));
		case Type_DetailVideo:
			return new HolderDetailVideo(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_video, parent, false));
		case Type_DetailTask:
			return new HolderDetailTask(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_task, parent, false));
		case Type_CommentEdit:
			return new HolderCommentEdit(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_edit, parent, false));
		case Type_CommentItem:
			return new HolderCommentItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_item, parent, false));
		}
		return null;
	}

//	private static final SimpleDateFormat formatDate1 = new SimpleDateFormat("yyyy - MM - dd");
//	private static final SimpleDateFormat formatDate2 = new SimpleDateFormat("MM-dd HH:mm");

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position)
	{
		final Context context = viewHolder.itemView.getContext();
		if (getItemViewType(position) == Type_DetailImg)
		{
			HolderDetailImg holder = (HolderDetailImg) viewHolder;
			SMatterDetail detail = details.get(position);

			ViewGroup.LayoutParams layoutParams = holder.img.getLayoutParams();
			layoutParams.width = Math.min(detail.imgWidth, SysUtils.screenWidth(context) - 160);
			layoutParams.height = layoutParams.width * detail.imgHeight / detail.imgWidth;
			holder.img.setLayoutParams(layoutParams);

			ImageLoad.displayImage(context, detail.imgUrl, 0, holder.img, R.drawable.img_default, null);
		}
		else if (getItemViewType(position) == Type_DetailVideo)
		{
			HolderDetailVideo holder = (HolderDetailVideo) viewHolder;
			final SMatterDetail detail = details.get(position);

			ViewGroup.LayoutParams layoutParams = holder.img.getLayoutParams();
			layoutParams.width = Math.min(detail.imgWidth, SysUtils.screenWidth(context) - 160);
			layoutParams.height = layoutParams.width * detail.imgHeight / detail.imgWidth;
			holder.img.setLayoutParams(layoutParams);

			ImageLoad.displayImage(context, detail.imgUrl, 0, holder.img, R.drawable.img_default, null);

			holder.play.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					ModulePlayer.instance().playSimple(context, Uri.parse(ImageLoad.checkUrl(detail.videoUrl)));
				}
			});
		}
		else if (getItemViewType(position) == Type_DetailTask)
		{
			HolderDetailTask holder = (HolderDetailTask) viewHolder;
			final SMatterDetail detail = details.get(position);

			holder.img.setImageResource(R.drawable.img_task_default_cover_small);
			File fileSet = new File(String.format("%s/pageset.json", AppConfig.getTaskDir(detail.task)));
			if (fileSet.exists())
			{
				SNotePageSet pageset = SNotePageSet.load(FsUtils.readText(fileSet));
				if (pageset.pages.size() > 0)
				{
					SNotePage page = pageset.pages.get(0);
					File dir = new File(String.format("%s/%s", AppConfig.getTaskDir(detail.task), page.pageDir));
					File file = BoardView.getSmallFile(dir);
					ImageLoad.displayImage(context, file.getAbsolutePath(), holder.img, 0, null);
				}
			}

			holder.play.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					ActivityTaskBook.goinWithNone(context, detail.task);
				}
			});
		}
		else if (getItemViewType(position) == Type_CommentEdit)
		{
			final HolderCommentEdit holder = (HolderCommentEdit) viewHolder;

			holder.editText.addTextChangedListener(new TextWatcher()
			{
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after)
				{
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count)
				{
					holder.wordCount.setText(String.format("%d / 300", s.length()));
				}

				@Override
				public void afterTextChanged(Editable s)
				{
				}
			});

			holder.commit.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					if (holder.rating.getRating() == 0)
					{
						LOG.toast(context, "您还没有评分");
						return;
					}
					String text = holder.editText.getText().toString();
					if (TextUtils.isEmpty(text))
					{
						LOG.toast(context, "请输入您的评价");
						return;
					}
					SysUtils.hideKeybord((Activity) context);

					SComment comment = new SComment();
					comment.matterId = owner.matter.id;
					comment.user = App.getUser();
					comment.star = (int) holder.rating.getRating();
					comment.text = text;
					comment.pass = !App.isStudent();

					SRequest_AddModifyComment request = new SRequest_AddModifyComment();
					request.comment = comment;
					Protocol.doPost(context, App.getApi(), SHandleId.AddModifyComment, request.saveToStr(), new Protocol.OnCallback()
					{
						@Override
						public void onResponse(int code, String data, String msg)
						{
							if (code == 200)
							{
								holder.rating.setRating(5);
								holder.editText.setText(null);
								LOG.toast(context, "提交成功！");
								if (!App.isStudent())
								{
									owner.request();
								}
							}
						}
					});
				}
			});
		}
		else if (getItemViewType(position) == Type_CommentItem)
		{
			HolderCommentItem holder = (HolderCommentItem) viewHolder;
			final SComment comment = comments.get(position - (details.size() + 1));

			ImageLoad.displayImage(context, comment.user.head, holder.head, R.drawable.img_default_head, null);
			holder.name.setText(comment.user.name);
			holder.rating.setRating(comment.star);
			holder.text.setText(comment.text);
			holder.time.setText(CommonUtils.formatTime2(System.currentTimeMillis() - comment.time));

			if (comment.pass)
			{
				holder.pass.setVisibility(View.GONE);
				holder.delete.setVisibility(View.GONE);
			}
			else
			{
				holder.pass.setVisibility(View.VISIBLE);
				holder.delete.setVisibility(View.VISIBLE);
				holder.pass.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View view)
					{
						comment.pass = true;
						SRequest_AddModifyComment request = new SRequest_AddModifyComment();
						request.comment = comment;
						Protocol.doPost(context, App.getApi(), SHandleId.AddModifyComment, request.saveToStr(), new Protocol.OnCallback()
						{
							@Override
							public void onResponse(int code, String data, String msg)
							{
								if (code == 200)
								{
									owner.request();
								}
							}
						});
					}
				});
				holder.delete.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View view)
					{
						DialogAlert.show(context, "确定要删除吗？", null, new DialogAlert.OnClickListener()
						{
							@Override
							public void onClick(int which)
							{
								if (which == 1)
								{
									SRequest_DeleteComment request = new SRequest_DeleteComment();
									request.commentId = comment.id;
									Protocol.doPost(context, App.getApi(), SHandleId.DeleteComment, request.saveToStr(), new Protocol.OnCallback()
									{
										@Override
										public void onResponse(int code, String data, String msg)
										{
											if (code == 200)
											{
												owner.request();
											}
										}
									});
								}
							}
						}, "取消", "删除");
					}
				});
			}
		}
	}

	@Override
	public int getItemCount()
	{
		return details.size() + 1 + comments.size();
	}

	@Override
	public int getItemViewType(int position)
	{
		if (position < details.size())
		{
			SMatterDetail detail = details.get(position);
			if (detail.type.equals(SMatterDetailType.Img))
				return Type_DetailImg;
			else if (detail.type.equals(SMatterDetailType.Video))
				return Type_DetailVideo;
			else //if (detail.type.equals(SMatterDetailType.Task))
				return Type_DetailTask;
		}
		else if (position == details.size())
			return Type_CommentEdit;
		else
			return Type_CommentItem;
	}

	protected class HolderDetailImg extends RecyclerView.ViewHolder
	{
		public ImageView img;

		public HolderDetailImg(View itemView)
		{
			super(itemView);
			img = itemView.findViewById(R.id.img);
		}
	}

	protected class HolderDetailVideo extends RecyclerView.ViewHolder
	{
		public ImageView img;
		public ImageView play;

		public HolderDetailVideo(View itemView)
		{
			super(itemView);
			img = itemView.findViewById(R.id.img);
			play = itemView.findViewById(R.id.play);
		}
	}

	public static class HolderDetailTask extends RecyclerView.ViewHolder
	{
		public ImageView img;
		public ImageView play;

		public HolderDetailTask(View itemView)
		{
			super(itemView);
			img = itemView.findViewById(R.id.img);
			play = itemView.findViewById(R.id.play);
		}
	}

	protected class HolderCommentEdit extends RecyclerView.ViewHolder
	{
		public RatingBar rating;
		public TextView wordCount;
		public EditText editText;
		public TextView commit;

		public HolderCommentEdit(View itemView)
		{
			super(itemView);
			rating = itemView.findViewById(R.id.rating);
			wordCount = itemView.findViewById(R.id.wordCount);
			editText = itemView.findViewById(R.id.editText);
			commit = itemView.findViewById(R.id.commit);
		}
	}

	public static class HolderCommentItem extends RecyclerView.ViewHolder
	{
		public ImageView head;
		public TextView name;
		public RatingBar rating;
		public TextView text;
		public TextView time;

		public TextView pass;
		public TextView delete;

		public HolderCommentItem(View itemView)
		{
			super(itemView);
			head = itemView.findViewById(R.id.head);
			name = itemView.findViewById(R.id.name);
			rating = itemView.findViewById(R.id.rating);
			text = itemView.findViewById(R.id.text);
			time = itemView.findViewById(R.id.time);

			pass = itemView.findViewById(R.id.pass);
			delete = itemView.findViewById(R.id.delete);
		}
	}

}
package com.lys.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lys.App;
import com.lys.activity.AppActivity;
import com.lys.app.R;
import com.lys.base.activity.BaseActivity;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.LOG;
import com.lys.kit.activity.ActivitySelectImage;
import com.lys.kit.config.Config;
import com.lys.kit.module.OssHelper;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SMatter;
import com.lys.protobuf.SMatterDetail;
import com.lys.protobuf.SMatterDetailType;
import com.lys.protobuf.SMatterHour;
import com.lys.protobuf.SMatterPlace;
import com.lys.protobuf.SMatterType;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SRequest_GetUser;
import com.lys.protobuf.SResponse_GetUser;
import com.lys.protobuf.SUser;
import com.lys.protobuf.SUserType;

import java.io.File;
import java.util.List;

public class DialogEditMatter extends Dialog implements View.OnClickListener
{
	public interface OnResultListener
	{
		void onResult(SMatter matter);
	}

	private OnResultListener listener = null;

	private void setListener(OnResultListener listener)
	{
		this.listener = listener;
	}

	private class Holder
	{
		private TextView id;
		private EditText name;
		private TextView sort;
		private CheckBox invalid;
		private TextView userId;

		private RadioButton typeClass;
		private RadioButton typePair;

		private RadioButton placeDefault;
		private RadioButton placeMain;
		private RadioButton placeBanner;

		private ImageView cover;
		private ImageView banner;

		private EditText buyCount;
		private EditText moneyRaw;
		private EditText money;

		private ViewGroup hourContainer;
		private ViewGroup detailContainer;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.id = findViewById(R.id.id);
		holder.name = findViewById(R.id.name);
		holder.sort = findViewById(R.id.sort);
		holder.invalid = findViewById(R.id.invalid);
		holder.userId = findViewById(R.id.userId);

		holder.typeClass = findViewById(R.id.typeClass);
		holder.typePair = findViewById(R.id.typePair);

		holder.placeDefault = findViewById(R.id.placeDefault);
		holder.placeMain = findViewById(R.id.placeMain);
		holder.placeBanner = findViewById(R.id.placeBanner);

		holder.cover = findViewById(R.id.cover);
		holder.banner = findViewById(R.id.banner);

		holder.buyCount = findViewById(R.id.buyCount);
		holder.moneyRaw = findViewById(R.id.moneyRaw);
		holder.money = findViewById(R.id.money);

		holder.hourContainer = findViewById(R.id.hourContainer);
		holder.detailContainer = findViewById(R.id.detailContainer);
	}

	private AppActivity context = null;

	private DialogEditMatter(@NonNull Context context, SMatter matter)
	{
		super(context, R.style.FullDialog);
		this.context = (AppActivity) context;
//		setCancelable(false);
		setContentView(R.layout.dialog_edit_matter);
		initHolder();

		applyToUI(matter);

		findViewById(R.id.close).setOnClickListener(this);
		findViewById(R.id.ok).setOnClickListener(this);

		holder.userId.setOnClickListener(this);
		holder.cover.setOnClickListener(this);
		holder.banner.setOnClickListener(this);

		findViewById(R.id.addHour).setOnClickListener(this);
		findViewById(R.id.addDetail).setOnClickListener(this);
	}

	@Override
	public void dismiss()
	{
		super.dismiss();
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.close)
		{
			dismiss();
		}
		else if (view.getId() == R.id.ok)
		{
			ok();
		}
		else if (view.getId() == R.id.userId)
		{
			context.selectUser(new BaseActivity.OnImageListener()
			{
				@Override
				public void onResult(String userStr)
				{
					SUser user = SUser.load(userStr);
					holder.userId.setTag(user.id);
					holder.userId.setText(user.name);
				}
			}, SUserType.Master, SUserType.Teacher);
		}
		else if (view.getId() == R.id.cover)
		{
			context.selectCustomImage(new BaseActivity.OnImageListener()
			{
				@Override
				public void onResult(String filepath)
				{
					OssHelper.instance().doUploadMd5FileWithProgress(OssHelper.ZjykFile, new File(filepath), OssHelper.DirMatter(), new OssHelper.OnProgressListener()
					{
						@Override
						public void onProgress(long currentSize, long totalSize)
						{
							LOG.v(String.format("currentSize = %s, totalSize = %s", currentSize, totalSize));
						}

						@Override
						public void onSuccess(final String url)
						{
							holder.cover.setTag(url);
							ImageLoad.displayImage(getContext(), url, holder.cover, R.drawable.img_bad, null);
						}

						@Override
						public void onFail()
						{
							LOG.toast(context, "上传失败");
						}
					});
				}
			});
		}
		else if (view.getId() == R.id.banner)
		{
			context.selectCustomImage(new BaseActivity.OnImageListener()
			{
				@Override
				public void onResult(String filepath)
				{
					OssHelper.instance().doUploadMd5FileWithProgress(OssHelper.ZjykFile, new File(filepath), OssHelper.DirMatter(), new OssHelper.OnProgressListener()
					{
						@Override
						public void onProgress(long currentSize, long totalSize)
						{
							LOG.v(String.format("currentSize = %s, totalSize = %s", currentSize, totalSize));
						}

						@Override
						public void onSuccess(final String url)
						{
							holder.banner.setTag(url);
							ImageLoad.displayImage(getContext(), url, holder.banner, R.drawable.img_bad, null);
						}

						@Override
						public void onFail()
						{
							LOG.toast(context, "上传失败");
						}
					});
				}
			});
		}
		else if (view.getId() == R.id.addHour)
		{
			addHour(holder.hourContainer.getChildCount(), null);
		}
		else if (view.getId() == R.id.addDetail)
		{
			addDetail(holder.detailContainer.getChildCount(), null);
		}
	}

	private void addHour(int pos, SMatterHour hour)
	{
		if (hour == null)
			hour = new SMatterHour();
		View view = LayoutInflater.from(getContext()).inflate(R.layout.view_edit_matter_hour, null);
		final HolderHour hd = new HolderHour(view);

		hd.hourBuy.setText(String.valueOf(hour.hourBuy));
		hd.hourGive.setText(String.valueOf(hour.hourGive));

		hd.insert.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				addHour(CommonUtils.findViewPos(hd.view), null);
			}
		});

		hd.remove.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				CommonUtils.removeView(hd.view);
			}
		});

		hd.up.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				CommonUtils.moveUpView(hd.view);
			}
		});

		hd.down.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				CommonUtils.moveDownView(hd.view);
			}
		});

		view.setTag(hd);
		holder.hourContainer.addView(view, pos);
	}

	private void addDetail(int pos, SMatterDetail detail)
	{
		if (detail == null)
			detail = new SMatterDetail();
		View view = LayoutInflater.from(getContext()).inflate(R.layout.view_edit_matter_detail, null);
		final HolderDetail hd = new HolderDetail(view);

		hd.bind(detail);

		hd.img.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				context.selectImageVideo(new BaseActivity.OnImageListener()
				{
					@Override
					public void onResult(final String filepath)
					{
						final SMatterDetail detail = new SMatterDetail();
						if (ActivitySelectImage.isMovie(filepath))
						{
							MediaMetadataRetriever mmr = new MediaMetadataRetriever();
							mmr.setDataSource(filepath);
							final long duration = Long.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
//							final int bitrate = Integer.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
//							final int videoWidth = Integer.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
//							final int videoHeight = Integer.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
							final Bitmap bitmap = mmr.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST);
							mmr.release();
							detail.imgWidth = bitmap.getWidth();
							detail.imgHeight = bitmap.getHeight();
							detail.videoDuration = duration;
							if (bitmap != null)
							{
								final File tmpFile = Config.genTmpFile(".jpg");
								CommonUtils.saveBitmap(bitmap, tmpFile);
								bitmap.recycle();
								OssHelper.instance().doUploadMd5FileWithProgress(OssHelper.ZjykFile, tmpFile, OssHelper.DirMatter(), new OssHelper.OnProgressListener()
								{
									@Override
									public void onProgress(long currentSize, long totalSize)
									{
										LOG.v(String.format("currentSize = %s, totalSize = %s", currentSize, totalSize));
									}

									@Override
									public void onSuccess(final String imgUrl)
									{
										tmpFile.delete();
										OssHelper.instance().doUploadMd5FileWithProgress(OssHelper.ZjykFile, new File(filepath), OssHelper.DirMatter(), new OssHelper.OnProgressListener()
										{
											@Override
											public void onProgress(long currentSize, long totalSize)
											{
												LOG.v(String.format("currentSize = %s, totalSize = %s", currentSize, totalSize));
											}

											@Override
											public void onSuccess(final String videoUrl)
											{
												detail.type = SMatterDetailType.Video;
												detail.imgUrl = imgUrl;
												detail.videoUrl = videoUrl;
												hd.bind(detail);
											}

											@Override
											public void onFail()
											{
												LOG.toast(context, "上传视频失败");
											}
										});
									}

									@Override
									public void onFail()
									{
										tmpFile.delete();
										LOG.toast(context, "上传图像失败");
									}
								});
							}
							else
							{
								LOG.toast(context, "视频解析失败");
							}
						}
						else
						{
							BitmapFactory.Options opts = CommonUtils.readBitmapSize(filepath);
							detail.imgWidth = opts.outWidth;
							detail.imgHeight = opts.outHeight;
							OssHelper.instance().doUploadMd5FileWithProgress(OssHelper.ZjykFile, new File(filepath), OssHelper.DirMatter(), new OssHelper.OnProgressListener()
							{
								@Override
								public void onProgress(long currentSize, long totalSize)
								{
									LOG.v(String.format("currentSize = %s, totalSize = %s", currentSize, totalSize));
								}

								@Override
								public void onSuccess(final String imgUrl)
								{
									detail.type = SMatterDetailType.Img;
									detail.imgUrl = imgUrl;
									hd.bind(detail);
								}

								@Override
								public void onFail()
								{
									LOG.toast(context, "上传失败");
								}
							});
						}
					}
				});
			}
		});

		hd.task.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				context.selectUser(new BaseActivity.OnImageListener()
				{
					@Override
					public void onResult(String userStr)
					{
						SUser user = SUser.load(userStr);
						DialogSelectTask.show(context, user.id, new DialogSelectTask.OnListener()
						{
							@Override
							public void onSelect(List<SPTask> selectedList, String taskText)
							{
								if (selectedList.size() == 1)
								{
									SPTask task = selectedList.get(0);

									SMatterDetail detail = new SMatterDetail();
									detail.type = SMatterDetailType.Task;
									detail.task = task;
									hd.bind(detail);
								}
								else
								{
									LOG.toast(context, "错误的个数：" + selectedList.size());
								}
							}
						});
					}
				}, SUserType.Master, SUserType.Teacher);
			}
		});

		hd.insert.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				addDetail(CommonUtils.findViewPos(hd.view), null);
			}
		});

		hd.remove.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				CommonUtils.removeView(hd.view);
			}
		});

		hd.up.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				CommonUtils.moveUpView(hd.view);
			}
		});

		hd.down.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				CommonUtils.moveDownView(hd.view);
			}
		});

		view.setTag(hd);
		holder.detailContainer.addView(view, pos);
	}

	private class HolderHour
	{
		private View view;

		private EditText hourBuy;
		private EditText hourGive;
		private TextView insert;
		private TextView remove;
		private TextView up;
		private TextView down;

		private HolderHour(View view)
		{
			this.view = view;

			hourBuy = view.findViewById(R.id.hourBuy);
			hourGive = view.findViewById(R.id.hourGive);
			insert = view.findViewById(R.id.insert);
			remove = view.findViewById(R.id.remove);
			up = view.findViewById(R.id.up);
			down = view.findViewById(R.id.down);
		}
	}

	private class HolderDetail
	{
		private View view;

		private ImageView img;
		private ImageView play;
		private TextView task;
		private TextView insert;
		private TextView remove;
		private TextView up;
		private TextView down;

		private HolderDetail(View view)
		{
			this.view = view;

			img = view.findViewById(R.id.img);
			play = view.findViewById(R.id.play);
			task = view.findViewById(R.id.task);
			insert = view.findViewById(R.id.insert);
			remove = view.findViewById(R.id.remove);
			up = view.findViewById(R.id.up);
			down = view.findViewById(R.id.down);
		}

		private SMatterDetail detail;

		private void bind(final SMatterDetail detail)
		{
			this.detail = detail;

			if (detail.type == SMatterDetailType.Img)
			{
				play.setVisibility(View.GONE);
				ImageLoad.displayImage(getContext(), detail.imgUrl, img, R.drawable.img_bad, null);
				task.setText("任务");
			}
			else if (detail.type == SMatterDetailType.Video)
			{
				play.setVisibility(View.VISIBLE);
				ImageLoad.displayImage(getContext(), detail.imgUrl, img, R.drawable.img_bad, null);
				task.setText("任务");
			}
			else if (detail.type == SMatterDetailType.Task)
			{
				play.setVisibility(View.GONE);
				img.setImageResource(R.drawable.img_task_default_cover_small);
				task.setText("刷新中。。。");
				final SRequest_GetUser request = new SRequest_GetUser();
				request.userId = detail.task.userId;
				Protocol.doPost(getContext(), App.getApi(), SHandleId.GetUser, request.saveToStr(), new Protocol.OnCallback()
				{
					@Override
					public void onResponse(int code, String data, String msg)
					{
						if (code == 200)
						{
							SResponse_GetUser response = SResponse_GetUser.load(data);
							if (response.user != null)
							{
								task.setText(String.format("%s：%s", response.user.name, detail.task.name));
							}
							else
							{
								task.setText(String.format("用户 %s 不存在", request.userId));
							}
						}
						else
						{
							task.setText("加载用户失败");
						}
					}
				});
			}
		}
	}

	private void applyToUI(SMatter data)
	{
		if (data == null)
			data = new SMatter();

		holder.id.setText(data.id);
		holder.name.setText(data.name);
		holder.sort.setText(String.valueOf(data.sort));
		holder.invalid.setChecked(data.invalid);

		holder.userId.setTag(data.userId);
		if (TextUtils.isEmpty(data.userId))
		{
			holder.userId.setText("未指定");
		}
		else
		{
			holder.userId.setText("");
			final SRequest_GetUser request = new SRequest_GetUser();
			request.userId = data.userId;
			Protocol.doPost(getContext(), App.getApi(), SHandleId.GetUser, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_GetUser response = SResponse_GetUser.load(data);
						if (response.user != null)
						{
							holder.userId.setText(response.user.name);
						}
						else
						{
							holder.userId.setText(String.format("用户 %s 不存在", request.userId));
						}
					}
					else
					{
						holder.userId.setText("加载失败");
					}
				}
			});
		}

		if (data.type == SMatterType.Class)
			holder.typeClass.setChecked(true);
		else if (data.type == SMatterType.Pair)
			holder.typePair.setChecked(true);

		if (data.place == SMatterPlace.Default)
			holder.placeDefault.setChecked(true);
		else if (data.place == SMatterPlace.Main)
			holder.placeMain.setChecked(true);
		else if (data.place == SMatterPlace.Banner)
			holder.placeBanner.setChecked(true);

		holder.cover.setTag(data.cover);
		if (!TextUtils.isEmpty(data.cover))
			ImageLoad.displayImage(getContext(), data.cover, holder.cover, R.drawable.img_bad, null);
		else
			holder.cover.setImageResource(R.drawable.img_default);

		holder.banner.setTag(data.banner);
		if (!TextUtils.isEmpty(data.banner))
			ImageLoad.displayImage(getContext(), data.banner, holder.banner, R.drawable.img_bad, null);
		else
			holder.banner.setImageResource(R.drawable.img_default);

		holder.buyCount.setText(String.valueOf(data.buyCount));
		holder.moneyRaw.setText(String.valueOf(data.moneyRaw));
		holder.money.setText(String.valueOf(data.money));

		holder.hourContainer.removeAllViews();
		for (SMatterHour hour : data.hours)
		{
			addHour(holder.hourContainer.getChildCount(), hour);
		}

		holder.detailContainer.removeAllViews();
		for (SMatterDetail detail : data.details)
		{
			addDetail(holder.detailContainer.getChildCount(), detail);
		}
	}

	private SMatter genData()
	{
		SMatter data = new SMatter();

		data.id = holder.id.getText().toString();
		data.name = holder.name.getText().toString();
		data.sort = Long.valueOf(holder.sort.getText().toString());
		data.invalid = holder.invalid.isChecked();

		data.userId = (String) holder.userId.getTag();

		if (holder.typeClass.isChecked())
			data.type = SMatterType.Class;
		else if (holder.typePair.isChecked())
			data.type = SMatterType.Pair;

		if (holder.placeDefault.isChecked())
			data.place = SMatterPlace.Default;
		else if (holder.placeMain.isChecked())
			data.place = SMatterPlace.Main;
		else if (holder.placeBanner.isChecked())
			data.place = SMatterPlace.Banner;

		data.cover = (String) holder.cover.getTag();
		data.banner = (String) holder.banner.getTag();

		data.buyCount = Integer.valueOf(holder.buyCount.getText().toString());
		data.moneyRaw = Integer.valueOf(holder.moneyRaw.getText().toString());
		data.money = Integer.valueOf(holder.money.getText().toString());

		for (int i = 0; i < holder.hourContainer.getChildCount(); i++)
		{
			View view = holder.hourContainer.getChildAt(i);
			HolderHour hd = (HolderHour) view.getTag();
			SMatterHour hour = new SMatterHour();
			hour.hourBuy = Float.valueOf(hd.hourBuy.getText().toString());
			hour.hourGive = Float.valueOf(hd.hourGive.getText().toString());
			data.hours.add(hour);
		}

		for (int i = 0; i < holder.detailContainer.getChildCount(); i++)
		{
			View view = holder.detailContainer.getChildAt(i);
			HolderDetail hd = (HolderDetail) view.getTag();
			data.details.add(hd.detail);
		}

		return data;
	}

	private boolean check(SMatter matter)
	{
		if (TextUtils.isEmpty(matter.userId))
		{
			LOG.toast(context, "请指派负责人");
			return false;
		}
		if (TextUtils.isEmpty(matter.cover))
		{
			LOG.toast(context, "请选择封面");
			return false;
		}
		if (matter.place.equals(SMatterPlace.Banner) && TextUtils.isEmpty(matter.banner))
		{
			LOG.toast(context, "请选择Banner");
			return false;
		}
		for (int i = 0; i < matter.details.size(); i++)
		{
			SMatterDetail detail = matter.details.get(i);
			if (detail.type.equals(SMatterDetailType.Img) && TextUtils.isEmpty(detail.imgUrl))
			{
				LOG.toast(context, String.format("第 %d 个详情未设置", i + 1));
				return false;
			}
			if (detail.type.equals(SMatterDetailType.Video) && (TextUtils.isEmpty(detail.imgUrl) || TextUtils.isEmpty(detail.videoUrl)))
			{
				LOG.toast(context, String.format("第 %d 个详情未设置", i + 1));
				return false;
			}
			if (detail.type.equals(SMatterDetailType.Task) && detail.task == null)
			{
				LOG.toast(context, String.format("第 %d 个详情未设置", i + 1));
				return false;
			}
		}
		return true;
	}

	private void ok()
	{
		SMatter matter = genData();
		if (check(matter))
		{
			dismiss();
			if (listener != null)
				listener.onResult(matter);
		}
	}

	public static void show(Context context, SMatter matter, OnResultListener listener)
	{
		DialogEditMatter dialog = new DialogEditMatter(context, matter);
		dialog.setListener(listener);
		dialog.show();
	}

}
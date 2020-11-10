package com.lys.kit.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.FsUtils;
import com.lys.base.utils.ImageLoader;
import com.lys.kit.R;
import com.lys.kit.activity.ActivitySelectImage;
import com.lys.kit.fragment.FragmentSelectImage;
import com.lys.kit.utils.ImageLoad;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class AdapterImage extends RecyclerView.Adapter<AdapterImage.Holder>
{
	private FragmentSelectImage owner = null;
	public List<File> images = null;

	public boolean editMode = false;
	public HashMap<Integer, File> selectMap = new HashMap<>();

	public AdapterImage(FragmentSelectImage owner)
	{
		this.owner = owner;
	}

	public void setData(List<File> images)
	{
		this.images = images;
		notifyDataSetChanged();
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, final int position)
	{
		final File image = images.get(position);
		final Context context = holder.itemView.getContext();

		final int imageWidth = 435;

		File smallDir = new File(image.getParentFile(), "small");
		if (!smallDir.exists())
			smallDir.mkdirs();
		final File small = new File(smallDir, image.getName() + "." + imageWidth + ".small");
		final File durationFile = new File(smallDir, image.getName() + ".duration");

		if (ActivitySelectImage.isMovie(image.getName()))
		{
			holder.videoFlag.setVisibility(View.VISIBLE);
			holder.durationText.setVisibility(View.VISIBLE);
			if (!small.exists() || !durationFile.exists())
			{
				try
				{
					MediaMetadataRetriever mmr = new MediaMetadataRetriever();
					mmr.setDataSource(image.getAbsolutePath());
					long duration = Long.valueOf(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
					FsUtils.writeText(durationFile, String.valueOf(duration));
					Bitmap bitmap = mmr.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST);
//					Bitmap bitmap = mmr.getFrameAtTime(duration * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
					if (bitmap != null)
					{
						Bitmap smallBitmap = CommonUtils.scaleBitmap(bitmap, imageWidth / 2);
						CommonUtils.saveBitmap(smallBitmap, Bitmap.CompressFormat.JPEG, small);
						bitmap.recycle();
						smallBitmap.recycle();
					}
					mmr.release();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			if (durationFile.exists())
				holder.durationText.setText(formatTime(Long.valueOf(FsUtils.readText(durationFile))));
			else
				holder.durationText.setText("unknow");
		}
		else
		{
			holder.videoFlag.setVisibility(View.GONE);
			holder.durationText.setVisibility(View.GONE);
		}

		if (small.exists())
		{
			ImageLoad.displayImage(context, small.getAbsolutePath(), 0, holder.image, R.drawable.img_default, new ImageLoader.OnDisplay()
			{
				@Override
				public void success(Bitmap bitmap, String url)
				{
					ViewGroup.LayoutParams layoutParams = holder.con.getLayoutParams();
					layoutParams.height = imageWidth * bitmap.getHeight() / bitmap.getWidth();
					holder.con.setLayoutParams(layoutParams);
				}
			});
		}
		else
		{
			if (ActivitySelectImage.isMovie(image.getName()))
			{
				holder.image.setImageResource(R.drawable.img_default);
				ViewGroup.LayoutParams layoutParams = holder.con.getLayoutParams();
				layoutParams.height = imageWidth;
				holder.con.setLayoutParams(layoutParams);
			}
			else
			{
				ImageLoad.displayImage(context, image.getAbsolutePath(), imageWidth / 2, holder.image, R.drawable.img_default, new ImageLoader.OnDisplay()
				{
					@Override
					public void success(Bitmap bitmap, String url)
					{
						ViewGroup.LayoutParams layoutParams = holder.con.getLayoutParams();
						layoutParams.height = imageWidth * bitmap.getHeight() / bitmap.getWidth();
						holder.con.setLayoutParams(layoutParams);
						CommonUtils.saveBitmap(bitmap, small);
					}
				});
			}
		}

		holder.image.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (editMode)
				{
					if (selectMap.containsKey(position))
						selectMap.remove(position);
					else
						selectMap.put(position, image);
					holder.check.setChecked(selectMap.containsKey(position));
				}
				else
				{
					owner.select(image);
				}
			}
		});

		updateCheck(holder, position);
	}

	private String formatTime(long ms)
	{
		int second = (int) (ms / 1000);
		int minute = second / 60;
		second = second % 60;
		int hour = minute / 60;
		minute = minute % 60;
		if (hour == 0)
			return String.format("%d:%02d", minute, second);
		else
			return String.format("%d:%02d:%02d", hour, minute, second);
	}

	private void updateCheck(final Holder holder, final int position)
	{
		final File image = images.get(position);
		final Context context = holder.itemView.getContext();

		holder.check.setVisibility(editMode ? View.VISIBLE : View.GONE);
		holder.check.setChecked(selectMap.containsKey(position));

		if (editMode)
		{
			holder.image.setLongClickable(false);
		}
		else
		{
			holder.image.setOnLongClickListener(new View.OnLongClickListener()
			{
				@Override
				public boolean onLongClick(View view)
				{
					selectMap.put(position, image);
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
		if (images != null)
			return images.size();
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ViewGroup con;
		public ImageView image;
		public ImageView videoFlag;
		public TextView durationText;
		public CheckBox check;

		public Holder(View itemView)
		{
			super(itemView);
			con = itemView.findViewById(R.id.con);
			image = itemView.findViewById(R.id.image);
			videoFlag = itemView.findViewById(R.id.videoFlag);
			durationText = itemView.findViewById(R.id.durationText);
			check = itemView.findViewById(R.id.check);
		}
	}
}
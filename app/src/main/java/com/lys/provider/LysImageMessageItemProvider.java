package com.lys.provider;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lys.app.R;
import com.lys.base.activity.BaseActivity;
import com.lys.base.utils.ImageLoader;
import com.lys.base.utils.LOG;
import com.lys.base.utils.SysUtils;
import com.lys.fragment.LysConversationFragment;
import com.lys.kit.activity.KitActivity;
import com.lys.protobuf.SImageMessageExtra;

import java.util.LinkedHashMap;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.AsyncImageView;
import io.rong.imkit.widget.provider.ImageMessageItemProvider;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;

@ProviderTag(messageContent = ImageMessage.class, showProgress = false, showReadState = true, showSummaryWithName = false)
public class LysImageMessageItemProvider extends ImageMessageItemProvider
{
	public static String getImageMessageUrl(ImageMessage imageMessage)
	{
		if (imageMessage.getLocalUri() != null)
			return imageMessage.getLocalUri().getPath();
		else if (imageMessage.getRemoteUri() != null)
			return imageMessage.getRemoteUri().toString();
		return null;
	}

	@Override
	public View newView(Context context, ViewGroup group)
	{
		return super.newView(context, group);
	}

	@Override
	public void onItemClick(View view, int position, ImageMessage imageMessage, UIMessage message)
	{
		if (imageMessage != null)
		{
			final Context context = view.getContext();
			if (context instanceof KitActivity)
			{
				final KitActivity activity = (KitActivity) context;
				String url = getImageMessageUrl(imageMessage);
				if (!TextUtils.isEmpty(url))
				{
					ImageLoader.load(context, url, SysUtils.screenWidth(context), new ImageLoader.OnLoad()
					{
						@Override
						public void over(Bitmap bitmap, String url)
						{
							if (bitmap != null)
							{
								activity.board(bitmap, "发送", new BaseActivity.OnImageListener()
								{
									@Override
									public void onResult(String filepath)
									{
										LysConversationFragment fragment = (LysConversationFragment) activity.getSupportFragmentManager().findFragmentById(R.id.conversation);
										boolean sendOrigin = true;
										LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap();
										linkedHashMap.put("file://" + filepath, 1);
										fragment.onImageResult(linkedHashMap, sendOrigin);
									}
								});
							}
							else
							{
								LOG.toast(context, "图像下载失败：" + url);
							}
						}
					});
				}
			}
		}
	}

	@Override
	public void bindView(View view, int position, ImageMessage imageMessage, UIMessage message)
	{
//		super.bindView(view, position, imageMessage, message);

		final ViewHolder holder = new ViewHolder();
		holder.img = view.findViewById(R.id.rc_img);
		holder.message = view.findViewById(R.id.rc_msg);

		if (message.getMessageDirection() == Message.MessageDirection.SEND)
		{
			view.setBackgroundResource(R.drawable.rc_ic_bubble_no_right);
		}
		else
		{
			view.setBackgroundResource(R.drawable.rc_ic_bubble_no_left);
		}

//		if (img.getWidth() > 0)
		{
			String url = getImageMessageUrl(imageMessage);
			if (!TextUtils.isEmpty(url))
			{
				if (!TextUtils.isEmpty(imageMessage.getExtra()))
				{
					SImageMessageExtra extra = SImageMessageExtra.load(imageMessage.getExtra());
					ViewGroup.LayoutParams params = holder.img.getLayoutParams();
					params.width = extra.width;
					params.height = extra.height;
					holder.img.setLayoutParams(params);
				}
				ImageLoader.displayImage(view.getContext(), url, 512, holder.img, R.drawable.img_default, new ImageLoader.OnDisplay()
				{
					@Override
					public void success(Bitmap bitmap, String url)
					{
						int width;
						int height;
						if (bitmap.getWidth() < 128)
						{
							width = 128;
							height = 128 * bitmap.getHeight() / bitmap.getWidth();
						}
						else if (bitmap.getWidth() > 512)
						{
							width = 512;
							height = 512 * bitmap.getHeight() / bitmap.getWidth();
						}
						else
						{
							width = bitmap.getWidth();
							height = bitmap.getHeight();
						}
						ViewGroup.LayoutParams params = holder.img.getLayoutParams();
						params.width = width;
						params.height = height;
						holder.img.setLayoutParams(params);
					}
				});
			}
		}

		int progress = message.getProgress();
		Message.SentStatus status = message.getSentStatus();
		if (status.equals(Message.SentStatus.SENDING) && progress < 100)
		{
			holder.message.setText(progress + "%");
			holder.message.setVisibility(View.VISIBLE);
		}
		else
		{
			holder.message.setVisibility(View.GONE);
		}

	}

	private static class ViewHolder
	{
		AsyncImageView img;
		TextView message;
	}

}

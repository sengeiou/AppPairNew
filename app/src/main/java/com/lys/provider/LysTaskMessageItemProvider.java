package com.lys.provider;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lys.activity.ActivityTaskBook;
import com.lys.app.R;
import com.lys.message.TaskMessage;
import com.lys.protobuf.SPTaskType;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

@ProviderTag(messageContent = TaskMessage.class, showReadState = true, showSummaryWithName = false)
public class LysTaskMessageItemProvider extends IContainerItemProvider.MessageProvider<TaskMessage>
{
	public static String getTypeDes(Integer type)
	{
		if (type.equals(SPTaskType.Job))
			return "作业";
		else if (type.equals(SPTaskType.Class))
			return "课程";
		return "未知";
	}

	@Override
	public View newView(Context context, ViewGroup viewGroup)
	{
		View view = LayoutInflater.from(context).inflate(R.layout.item_task_message, null);
		ViewHolder holder = new ViewHolder();

		holder.conRight = view.findViewById(R.id.conRight);
		holder.bgRight = view.findViewById(R.id.bgRight);
		holder.typeRight = view.findViewById(R.id.typeRight);
		holder.nameRight = view.findViewById(R.id.nameRight);
		holder.sendNameRight = view.findViewById(R.id.sendNameRight);
		holder.dateRight = view.findViewById(R.id.dateRight);

		holder.conLeft = view.findViewById(R.id.conLeft);
		holder.bgLeft = view.findViewById(R.id.bgLeft);
		holder.typeLeft = view.findViewById(R.id.typeLeft);
		holder.nameLeft = view.findViewById(R.id.nameLeft);
		holder.sendNameLeft = view.findViewById(R.id.sendNameLeft);
		holder.dateLeft = view.findViewById(R.id.dateLeft);

		view.setTag(holder);
		return view;
	}

	@Override
	public Spannable getContentSummary(TaskMessage taskMessage)
	{
		return new SpannableString(String.format("%s：%s", getTypeDes(taskMessage.type), taskMessage.name));
	}

	@Override
	public void onItemClick(View view, int position, TaskMessage taskMessage, UIMessage uiMessage)
	{
	}

	@Override
	public void onItemLongClick(View view, int position, TaskMessage taskMessage, UIMessage uiMessage)
	{
	}

	@Override
	public void bindView(final View view, int position, final TaskMessage taskMessage, UIMessage uiMessage)
	{
		ViewHolder holder = (ViewHolder) view.getTag();

		holder.conRight.setVisibility(View.GONE);
		holder.conLeft.setVisibility(View.GONE);

		if (uiMessage.getMessageDirection() == Message.MessageDirection.SEND)
		{
			holder.conRight.setVisibility(View.VISIBLE);

			if (taskMessage.type.equals(SPTaskType.Job))
			{
				holder.bgRight.setBackgroundResource(R.drawable.img_task_bg_right_job);
				holder.typeRight.setImageResource(R.drawable.img_task_type_job);
			}
			else if (taskMessage.type.equals(SPTaskType.Class))
			{
				holder.bgRight.setBackgroundResource(R.drawable.img_task_bg_right_class);
				holder.typeRight.setImageResource(R.drawable.img_task_type_class);
			}

			holder.nameRight.setText(taskMessage.name);
			holder.sendNameRight.setText(String.format("发送者：%s", TextUtils.isEmpty(taskMessage.sendUser_name) ? "未知" : taskMessage.sendUser_name));
			holder.dateRight.setText(new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date(taskMessage.createTime)));

			holder.bgRight.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					ActivityTaskBook.goinWithNone(view.getContext(), taskMessage.convert());
				}
			});
		}
		else
		{
			holder.conLeft.setVisibility(View.VISIBLE);

			if (taskMessage.type.equals(SPTaskType.Job))
			{
				holder.bgLeft.setBackgroundResource(R.drawable.img_task_bg_left_job);
				holder.typeLeft.setImageResource(R.drawable.img_task_type_job);
			}
			else if (taskMessage.type.equals(SPTaskType.Class))
			{
				holder.bgLeft.setBackgroundResource(R.drawable.img_task_bg_left_class);
				holder.typeLeft.setImageResource(R.drawable.img_task_type_class);
			}

			holder.nameLeft.setText(taskMessage.name);
			holder.sendNameLeft.setText(String.format("发送者：%s", TextUtils.isEmpty(taskMessage.sendUser_name) ? "未知" : taskMessage.sendUser_name));
			holder.dateLeft.setText(new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date(taskMessage.createTime)));

			holder.bgLeft.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					ActivityTaskBook.goinWithNone(view.getContext(), taskMessage.convert());
				}
			});
		}
	}

	private static class ViewHolder
	{
		ViewGroup conRight;
		ImageView bgRight;
		ImageView typeRight;
		TextView nameRight;
		TextView sendNameRight;
		TextView dateRight;

		ViewGroup conLeft;
		ImageView bgLeft;
		ImageView typeLeft;
		TextView nameLeft;
		TextView sendNameLeft;
		TextView dateLeft;
	}
}

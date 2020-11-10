package com.lys.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lys.App;
import com.lys.activity.ActivityTaskBook;
import com.lys.activity.ActivityTeachRecord;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.dialog.DialogStudentConfirm;
import com.lys.dialog.DialogStudentQuestionShow;
import com.lys.dialog.DialogTeachPageDetail;
import com.lys.dialog.DialogTeacherQuestionShow;
import com.lys.kit.dialog.DialogAlert;
import com.lys.protobuf.STeachRecord;
import com.lys.protobuf.SUser;
import com.lys.utils.UserCacheManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterTeachRecord extends RecyclerView.Adapter<AdapterTeachRecord.Holder>
{
	private ActivityTeachRecord owner = null;
	public List<STeachRecord> teachRecords = null;

	public AdapterTeachRecord(ActivityTeachRecord owner)
	{
		this.owner = owner;
	}

	public void setData(List<STeachRecord> teachRecords)
	{
		this.teachRecords = teachRecords;
		notifyDataSetChanged();
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teach_record, parent, false));
	}

	private static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy - MM - dd   HH : mm");

	@Override
	public void onBindViewHolder(final Holder holder, int position)
	{
		final STeachRecord teachRecord = teachRecords.get(position);
		final Context context = holder.itemView.getContext();

		if (teachRecord.task != null)
			holder.taskName.setText(teachRecord.task.name);
		else
			holder.taskName.setText("[任务不存在]");

		bindTargetName(holder.targetName, teachRecord.targetIds, position);

		holder.startTime.setText("" + formatDate.format(new Date(teachRecord.startTime)));

		long timeLen = teachRecord.overTime - teachRecord.startTime;
		holder.timeLen.setText("" + DialogStudentConfirm.formatTime(timeLen));
		bindTimeLenProcess(holder, timeLen / 1000);

		holder.host.setVisibility(teachRecord.isHost ? View.VISIBLE : View.GONE);

		boolean isMaster = (App.isSupterMaster() || App.isMaster());

		holder.confirm.setVisibility((isMaster && !TextUtils.isEmpty(teachRecord.confirmMsg)) ? View.VISIBLE : View.GONE);
		holder.confirm.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				DialogAlert.show(context, "学生留言", teachRecord.confirmMsg, null);
			}
		});

		holder.questionStudent.setVisibility((isMaster && hasQuestionStudent(teachRecord)) ? View.VISIBLE : View.GONE);
		holder.questionStudent.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				DialogStudentQuestionShow.show(context, teachRecord);
			}
		});

		holder.questionTeacher.setVisibility((isMaster && hasQuestionTeacher(teachRecord)) ? View.VISIBLE : View.GONE);
		holder.questionTeacher.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				DialogTeacherQuestionShow.show(context, teachRecord);
			}
		});

		holder.pageDetail.setVisibility((teachRecord.teachPages != null && teachRecord.teachPages.size() > 0) ? View.VISIBLE : View.GONE);
		holder.pageDetail.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				DialogTeachPageDetail.show(context, teachRecord.teachPages);
			}
		});

		holder.openTask.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (teachRecord.task != null)
					ActivityTaskBook.goinWithNone(context, teachRecord.task);
				else
					LOG.toast(context, "任务不存在");
			}
		});
	}

	private void bindTimeLenProcess(Holder holder, long timeLen)
	{
		final long allLen = 2 * 3600;
		timeLen = Math.max(timeLen, 0);
		timeLen = Math.min(timeLen, allLen);
		{
			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.timeLenProcessUse.getLayoutParams();
			lp.weight = timeLen;
			holder.timeLenProcessUse.setLayoutParams(lp);
		}
		{
			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.timeLenProcessFree.getLayoutParams();
			lp.weight = allLen - timeLen;
			holder.timeLenProcessFree.setLayoutParams(lp);
		}
	}

	private boolean hasQuestionStudent(STeachRecord teachRecord)
	{
		if (!TextUtils.isEmpty(teachRecord.questionMatch))
			return true;
		if (!TextUtils.isEmpty(teachRecord.questionDiff))
			return true;
		if (!TextUtils.isEmpty(teachRecord.questionGot))
			return true;
		if (!TextUtils.isEmpty(teachRecord.questionQuality))
			return true;
		if (!TextUtils.isEmpty(teachRecord.questionLike))
			return true;
		return false;
	}

	private boolean hasQuestionTeacher(STeachRecord teachRecord)
	{
		if (!TextUtils.isEmpty(teachRecord.questionHot))
			return true;
		if (!TextUtils.isEmpty(teachRecord.questionMind))
			return true;
		if (!TextUtils.isEmpty(teachRecord.questionLogic))
			return true;
		if (!TextUtils.isEmpty(teachRecord.questionOther))
			return true;
		return false;
	}

	private void bindTargetName(TextView targetName, List<String> targetIds, int position)
	{
		targetName.setText("");
		if (targetIds != null && targetIds.size() > 0)
		{
			targetName.setTag(position);
			StringBuilder sb = new StringBuilder();
			bindTargetNameImpl(targetName, sb, targetIds, 0, position);
		}
	}

	private void bindTargetNameImpl(final TextView targetName, final StringBuilder sb, final List<String> targetIds, final int index, final int position)
	{
		if (index < targetIds.size())
		{
			UserCacheManager.instance().getUser(targetIds.get(index), new UserCacheManager.OnResult()
			{
				@Override
				public void result(SUser user)
				{
					if (user != null)
					{
						if (index > 0)
							sb.append("，");
						sb.append(user.name);
						bindTargetNameImpl(targetName, sb, targetIds, index + 1, position);
					}
				}
			});
		}
		else
		{
			if (targetName.getTag().equals(position))
				targetName.setText(sb.toString());
		}
	}

	@Override
	public int getItemCount()
	{
		if (teachRecords != null)
			return teachRecords.size();
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public TextView taskName;
		public TextView targetName;
		public TextView startTime;
		public TextView timeLen;
		public View timeLenProcessUse;
		public View timeLenProcessFree;
		public TextView host;
		public TextView confirm;
		public TextView questionStudent;
		public TextView questionTeacher;
		public TextView pageDetail;
		public TextView openTask;

		public Holder(View itemView)
		{
			super(itemView);
			taskName = itemView.findViewById(R.id.taskName);
			targetName = itemView.findViewById(R.id.targetName);
			startTime = itemView.findViewById(R.id.startTime);
			timeLen = itemView.findViewById(R.id.timeLen);
			timeLenProcessUse = itemView.findViewById(R.id.timeLenProcessUse);
			timeLenProcessFree = itemView.findViewById(R.id.timeLenProcessFree);
			host = itemView.findViewById(R.id.host);
			confirm = itemView.findViewById(R.id.confirm);
			questionStudent = itemView.findViewById(R.id.questionStudent);
			questionTeacher = itemView.findViewById(R.id.questionTeacher);
			pageDetail = itemView.findViewById(R.id.pageDetail);
			openTask = itemView.findViewById(R.id.openTask);
		}
	}

}
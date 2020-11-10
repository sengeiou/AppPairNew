package com.lys.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lys.App;
import com.lys.activity.ActivityTeach;
import com.lys.activity.ActivityTeachTeacher;
import com.lys.app.R;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.LOG;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SRequest_ModifyTeach;
import com.lys.protobuf.SResponse_ModifyTeach;
import com.lys.protobuf.STeach;
import com.lys.protobuf.STeachBlock;
import com.lys.protobuf.STeachFlag;
import com.lys.protobuf.STeachLine;
import com.lys.protobuf.SUser;
import com.lys.utils.UserCacheManager;

import java.util.Date;
import java.util.List;

public class AdapterTeach extends RecyclerView.Adapter<AdapterTeach.Holder>
{
	private ActivityTeach owner = null;
	private List<STeachLine> teachLines = null;

	public AdapterTeach(ActivityTeach owner)
	{
		this.owner = owner;
	}

	public void setData(List<STeachLine> teachLines)
	{
		this.teachLines = teachLines;
		notifyDataSetChanged();
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teach, parent, false));
	}

	@Override
	public void onBindViewHolder(final Holder holder, int position)
	{
		final STeachLine teachLine = teachLines.get(position);
		final Context context = holder.itemView.getContext();

		holder.name.setText(teachLine.teacher.name);
		holder.name.setTextColor(0xff202122);

		holder.name.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(context, ActivityTeachTeacher.class);
				intent.putExtra("teacher", teachLine.teacher.saveToStr());
				context.startActivity(intent);
			}
		});

		holder.eventCon.removeAllViews();
		holder.commitCon.removeAllViews();

		holder.touch.setOnTouchListener(new View.OnTouchListener()
		{
			private int startIndex = -1;
			private int currIndex = -1;

			private Integer targetFlag = -1;

			@Override
			public boolean onTouch(View view, MotionEvent event)
			{
				if (!owner.modeIsFree() && !owner.modeIsUse() && !owner.modeIsOver())
					return false;

				ViewGroup parent = (ViewGroup) view.getParent();
				parent.requestDisallowInterceptTouchEvent(true);

				int pos = (int) event.getX();
				int count = ActivityTeach.blockEnd - ActivityTeach.blockBegin;
				int width = view.getWidth();
				int index = pos * count / width; // pos / (width / count)

				boolean hasChange = false;

				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					startIndex = index;
					currIndex = index;

					targetFlag = -1;

					hasChange = true;

					STeachBlock block = teachLine.blocks.get(index);
					if (owner.modeIsFree())
					{
						// 设置空闲
						if (block.flag.equals(STeachFlag.None))
							targetFlag = STeachFlag.Free;
						else if (block.flag.equals(STeachFlag.Free))
							targetFlag = STeachFlag.None;
					}
					else if (owner.modeIsUse())
					{
						if (owner.currStudent != null)
						{
							// 设置使用
							if (block.flag.equals(STeachFlag.Free))
								targetFlag = STeachFlag.Use;
							else if (block.flag.equals(STeachFlag.Use) && block.studentId.equals(owner.currStudent.id))
								targetFlag = STeachFlag.Free;
						}
						else
						{
							DialogAlert.show(context, "提示", "请先选择学生", null, "我知道了");
						}
					}
					else if (owner.modeIsOver())
					{
						// 设置结束
						if (block.flag.equals(STeachFlag.Use))
							targetFlag = STeachFlag.Over;
						else if (block.flag.equals(STeachFlag.Over))
							targetFlag = STeachFlag.Use;
					}
				}
				else if (event.getAction() == MotionEvent.ACTION_MOVE)
				{
					if (targetFlag != -1)
					{
						if (index != currIndex)
						{
							currIndex = index;
							hasChange = true;
						}
					}
				}
				else if (event.getAction() == MotionEvent.ACTION_UP)
				{
					if (targetFlag != -1)
					{
						Date date = new Date(owner.currTime);

						SRequest_ModifyTeach request = new SRequest_ModifyTeach();

						int min = Math.min(startIndex, currIndex);
						int max = Math.max(startIndex, currIndex);
						for (int i = 0; i < holder.eventCon.getChildCount(); i++)
						{
							STeachBlock block = teachLine.blocks.get(i);

							boolean canModify = false;
							if (owner.modeIsFree())
							{
								if (block.flag.equals(STeachFlag.None))
									canModify = true;
								else if (block.flag.equals(STeachFlag.Free))
									canModify = true;
							}
							else if (owner.modeIsUse())
							{
								if (block.flag.equals(STeachFlag.Free))
									canModify = true;
								else if (block.flag.equals(STeachFlag.Use) && block.studentId.equals(owner.currStudent.id))
									canModify = true;
							}
							else if (owner.modeIsOver())
							{
								if (block.flag.equals(STeachFlag.Use))
									canModify = true;
								else if (block.flag.equals(STeachFlag.Over))
									canModify = true;
							}

							if (i >= min && i <= max && canModify)
							{
								block.flag = targetFlag;
								if (owner.modeIsUse())
								{
									if (targetFlag.equals(STeachFlag.Free))
										block.studentId = "";
									else if (targetFlag.equals(STeachFlag.Use))
										block.studentId = owner.currStudent.id;
								}

								STeach teach = new STeach();
								teach.teacherId = teachLine.teacher.id;
								teach.year = date.getYear() + 1900;
								teach.month = date.getMonth() + 1;
								teach.day = date.getDate();
								teach.block = block.block;
								teach.flag = block.flag;
								teach.studentId = block.studentId;

								request.teachs.add(teach);
							}
						}

						Protocol.doPost(context, App.getApi(), SHandleId.ModifyTeach, request.saveToStr(), new Protocol.OnCallback()
						{
							@Override
							public void onResponse(int code, String data, String msg)
							{
								if (code == 200)
								{
									SResponse_ModifyTeach response = SResponse_ModifyTeach.load(data);
									notifyDataSetChanged();
								}
								else
								{
									DialogAlert.show(context, "设置失败", "请点击刷新界面", new DialogAlert.OnClickListener()
									{
										@Override
										public void onClick(int which)
										{
											owner.requestTeacher(owner.currTime);
										}
									}, "刷新界面");
								}
							}
						});
					}
				}

				if (targetFlag != -1 && hasChange)
				{
					LOG.v("has change " + targetFlag);

					int min = Math.min(startIndex, currIndex);
					int max = Math.max(startIndex, currIndex);
					for (int i = 0; i < holder.eventCon.getChildCount(); i++)
					{
						STeachBlock block = teachLine.blocks.get(i);

						boolean canModify = false;
						if (owner.modeIsFree())
						{
							if (block.flag.equals(STeachFlag.None))
								canModify = true;
							else if (block.flag.equals(STeachFlag.Free))
								canModify = true;
						}
						else if (owner.modeIsUse())
						{
							if (block.flag.equals(STeachFlag.Free))
								canModify = true;
							else if (block.flag.equals(STeachFlag.Use) && block.studentId.equals(owner.currStudent.id))
								canModify = true;
						}
						else if (owner.modeIsOver())
						{
							if (block.flag.equals(STeachFlag.Use))
								canModify = true;
							else if (block.flag.equals(STeachFlag.Over))
								canModify = true;
						}

						if (i >= min && i <= max && canModify)
							bindBlock(holder.eventCon.getChildAt(i), block.block, targetFlag);
						else
							bindBlock(holder.eventCon.getChildAt(i), block.block, block.flag);
					}

				}

				return true;
			}
		});

		for (int i = 0; i < teachLine.blocks.size(); i++)
		{
			final STeachBlock block = teachLine.blocks.get(i);
			addBlock(context, holder.eventCon, block);
		}

		STeachBlock lastBlock = null;
		for (int i = 0; i < teachLine.blocks.size(); i++)
		{
			STeachBlock block = teachLine.blocks.get(i);
			if (i == 0)
			{
				lastBlock = block;
			}
			else if (!CommonUtils.strEqual(lastBlock.studentId, block.studentId))
			{
				if (!TextUtils.isEmpty(lastBlock.studentId))
					addUser(context, holder.commitCon, lastBlock, block.block - lastBlock.block);
				else
					addSpace(context, holder.commitCon, block.block - lastBlock.block);
				lastBlock = block;
			}
		}
		if (!TextUtils.isEmpty(lastBlock.studentId))
			addUser(context, holder.commitCon, lastBlock, 48 - lastBlock.block);
		else
			addSpace(context, holder.commitCon, 48 - lastBlock.block);

	}

	private void bindBlock(View view, Integer block, Integer flag)
	{
		if (flag.equals(STeachFlag.None))
		{
			if (block / 2 * 2 % 4 == 0)
				view.setBackgroundColor(0xfff5f5dc);
			else
				view.setBackgroundColor(0xfff0ffff);
		}
		else if (flag.equals(STeachFlag.Free))
		{
			view.setBackgroundColor(Color.GREEN);
		}
		else if (flag.equals(STeachFlag.Use))
		{
			view.setBackgroundColor(Color.BLUE);
		}
		else if (flag.equals(STeachFlag.Over))
		{
			view.setBackgroundColor(Color.BLACK);
		}
	}

	private void addBlock(Context context, ViewGroup con, STeachBlock block)
	{
		View view = new View(context);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
		layoutParams.weight = 1;
		view.setLayoutParams(layoutParams);
		bindBlock(view, block.block, block.flag);
		con.addView(view);
	}

	private void addSpace(Context context, ViewGroup con, float weight)
	{
		View view = new View(context);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
		layoutParams.weight = weight;
		view.setLayoutParams(layoutParams);
		con.addView(view);
	}

	private void addUser(Context context, ViewGroup con, final STeachBlock block, float weight)
	{
		final TextView view = new TextView(context);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
		layoutParams.weight = weight;
		view.setLayoutParams(layoutParams);
		view.setTextSize(15);
		view.setTextColor(0xffffffff);
		view.setBackgroundResource(R.drawable.teach_name_bg);
		view.setGravity(Gravity.CENTER);
		if (!owner.onlyShow() || (owner.currStudent != null && block.studentId.equals(owner.currStudent.id)))
		{
			UserCacheManager.instance().getUser(block.studentId, new UserCacheManager.OnResult()
			{
				@Override
				public void result(SUser user)
				{
					if (user != null)
						view.setText(user.name);
					else
						view.setText(block.studentId);
				}
			});
		}
		con.addView(view);
	}

	@Override
	public int getItemCount()
	{
		if (teachLines != null)
			return teachLines.size();
		else
			return 0;
	}

	protected class Holder extends RecyclerView.ViewHolder
	{
		public ViewGroup con;
		public TextView name;
		public ViewGroup eventCon;
		public ViewGroup commitCon;
		public View touch;

		public Holder(View itemView)
		{
			super(itemView);
			con = itemView.findViewById(R.id.con);
			name = itemView.findViewById(R.id.name);
			eventCon = itemView.findViewById(R.id.eventCon);
			commitCon = itemView.findViewById(R.id.commitCon);
			touch = itemView.findViewById(R.id.touch);
		}
	}

}
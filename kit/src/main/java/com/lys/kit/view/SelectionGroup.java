package com.lys.kit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lys.base.utils.LOG;
import com.lys.kit.R;
import com.lys.protobuf.SProblemType;
import com.lys.protobuf.SSelectionGroup;

import java.util.ArrayList;

public class SelectionGroup extends RelativeLayout
{
	private class Holder
	{
		private ArrayList<ViewGroup> selectionCons = new ArrayList<>();
		private ArrayList<CheckBox> selections = new ArrayList<>();
		private ArrayList<ImageView> isRightSelections = new ArrayList<>();
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.selectionCons.add((ViewGroup) findViewById(R.id.selectionCon0));
		holder.selectionCons.add((ViewGroup) findViewById(R.id.selectionCon1));
		holder.selectionCons.add((ViewGroup) findViewById(R.id.selectionCon2));
		holder.selectionCons.add((ViewGroup) findViewById(R.id.selectionCon3));
		holder.selectionCons.add((ViewGroup) findViewById(R.id.selectionCon4));
		holder.selectionCons.add((ViewGroup) findViewById(R.id.selectionCon5));
		holder.selectionCons.add((ViewGroup) findViewById(R.id.selectionCon6));

		holder.selections.add((CheckBox) findViewById(R.id.selection0));
		holder.selections.add((CheckBox) findViewById(R.id.selection1));
		holder.selections.add((CheckBox) findViewById(R.id.selection2));
		holder.selections.add((CheckBox) findViewById(R.id.selection3));
		holder.selections.add((CheckBox) findViewById(R.id.selection4));
		holder.selections.add((CheckBox) findViewById(R.id.selection5));
		holder.selections.add((CheckBox) findViewById(R.id.selection6));

		holder.isRightSelections.add((ImageView) findViewById(R.id.isRightSelection0));
		holder.isRightSelections.add((ImageView) findViewById(R.id.isRightSelection1));
		holder.isRightSelections.add((ImageView) findViewById(R.id.isRightSelection2));
		holder.isRightSelections.add((ImageView) findViewById(R.id.isRightSelection3));
		holder.isRightSelections.add((ImageView) findViewById(R.id.isRightSelection4));
		holder.isRightSelections.add((ImageView) findViewById(R.id.isRightSelection5));
		holder.isRightSelections.add((ImageView) findViewById(R.id.isRightSelection6));
	}

	public SSelectionGroup mSelectionGroup = null;

	public SelectionGroup(Context context)
	{
		super(context);
		init(context);
	}

	public SelectionGroup(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	private void init(Context context)
	{
		LayoutInflater.from(context).inflate(R.layout.view_selection_group, this, true);
		initHolder();
	}

	public void setSelectionGroup(SSelectionGroup selectionGroup)
	{
		this.mSelectionGroup = selectionGroup;
	}

	public void updateSelections(boolean showRightSelection)
	{
		for (int i = 0; i < holder.selections.size(); i++)
		{
			if (i < mSelectionGroup.selections.size())
			{
				holder.selectionCons.get(i).setVisibility(View.VISIBLE);
				holder.selections.get(i).setText(mSelectionGroup.selections.get(i));
				if (mSelectionGroup.answer.contains(mSelectionGroup.selections.get(i)))
				{
					holder.selections.get(i).setChecked(true);
					holder.selections.get(i).setTextColor(0xffffffff);
				}
				else
				{
					holder.selections.get(i).setChecked(false);
					holder.selections.get(i).setTextColor(0xff50daff);
				}
				if (showRightSelection && mSelectionGroup.rightAnswer.contains(mSelectionGroup.selections.get(i)))
				{
					holder.isRightSelections.get(i).setVisibility(View.VISIBLE);
				}
				else
				{
					holder.isRightSelections.get(i).setVisibility(View.GONE);
				}
			}
			else
			{
				holder.selectionCons.get(i).setVisibility(View.GONE);
			}
		}
	}

	public void lockSelections()
	{
		for (int i = 0; i < holder.selections.size(); i++)
		{
			holder.selections.get(i).setClickable(false);
		}
	}

	public void unlockSelections()
	{
		for (int i = 0; i < holder.selections.size(); i++)
		{
			holder.selections.get(i).setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					if (mSelectionGroup != null)
					{
						switch (mSelectionGroup.problemType)
						{
						case SProblemType.SingleSelect:
							for (int i = 0; i < holder.selections.size(); i++)
							{
								if (holder.selections.get(i) == view)
								{
									holder.selections.get(i).setChecked(true);
								}
								else
								{
									holder.selections.get(i).setChecked(false);
								}
							}
							break;
						}

						for (int i = 0; i < holder.selections.size(); i++)
						{
							if (holder.selections.get(i).isChecked())
							{
								holder.selections.get(i).setTextColor(0xffffffff);
							}
							else
							{
								holder.selections.get(i).setTextColor(0xff50daff);
							}
						}

						mSelectionGroup.answer.clear();
						int count = Math.min(mSelectionGroup.selections.size(), holder.selections.size());
						for (int i = 0; i < count; i++)
						{
							if (holder.selections.get(i).isChecked())
							{
								mSelectionGroup.answer.add(mSelectionGroup.selections.get(i));
							}
						}
						if (mListener != null)
							mListener.onModifySelections();
					}
					else
					{
						LOG.toast(getContext(), "数据未加载");
					}
				}
			});
		}
	}

	private OnListener mListener = null;

	public void setListener(OnListener listener)
	{
		this.mListener = listener;
	}

	public interface OnListener
	{
		void onModifySelections();
	}
}

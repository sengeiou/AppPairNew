package com.lys.kit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.lys.base.utils.LOG;
import com.lys.kit.R;
import com.lys.kit.config.Config;
import com.lys.kit.pop.PopChoice;
import com.lys.kit.pop.PopColor;
import com.lys.protobuf.SBoardText;
import com.lys.protobuf.SChoiceItem;

import java.util.ArrayList;
import java.util.List;

public class DialogAddBoardText extends Dialog implements View.OnClickListener
{
	public interface OnAddTextListener
	{
		void onResult(SBoardText boardText);
	}

	private OnAddTextListener listener = null;

	private void setListener(OnAddTextListener listener)
	{
		this.listener = listener;
	}

	private class Holder
	{
//		private EditText account;
//		private EditText psw;
//		private EditText userName;
//
//		private RadioButton typeMaster;
//		private RadioButton typeTeacher;
//		private RadioButton typeStudent;
//
//		private RadioButton sexGirl;
//		private RadioButton sexBoy;

		private TextView boardText;

		private SeekBar size;
		private View color;

		private TextView gravityH;
		private TextView gravityV;

//		private TextView textStyle;

		private Switch bold;

		private View solidColor;
		private SeekBar strokeWidth;
		private View strokeColor;

		private TextView cornerType;
		private List<ViewGroup> cornerCons = new ArrayList<>();
		private List<TextView> cornerNames = new ArrayList<>();
		private List<SeekBar> corners = new ArrayList<>();

		private TextView paddingType;
		private List<ViewGroup> paddingCons = new ArrayList<>();
		private List<TextView> paddingNames = new ArrayList<>();
		private List<SeekBar> paddings = new ArrayList<>();

		private SeekBar letterSpacing;
		//		private SeekBar lineSpacingAdd;
		private SeekBar lineSpacingMult;
		private SeekBar textScaleX;

		private SeekBar lines;
//		private TextView ellipsize;

		private SeekBar shadowLayerRadius;
		private SeekBar shadowLayerDx;
		private SeekBar shadowLayerDy;
		private View shadowLayerColor;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
//		holder.account = findViewById(R.id.account);
//		holder.psw = findViewById(R.id.psw);
//		holder.userName = findViewById(R.id.userName);
//
//		holder.typeMaster = findViewById(R.id.typeMaster);
//		holder.typeTeacher = findViewById(R.id.typeTeacher);
//		holder.typeStudent = findViewById(R.id.typeStudent);
//
//		holder.sexGirl = findViewById(R.id.sexGirl);
//		holder.sexBoy = findViewById(R.id.sexBoy);

		holder.boardText = findViewById(R.id.boardText);

		holder.size = findViewById(R.id.size);
		holder.color = findViewById(R.id.color);

		holder.gravityH = findViewById(R.id.gravityH);
		holder.gravityV = findViewById(R.id.gravityV);

//		holder.textStyle = findViewById(R.id.textStyle);

		holder.bold = findViewById(R.id.bold);

		holder.solidColor = findViewById(R.id.solidColor);
		holder.strokeWidth = findViewById(R.id.strokeWidth);
		holder.strokeColor = findViewById(R.id.strokeColor);

		holder.cornerType = findViewById(R.id.cornerType);

		holder.cornerCons.add((ViewGroup) findViewById(R.id.cornerCon0));
		holder.cornerCons.add((ViewGroup) findViewById(R.id.cornerCon1));
		holder.cornerCons.add((ViewGroup) findViewById(R.id.cornerCon2));
		holder.cornerCons.add((ViewGroup) findViewById(R.id.cornerCon3));
		holder.cornerCons.add((ViewGroup) findViewById(R.id.cornerCon4));
		holder.cornerCons.add((ViewGroup) findViewById(R.id.cornerCon5));
		holder.cornerCons.add((ViewGroup) findViewById(R.id.cornerCon6));
		holder.cornerCons.add((ViewGroup) findViewById(R.id.cornerCon7));

		holder.cornerNames.add((TextView) findViewById(R.id.cornerName0));
		holder.cornerNames.add((TextView) findViewById(R.id.cornerName1));
		holder.cornerNames.add((TextView) findViewById(R.id.cornerName2));
		holder.cornerNames.add((TextView) findViewById(R.id.cornerName3));
		holder.cornerNames.add((TextView) findViewById(R.id.cornerName4));
		holder.cornerNames.add((TextView) findViewById(R.id.cornerName5));
		holder.cornerNames.add((TextView) findViewById(R.id.cornerName6));
		holder.cornerNames.add((TextView) findViewById(R.id.cornerName7));

		holder.corners.add((SeekBar) findViewById(R.id.corner0));
		holder.corners.add((SeekBar) findViewById(R.id.corner1));
		holder.corners.add((SeekBar) findViewById(R.id.corner2));
		holder.corners.add((SeekBar) findViewById(R.id.corner3));
		holder.corners.add((SeekBar) findViewById(R.id.corner4));
		holder.corners.add((SeekBar) findViewById(R.id.corner5));
		holder.corners.add((SeekBar) findViewById(R.id.corner6));
		holder.corners.add((SeekBar) findViewById(R.id.corner7));

		holder.paddingType = findViewById(R.id.paddingType);

		holder.paddingCons.add((ViewGroup) findViewById(R.id.paddingCon0));
		holder.paddingCons.add((ViewGroup) findViewById(R.id.paddingCon1));
		holder.paddingCons.add((ViewGroup) findViewById(R.id.paddingCon2));
		holder.paddingCons.add((ViewGroup) findViewById(R.id.paddingCon3));

		holder.paddingNames.add((TextView) findViewById(R.id.paddingName0));
		holder.paddingNames.add((TextView) findViewById(R.id.paddingName1));
		holder.paddingNames.add((TextView) findViewById(R.id.paddingName2));
		holder.paddingNames.add((TextView) findViewById(R.id.paddingName3));

		holder.paddings.add((SeekBar) findViewById(R.id.padding0));
		holder.paddings.add((SeekBar) findViewById(R.id.padding1));
		holder.paddings.add((SeekBar) findViewById(R.id.padding2));
		holder.paddings.add((SeekBar) findViewById(R.id.padding3));

		holder.letterSpacing = findViewById(R.id.letterSpacing);
//		holder.lineSpacingAdd = findViewById(R.id.lineSpacingAdd);
		holder.lineSpacingMult = findViewById(R.id.lineSpacingMult);
		holder.textScaleX = findViewById(R.id.textScaleX);

		holder.lines = findViewById(R.id.lines);
//		holder.ellipsize = findViewById(R.id.ellipsize);

		holder.shadowLayerRadius = findViewById(R.id.shadowLayerRadius);
		holder.shadowLayerDx = findViewById(R.id.shadowLayerDx);
		holder.shadowLayerDy = findViewById(R.id.shadowLayerDy);
		holder.shadowLayerColor = findViewById(R.id.shadowLayerColor);
	}

	private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener()
	{
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
		{
			if (fromUser)
			{
				LOG.v("progress : " + progress);
				for (SeekBar corner : holder.corners)
				{
					if (seekBar.getId() == corner.getId())
					{
						sync_corners();
					}
				}
				for (SeekBar padding : holder.paddings)
				{
					if (seekBar.getId() == padding.getId())
					{
						sync_paddings();
					}
				}
				apply();
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar)
		{
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar)
		{
		}
	};

	private CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener()
	{
		@Override
		public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
		{
			apply();
		}
	};

	private static final int size_min = 10;
	private static final int size_max = 250;

	private static final int strokeWidth_min = 0;
	private static final int strokeWidth_max = 30;

	private static final int corner_min = 0;
	private static final int corner_max = 250;

	private static final int padding_min = 0;
	private static final int padding_max = 250;

	private static final int letterSpacing_min = 0;
	private static final int letterSpacing_max = 200;

//	private static final int lineSpacingAdd_min = 0;
//	private static final int lineSpacingAdd_max = 50;

	private static final int lineSpacingMult_min = 70;
	private static final int lineSpacingMult_max = 200;

	private static final int textScaleX_min = 50;
	private static final int textScaleX_max = 200;

	private static final int lines_min = 0;
	private static final int lines_max = 20;

	private static final int shadowLayerRadius_min = 1;
	private static final int shadowLayerRadius_max = 10;

	private static final int shadowLayerDx_min = -20;
	private static final int shadowLayerDx_max = 20;

	private static final int shadowLayerDy_min = -20;
	private static final int shadowLayerDy_max = 20;

	private DialogAddBoardText(@NonNull Context context, SBoardText boardText)
	{
		super(context, R.style.FullDialog);
//		setCancelable(false);
		setContentView(R.layout.dialog_add_board_text);
		initHolder();

		holder.size.setMax(size_max - size_min);
		holder.size.setOnSeekBarChangeListener(mSeekBarChangeListener);

		holder.strokeWidth.setMax(strokeWidth_max - strokeWidth_min);
		holder.strokeWidth.setOnSeekBarChangeListener(mSeekBarChangeListener);

		for (SeekBar corner : holder.corners)
		{
			corner.setMax(corner_max - corner_min);
			corner.setOnSeekBarChangeListener(mSeekBarChangeListener);
		}

		for (SeekBar padding : holder.paddings)
		{
			padding.setMax(padding_max - padding_min);
			padding.setOnSeekBarChangeListener(mSeekBarChangeListener);
		}

		holder.letterSpacing.setMax(letterSpacing_max - letterSpacing_min);
		holder.letterSpacing.setOnSeekBarChangeListener(mSeekBarChangeListener);

//		holder.lineSpacingAdd.setMax(lineSpacingAdd_max - lineSpacingAdd_min);
//		holder.lineSpacingAdd.setOnSeekBarChangeListener(mSeekBarChangeListener);

		holder.lineSpacingMult.setMax(lineSpacingMult_max - lineSpacingMult_min);
		holder.lineSpacingMult.setOnSeekBarChangeListener(mSeekBarChangeListener);

		holder.textScaleX.setMax(textScaleX_max - textScaleX_min);
		holder.textScaleX.setOnSeekBarChangeListener(mSeekBarChangeListener);

		holder.lines.setMax(lines_max - lines_min);
		holder.lines.setOnSeekBarChangeListener(mSeekBarChangeListener);

		holder.shadowLayerRadius.setMax(shadowLayerRadius_max - shadowLayerRadius_min);
		holder.shadowLayerRadius.setOnSeekBarChangeListener(mSeekBarChangeListener);

		holder.shadowLayerDx.setMax(shadowLayerDx_max - shadowLayerDx_min);
		holder.shadowLayerDx.setOnSeekBarChangeListener(mSeekBarChangeListener);

		holder.shadowLayerDy.setMax(shadowLayerDy_max - shadowLayerDy_min);
		holder.shadowLayerDy.setOnSeekBarChangeListener(mSeekBarChangeListener);

		applyToUI(boardText);
		apply();

		findViewById(R.id.close).setOnClickListener(this);
//		findViewById(R.id.add).setOnClickListener(this);
		findViewById(R.id.ok).setOnClickListener(this);

		holder.color.setOnClickListener(this);
		holder.gravityH.setOnClickListener(this);
		holder.gravityV.setOnClickListener(this);
//		holder.textStyle.setOnClickListener(this);
		holder.solidColor.setOnClickListener(this);
		holder.strokeColor.setOnClickListener(this);
		holder.cornerType.setOnClickListener(this);
		holder.paddingType.setOnClickListener(this);
//		holder.ellipsize.setOnClickListener(this);
		holder.shadowLayerColor.setOnClickListener(this);

		holder.bold.setOnCheckedChangeListener(mCheckedChangeListener);
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
//		else if (view.getId() == R.id.add)
//		{
//			add();
//		}
		else if (view.getId() == R.id.ok)
		{
			ok();
		}
		else if (view.getId() == R.id.color)
		{
			click_color(view);
		}
		else if (view.getId() == R.id.gravityH)
		{
			click_gravityH(view);
		}
		else if (view.getId() == R.id.gravityV)
		{
			click_gravityV(view);
		}
//		else if (view.getId() == R.id.textStyle)
//		{
//			click_textStyle(view);
//		}
		else if (view.getId() == R.id.solidColor)
		{
			click_color(view);
		}
		else if (view.getId() == R.id.strokeColor)
		{
			click_color(view);
		}
		else if (view.getId() == R.id.cornerType)
		{
			click_cornerType(view);
		}
		else if (view.getId() == R.id.paddingType)
		{
			click_paddingType(view);
		}
//		else if (view.getId() == R.id.ellipsize)
//		{
//			click_ellipsize(view);
//		}
		else if (view.getId() == R.id.shadowLayerColor)
		{
			click_color(view);
		}
	}

	private void click_color(final View view)
	{
		PopColor.show2(getContext(), view, new PopColor.OnClickListener()
		{
			@Override
			public void onClick(int color)
			{
				view.setBackgroundColor(color);
				apply();
			}
		});
	}

	private void click_gravityH(View view)
	{
		List<SChoiceItem> choiceItems = new ArrayList<>();
		choiceItems.add(SChoiceItem.create("左", 0, Gravity.LEFT));
		choiceItems.add(SChoiceItem.create("水平居中", 0, Gravity.CENTER_HORIZONTAL));
		choiceItems.add(SChoiceItem.create("右", 0, Gravity.RIGHT));
		PopChoice.show(getContext(), view, choiceItems, new PopChoice.OnListener()
		{
			@Override
			public void onSelect(SChoiceItem choiceItem)
			{
				applyToUI_gravityH(choiceItem.value);
				apply();
			}
		});
	}

	private void applyToUI_gravityH(int value)
	{
		holder.gravityH.setTag(value);
		if (value == Gravity.LEFT)
			holder.gravityH.setText("左");
		else if (value == Gravity.CENTER_HORIZONTAL)
			holder.gravityH.setText("水平居中");
		else if (value == Gravity.RIGHT)
			holder.gravityH.setText("右");
	}

	private void click_gravityV(View view)
	{
		List<SChoiceItem> choiceItems = new ArrayList<>();
		choiceItems.add(SChoiceItem.create("上", 0, Gravity.TOP));
		choiceItems.add(SChoiceItem.create("垂直居中", 0, Gravity.CENTER_VERTICAL));
		choiceItems.add(SChoiceItem.create("下", 0, Gravity.BOTTOM));
		PopChoice.show(getContext(), view, choiceItems, new PopChoice.OnListener()
		{
			@Override
			public void onSelect(SChoiceItem choiceItem)
			{
				applyToUI_gravityV(choiceItem.value);
				apply();
			}
		});
	}

	private void applyToUI_gravityV(int value)
	{
		holder.gravityV.setTag(value);
		if (value == Gravity.TOP)
			holder.gravityV.setText("上");
		else if (value == Gravity.CENTER_VERTICAL)
			holder.gravityV.setText("垂直居中");
		else if (value == Gravity.BOTTOM)
			holder.gravityV.setText("下");
	}

//	private void click_textStyle(View view)
//	{
//		List<SChoiceItem> choiceItems = new ArrayList<>();
//		choiceItems.add(SChoiceItem.create("默认", 0, Typeface.NORMAL));
//		choiceItems.add(SChoiceItem.create("加粗", 0, Typeface.BOLD));
////		choiceItems.add(SChoiceItem.create("倾斜", 0, Typeface.ITALIC));
////		choiceItems.add(SChoiceItem.create("加粗并倾斜", 0, (Typeface.BOLD | Typeface.ITALIC)));
//		PopChoice.show(getContext(), view, choiceItems, new PopChoice.OnListener()
//		{
//			@Override
//			public void onSelect(SChoiceItem choiceItem)
//			{
//				applyToUI_textStyle(choiceItem.value);
//				apply();
//			}
//		});
//	}
//
//	private void applyToUI_textStyle(int value)
//	{
//		holder.textStyle.setTag(value);
//		if (value == Typeface.NORMAL)
//			holder.textStyle.setText("默认");
//		else if (value == Typeface.BOLD)
//			holder.textStyle.setText("加粗");
////		else if (value == Typeface.ITALIC)
////			holder.textStyle.setText("倾斜");
////		else if (value == (Typeface.BOLD | Typeface.ITALIC))
////			holder.textStyle.setText("加粗并倾斜");
//	}

	private void click_cornerType(View view)
	{
		List<SChoiceItem> choiceItems = new ArrayList<>();
		choiceItems.add(SChoiceItem.create("一值", 0, 1));
		choiceItems.add(SChoiceItem.create("四值", 0, 4));
		choiceItems.add(SChoiceItem.create("八值", 0, 8));
		PopChoice.show(getContext(), view, choiceItems, new PopChoice.OnListener()
		{
			@Override
			public void onSelect(SChoiceItem choiceItem)
			{
				applyToUI_cornerType(choiceItem.value);
				sync_corners();
				apply();
			}
		});
	}

	private void sync_corners()
	{
		int value = (int) holder.cornerType.getTag();
		if (value == 1)
		{
			for (int i = 1; i < holder.cornerCons.size(); i++)
			{
				holder.corners.get(i).setProgress(holder.corners.get(0).getProgress());
			}
		}
		else if (value == 4)
		{
			for (int i = 0; i < holder.cornerCons.size(); i++)
			{
				if (i % 2 == 1)
				{
					holder.corners.get(i).setProgress(holder.corners.get(i - 1).getProgress());
				}
			}
		}
	}

	private void applyToUI_cornerType(int value)
	{
		holder.cornerType.setTag(value);
		if (value == 1)
		{
			holder.cornerType.setText("一值");
			holder.cornerNames.get(0).setText("圆角度：");
			holder.cornerCons.get(0).setVisibility(View.VISIBLE);
			for (int i = 1; i < holder.cornerCons.size(); i++)
			{
				holder.cornerCons.get(i).setVisibility(View.GONE);
			}
		}
		else if (value == 4)
		{
			holder.cornerType.setText("四值");
			holder.cornerNames.get(0).setText("圆角度（左上）：");
			holder.cornerNames.get(2).setText("圆角度（右上）：");
			holder.cornerNames.get(4).setText("圆角度（右下）：");
			holder.cornerNames.get(6).setText("圆角度（左下）：");
			for (int i = 0; i < holder.cornerCons.size(); i++)
			{
				if (i % 2 == 0)
					holder.cornerCons.get(i).setVisibility(View.VISIBLE);
				else
					holder.cornerCons.get(i).setVisibility(View.GONE);
			}
		}
		else if (value == 8)
		{
			holder.cornerType.setText("八值");
			holder.cornerNames.get(0).setText("圆角度（左上X）：");
			holder.cornerNames.get(1).setText("圆角度（左上Y）：");
			holder.cornerNames.get(2).setText("圆角度（右上X）：");
			holder.cornerNames.get(3).setText("圆角度（右上Y）：");
			holder.cornerNames.get(4).setText("圆角度（右下X）：");
			holder.cornerNames.get(5).setText("圆角度（右下Y）：");
			holder.cornerNames.get(6).setText("圆角度（左下X）：");
			holder.cornerNames.get(7).setText("圆角度（左下Y）：");
			for (ViewGroup cornerCon : holder.cornerCons)
			{
				cornerCon.setVisibility(View.VISIBLE);
			}
		}
	}

	private void click_paddingType(View view)
	{
		List<SChoiceItem> choiceItems = new ArrayList<>();
		choiceItems.add(SChoiceItem.create("一值", 0, 1));
		choiceItems.add(SChoiceItem.create("四值", 0, 4));
		PopChoice.show(getContext(), view, choiceItems, new PopChoice.OnListener()
		{
			@Override
			public void onSelect(SChoiceItem choiceItem)
			{
				applyToUI_paddingType(choiceItem.value);
				sync_paddings();
				apply();
			}
		});
	}

	private void sync_paddings()
	{
		int value = (int) holder.paddingType.getTag();
		if (value == 1)
		{
			for (int i = 1; i < holder.paddingCons.size(); i++)
			{
				holder.paddings.get(i).setProgress(holder.paddings.get(0).getProgress());
			}
		}
	}

	private void applyToUI_paddingType(int value)
	{
		holder.paddingType.setTag(value);
		if (value == 1)
		{
			holder.paddingType.setText("一值");
			holder.paddingNames.get(0).setText("边距：");
			holder.paddingCons.get(0).setVisibility(View.VISIBLE);
			for (int i = 1; i < holder.paddingCons.size(); i++)
			{
				holder.paddingCons.get(i).setVisibility(View.GONE);
			}
		}
		else if (value == 4)
		{
			holder.paddingType.setText("四值");
			holder.paddingNames.get(0).setText("边距（左）：");
			holder.paddingNames.get(1).setText("边距（上）：");
			holder.paddingNames.get(2).setText("边距（右）：");
			holder.paddingNames.get(3).setText("边距（下）：");
			for (ViewGroup paddingCon : holder.paddingCons)
			{
				paddingCon.setVisibility(View.VISIBLE);
			}
		}
	}

//	private void click_ellipsize(View view)
//	{
//		List<SChoiceItem> choiceItems = new ArrayList<>();
//		choiceItems.add(SChoiceItem.create("开始省略", 0, TextUtils.TruncateAt.START.ordinal()));
//		choiceItems.add(SChoiceItem.create("中间省略", 0, TextUtils.TruncateAt.MIDDLE.ordinal()));
//		choiceItems.add(SChoiceItem.create("结尾省略", 0, TextUtils.TruncateAt.END.ordinal()));
//		choiceItems.add(SChoiceItem.create("跑马灯", 0, TextUtils.TruncateAt.MARQUEE.ordinal()));
//		PopChoice.show(getContext(), view, choiceItems, new PopChoice.OnListener()
//		{
//			@Override
//			public void onSelect(SChoiceItem choiceItem)
//			{
//				applyToUI_ellipsize(choiceItem.value);
//				apply();
//			}
//		});
//	}
//
//	private void applyToUI_ellipsize(int value)
//	{
//		holder.ellipsize.setTag(value);
//		if (value == TextUtils.TruncateAt.START.ordinal())
//			holder.ellipsize.setText("开始省略");
//		else if (value == TextUtils.TruncateAt.MIDDLE.ordinal())
//			holder.ellipsize.setText("中间省略");
//		else if (value == TextUtils.TruncateAt.END.ordinal())
//			holder.ellipsize.setText("结尾省略");
//		else if (value == TextUtils.TruncateAt.MARQUEE.ordinal())
//			holder.ellipsize.setText("跑马灯");
//	}

	private void applyToUI(SBoardText data)
	{
		if (data == null)
			data = new SBoardText();
		holder.boardText.setText(data.text);
		holder.size.setProgress(data.size - size_min);
		holder.color.setBackgroundColor(data.color);

		applyToUI_gravityH(data.gravity & 0x0f);
		applyToUI_gravityV(data.gravity & 0xf0);

//		applyToUI_textStyle(data.textStyle);
		holder.bold.setChecked(data.textStyle == Typeface.BOLD);

		holder.solidColor.setBackgroundColor(data.solidColor);
		holder.strokeWidth.setProgress(data.strokeWidth - strokeWidth_min);
		holder.strokeColor.setBackgroundColor(data.strokeColor);

		if (data.corners.size() != 8)
		{
			data.corners.clear();
			for (int i = 0; i < 8; i++)
				data.corners.add((float) corner_min);
		}
		if (data.corners.get(0).equals(data.corners.get(1)) && //
				data.corners.get(0).equals(data.corners.get(2)) && //
				data.corners.get(0).equals(data.corners.get(3)) && //
				data.corners.get(0).equals(data.corners.get(4)) && //
				data.corners.get(0).equals(data.corners.get(5)) && //
				data.corners.get(0).equals(data.corners.get(6)) && //
				data.corners.get(0).equals(data.corners.get(7)))
			applyToUI_cornerType(1);
		else if (data.corners.get(0).equals(data.corners.get(1)) && //
				data.corners.get(2).equals(data.corners.get(3)) && //
				data.corners.get(4).equals(data.corners.get(5)) && //
				data.corners.get(6).equals(data.corners.get(7)))
			applyToUI_cornerType(4);
		else
			applyToUI_cornerType(8);
		for (int i = 0; i < data.corners.size(); i++)
		{
			holder.corners.get(i).setProgress((int) (data.corners.get(i) - corner_min));
		}

		if (data.paddings.size() != 4)
		{
			data.paddings.clear();
			for (int i = 0; i < 4; i++)
				data.paddings.add(padding_min);
		}
		if (data.paddings.get(0).equals(data.paddings.get(1)) && //
				data.paddings.get(0).equals(data.paddings.get(2)) && //
				data.paddings.get(0).equals(data.paddings.get(3)))
			applyToUI_paddingType(1);
		else
			applyToUI_paddingType(4);
		for (int i = 0; i < data.paddings.size(); i++)
		{
			holder.paddings.get(i).setProgress(data.paddings.get(i) - padding_min);
		}

		holder.letterSpacing.setProgress((int) (data.letterSpacing * 100 - letterSpacing_min));
//		holder.lineSpacingAdd.setProgress((int) (data.lineSpacingAdd - lineSpacingAdd_min));
		holder.lineSpacingMult.setProgress((int) (data.lineSpacingMult * 100 - lineSpacingMult_min));
		holder.textScaleX.setProgress((int) (data.textScaleX * 100 - textScaleX_min));

		holder.lines.setProgress(data.lines - lines_min);
//		applyToUI_ellipsize(data.ellipsize);

		holder.shadowLayerRadius.setProgress((int) (data.shadowLayerRadius - shadowLayerRadius_min));
		holder.shadowLayerDx.setProgress((int) (data.shadowLayerDx - shadowLayerDx_min));
		holder.shadowLayerDy.setProgress((int) (data.shadowLayerDy - shadowLayerDy_min));
		holder.shadowLayerColor.setBackgroundColor(data.shadowLayerColor);
	}

	private SBoardText genData()
	{
		SBoardText data = new SBoardText();
		data.text = holder.boardText.getText().toString();
		data.size = holder.size.getProgress() + size_min;
		data.color = ((ColorDrawable) holder.color.getBackground()).getColor();

		data.gravity = (int) holder.gravityH.getTag() | (int) holder.gravityV.getTag();

//		data.textStyle = (int) holder.textStyle.getTag();
		data.textStyle = holder.bold.isChecked() ? Typeface.BOLD : Typeface.NORMAL;

		data.solidColor = ((ColorDrawable) holder.solidColor.getBackground()).getColor();
		data.strokeWidth = holder.strokeWidth.getProgress() + strokeWidth_min;
		data.strokeColor = ((ColorDrawable) holder.strokeColor.getBackground()).getColor();

		int cornerType = (int) holder.cornerType.getTag();
		if (cornerType == 1)
		{
			data.corners.add((float) (holder.corners.get(0).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(0).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(0).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(0).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(0).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(0).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(0).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(0).getProgress() + corner_min));
		}
		else if (cornerType == 4)
		{
			data.corners.add((float) (holder.corners.get(0).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(0).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(2).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(2).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(4).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(4).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(6).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(6).getProgress() + corner_min));
		}
		else if (cornerType == 8)
		{
			data.corners.add((float) (holder.corners.get(0).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(1).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(2).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(3).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(4).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(5).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(6).getProgress() + corner_min));
			data.corners.add((float) (holder.corners.get(7).getProgress() + corner_min));
		}

		int paddingType = (int) holder.paddingType.getTag();
		if (paddingType == 1)
		{
			data.paddings.add(holder.paddings.get(0).getProgress() + padding_min);
			data.paddings.add(holder.paddings.get(0).getProgress() + padding_min);
			data.paddings.add(holder.paddings.get(0).getProgress() + padding_min);
			data.paddings.add(holder.paddings.get(0).getProgress() + padding_min);
		}
		else if (paddingType == 4)
		{
			data.paddings.add(holder.paddings.get(0).getProgress() + padding_min);
			data.paddings.add(holder.paddings.get(1).getProgress() + padding_min);
			data.paddings.add(holder.paddings.get(2).getProgress() + padding_min);
			data.paddings.add(holder.paddings.get(3).getProgress() + padding_min);
		}

		data.letterSpacing = Float.valueOf(holder.letterSpacing.getProgress() + letterSpacing_min) * 0.01f;
//		data.lineSpacingAdd = Float.valueOf(holder.lineSpacingAdd.getProgress() + lineSpacingAdd_min);
		data.lineSpacingMult = Float.valueOf(holder.lineSpacingMult.getProgress() + lineSpacingMult_min) * 0.01f;
		data.textScaleX = Float.valueOf(holder.textScaleX.getProgress() + textScaleX_min) * 0.01f;

		data.lines = holder.lines.getProgress() + lines_min;
//		data.ellipsize = (int) holder.ellipsize.getTag();

		data.shadowLayerRadius = Float.valueOf(holder.shadowLayerRadius.getProgress() + shadowLayerRadius_min);
		data.shadowLayerDx = Float.valueOf(holder.shadowLayerDx.getProgress() + shadowLayerDx_min);
		data.shadowLayerDy = Float.valueOf(holder.shadowLayerDy.getProgress() + shadowLayerDy_min);
		data.shadowLayerColor = ((ColorDrawable) holder.shadowLayerColor.getBackground()).getColor();

		return data;
	}

	private void apply()
	{
		SBoardText data = genData();
		applyBoardText(holder.boardText, data);
	}

	private void ok()
	{
		SBoardText boardText = genData();
		dismiss();
		if (listener != null)
			listener.onResult(boardText);
	}

	public static void applyBoardText(TextView view, SBoardText data)
	{
		view.setText(data.text);
		view.setTextColor(data.color);
		view.setTextSize(TypedValue.COMPLEX_UNIT_PX, data.size);

		view.setGravity(data.gravity); // Gravity.CENTER
		view.setTypeface(Config.CurrTypeface, data.textStyle); // Typeface.NORMAL

		GradientDrawable drawable = new GradientDrawable();
		drawable.setColor(data.solidColor);
		drawable.setStroke(data.strokeWidth, data.strokeColor);
		// 左上，右上，右下，左下
		if (data.corners.size() == 8)
		{
			float[] array = new float[data.corners.size()];
			for (int i = 0; i < data.corners.size(); i++)
			{
				array[i] = data.corners.get(i);
			}
			drawable.setCornerRadii(array);
		}
		view.setBackground(drawable);

		if (data.paddings.size() == 4)
			view.setPadding(data.paddings.get(0), data.paddings.get(1), data.paddings.get(2), data.paddings.get(3));
		else
			view.setPadding(0, 0, 0, 0);

		view.setLetterSpacing(data.letterSpacing);
		view.setLineSpacing(data.lineSpacingAdd, data.lineSpacingMult);
		view.setTextScaleX(data.textScaleX);

		view.setSingleLine(data.lines == 1); // 解决当view为EditText时，一行情况下，显示错位的问题（原因未知）
		if (data.lines > 0)
			view.setLines(data.lines);
		else
			view.setLines(Integer.MAX_VALUE);
		view.setEllipsize(TextUtils.TruncateAt.values()[data.ellipsize]);

		view.setShadowLayer(data.shadowLayerRadius, data.shadowLayerDx, data.shadowLayerDy, data.shadowLayerColor);
	}

	public static void show(Context context, SBoardText boardText, OnAddTextListener listener)
	{
		DialogAddBoardText dialog = new DialogAddBoardText(context, boardText);
		dialog.setListener(listener);
		dialog.show();
	}

}
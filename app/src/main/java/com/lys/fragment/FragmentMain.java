package com.lys.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lys.App;
import com.lys.activity.ActivityFriendManager;
import com.lys.activity.ActivityIM;
import com.lys.activity.ActivityLiveList;
import com.lys.activity.ActivityMain;
import com.lys.activity.ActivityMainNote;
import com.lys.activity.ActivityMainTopic;
import com.lys.activity.ActivityTaskLib;
import com.lys.activity.ActivityTaskList;
import com.lys.activity.ActivityUser;
import com.lys.activity.ActivityUserManager;
import com.lys.app.R;
import com.lys.base.fragment.BaseFragment;
import com.lys.base.utils.HttpUtils;
import com.lys.base.utils.LOG;
import com.lys.base.utils.LOGJson;
import com.lys.protobuf.SDynamicConfig;
import com.lys.protobuf.SSex;
import com.lys.utils.LysIM;
import com.lys.view.SnowEffect;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FragmentMain extends BaseFragment implements View.OnClickListener, LysIM.OnUnReadMessageObserver
{
	private class Holder
	{
		private TextView time;
		private TextView date;
		private TextView week;

		private ViewGroup friendManagerCon;
		private ViewGroup userManagerCon;

		private TextView msgCount;
		private ImageView show;
		private TextView name;

		private ViewGroup effectContainer;
	}

	private Holder holder = new Holder();

	private void initHolder(View view)
	{
		holder.time = view.findViewById(R.id.time);
		holder.date = view.findViewById(R.id.date);
		holder.week = view.findViewById(R.id.week);

		holder.friendManagerCon = view.findViewById(R.id.friendManagerCon);
		holder.userManagerCon = view.findViewById(R.id.userManagerCon);

		holder.msgCount = view.findViewById(R.id.msgCount);
		holder.show = view.findViewById(R.id.show);
		holder.name = view.findViewById(R.id.name);

		holder.effectContainer = view.findViewById(R.id.effectContainer);
	}

	private ActivityMain getMainActivity()
	{
		return (ActivityMain) getActivity();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActivity().getWindow().setBackgroundDrawableResource(R.drawable.img_home_bg);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		initHolder(view);

		if (App.isSupterMaster() || App.isMaster() || App.isTeacher())
			holder.friendManagerCon.setVisibility(View.VISIBLE);
		else
			holder.friendManagerCon.setVisibility(View.INVISIBLE);

		if (App.isSupterMaster())
			holder.userManagerCon.setVisibility(View.VISIBLE);
		else
			holder.userManagerCon.setVisibility(View.INVISIBLE);

		if (App.sex().equals(SSex.Girl))
			holder.show.setImageResource(R.drawable.img_home_girl);
		else
			holder.show.setImageResource(R.drawable.img_home_boy);

		holder.name.setText(App.name());

		view.findViewById(R.id.task).setOnClickListener(this);
		view.findViewById(R.id.note).setOnClickListener(this);
		view.findViewById(R.id.topic).setOnClickListener(this);
		view.findViewById(R.id.im).setOnClickListener(this);
		view.findViewById(R.id.user).setOnClickListener(this);
		view.findViewById(R.id.phone).setOnClickListener(this);
		view.findViewById(R.id.friendManager).setOnClickListener(this);
		view.findViewById(R.id.userManager).setOnClickListener(this);

		LysIM.instance().addUnReadMessageCountChangedObserver(this);

		waitHandler.post(waitRunnable);

//		SBoardText boardText = new SBoardText();
//		boardText.text = "公司申请终";
////		boardText.text = "除赔偿上述费用，《裁决书》还确认王老吉公司申请终止增资协议无效，须根据增资协议将加多宝商标注入清远加多宝，并完成相关的商标注入手续。清远加多宝也须配合相关商标注入手续。清远加多宝原控股公司智首公司及清远加多宝须和王老吉公司承担连带责任。以上仲裁裁决为终局，自作出之日起生效。\n" + "对此，加多宝相关负责人表示，目前双方已经和解，2.3亿元是按照协议规定的费用，并非是所谓的赔偿，并表示，“这是非常好的局面，加多宝会根据中粮包装发布的公告内容和《裁决书》执行。”\n" + "加多宝在随后的声明中还指出，清远加多宝是加多宝绝对控股子公司，中粮包装加盟清远加多宝将对加多宝的运营产生积极影响。目前，王老吉公司还未向清远加多宝注入加多宝商标。企查查数据显示，清远加多宝并没有任何知识产权相关的权益。\n" + "长江商报记者了解到，2017年10月，中粮包装正式入股加多宝，选择的是生产浓缩液的清远加多宝草本植物科技有限公司。中粮包装选择现金入股，20亿元换得30.58%的股份；王老吉公司（加多宝商标持有方）以商标注入的方式作为增资，作价30亿元，从而获得清远加多宝草本45.87%的股份，而原100%控股股东智首有限公司的持股比例降至23.55%。\n" + "然而，在双方达成交易后，由于有关加多宝品牌资产注入一事迟迟未能落地，直到中粮包装将加多宝告上法庭。2018年7月开始，持续一年多时间的中粮包装和加多宝之间的官司开始展开。中粮包装就合作提出异议，双方合作关系出现裂痕。";
//		DialogAddBoardText.show(context, boardText, null);

		return view;
	}

	@Override
	public void onResume()
	{
		super.onResume();
		getMainActivity().checkUpdate();
		requestDynamicConfig();
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		LysIM.instance().removeUnReadMessageCountChangedObserver(this);
		waitHandler.removeCallbacks(waitRunnable);
	}

	private void requestDynamicConfig()
	{
		HttpUtils.doHttpGet(context, "http://zjyk-file.oss-cn-huhehaote.aliyuncs.com/config/dynamic_config.ini", new HttpUtils.OnCallback()
		{
			@Override
			public void onResponse(String config)
			{
				if (!TextUtils.isEmpty(config))
				{
					LOGJson.log(config);
					SDynamicConfig dynamicConfig = SDynamicConfig.load(config);
					processDynamicConfig(dynamicConfig);
				}
			}
		});
	}

	private SnowEffect snowEffect = null;

	private void processDynamicConfig(final SDynamicConfig dynamicConfig)
	{
		if (dynamicConfig.showSnow)
		{
			if (snowEffect == null)
			{
				LOG.v("will show snow");
				snowEffect = new SnowEffect(getContext());
				holder.effectContainer.addView(snowEffect);
				snowEffect.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
				{
					@Override
					public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
					{
						view.removeOnLayoutChangeListener(this);
						snowEffect.start(R.drawable.img_snow_14, dynamicConfig.snowCount);
					}
				});
			}
		}
		else
		{
			if (snowEffect != null)
			{
				LOG.v("will remove snow");
				holder.effectContainer.removeView(snowEffect);
				snowEffect = null;
			}
		}
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.task)
		{
			if (App.isStudent())
			{
				Intent intent = new Intent(context, ActivityTaskList.class);
				intent.putExtra("userId", App.userId());
				startActivity(intent);
			}
			else
			{
				Intent intent = new Intent(context, ActivityTaskLib.class);
				intent.putExtra("userId", App.userId());
				startActivity(intent);
			}
		}
		else if (view.getId() == R.id.note)
		{
//			SysUtils.launchApp(context, Config.PackageNameNote);
			Intent intent = new Intent(context, ActivityMainNote.class);
			startActivity(intent);
		}
		else if (view.getId() == R.id.topic)
		{
			Intent intent = new Intent(context, ActivityMainTopic.class);
			startActivity(intent);
		}
		else if (view.getId() == R.id.im)
		{
			Intent intent = new Intent(context, ActivityIM.class);
			startActivity(intent);
		}
		else if (view.getId() == R.id.user)
		{
			Intent intent = new Intent(context, ActivityUser.class);
			getMainActivity().startActivityForResult(intent, ActivityMain.REQUEST_CODE_GOIN_USER);
		}
		else if (view.getId() == R.id.phone)
		{
//			if (KitUtils.isC5())
//			{
//				Intent intent = new Intent(Intent.ACTION_DIAL);
//				intent.setData(Uri.parse("tel:" + ""));
//				startActivity(intent);
//			}
			Intent intent = new Intent(context, ActivityLiveList.class);
			intent.putExtra("userId", App.userId());
			startActivity(intent);
		}
		else if (view.getId() == R.id.friendManager)
		{
			Intent intent = new Intent(context, ActivityFriendManager.class);
			intent.putExtra("userId", App.userId());
			startActivity(intent);
		}
		else if (view.getId() == R.id.userManager)
		{
			Intent intent = new Intent(context, ActivityUserManager.class);
			startActivity(intent);
//			Intent intent = new Intent(context, ActivityShopHome.class);
//			startActivity(intent);
		}
	}

	@Override
	public void onCountChanged(int count)
	{
		LOG.v("count : " + count);
		if (count > 0)
		{
			holder.msgCount.setVisibility(View.VISIBLE);
			holder.msgCount.setText(String.valueOf(count));
		}
		else
		{
			holder.msgCount.setVisibility(View.GONE);
		}
	}

	//------------------ 时钟（开始） -------------------

	private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy - MM - dd");
	private SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");

	public static String getWeekDes(int week)
	{
		if (week == 1)
			return "星期日";
		else if (week == 2)
			return "星期一";
		else if (week == 3)
			return "星期二";
		else if (week == 4)
			return "星期三";
		else if (week == 5)
			return "星期四";
		else if (week == 6)
			return "星期五";
		else if (week == 7)
			return "星期六";
		return "未知";
	}

	private Handler waitHandler = new Handler();
	private Runnable waitRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			Date date = new Date(System.currentTimeMillis() - App.TimeOffset);

			holder.date.setText(formatDate.format(date));
			holder.time.setText(formatTime.format(date));

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			holder.week.setText(getWeekDes(calendar.get(Calendar.DAY_OF_WEEK)));

			waitHandler.postDelayed(waitRunnable, 1000);
		}
	};

	//------------------ 时钟（结束） -------------------

}

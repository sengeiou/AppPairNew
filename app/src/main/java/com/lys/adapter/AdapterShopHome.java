package com.lys.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lys.App;
import com.lys.activity.ActivityShopClass;
import com.lys.activity.ActivityShopDetail;
import com.lys.activity.ActivityShopHome;
import com.lys.activity.ActivityShopPair;
import com.lys.app.R;
import com.lys.base.utils.LOG;
import com.lys.dialog.DialogEditMatter;
import com.lys.fragment.FragmentShopLoop;
import com.lys.kit.adapter.SimpleFragmentPagerAdapter;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.dialog.DialogMenu;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SHandleId;
import com.lys.protobuf.SMatter;
import com.lys.protobuf.SMatterPlace;
import com.lys.protobuf.SMatterType;
import com.lys.protobuf.SRequest_AddModifyMatter;
import com.lys.protobuf.SRequest_DeleteMatter;

import java.util.ArrayList;
import java.util.List;

public class AdapterShopHome extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	public static final int Type_MatterBanner = 1;
	public static final int Type_MatterMoreClass = 2;
	public static final int Type_MatterGroupClass = 3;
	public static final int Type_MatterMorePair = 4;
	public static final int Type_MatterGroupPair = 5;

	public static final int MatterCoverWidth = 384;
	public static final int BannerLoopDelay = 5000;

	private ActivityShopHome owner = null;

	private List<SMatter> bannerMatters = new ArrayList<>();
	private List<List<SMatter>> classMatterGroups = new ArrayList<>();
	private List<List<SMatter>> pairMatterGroups = new ArrayList<>();

	public AdapterShopHome(ActivityShopHome owner)
	{
		this.owner = owner;
	}

	private void addDataImpl(List<List<SMatter>> matterGroups, SMatter matter)
	{
		if (matterGroups.size() == 0)
		{
			matterGroups.add(new ArrayList<SMatter>());
		}
		List<SMatter> lastGroup = matterGroups.get(matterGroups.size() - 1);
		if (lastGroup.size() < 4)
		{
			lastGroup.add(matter);
		}
		else
		{
			matterGroups.add(new ArrayList<SMatter>());
			lastGroup = matterGroups.get(matterGroups.size() - 1);
			lastGroup.add(matter);
		}
	}

	public void setData(List<SMatter> matters)
	{
		bannerMatters.clear();
		classMatterGroups.clear();
		pairMatterGroups.clear();
		for (SMatter matter : matters)
		{
			if (matter.place.equals(SMatterPlace.Banner))
			{
				bannerMatters.add(matter);
			}
			else if (matter.place.equals(SMatterPlace.Main))
			{
				if (matter.type.equals(SMatterType.Class))
				{
					addDataImpl(classMatterGroups, matter);
				}
				else if (matter.type.equals(SMatterType.Pair))
				{
					addDataImpl(pairMatterGroups, matter);
				}
			}
		}
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		switch (viewType)
		{
		case Type_MatterBanner:
			return new HolderMatterBanner(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matter_banner, parent, false));
		case Type_MatterMoreClass:
			return new HolderMatterMoreClass(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matter_more_class, parent, false));
		case Type_MatterGroupClass:
			return new HolderMatterGroupClass(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matter_group_class, parent, false));
		case Type_MatterMorePair:
			return new HolderMatterMorePair(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matter_more_pair, parent, false));
		case Type_MatterGroupPair:
			return new HolderMatterGroupPair(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matter_group_pair, parent, false));
		}
		return null;
	}

//	private static final SimpleDateFormat formatDate1 = new SimpleDateFormat("yyyy - MM - dd");
//	private static final SimpleDateFormat formatDate2 = new SimpleDateFormat("MM-dd HH:mm");

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position)
	{
		final Context context = viewHolder.itemView.getContext();
		if (getItemViewType(position) == Type_MatterBanner)
		{
			HolderMatterBanner holder = (HolderMatterBanner) viewHolder;
			holder.bindView(context, bannerMatters);
		}
		else if (getItemViewType(position) == Type_MatterMoreClass)
		{
			HolderMatterMoreClass holder = (HolderMatterMoreClass) viewHolder;
			holder.more.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					Intent intent = new Intent(context, ActivityShopClass.class);
					context.startActivity(intent);
				}
			});
		}
		else if (getItemViewType(position) == Type_MatterGroupClass)
		{
			HolderMatterGroupClass holderGroup = (HolderMatterGroupClass) viewHolder;
			List<SMatter> matterGroup = classMatterGroups.get(position - (1 + 1));
			holderGroup.bindView(context, matterGroup, true, new OnMatterOperateListener()
			{
				@Override
				public void onModify()
				{
					owner.request();
				}

				@Override
				public void onMoveUp(SMatter matter)
				{
				}

				@Override
				public void onMoveDown(SMatter matter)
				{
				}

				@Override
				public void onDelete()
				{
					owner.request();
				}
			});
		}
		else if (getItemViewType(position) == Type_MatterMorePair)
		{
			HolderMatterMorePair holder = (HolderMatterMorePair) viewHolder;
			holder.more.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					Intent intent = new Intent(context, ActivityShopPair.class);
					context.startActivity(intent);
				}
			});
		}
		else if (getItemViewType(position) == Type_MatterGroupPair)
		{
			HolderMatterGroupPair holderGroup = (HolderMatterGroupPair) viewHolder;
			List<SMatter> matterGroup = pairMatterGroups.get(position - (1 + 1 + classMatterGroups.size() + 1));
			holderGroup.bindView(context, matterGroup, true, new OnMatterOperateListener()
			{
				@Override
				public void onModify()
				{
					owner.request();
				}

				@Override
				public void onMoveUp(SMatter matter)
				{
				}

				@Override
				public void onMoveDown(SMatter matter)
				{
				}

				@Override
				public void onDelete()
				{
					owner.request();
				}
			});
		}
	}

	@Override
	public int getItemCount()
	{
		return 1 + 1 + classMatterGroups.size() + 1 + pairMatterGroups.size();
	}

	@Override
	public int getItemViewType(int position)
	{
		if (position == 0)
			return Type_MatterBanner;
		else if (position == 1)
			return Type_MatterMoreClass;
		else if (position < 1 + 1 + classMatterGroups.size())
			return Type_MatterGroupClass;
		else if (position == 1 + 1 + classMatterGroups.size())
			return Type_MatterMorePair;
		else
			return Type_MatterGroupPair;
	}

	protected class HolderMatterBanner extends RecyclerView.ViewHolder
	{
		public ViewPager viewPager;
		public ViewGroup indicatorContiner;

		private SimpleFragmentPagerAdapter adapterLoop;

		public HolderMatterBanner(View itemView)
		{
			super(itemView);
			viewPager = itemView.findViewById(R.id.viewPager);
			indicatorContiner = itemView.findViewById(R.id.indicatorContiner);

			adapterLoop = new SimpleFragmentPagerAdapter(owner.getSupportFragmentManager(), null);
			viewPager.setAdapter(adapterLoop);

			viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
			{
				@Override
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
				{
				}

				@Override
				public void onPageSelected(int position)
				{
					setSelectedFlag(position);
				}

				@Override
				public void onPageScrollStateChanged(int state)
				{
				}
			});

			viewPager.postDelayed(loopRunnable, BannerLoopDelay);
		}

		private void bindView(Context context, List<SMatter> bannerMatters)
		{
			indicatorContiner.removeAllViews();

			for (SMatter matter : bannerMatters)
			{
				View view = new View(context);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(14, 14);
				layoutParams.leftMargin = 20;
				layoutParams.rightMargin = 20;
				view.setLayoutParams(layoutParams);
				view.setBackgroundResource(R.drawable.loop_dot);
				indicatorContiner.addView(view);
			}

			List<Fragment> fragments = new ArrayList<>();
			for (SMatter matter : bannerMatters)
			{
				fragments.add(genFragment(matter));
			}

			adapterLoop.setData(fragments);
			viewPager.setAdapter(adapterLoop);

			viewPager.setCurrentItem(0);
			setSelectedFlag(0);
		}

		private Runnable loopRunnable = new Runnable()
		{
			@Override
			public void run()
			{
				if (!owner.isDestroyed())
				{
//					LOG.v("loop tick");
					try
					{
						if (adapterLoop.getCount() > 0)
						{
							int index = viewPager.getCurrentItem() + 1;
							index = index % adapterLoop.getCount();
							viewPager.setCurrentItem(index);
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					viewPager.postDelayed(this, BannerLoopDelay);
				}
				else
				{
					LOG.v("loop over");
				}
			}
		};

		private Fragment genFragment(SMatter matter)
		{
			FragmentShopLoop fragment = new FragmentShopLoop();
			Bundle bundle = new Bundle();
			bundle.putString("matter", matter.saveToStr());
			fragment.setArguments(bundle);
			return fragment;
		}

		private void setSelectedFlag(int index)
		{
			for (int i = 0; i < indicatorContiner.getChildCount(); i++)
			{
				View view = indicatorContiner.getChildAt(i);
				if (i == index)
				{
					ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
					layoutParams.width = 38;
					view.setLayoutParams(layoutParams);
				}
				else
				{
					ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
					layoutParams.width = 14;
					view.setLayoutParams(layoutParams);
				}
			}
		}
	}

	protected class HolderMatterMoreClass extends RecyclerView.ViewHolder
	{
		public TextView more;

		public HolderMatterMoreClass(View itemView)
		{
			super(itemView);
			more = itemView.findViewById(R.id.more);
		}
	}

	public static class HolderMatterClass
	{
		public View itemView;

		public ImageView cover;
		public TextView info;
		public TextView name;
		public TextView moneyRaw;
		public TextView money;
		public TextView buyCount;
		public ImageView place;
		public TextView invalid;

		public HolderMatterClass(View itemView)
		{
			this.itemView = itemView;

			cover = itemView.findViewById(R.id.cover);
			info = itemView.findViewById(R.id.info);
			name = itemView.findViewById(R.id.name);
			moneyRaw = itemView.findViewById(R.id.moneyRaw);
			money = itemView.findViewById(R.id.money);
			buyCount = itemView.findViewById(R.id.buyCount);
			place = itemView.findViewById(R.id.place);
			invalid = itemView.findViewById(R.id.invalid);
		}

		public void bindView(final Context context, final SMatter matter, final boolean isHome, final OnMatterOperateListener operateListener)
		{
			ImageLoad.displayImage(context, matter.cover, MatterCoverWidth, cover, R.drawable.img_default, null);

			if (App.isSupterMaster() && !isHome)
			{
				info.setVisibility(View.VISIBLE);
				info.setText(String.format("%s  %s  [%s]  [%s]", matter.userId, matter.sort, matter.hours.size(), matter.details.size()));
			}
			else
			{
				info.setVisibility(View.GONE);
			}

			name.setText(matter.name);

			moneyRaw.setText(String.format("%s", matter.moneyRaw));
			money.setText(String.format("%s", matter.money));

			buyCount.setText(String.format("%s 人购买", matter.buyCount));

			if (App.isSupterMaster() && !isHome)
			{
				if (matter.place.equals(SMatterPlace.Banner))
				{
					place.setImageResource(R.drawable.img_matter_place_banner);
				}
				else if (matter.place.equals(SMatterPlace.Main))
				{
					place.setImageResource(R.drawable.img_matter_place_main);
				}
				else
				{
					place.setImageDrawable(null);
				}
			}
			else
			{
				place.setImageDrawable(null);
			}

			invalid.setVisibility(matter.invalid ? View.VISIBLE : View.GONE);

			cover.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					Intent intent = new Intent(context, ActivityShopDetail.class);
					intent.putExtra("matter", matter.saveToStr());
					context.startActivity(intent);
				}
			});

			if (App.isSupterMaster())
			{
				cover.setOnLongClickListener(new View.OnLongClickListener()
				{
					@Override
					public boolean onLongClick(View view)
					{
						DialogMenu.Builder builder = new DialogMenu.Builder(context);
						builder.setMenu("编辑", new DialogMenu.OnClickMenuListener()
						{
							@Override
							public void onClick()
							{
								DialogEditMatter.show(context, matter, new DialogEditMatter.OnResultListener()
								{
									@Override
									public void onResult(SMatter matter)
									{
										SRequest_AddModifyMatter request = new SRequest_AddModifyMatter();
										request.matter = matter;
										Protocol.doPost(context, App.getApi(), SHandleId.AddModifyMatter, request.saveToStr(), new Protocol.OnCallback()
										{
											@Override
											public void onResponse(int code, String data, String msg)
											{
												if (code == 200)
												{
													operateListener.onModify();
												}
											}
										});
									}
								});
							}
						});
						if (!isHome)
						{
							builder.setMenu("上移", new DialogMenu.OnClickMenuListener()
							{
								@Override
								public void onClick()
								{
									operateListener.onMoveUp(matter);
								}
							});
							builder.setMenu("下移", new DialogMenu.OnClickMenuListener()
							{
								@Override
								public void onClick()
								{
									operateListener.onMoveDown(matter);
								}
							});
						}
						builder.setMenu("删除", new DialogMenu.OnClickMenuListener()
						{
							@Override
							public void onClick()
							{
								DialogAlert.show(context, "确定要删除吗？", null, new DialogAlert.OnClickListener()
								{
									@Override
									public void onClick(int which)
									{
										if (which == 1)
										{
											SRequest_DeleteMatter request = new SRequest_DeleteMatter();
											request.matterId = matter.id;
											Protocol.doPost(context, App.getApi(), SHandleId.DeleteMatter, request.saveToStr(), new Protocol.OnCallback()
											{
												@Override
												public void onResponse(int code, String data, String msg)
												{
													if (code == 200)
													{
														operateListener.onDelete();
													}
												}
											});
										}
									}
								}, "取消", "删除");
							}
						});
						builder.show();
						return true;
					}
				});
			}

		}
	}

	public static class HolderMatterGroupClass extends RecyclerView.ViewHolder
	{
		public List<HolderMatterClass> cards = new ArrayList<>();

		public HolderMatterGroupClass(View itemView)
		{
			super(itemView);
			cards.add(new HolderMatterClass(itemView.findViewById(R.id.card0)));
			cards.add(new HolderMatterClass(itemView.findViewById(R.id.card1)));
			cards.add(new HolderMatterClass(itemView.findViewById(R.id.card2)));
			cards.add(new HolderMatterClass(itemView.findViewById(R.id.card3)));
		}

		public void bindView(Context context, List<SMatter> matterGroup, boolean isHome, OnMatterOperateListener operateListener)
		{
			for (int i = 0; i < cards.size(); i++)
			{
				HolderMatterClass holder = cards.get(i);
				if (i < matterGroup.size())
				{
					SMatter matter = matterGroup.get(i);
					holder.itemView.setVisibility(View.VISIBLE);
					holder.bindView(context, matter, isHome, operateListener);
				}
				else
				{
					holder.itemView.setVisibility(View.INVISIBLE);
				}
			}
		}
	}

	protected class HolderMatterMorePair extends RecyclerView.ViewHolder
	{
		public TextView more;

		public HolderMatterMorePair(View itemView)
		{
			super(itemView);
			more = itemView.findViewById(R.id.more);
		}
	}

	public static class HolderMatterPair
	{
		public View itemView;

		public ImageView cover;
		public TextView info;
		public TextView name;
		public TextView money;
		public ImageView place;
		public TextView invalid;

		public HolderMatterPair(View itemView)
		{
			this.itemView = itemView;

			cover = itemView.findViewById(R.id.cover);
			info = itemView.findViewById(R.id.info);
			name = itemView.findViewById(R.id.name);
			money = itemView.findViewById(R.id.money);
			place = itemView.findViewById(R.id.place);
			invalid = itemView.findViewById(R.id.invalid);
		}

		public void bindView(final Context context, final SMatter matter, final boolean isHome, final OnMatterOperateListener operateListener)
		{
			ImageLoad.displayImage(context, matter.cover, MatterCoverWidth, cover, R.drawable.img_default, null);

			if (App.isSupterMaster() && !isHome)
			{
				info.setVisibility(View.VISIBLE);
				info.setText(String.format("%s  %s  [%s]  [%s]", matter.userId, matter.sort, matter.hours.size(), matter.details.size()));
			}
			else
			{
				info.setVisibility(View.GONE);
			}

			name.setText(matter.name);

			money.setText(String.format("%s/", matter.money));

			if (App.isSupterMaster() && !isHome)
			{
				if (matter.place.equals(SMatterPlace.Banner))
				{
					place.setImageResource(R.drawable.img_matter_place_banner);
				}
				else if (matter.place.equals(SMatterPlace.Main))
				{
					place.setImageResource(R.drawable.img_matter_place_main);
				}
				else
				{
					place.setImageDrawable(null);
				}
			}
			else
			{
				place.setImageDrawable(null);
			}

			invalid.setVisibility(matter.invalid ? View.VISIBLE : View.GONE);

			cover.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					Intent intent = new Intent(context, ActivityShopDetail.class);
					intent.putExtra("matter", matter.saveToStr());
					context.startActivity(intent);
				}
			});

			if (App.isSupterMaster())
			{
				cover.setOnLongClickListener(new View.OnLongClickListener()
				{
					@Override
					public boolean onLongClick(View view)
					{
						DialogMenu.Builder builder = new DialogMenu.Builder(context);
						builder.setMenu("编辑", new DialogMenu.OnClickMenuListener()
						{
							@Override
							public void onClick()
							{
								DialogEditMatter.show(context, matter, new DialogEditMatter.OnResultListener()
								{
									@Override
									public void onResult(SMatter matter)
									{
										SRequest_AddModifyMatter request = new SRequest_AddModifyMatter();
										request.matter = matter;
										Protocol.doPost(context, App.getApi(), SHandleId.AddModifyMatter, request.saveToStr(), new Protocol.OnCallback()
										{
											@Override
											public void onResponse(int code, String data, String msg)
											{
												if (code == 200)
												{
													operateListener.onModify();
												}
											}
										});
									}
								});
							}
						});
						if (!isHome)
						{
							builder.setMenu("上移", new DialogMenu.OnClickMenuListener()
							{
								@Override
								public void onClick()
								{
									operateListener.onMoveUp(matter);
								}
							});
							builder.setMenu("下移", new DialogMenu.OnClickMenuListener()
							{
								@Override
								public void onClick()
								{
									operateListener.onMoveDown(matter);
								}
							});
						}
						builder.setMenu("删除", new DialogMenu.OnClickMenuListener()
						{
							@Override
							public void onClick()
							{
								DialogAlert.show(context, "确定要删除吗？", null, new DialogAlert.OnClickListener()
								{
									@Override
									public void onClick(int which)
									{
										if (which == 1)
										{
											SRequest_DeleteMatter request = new SRequest_DeleteMatter();
											request.matterId = matter.id;
											Protocol.doPost(context, App.getApi(), SHandleId.DeleteMatter, request.saveToStr(), new Protocol.OnCallback()
											{
												@Override
												public void onResponse(int code, String data, String msg)
												{
													if (code == 200)
													{
														operateListener.onDelete();
													}
												}
											});
										}
									}
								}, "取消", "删除");
							}
						});
						builder.show();
						return true;
					}
				});
			}
		}
	}

	public static class HolderMatterGroupPair extends RecyclerView.ViewHolder
	{
		public List<HolderMatterPair> cards = new ArrayList<>();

		public HolderMatterGroupPair(View itemView)
		{
			super(itemView);
			cards.add(new HolderMatterPair(itemView.findViewById(R.id.card0)));
			cards.add(new HolderMatterPair(itemView.findViewById(R.id.card1)));
			cards.add(new HolderMatterPair(itemView.findViewById(R.id.card2)));
			cards.add(new HolderMatterPair(itemView.findViewById(R.id.card3)));
		}

		public void bindView(Context context, List<SMatter> matterGroup, boolean isHome, OnMatterOperateListener operateListener)
		{
			for (int i = 0; i < cards.size(); i++)
			{
				HolderMatterPair holder = cards.get(i);
				if (i < matterGroup.size())
				{
					SMatter matter = matterGroup.get(i);
					holder.itemView.setVisibility(View.VISIBLE);
					holder.bindView(context, matter, isHome, operateListener);
				}
				else
				{
					holder.itemView.setVisibility(View.INVISIBLE);
				}
			}
		}
	}

	public interface OnMatterOperateListener
	{
		void onModify();

		void onMoveUp(SMatter matter);

		void onMoveDown(SMatter matter);

		void onDelete();
	}

}
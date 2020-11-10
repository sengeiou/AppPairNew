package com.lys.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lys.activity.ActivityShopDetail;
import com.lys.app.R;
import com.lys.base.fragment.BaseFragment;
import com.lys.base.utils.ImageLoader;
import com.lys.protobuf.SMatter;

public class FragmentShopLoop extends BaseFragment
{
	private class Holder
	{
		private ImageView image;
	}

	private Holder holder = new Holder();

	private void initHolder(View view)
	{
		holder.image = view.findViewById(R.id.image);
	}

	private View view = null;
	private SMatter matter;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		if (view == null)
		{
			view = inflater.inflate(R.layout.fragment_shop_loop, container, false);
			initHolder(view);
			matter = SMatter.load(getArguments().getString("matter"));
			ImageLoader.displayImage(context, matter.banner, holder.image, R.drawable.img_default, null);
			holder.image.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					Intent intent = new Intent(context, ActivityShopDetail.class);
					intent.putExtra("matter", matter.saveToStr());
					context.startActivity(intent);
				}
			});
		}
		return view;
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
	}
}

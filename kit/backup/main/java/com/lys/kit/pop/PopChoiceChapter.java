package com.lys.kit.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.lys.kit.R;
import com.lys.kit.adapter.AdapterChoiceChapter;
import com.lys.kit.adapter.AdapterChoiceMaterial;
import com.lys.kit.config.Config;
import com.lys.kit.utils.Protocol;
import com.lys.protobuf.SChapter;
import com.lys.protobuf.SMaterial;
import com.lys.protobuf.SRequest_GetChapter;
import com.lys.protobuf.SRequest_GetMaterial;
import com.lys.protobuf.SResponse_GetChapter;
import com.lys.protobuf.SResponse_GetMaterial;

import java.util.ArrayList;
import java.util.List;

public class PopChoiceChapter extends PopupWindow
{
	public interface OnListener
	{
		void onSelect(String materialId, SChapter chapter);
	}

	private OnListener listener = null;

	public void setListener(OnListener listener)
	{
		this.listener = listener;
	}

	private Context context;

	private RecyclerView recyclerViewMaterial;
	private AdapterChoiceMaterial adapterMaterial;

	private RecyclerView recyclerViewChapter;
	private AdapterChoiceChapter adapterChapter;

	public PopChoiceChapter(Context context, Integer subject, String currMaterialId)
	{
		super(context);
		this.context = context;
		View view = LayoutInflater.from(context).inflate(R.layout.pop_choice_chapter, null);
		setContentView(view);
		setWidth(800);
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new ColorDrawable(0x00000000)); // 点击外面消失
		setFocusable(true);

		recyclerViewMaterial = view.findViewById(R.id.recyclerViewMaterial);
		recyclerViewMaterial.setLayoutManager(new LinearLayoutManager(context));
		adapterMaterial = new AdapterChoiceMaterial(this);
		recyclerViewMaterial.setAdapter(adapterMaterial);

		recyclerViewChapter = view.findViewById(R.id.recyclerViewChapter);
		recyclerViewChapter.setLayoutManager(new LinearLayoutManager(context));
		adapterChapter = new AdapterChoiceChapter(this);
		recyclerViewChapter.setAdapter(adapterChapter);

		request(subject, currMaterialId);
	}

	private List<SMaterial> materials = null;
	private int selectIndex = -1;

	private void request(Integer subject, final String currMaterialId)
	{
		if (false)
		{
			SRequest_GetMaterial request = new SRequest_GetMaterial();
//			request.personId = Config.getPersonId(context);
			request.subject = subject;
			Protocol.doPost(context, 0, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_GetMaterial response = SResponse_GetMaterial.load(data);
						materials = response.materials;
						adapterMaterial.setData(materials);
						if (!TextUtils.isEmpty(currMaterialId))
						{
							for (int i = 0; i < materials.size(); i++)
							{
								if (materials.get(i).id.equals(currMaterialId))
								{
									select(i);
									break;
								}
							}
						}
						else
						{
							for (int i = 0; i < materials.size(); i++)
							{
								if (materials.get(i).id.equals(response.defaultMaterialId))
								{
									select(i);
									break;
								}
							}
						}
					}
				}
			});
		}
		else
		{
			SResponse_GetMaterial response = new SResponse_GetMaterial();

			{
				SMaterial material = new SMaterial();
				material.id = "9999";
				material.name = "高考专项";
				response.materials.add(material);
			}
			for (int i = 0; i < 30; i++)
			{
				SMaterial material = new SMaterial();
				material.id = String.valueOf(i + 1);
				material.name = "必修" + (i + 1);
				response.materials.add(material);
			}
			response.defaultMaterialId = "2";

			materials = response.materials;
			adapterMaterial.setData(materials);
			if (!TextUtils.isEmpty(currMaterialId))
			{
				for (int i = 0; i < materials.size(); i++)
				{
					if (materials.get(i).id.equals(currMaterialId))
					{
						select(i);
						break;
					}
				}
			}
			else
			{
				for (int i = 0; i < materials.size(); i++)
				{
					if (materials.get(i).id.equals(response.defaultMaterialId))
					{
						select(i);
						break;
					}
				}
			}
		}
	}

	public void select(int index)
	{
		selectIndex = index;
		adapterMaterial.setSelectIndex(selectIndex);
		if (false)
		{
			SRequest_GetChapter request = new SRequest_GetChapter();
			request.materialId = materials.get(index).id;
			Protocol.doPost(context, 0, request.saveToStr(), new Protocol.OnCallback()
			{
				@Override
				public void onResponse(int code, String data, String msg)
				{
					if (code == 200)
					{
						SResponse_GetChapter response = SResponse_GetChapter.load(data);
						adapterChapter.setData(reGroup(response.chapters));
					}
				}
			});
		}
		else
		{
			SResponse_GetChapter response = new SResponse_GetChapter();

			int id = 0;
			for (int i = 0; i < 10; i++)
			{
				SChapter chapter = new SChapter();
				chapter.id = String.valueOf(++id);
				chapter.unit = materials.get(index).name + "  章章章章章章章" + chapter.id;
				for (int j = 0; j < 6; j++)
				{
					SChapter chapterSub = new SChapter();
					chapterSub.id = String.valueOf(++id);
					chapterSub.unit = materials.get(index).name + "  节节节节" + chapterSub.id;
					chapter.nodes.add(chapterSub);
				}
				response.chapters.add(chapter);
			}

			adapterChapter.setData(reGroup(response.chapters));
		}
	}

	private void reGroup(List<SChapter> arrayOld, List<SChapter> arrayNew, int level)
	{
		for (int i = 0; i < arrayOld.size(); i++)
		{
			SChapter old = arrayOld.get(i);

			SChapter chapter = new SChapter();
			chapter.id = old.id;
			chapter.unit = old.unit;
			chapter.level = level;
			arrayNew.add(chapter);

			reGroup(old.nodes, arrayNew, level + 1);
		}
	}

	private List<SChapter> reGroup(List<SChapter> arrayOld)
	{
		List<SChapter> arrayNew = new ArrayList<>();
		arrayNew.add(Config.chapterNull());
		reGroup(arrayOld, arrayNew, 0);
		return arrayNew;
	}

	public void selectChapter(SChapter chapter)
	{
		dismiss();
		if (listener != null)
			listener.onSelect(materials.get(selectIndex).id, chapter);
	}
}
package com.georgebindragon.base.ui.activity;

import android.view.LayoutInflater;

import com.georgebindragon.base.databinding.CommonTabViewpagerBinding;
import com.georgebindragon.base.ui.ability.IUITab;
import com.georgebindragon.base.ui.base.BaseActivity;
import com.georgebindragon.base.utils.EmptyUtil;
import com.qmuiteam.qmui.widget.tab.QMUITab;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * author：
 *
 * description：
 * action：
 *
 * modification：
 */


public abstract class CommonTabViewPagerActivity extends BaseActivity<CommonTabViewpagerBinding> implements IUITab
{
	private static final String TAG = "CommonTabViewPagerActivity-->";

	@Override
	protected CommonTabViewpagerBinding createViewBinding(@NonNull LayoutInflater layoutInflater) { return CommonTabViewpagerBinding.inflate(layoutInflater); }

	protected List<QMUITab> tabs;

	@Override
	public void initTabView(QMUITabSegment tabSegment)
	{
		//		int normalColor = ResourceProxy.getColorFromRes(R.color.gray);
		//		int selectColor = ResourceProxy.getColorFromRes(R.color.pure_white);
		//
		//		tabSegment.setDefaultNormalColor(normalColor);
		//		tabSegment.setDefaultSelectedColor(selectColor);

		//		tabSegment.selectTab(indexExtra);

		tabs = initTabs();
		if (EmptyUtil.notEmpty(tabs))
		{
			for (QMUITab tab : tabs)
			{
				tabSegment.addTab(tab);
			}
		}
		tabSegment.notifyDataChanged();
	}

	public abstract List<QMUITab> initTabs();
}
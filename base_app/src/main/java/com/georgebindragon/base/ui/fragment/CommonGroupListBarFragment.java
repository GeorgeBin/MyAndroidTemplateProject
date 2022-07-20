package com.georgebindragon.base.ui.fragment;

import android.view.LayoutInflater;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.databinding.CommonGrouplistviewBarBinding;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.ui.ability.IUIGroupListView;
import com.georgebindragon.base.ui.ability.IUITopBar;
import com.georgebindragon.base.ui.base.BaseFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import androidx.annotation.NonNull;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class CommonGroupListBarFragment extends BaseFragment<CommonGrouplistviewBarBinding> implements IUITopBar, IUIGroupListView
{
	@Override
	protected CommonGrouplistviewBarBinding createViewBinding(@NonNull LayoutInflater layoutInflater)
	{
		return CommonGrouplistviewBarBinding.inflate(layoutInflater);
	}

	@Override
	public void initView()
	{
		super.initView();

		initTopBar(getViewBinding().topBar);
		initGroupListView(getViewBinding().groupListView);
	}

	/* ---------------------------------------- TopBar ---------------------------------------- */

	@Override
	public void initTopBar(QMUITopBarLayout topBar)
	{
		LogProxy.i(TAG, "initTopBar-->");
	}

	@Override
	public void setTopBarNormalBack(QMUITopBarLayout topBar)
	{
		LogProxy.i(TAG, "setTopBarNormalBack-->");
		topBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
	}

	/* ---------------------------------------- GroupListView ---------------------------------------- */

	protected int leftIcon_width  = QMUIDisplayHelper.dp2px(BaseUtils.getContext(), 23);
	protected int leftIcon_height = leftIcon_width;

	@Override
	public void initGroupListView(QMUIGroupListView groupListView)
	{
		LogProxy.i(TAG, "initGroupListView-->");
	}

}

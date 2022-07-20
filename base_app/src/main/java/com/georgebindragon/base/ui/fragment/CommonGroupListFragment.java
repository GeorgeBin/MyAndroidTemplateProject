package com.georgebindragon.base.ui.fragment;

import android.view.LayoutInflater;

import com.georgebindragon.base.ui.ability.IUIGroupListView;
import com.georgebindragon.base.ui.ability.IUITopBar;
import com.georgebindragon.base.ui.base.BaseFragment;
import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.databinding.CommonGrouplistviewBinding;
import com.georgebindragon.base.function.log.LogProxy;
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


public class CommonGroupListFragment extends BaseFragment<CommonGrouplistviewBinding> implements IUIGroupListView
{
	@Override
	protected CommonGrouplistviewBinding createViewBinding(@NonNull LayoutInflater layoutInflater) { return CommonGrouplistviewBinding.inflate(layoutInflater); }

	@Override
	public void initView()
	{
		super.initView();

		initGroupListView(getViewBinding().groupListView);
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

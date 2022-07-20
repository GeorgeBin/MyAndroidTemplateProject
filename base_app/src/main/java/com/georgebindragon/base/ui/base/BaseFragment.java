package com.georgebindragon.base.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.georgebindragon.base.ui.ability.IUIBase;
import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.R;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.rx.utils.RxCommonUtil;
import com.georgebindragon.base.system.software.ActivityUtil;
import com.georgebindragon.base.utils.StringUtil;
import com.qmuiteam.qmui.arch.QMUIFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public abstract class BaseFragment<VB extends ViewBinding> extends QMUIFragment implements IUIBase
{
	protected String TAG = "Fragment: " + getClass().getSimpleName() + "-->";

	protected VB               viewBinding;
	protected List<Disposable> disposeList = new ArrayList<>();

	public VB getViewBinding() { return viewBinding; }

	/* ---------------------------------------- Fragment life cycle ---------------------------------------- */

	//	@Override
	//	public void onConfigurationChanged(@NonNull Configuration oldConfig)
	//	{
	//		LogProxy.i(TAG, "onConfigurationChanged-->");
	//		Configuration newConfig = Language.getInstance().onFragmentConfigurationChanged(oldConfig);
	//		super.onConfigurationChanged(newConfig);
	//		onScreenOrientationChanged();
	//	}

	@Override
	protected View onCreateView()
	{
		beforeContentView();

		viewBinding = createViewBinding(getLayoutInflater());// LayoutInflater.from(getActivity())
		View contentView = viewBinding.getRoot();

		Bundle argumentBundles = getArguments();
		if (null != argumentBundles) onBundle(argumentBundles);

		initBase();
		initView();
		initData();

		return contentView;
	}

	@Override
	protected void onViewCreated(@NonNull View rootView)
	{
		super.onViewCreated(rootView);// it called only when new UI (not cached UI) is created
		LogProxy.i(TAG, "onViewCreated-->rootView");
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		LogProxy.i(TAG, "onDestroyView-->");
		viewBinding = null;
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState)
	{
		super.onSaveInstanceState(outState);
		LogProxy.i(TAG, "onSaveInstanceState");
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		LogProxy.i(TAG, "onCreate");
	}

	@Override
	public void onStart()
	{
		super.onStart();
		LogProxy.i(TAG, "onStart");
	}

	@Override
	public void onResume()
	{
		super.onResume();
		LogProxy.i(TAG, "onResume");
	}

	@Override
	public void onPause()
	{
		super.onPause();
		LogProxy.i(TAG, "onPause");
	}

	@Override
	public void onStop()
	{
		super.onStop();
		LogProxy.i(TAG, "onStop");
	}

	@Override
	public void onDestroy()
	{
		RxCommonUtil.dispose(disposeList);
		super.onDestroy();
		LogProxy.i(TAG, "onDestroy");
	}

	/* ---------------------------------------- implements ---------------------------------------- */

	@Override
	public void beforeContentView() { LogProxy.i(TAG, "beforeContentView()"); }

	protected abstract VB createViewBinding(@NonNull LayoutInflater layoutInflater);

	@Override
	public void initBase() { LogProxy.i(TAG, "initBase()"); }

	@Override
	public void initView() { LogProxy.i(TAG, "initView()"); }

	@Override
	public void initData() { LogProxy.i(TAG, "initData()"); }

	@Override
	public void addDispose(Disposable disposable)
	{
		LogProxy.i(TAG, "addDispose-->disposable=" + StringUtil.getPrintString(disposable));
		if (null != disposable) disposeList.add(disposable);
	}

	@Override
	public void dispose(Disposable disposable)
	{
		LogProxy.i(TAG, "dispose-->disposable=" + StringUtil.getPrintString(disposable));
		RxCommonUtil.dispose(disposable);
	}

	/* ---------------------------------------- jump ---------------------------------------- */

	@Override
	public void jumpActivity(Class<? extends Activity> clasz, boolean needFinish, long delayMilliSeconds)
	{
		LogProxy.i(TAG + "jumpActivity-->delaySecond=" + delayMilliSeconds + " ms，要跳转的页面=" + (null == clasz ? "NULL" : clasz.getSimpleName()));
		Intent intent = null;
		if (null != clasz) intent = new Intent(getActivity(), clasz);
		jumpActivity(intent, needFinish, delayMilliSeconds);
	}

	@Override
	public void jumpActivity(Intent intent, boolean needFinish, long delayMilliSeconds)
	{
		if (delayMilliSeconds <= 0)
		{
			jumpActivity(intent, needFinish);
		} else
		{
			jumpActivity(intent, needFinish, delayMilliSeconds / 10, 10, TimeUnit.MILLISECONDS);
		}
	}

	@Override
	public void jumpActivity(Class<? extends Activity> clasz, boolean needFinish)
	{
		Intent intent = null;
		if (null != clasz) intent = new Intent(getActivity(), clasz);
		jumpActivity(intent, needFinish);
	}

	@Override
	public void jumpActivity(Intent intent, boolean needFinish)
	{
		if (null != intent)
		{
			if (null != getActivity())
			{
				getActivity().startActivity(intent);
				getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);//页面切换方式
				if (needFinish) getActivity().finish();
			} else
			{
				ActivityUtil.jumpActivity(BaseUtils.getContext(), intent);
			}
		} else
		{
			LogProxy.e(TAG, "jumpActivity-->intent=NULL");
		}
	}

	private Disposable jumpTimer;
	private int        count = 1;

	public void jumpActivity(final Intent intent, final boolean needFinish, final long delayCount, long period, TimeUnit unit)
	{
		LogProxy.i(TAG, "jumpActivity-->intent=" + intent, "needFinish=" + needFinish, "delayCount=" + delayCount, "period=" + period, "unit=" + unit);

		if (delayCount <= 0)
		{
			jumpActivity(intent, needFinish);
		} else
		{
			count = 1;
			jumpTimer = Observable.interval(period, unit)
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(aLong ->
					{
						LogProxy.i(TAG, "accept count=" + count, "delayCount=" + delayCount);
						if (count >= delayCount)
						{
							jumpActivity(intent, needFinish);
							dispose(jumpTimer);//注销
						}
						count++;
						LogProxy.i(TAG, "count=" + count);
					});
		}
	}

	protected Disposable fragmentJumpTimer;
	protected int        fragmentJumpTCounter = 1;

	/**
	 * Fragment跳转
	 *
	 * @param qmuiFragment              需要跳转的页面
	 * @param needFinishCurrentFragment 是否结束当前页
	 * @param delayCount                跳转延迟：次数
	 * @param period                    跳转延迟：间隔
	 * @param unit                      跳转延迟：单位
	 */
	public void jumpFragment(QMUIFragment qmuiFragment, final boolean needFinishCurrentFragment, final long delayCount, long period, TimeUnit unit)
	{
		LogProxy.i(TAG, "jumpFragment-->qmuiFragment=" + qmuiFragment, "needFinishCurrentFragment=" + needFinishCurrentFragment,
				"delayCount=" + delayCount, "period=" + period, "unit=" + unit);

		if (delayCount <= 0 || period <= 0)
		{
			jumpFragment(qmuiFragment, needFinishCurrentFragment);
		} else
		{
			fragmentJumpTCounter = 1;
			fragmentJumpTimer = Observable.interval(period, unit)
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(aLong ->
					{
						LogProxy.i(TAG, "accept count=" + fragmentJumpTCounter, "delayCount=" + delayCount);
						if (fragmentJumpTCounter >= delayCount)
						{
							jumpFragment(qmuiFragment, needFinishCurrentFragment);
							dispose(fragmentJumpTimer);//注销
						}
						fragmentJumpTCounter++;
						LogProxy.i(TAG, "count=" + fragmentJumpTCounter);
					});
		}
	}


	public void jumpFragment(QMUIFragment qmuiFragment, boolean needFinishCurrentFragment)
	{
		LogProxy.i(TAG, "jumpFragment 最终方法-->needFinishCurrentFragment=" + needFinishCurrentFragment);

		// 注意：自动跳转，最好不要使用Fragment 跳转 Fragment，在不可见的的状态下（onSaveInstanceState）之后，不能跳转
		if (needFinishCurrentFragment)
		{
			startFragmentAndDestroyCurrent(qmuiFragment);
		} else
		{
			startFragment(qmuiFragment);
		}
	}

	/* ---------------------------------------- private ---------------------------------------- */

	protected void onBundle(Bundle argumentBundles)
	{
		LogProxy.i(TAG, "onBundle-->bundles=" + StringUtil.getPrintString(argumentBundles));
	}

	//   public View inflateTransparentView(int heightInDp)
	//   {
	//      View     root       = LayoutInflater.from(BaseUtils.getContext()).inflate(R.layout.item_foot_transparent, null);
	//      TextView emptyView2 = root.findViewById(R.id.item_foot_transparent_empty_tv);
	//      emptyView2.setHeight(QMUIDisplayHelper.dp2px(BaseUtils.getContext(), heightInDp));
	//      return root;
	//   }
	/* ---------------------------------------- Override Config ---------------------------------------- */

	@Override
	protected boolean canDragBack() { return false; }

	@Override
	protected void onBackPressed()
	{
		super.onBackPressed();
		LogProxy.i(TAG, "onBackPressed-->");
	}

	/* ---------------------------------------- Override ---------------------------------------- */


}

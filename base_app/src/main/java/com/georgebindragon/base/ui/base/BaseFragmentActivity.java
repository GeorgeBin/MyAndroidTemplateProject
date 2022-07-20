package com.georgebindragon.base.ui.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.georgebindragon.base.ui.ability.IUIBase;
import com.georgebindragon.base.R;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.rx.utils.RxCommonUtil;
import com.georgebindragon.base.utils.StringUtil;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;

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


@SuppressLint("Registered")
public abstract class BaseFragmentActivity<VB extends ViewBinding> extends QMUIFragmentActivity implements IUIBase
{
	protected String TAG = "FragmentActivity：" + getClass().getSimpleName() + "-->";

	//	@Override
	//	protected void attachBaseContext(Context oldContext)
	//	{
	//		LogProxy.i(TAG, "attachBaseContext-->");
	//		Context newContext = Language.getInstance().onAttachActivityBaseContext(oldContext);
	//		super.attachBaseContext(newContext);
	//	}
	//
	//	@Override
	//	public void onConfigurationChanged(@NonNull Configuration oldConfig)
	//	{
	//		LogProxy.i(TAG, "onConfigurationChanged-->");
	//
	//		Configuration newConfig = Language.getInstance().onActivityConfigurationChanged(oldConfig);
	//		super.onConfigurationChanged(newConfig);
	//	}

	protected VB               viewBinding;
	protected List<Disposable> disposeList = new ArrayList<>();

	/* ---------------------------------------- Activity life cycle ---------------------------------------- */

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		LogProxy.i(TAG, "onCreate()-->TaskId=" + getTaskId());

		//		LogProxy.i(TAG, "onCreate-->isAppRunning=" + AppLifecycle.isAppRunning());
		//		if (!AppLifecycle.isAppRunning())// 权限被取消后，应用重启的问题
		//		{
		//			LogProxy.i(TAG, "onCreate-->isAppRunning 权限被取消后，应用重启的问题");
		//			finish();
		//			AppLifecycle.launch();
		//		}

		beforeContentView();

		viewBinding = createViewBinding(getLayoutInflater());
		View view = viewBinding.getRoot();
		setContentView(view);

		Intent intent = getIntent();
		if (null != intent) onIntent(intent, false);

		initBase();
		initView();
		initData();
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
		if (null != intent) onIntent(intent, true);
		LogProxy.i(TAG, "onNewIntent-->intent=" + StringUtil.getPrintString(intent));
	}

	@Override
	public void onStart()
	{
		super.onStart();
		LogProxy.i(TAG, "onStart()");
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		LogProxy.i(TAG, "onResume()");
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		LogProxy.i(TAG, "onPause()");
	}

	@Override
	public void onStop()
	{
		super.onStop();
		LogProxy.i(TAG, "onStop()");
	}

	@Override
	protected void onDestroy()
	{
		RxCommonUtil.dispose(disposeList);
		super.onDestroy();
		LogProxy.i(TAG, "onDestroy()");
	}

	@Override
	public void finish()
	{
		LogProxy.i(TAG, "finish()");
		super.finish();
	}

	/* ---------------------------------------- abstract ---------------------------------------- */

	protected abstract VB createViewBinding(@NonNull LayoutInflater layoutInflater);

	/* ---------------------------------------- implements ---------------------------------------- */

	@Override
	public void beforeContentView() { LogProxy.i(TAG, "beforeContentView()"); }

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
		if (null != clasz) intent = new Intent(this, clasz);
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
		if (null != clasz) intent = new Intent(this, clasz);
		jumpActivity(intent, needFinish);
	}

	@Override
	public void jumpActivity(Intent intent, boolean needFinish)
	{
		if (null != intent)
		{
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);//页面切换方式
		}
		if (needFinish)
		{
			finish();
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);//页面切换方式
		}
	}

	protected Disposable jumpTimer;
	int count = 1;

	/**
	 * jumpActivity
	 *
	 * delay time in MilliSeconds = delayCount * period * unit
	 *
	 * @param intent     jump intent
	 * @param needFinish need finish current
	 * @param delayCount delay count
	 * @param period     time period
	 * @param unit       time unit
	 */
	public void jumpActivity(final Intent intent, final boolean needFinish, final long delayCount, long period, TimeUnit unit)
	{
		LogProxy.i(TAG, "jumpActivity-->intent=" + intent, "needFinish=" + needFinish, "delayCount=" + delayCount, "period=" + period, "unit=" + unit);

		if (delayCount <= 0)
		{
			jumpActivity(intent, needFinish);
		} else
		{
			dispose(jumpTimer);//注销
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
					});
			addDispose(jumpTimer);
		}
	}


	/* ---------------------------------------- private ---------------------------------------- */

	public void onIntent(Intent intent, boolean isFromNewIntent)
	{
		LogProxy.i(TAG, "onIntent-->intent=" + StringUtil.getPrintString(intent), "isFromNewIntent=" + isFromNewIntent);
	}

	/* ---------------------------------------- Override ---------------------------------------- */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		LogProxy.i(TAG, "onKeyDown-->keyCode=" + keyCode, "event=" + StringUtil.getPrintString(event));
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		LogProxy.i(TAG, "onKeyUp-->keyCode=" + keyCode, "event=" + StringUtil.getPrintString(event));
		return super.onKeyUp(keyCode, event);
	}

	//
	//	@Override
	//	public QMUIFragment getCurrentFragment()
	//	{
	//		try
	//		{
	//			return (QMUIFragment) getSupportFragmentManager().findFragmentById(getContextViewId());
	//		} catch (Exception e)
	//		{
	//			LogProxy.e(TAG, "getCurrentFragment", e);// 可能会有类型转换错误
	//			return null;
	//		}
	//	}
	//
	//	protected static final int ContextViewId_Null = -123450;//使 getSupportFragmentManager().findFragmentById(getContextViewId()) 能返回NULL的值。0不可以、


}

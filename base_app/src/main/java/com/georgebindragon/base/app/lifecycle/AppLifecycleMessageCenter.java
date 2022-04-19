package com.georgebindragon.base.app.lifecycle;

import com.georgebindragon.base.function.log.LogProxy;
import com.jakewharton.rxrelay2.BehaviorRelay;

import io.reactivex.Observable;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改：
 */


public class AppLifecycleMessageCenter
{
	private static final String TAG = "AppLifecycleMessageCenter-->";

	private static final AppLifecycleMessageCenter ourInstance = new AppLifecycleMessageCenter();

	public static AppLifecycleMessageCenter getInstance() { return ourInstance; }

	private AppLifecycleMessageCenter() { }

	public void init()
	{
		LogProxy.i(TAG, "init-->");
	}

	/* ---------------------------------------- 状态响应 ---------------------------------------- */

	private BehaviorRelay<AppLifecycleType> appLifecycleMessageObservable = BehaviorRelay.create();

	public Observable<AppLifecycleType> getAppLifecycleMessageObservable() { return appLifecycleMessageObservable; }

	private AppLifecycleType lastCommand = AppLifecycleType.Unknown;

	private void updateAppCommand(AppLifecycleType type)
	{
		if (type != lastCommand)
		{
			appLifecycleMessageObservable.accept(type);
			lastCommand = type;
		}
	}

	/* ---------------------------------------- 具体事件 ---------------------------------------- */

	public void onAppExit()
	{
		updateAppCommand(AppLifecycleType.Exit);
	}

	public void onAppCrash()
	{
		updateAppCommand(AppLifecycleType.Crash);
	}
}

package com.georgebindragon.base.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;

import com.georgebindragon.base.lifecycle.AppLifecycleMessageCenter;
import com.georgebindragon.base.lifecycle.AppLifecycleType;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.rx.utils.RxCommonUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改：
 */


public abstract class BaseService extends Service
{
	protected String TAG = "Service: " + getClass().getSimpleName() + "-->";

	protected List<Disposable> disposeList = new ArrayList<>();

	@Override
	protected void attachBaseContext(Context base)
	{
		LogProxy.i(TAG, "attachBaseContext-->");
		super.attachBaseContext(base);

		//		Context newContext = RChatLanguage.getInstance().onAttachServiceBaseContext(base);
		//		super.attachBaseContext(newContext);
	}

	@Override
	public void onConfigurationChanged(Configuration oldConfig)
	{
		LogProxy.i(TAG, "onConfigurationChanged-->");
		super.onConfigurationChanged(oldConfig);
		//		Configuration newConfig = RChatLanguage.getInstance().onServiceConfigurationChanged(oldConfig);
		//		super.onConfigurationChanged(newConfig);
	}

	/* ---------------------------------------- 生命周期 ---------------------------------------- */

	/* --------------------- start --------------------- */

	@Override
	public void onCreate()
	{
		LogProxy.d(TAG, "onCreate");
		initDataOnCreate();
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		LogProxy.d(TAG, "onStartCommand");
		initDataOnStartCommand();// 初始化操作
		super.onStartCommand(intent, flags, startId);
		return getServiceMode(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		LogProxy.d(TAG, "onBind");
		return null; // 服务默认不需要绑定
	}

	/* --------------------- stop --------------------- */

	@Override
	public void onTaskRemoved(Intent rootIntent)
	{
		LogProxy.i(TAG, "onTaskRemoved-->rootIntent=" + rootIntent);
		super.onTaskRemoved(rootIntent);
	}

	@Override
	public void onDestroy()
	{
		LogProxy.d(TAG, "onDestroy");
		RxCommonUtil.dispose(disposeList);
		stopForeground(true);

		super.onDestroy();
	}

	/* ---------------------------------------- 生命周期 相关 ---------------------------------------- */

	/**
	 * 在服务启动时的初始化
	 */
	Disposable appLifeDisposable;

	protected void initDataOnCreate()
	{
		LogProxy.d(TAG, "initDataOnCreate");
		RxCommonUtil.dispose(appLifeDisposable);
		appLifeDisposable = AppLifecycleMessageCenter.getInstance().getAppLifecycleMessageObservable().subscribe(this::onAppCommand);
		addDisposable(appLifeDisposable);
	}

	/**
	 * 在服务启动(每次启动)时的初始化
	 */
	protected void initDataOnStartCommand()
	{
		LogProxy.d(TAG, "initDataOnStartCommand");
		if (LogProxy.isLogEnable()) test();
	}

	/* --------------------- 参数 --------------------- */

	/*
	 * START_STICKY：如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。
	 *   随后系统会尝试重新创建service，由于服务状态为开始状态，所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。如果在此期间没有任何启动命令被传递到service，那么参数Intent将为null。
	 * START_NOT_STICKY：“非粘性的”。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统不会自动重启该服务。
	 * START_REDELIVER_INTENT：重传Intent。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统会自动重启该服务，并将Intent的值传入。
	 * START_STICKY_COMPATIBILITY：START_STICKY的兼容版本，但不保证服务被kill后一定能重启。
	 *
	 * return Service.START_STICKY;//则服务被异常kill掉，会重启该服务
	 * return Service.START_NOT_STICKY;//则服务被异常kill掉，不重启该服务
	 * */
	protected int getServiceMode(Intent intent, int flags, int startId) { return Service.START_NOT_STICKY; }

	/* --------------------- api --------------------- */

	protected void stopService()
	{
		LogProxy.i(TAG, "stopService-->");

		stopForeground(true);
		stopSelf();
	}

	/* --------------------- 自定义回调 --------------------- */

	protected void onAppCommand(AppLifecycleType type)
	{
		LogProxy.i(TAG, "onAppCommand-->type=" + type);
		if (type == AppLifecycleType.Exit) stopService();
	}

	/* ---------------------------------------- 内部方法 ---------------------------------------- */

	protected void addDisposable(Disposable disposable)
	{
		disposeList.add(disposable);
	}

	/* ---------------------------------------- debug相关 ---------------------------------------- */

	protected void test()
	{
		LogProxy.d(TAG, "test");
	}
}

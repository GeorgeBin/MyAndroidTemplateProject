package com.georgebindragon.base.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.georgebindragon.base.function.log.LogProxy;


/**
 * author：George
 * description：
 *
 * modification：
 */

public abstract class BaseRemoteService extends Service
{
	protected String TAG = "RemoteService: " + getClass().getSimpleName() + "-->";

	final private Binder mBinder = getBinder();

	@Override
	public IBinder onBind(Intent intent)
	{
		LogProxy.d(TAG, "onBind");
		return mBinder;
	}

	protected abstract Binder getBinder();

	@Override
	public void onDestroy()
	{
		LogProxy.d(TAG, "onDestroy");
		super.onDestroy();
	}
}

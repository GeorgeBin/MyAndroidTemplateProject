package com.georgebindragon.base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.georgebindragon.base.debug.PrintUtil;
import com.georgebindragon.base.function.log.LogProxy;

/**
 * author：
 *
 * description：
 * action：
 *
 * modification：
 */


public class BaseBroadcastReceiver extends BroadcastReceiver
{
	protected String TAG = "BroadcastReceiver: " + getClass().getSimpleName() + "-->";

	@Override
	public void onReceive(Context context, Intent intent)
	{
		if (null == intent) return;
		LogProxy.d(TAG, "onReceive-->intent=" + PrintUtil.getIntentString(intent));
	}
}

package com.georgebindragon.base.lifecycle;

import android.content.Context;
import android.content.Intent;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.StringUtil;


/**
 * author：
 *
 * description：
 * action：
 *
 * modification：
 */

public class AppLifeCycleUtil
{
	private static final String TAG = "AppLifeCycleUtil-->";

	// restart App, better call from activity
	public static void triggerRebirth(Context context, Intent intent)
	{
		LogProxy.i(TAG, "triggerRebirth-->intent=" + StringUtil.getPrintString(intent));

		if (null == intent) intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		context.startActivity(intent);
		Runtime.getRuntime().exit(0);
	}
}
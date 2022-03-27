package com.georgebindragon.lib.third.umeng;

import android.content.Context;

import com.umeng.commonsdk.UMConfigure;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改：
 */


public class UMeng
{
	private static final String TAG = "UMeng-->";

	public static void setLogEnabled(boolean enabled)
	{
		UMConfigure.setLogEnabled(enabled);
	}

	public static void preInit(Context app, String appKey, String channel)
	{
		UMConfigure.preInit(app, appKey, channel);
	}

	public static void init(Context app, String appKey, String channel, int deviceType, String pushSecret)
	{
		UMConfigure.init(app, appKey, channel, deviceType, pushSecret);
	}

	public static void init(Context app, String appKey, String channel)
	{
		UMConfigure.init(app, appKey, channel, UMConfigure.DEVICE_TYPE_PHONE, "");
	}


}
package com.georgebindragon.lib.locate.api;

import android.content.Context;
import android.util.Log;


/**
 * 创建人：George
 * 类名称：BaseLocationFunction
 *
 * 描述：定位功能的基类
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BaseLocationFunction implements ILocationFunction
{
	protected String TAG = "" + getClass().getSimpleName() + "-->";

	private boolean isLocationFunctionRunning = false;  //是否已经开启

	@Override
	public void init(Context context)
	{
		Log.i(TAG, "init");
	}

	@Override
	public boolean isRunning() { return isLocationFunctionRunning; }

	@Override
	public void start()
	{
		Log.i(TAG, "start");
		isLocationFunctionRunning = true;
	}

	@Override
	public void stop()
	{
		Log.i(TAG, "stop");
		isLocationFunctionRunning = false;
	}

	@Override
	public void restart()
	{
		Log.i(TAG, "restart");
		isLocationFunctionRunning = true;
	}
}

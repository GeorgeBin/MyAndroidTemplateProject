package com.georgebindragon.lib.locate.baidu;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClientOption;
import com.georgebindragon.lib.locate.api.BaseLocationFunction;

/**
 * 创建人：George
 * 类名称：LocationFunction
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BaiduLocationFunction extends BaseLocationFunction
{
	private static final BaiduLocationFunction ourInstance = new BaiduLocationFunction();

	public static BaiduLocationFunction getInstance() { return ourInstance; }

	private BaiduLocationFunction() { }

	public LocationClientOption makeDefaultClientOption(int intervalMS)
	{
		return BaiduMapLocationManager.getInstance().makeDefaultClientOption(intervalMS);
	}

	@Override
	public void init(Context context)
	{
		super.init(context);
		BaiduMapLocationManager.getInstance().init(context);// 可以在使用过程中更新
	}

	public void setOption(LocationClientOption option)
	{
		Log.i(TAG, "setOption");
		BaiduMapLocationManager.getInstance().setClientOption(option);// 可以在使用过程中更新
	}

	public void registerLocationListener(BDAbstractLocationListener listener)
	{
		BaiduMapLocationManager.getInstance().registerLocationListener(listener);
	}

	@Override
	public void start()
	{
		super.start();
		BaiduMapLocationManager.getInstance().startClint();
	}

	@Override
	public void stop()
	{
		super.stop();
		BaiduMapLocationManager.getInstance().stopClint();
	}

	@Override
	public void restart()
	{
		super.restart();
		BaiduMapLocationManager.getInstance().restartClint();

	}

}

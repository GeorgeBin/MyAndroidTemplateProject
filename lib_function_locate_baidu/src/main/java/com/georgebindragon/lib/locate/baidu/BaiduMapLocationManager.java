package com.georgebindragon.lib.locate.baidu;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.georgebindragon.lib.locate.api.LocationType;

/**
 * 创建人：George
 * 类名称：BaiduMapLocationManager
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BaiduMapLocationManager
{
	private static final String TAG = "BaiduMapLocationManager-->";

	private static final BaiduMapLocationManager ourInstance = new BaiduMapLocationManager();

	static BaiduMapLocationManager getInstance() { return ourInstance; }

	private BaiduMapLocationManager() { }

	private LocationClient baiduMapLocationClient;

	private boolean checkClient()
	{
		if (null != baiduMapLocationClient)
		{
			return true;
		} else
		{
			init(context);
			Log.e(TAG, "checkClient, BaiduMapLocationClient = NULL!!!!!");
			return false;
		}
	}

	private Context context;

	public void init(Context context)
	{
		//初始化AMapLocationClient类对象
		this.context = context;
		baiduMapLocationClient = new LocationClient(context.getApplicationContext());

	}

	public void registerLocationListener(BDAbstractLocationListener listener)
	{
		if (checkClient())
		{
			baiduMapLocationClient.registerLocationListener(listener);
		}
	}

	public void setClientOption(LocationClientOption option)
	{
		if (checkClient())
		{
			baiduMapLocationClient.setLocOption(option);
			//mLocationClient为第二步初始化过的LocationClient对象
			//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
			//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
		}
	}

	public LocationClientOption makeDefaultClientOption(int IntervalMS)
	{
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		//可选，设置定位模式，默认高精度
		//LocationMode.Hight_Accuracy：高精度；
		//LocationMode.Battery_Saving：低功耗；
		//LocationMode.Device_Sensors：仅使用设备；

		option.setCoorType(LocationType.string_BD09ll);
		// option.setCoorType("bd09ll");
		//可选，设置返回经纬度坐标类型，默认GCJ02
		//gcj02：国测局坐标；
		//bd09ll：百度经纬度坐标；
		//bd09：百度墨卡托坐标；
		//海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

		option.setScanSpan(IntervalMS);
		//可选，设置发起定位请求的间隔，int类型，单位ms
		//如果设置为0，则代表单次定位，即仅定位一次，默认为0
		//如果设置非0，需设置1000ms以上才有效

		option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		option.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
		option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

		option.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
		option.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用

		option.setOpenAutoNotifyMode();
		//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者

		option.setOpenAutoNotifyMode(3000, 1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
		//参数：最短回调间隔（当小于 180,000=3分钟 时，则最大为3分钟，否则为最小值+1秒）、最小移动距离、敏感度
		//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者

		option.setOpenGps(true);
		//可选，设置是否使用gps，默认false
		//使用高精度和仅用设备两种定位模式的，参数必须设置为true

		option.setLocationNotify(false);
		//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

		option.setIgnoreKillProcess(false);
		//可选，定位SDK内部是一个service，并放到了独立进程。
		//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

		option.SetIgnoreCacheException(false);
		//可选，设置是否收集Crash信息，默认收集，即参数为false

		option.setWifiCacheTimeOut(5 * 60 * 1000);
		//可选，V7.2版本新增能力
		//如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

		option.setEnableSimulateGps(false);
		//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

		return option;
	}

	public void stopClint()
	{
		if (checkClient())
		{
			baiduMapLocationClient.stop();
		}
	}

	public void startClint()
	{
		if (checkClient())
		{
			baiduMapLocationClient.start();
		}
	}

	public void restartClint()
	{
		if (checkClient())
		{
			baiduMapLocationClient.restart();
		}
	}
}

package com.georgebindragon.lib.locate.baidu;

import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.georgebindragon.lib.locate.api.ILocationListener;
import com.georgebindragon.lib.locate.api.LocationType;
import com.georgebindragon.lib.locate.utils.LocationUtil;

/**
 * 创建人：George
 * 类名称：BaiduMapLocationListenerImp
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BaseBaiduMapLocationListener extends BDAbstractLocationListener implements ILocationListener
{
	protected String TAG = "" + getClass().getSimpleName() + "-->";

	@Override
	public void onReceiveLocation(BDLocation bdLocation)
	{
		if (null == bdLocation) return;

		int strategy    = bdLocation.getMockGpsStrategy();// 防作弊策略识别码，用于辅助分析排查问题
		int probability = bdLocation.getMockGpsProbability();// 此定位点作弊概率，3代表高概率，2代表中概率，1代表低概率，0代表概率为0
		Log.i(TAG, "onReceiveLocation-->strategy=" + strategy + (strategy > 0 ? "可能有虚拟定位" : "") + "\tprobability=" + probability);

		BDLocation realLoc = bdLocation.getReallLocation();
		if (strategy > 0 && null != realLoc)
		{
			double dis = realLoc.getDisToRealLocation();// 虚假位置和真实位置之间的距离
			Log.i(TAG, "onReceiveLocation-->虚假位置和真实位置之间的距离=" + dis);
			onBDLocation(realLoc);
		} else
		{
			onBDLocation(bdLocation);
		}
	}

	public void onBDLocation(BDLocation bdLocation)
	{
		if (null == bdLocation) return;

		int LocType = bdLocation.getLocType();//获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
		Log.i(TAG, "onBDLocation-->LocType=" + LocType);

		if (
				LocType == BDLocation.TypeNetWorkLocation //网络定位成功
						|| LocType == BDLocation.TypeGpsLocation  //GPS定位成功
						|| LocType == BDLocation.TypeOffLineLocation //离线定位结果
			// || LocType == BDLocation.TypeCacheLocation //缓存定位结果
		)
		{
			double latitude      = bdLocation.getLatitude();    //获取纬度信息
			double longitude     = bdLocation.getLongitude();    //获取经度信息
			float  radius        = bdLocation.getRadius();    //获取定位精度，默认值为0.0f
			String coorType      = bdLocation.getCoorType();   //坐标类型-->获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
			int    locationWhere = bdLocation.getLocationWhere();//方法可获得当前定位点是否是国内，它的取值及含义如下：

			Log.i(TAG, "onReceiveLocation-->coorType=" + coorType + "\tlatitude=" + latitude + "\tlongitude=" + longitude
					+ "\tradius=" + radius + "\tlocationWhere=" + locationWhere);

			if (LocationUtil.isGoodCoordinates(latitude) && LocationUtil.isGoodCoordinates(longitude))
			{
				if (locationWhere == BDLocation.LOCATION_WHERE_IN_CN)
				{
					onLocationChanged(LocationType.getTypeFromString(coorType, LocationType.BD09ll), latitude, longitude, radius);
				} else
				{
					onLocationChanged(LocationType.WGS84, latitude, longitude, radius);
				}
			}
		}
	}

	@Override
	public void onLocDiagnosticMessage(int LocType, int LocDiagnosticType, String reason)
	{
		Log.i(TAG, "onLocDiagnosticMessage-->LocType=" + LocType + "\tLocDiagnosticType=" + LocDiagnosticType + "\treason=" + reason);
	}


	@Override
	public void onLocationChanged(LocationType locationType, double latitude, double longitude, float accuracy)
	{

	}
}

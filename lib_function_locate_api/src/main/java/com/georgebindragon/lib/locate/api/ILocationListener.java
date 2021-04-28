package com.georgebindragon.lib.locate.api;

/**
 * 创建人：George
 * 类名称：ILocationListener
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public interface ILocationListener
{
	void onLocationChanged(LocationType locationType, double latitude, double longitude, float accuracy); // 数据回调
}

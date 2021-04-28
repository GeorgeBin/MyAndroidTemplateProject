package com.georgebindragon.lib.locate.api;

/**
 * 创建人：George
 * 类名称：LocationType
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public enum LocationType
{
	WGS84,//WGS84：GPS原始坐标体系；国际通用坐标系，地心坐标系，也称地球坐标系，

	GCJ02,//GCJ02：国测局坐标；也称火星坐标系

	BD09ll,//BD09ll：百度经纬度坐标；
	BD09,//BD09：百度墨卡托坐标；

	UnKnow, //
	;

	public String getTypeString()
	{
		switch (this)
		{
			case WGS84:
				return string_WGS84;
			case GCJ02:
				return string_GCJ02;
			case BD09ll:
				return string_BD09ll;
			case BD09:
				return string_BD09;
			case UnKnow:
			default:
				return string_UnKnow;
		}
	}

	public static final String string_WGS84  = "wgs84";
	public static final String string_GCJ02  = "gcj02";
	public static final String string_BD09ll = "bd09ll";
	public static final String string_BD09   = "bd09";
	public static final String string_UnKnow = "";

	public static LocationType getTypeFromString(String typeString)
	{
		if (null != typeString)
		{
			if (typeString.equalsIgnoreCase(string_WGS84)) return WGS84;
			if (typeString.equalsIgnoreCase(string_GCJ02)) return GCJ02;
			if (typeString.equalsIgnoreCase(string_BD09ll)) return BD09ll;
			if (typeString.equalsIgnoreCase(string_BD09)) return BD09;
			if (typeString.equalsIgnoreCase(string_UnKnow)) return UnKnow;
		}
		return UnKnow;
	}

	public static LocationType getTypeFromString(String typeString, LocationType defaultType)
	{
		if (null != typeString)
		{
			if (typeString.equalsIgnoreCase(string_WGS84)) return WGS84;
			if (typeString.equalsIgnoreCase(string_GCJ02)) return GCJ02;
			if (typeString.equalsIgnoreCase(string_BD09ll)) return BD09ll;
			if (typeString.equalsIgnoreCase(string_BD09)) return BD09;
		}
		return defaultType;
	}
}

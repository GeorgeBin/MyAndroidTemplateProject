package com.georgebindragon.lib.locate.utils;

import java.math.BigDecimal;

/**
 * 创建人：George
 * 类名称：LocationUtil
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class LocationUtil
{
	public static boolean isGoodCoordinates(double coordinates) { return coordinates != 0 && coordinates != 4.9E-324; }

	// 移动距离 都移动0.0001=15m, 单个移动0.0001=11m, 单个移动 0.00005=6m
	public static double moveDistance(double newCoordinates, double oldCoordinates) { return Math.abs(subtract(newCoordinates, oldCoordinates)); }

	// 进行 double 减法运算
	private static double subtract(double d1, double d2)
	{
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.subtract(b2).doubleValue();
	}
}

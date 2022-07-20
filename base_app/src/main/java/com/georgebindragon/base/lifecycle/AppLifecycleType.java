package com.georgebindragon.base.lifecycle;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改：
 */


public enum AppLifecycleType
{
	Unknown,// 默认，或未知

	/* ------------ 用户 生命周期 ------------ */
	Logout,

	/* ------------ application 生命周期 ------------ */

	// App关闭
	Exit,
	Crash,

	// App重启
	ReStart,
}

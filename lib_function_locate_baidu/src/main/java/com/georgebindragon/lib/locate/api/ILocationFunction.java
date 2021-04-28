package com.georgebindragon.lib.locate.api;

import android.content.Context;

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


public interface ILocationFunction
{
	void init(Context context);

	boolean isRunning();

	void start();

	void stop();

	void restart();
}

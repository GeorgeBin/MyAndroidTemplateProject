package com.georgebindragon.base;

import com.georgebindragon.base.abilities.function.IFunction;

/**
 * author：George
 *
 * description：
 *
 * modification：
 */

public abstract class BaseFunction implements IFunction
{
	protected String TAG = "Function:" + getClass().getSimpleName() + "-->";

	/**
	 * function is available: static, available state should not change after package release
	 *
	 * @return the function is available; true=available
	 */
	public abstract boolean isFunctionAvailable();
}

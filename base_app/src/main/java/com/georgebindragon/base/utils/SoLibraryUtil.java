package com.georgebindragon.base.utils;

import android.content.Context;

import com.georgebindragon.base.BaseUtils;
import com.getkeepsafe.relinker.ReLinker;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改：
 */


public class SoLibraryUtil
{
	private static final String TAG = "SoLibraryUtil-->";

	public static void loadLibrary(final Context context, final String library)
	{
		ReLinker.loadLibrary(context, library);
	}

	public static void loadLibrary(final String library)
	{
		loadLibrary(BaseUtils.getContext(), library);
	}
}
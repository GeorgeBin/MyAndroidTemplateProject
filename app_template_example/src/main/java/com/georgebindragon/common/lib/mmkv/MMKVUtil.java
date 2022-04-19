package com.georgebindragon.common.lib.mmkv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.StringUtil;
import com.getkeepsafe.relinker.ReLinker;
import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVLogLevel;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class MMKVUtil
{
	private static final String TAG = "MMKVUtil-->";

	public static String init(Context context)
	{
		LogProxy.i(TAG, "init-->");

		String         root      = context.getFilesDir().getAbsolutePath() + "/mmkv";
		MMKVLogLevel   logLevel  = MMKVLogLevel.LevelInfo;
		MMKV.LibLoader libLoader = libName -> ReLinker.loadLibrary(context, libName);

		return MMKV.initialize(root, libLoader, logLevel);
	}

	public static void setLogLevel(MMKVLogLevel logLevel)
	{
		LogProxy.i(TAG, "setLogLevel-->logLevel=" + StringUtil.getPrintString(logLevel));
		MMKV.setLogLevel(logLevel);
	}

	/*-------------------------------------------------------- 创建新的实例 --------------------------------------------------------*/

	public static MMKV makeNewFile(String fileName)
	{
		LogProxy.i(TAG, "makeNewFile-->fileName=" + StringUtil.getPrintString(fileName));
		return MMKV.mmkvWithID(fileName);
	}

	public static MMKV makeNewFile(String fileName, int mode)
	{
		return MMKV.mmkvWithID(fileName, mode);
	}

	public static MMKV makeNewFile(String fileName, int mode, String cryptKey)
	{
		return MMKV.mmkvWithID(fileName, mode, cryptKey);
	}

	public static MMKV makeNewFile(String fileName, String relativePath)
	{
		return MMKV.mmkvWithID(fileName, relativePath);
	}

	public static MMKV mmkvWithID(String fileName, int mode, String cryptKey, String relativePath)
	{
		return MMKV.mmkvWithID(fileName, mode, cryptKey, relativePath);
	}

	public static MMKV mmkvWithAshmemID(Context context, String fileName, int size, int mode, String cryptKey)
	{
		return MMKV.mmkvWithAshmemID(context, fileName, size, mode, cryptKey);
	}

	/*-------------------------------------------------------- 导入数据 --------------------------------------------------------*/

	@SuppressLint("ApplySharedPref")
	public void importFromSP(MMKV mmkv, SharedPreferences oldData, boolean delete)
	{
		if (null != oldData && null != mmkv)
		{
			mmkv.importFromSharedPreferences(oldData);
			if (delete) oldData.edit().clear().commit();
		}
	}

}

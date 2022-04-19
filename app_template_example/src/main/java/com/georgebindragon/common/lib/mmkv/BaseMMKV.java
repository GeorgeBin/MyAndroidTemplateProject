package com.georgebindragon.common.lib.mmkv;

import android.os.Parcelable;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.StringUtil;
import com.tencent.mmkv.MMKV;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BaseMMKV
{
	protected String TAG = "MMKV：" + getClass().getSimpleName() + "-->";

	protected static final String Value_String_Default_Empty = "";//默认的字符串：空
	protected static final int    Value_Int_Default_Empty    = -1;//默认的Int：-1

	public BaseMMKV(MMKV kv)
	{
		if (null == kv)
		{
			this.kv = MMKV.defaultMMKV();
		} else
		{
			this.kv = kv;
		}
	}

	/*-------------------------------------------------------- 内部变量 --------------------------------------------------------*/

	protected MMKV kv;

	public MMKV getMMKV()
	{
		if (null == kv) kv = MMKV.defaultMMKV();
		return kv;
	}

	/*-------------------------------------------------------- 存储、读取操作 6+1 --------------------------------------------------------*/

	//boolean
	protected boolean readBoolean(String key, boolean defValue)
	{
		LogProxy.i(TAG, "readBoolean-->key=" + StringUtil.getPrintString(key), "defValue=" + StringUtil.getPrintString(defValue));
		return getMMKV().decodeBool(key, defValue);
	}

	protected boolean saveBoolean(String key, boolean value)
	{
		LogProxy.i(TAG, "saveBoolean-->key=" + StringUtil.getPrintString(key), "value=" + StringUtil.getPrintString(value));
		return getMMKV().encode(key, value);
	}

	//String
	protected String readString(String key, String defValue)
	{
		LogProxy.i(TAG, "readString-->key=" + StringUtil.getPrintString(key), "defValue=" + StringUtil.getPrintString(defValue));
		return getMMKV().decodeString(key, defValue);
	}

	protected boolean saveString(String key, String value)
	{
		LogProxy.i(TAG, "saveString-->key=" + StringUtil.getPrintString(key), "value=" + StringUtil.getPrintString(value));
		return getMMKV().encode(key, value);
	}

	//int
	protected int readInt(String key, int defValue)
	{
		LogProxy.i(TAG, "readInt-->key=" + StringUtil.getPrintString(key), "defValue=" + StringUtil.getPrintString(defValue));
		return getMMKV().decodeInt(key, defValue);
	}

	protected boolean saveInt(String key, int value)
	{
		LogProxy.i(TAG, "saveInt-->key=" + StringUtil.getPrintString(key), "value=" + StringUtil.getPrintString(value));
		return getMMKV().encode(key, value);
	}

	//long
	protected long readLong(String key, long defValue)
	{
		LogProxy.i(TAG, "readLong-->key=" + StringUtil.getPrintString(key), "defValue=" + StringUtil.getPrintString(defValue));
		return getMMKV().decodeLong(key, defValue);
	}

	protected boolean saveLong(String key, long value)
	{
		LogProxy.i(TAG, "saveLong-->key=" + StringUtil.getPrintString(key), "value=" + StringUtil.getPrintString(value));
		return getMMKV().encode(key, value);
	}

	//float
	protected float readFloat(String key, float defValue)
	{
		LogProxy.i(TAG, "readFloat-->key=" + StringUtil.getPrintString(key), "defValue=" + StringUtil.getPrintString(defValue));
		return getMMKV().decodeFloat(key, defValue);
	}

	protected boolean saveFloat(String key, float value)
	{
		LogProxy.i(TAG, "saveFloat-->key=" + StringUtil.getPrintString(key), "value=" + StringUtil.getPrintString(value));
		return getMMKV().encode(key, value);
	}

	//double
	protected double readDouble(String key, double defValue)
	{
		LogProxy.i(TAG, "readDouble-->key=" + StringUtil.getPrintString(key), "defValue=" + StringUtil.getPrintString(defValue));
		return getMMKV().decodeDouble(key, defValue);
	}

	protected boolean saveDouble(String key, double value)
	{
		LogProxy.i(TAG, "saveDouble-->key=" + StringUtil.getPrintString(key), "value=" + StringUtil.getPrintString(value));
		return getMMKV().encode(key, value);
	}

	//Parcelable
	protected <T extends Parcelable> T readParcelable(String key, Class<T> tClass, T defValue)
	{
		LogProxy.i(TAG, "readParcelable-->key=" + StringUtil.getPrintString(key), "defValue=" + StringUtil.getPrintString(defValue));
		return getMMKV().decodeParcelable(key, tClass, defValue);
	}

	protected boolean saveParcelable(String key, Parcelable value)
	{
		LogProxy.i(TAG, "saveParcelable-->key=" + StringUtil.getPrintString(key), "value=" + StringUtil.getPrintString(value));
		return getMMKV().encode(key, value);
	}

	/*-------------------------------------------------------- 查询、删除 --------------------------------------------------------*/

	protected String[] getAllKeys()
	{
		return getMMKV().allKeys();
	}

	protected boolean containsKey(String key)
	{
		return getMMKV().containsKey(key);
	}

	protected void removeKeyAndValue(String key)
	{
		getMMKV().removeValueForKey(key);
	}

	protected void removeKeysAndValues(String[] keys)
	{
		getMMKV().removeValuesForKeys(keys);
	}
}

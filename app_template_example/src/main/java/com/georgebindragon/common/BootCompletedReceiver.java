package com.georgebindragon.common;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 创建人：George
 * 类名称：BootCompletedReceiver
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class BootCompletedReceiver extends BroadcastReceiver
{
	private static final String TAG = "BootCompletedReceiver-->";

	//接收到广播
	@SuppressLint("UnsafeProtectedBroadcastReceiver")
	@Override
	public void onReceive(Context context, Intent intent)
	{
		if (null != intent)
		{
			String action = intent.getAction();
			if (Intent.ACTION_BOOT_COMPLETED.equalsIgnoreCase(action))
			{
				//RChatAppLifeCycle.getInstance().onAppReceiveBootCompleted();
			}
		}
		//BroadcastReceiverUtil.getBroadcastIntentDetail(intent);
	}
}

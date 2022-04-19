package com.georgebindragon.application.billboard.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.data.basic.NumberUtil;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.receiver.UtilsActions;
import com.georgebindragon.base.receiver.callbacks.IBaseReceiverCallBack;
import com.georgebindragon.base.system.software.AppUtil;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.base.utils.StringUtil;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改：
 */


public abstract class BaseBroadcastNotification extends BaseNotification implements IBaseReceiverCallBack
{
	/* ---------------------------------------- 默认值 ---------------------------------------- */

	protected static final String ClickBroadcast_PackageName = "BroadcastNotification_" + AppUtil.getPackageName() + "_" + NumberUtil.getRandom(1000);

	protected static String ClickBroadcast = null;

	public BaseBroadcastNotification()
	{
		listenToClickBroadcast();
	}

	protected void listenToClickBroadcast()
	{
		LogProxy.i(TAG, "listenToClickBroadcast-->Broadcast=" + StringUtil.getPrintString(getClickBroadcast()));
		UtilsActions.getInstance().listenSomeKey(getClickBroadcast(), this);
	}

	/* ---------------------------------------- 需要实现 ---------------------------------------- */

	protected abstract String getClickBroadcastString();

	protected abstract void onBroadcastReceive(Context context, Intent intent);

	/* ---------------------------------------- api ---------------------------------------- */

	public String getClickBroadcast()
	{
		if (EmptyUtil.isEmpty(ClickBroadcast))
		{
			String clickBroadcastString = getClickBroadcastString();
			ClickBroadcast = EmptyUtil.notEmpty(clickBroadcastString) ? clickBroadcastString : ClickBroadcast_PackageName;
		}
		return ClickBroadcast;
	}

	public PendingIntent getBroadcastPendingIntent() { return getBroadcastPendingIntent(null, 0, null); }

	public PendingIntent getBroadcastPendingIntent(Context context, int requestCode, Intent intent)
	{
		if (null == context) context = BaseUtils.getContext();
		if (null == intent) intent = new Intent();

		intent.setAction(getClickBroadcast());
		return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	/* ---------------------------------------- 回调 ---------------------------------------- */

	@Override
	public void onReceive(Context context, Intent intent)
	{
		LogProxy.i(TAG, "onReceive-->context=" + context, "intent=" + StringUtil.getPrintString(intent));
		if (null != intent)
		{
			String action = intent.getAction();
			if (null != action && action.length() > 0 && action.equalsIgnoreCase(getClickBroadcast()))
			{
				onBroadcastReceive(context, intent);
			}
		}
	}

}

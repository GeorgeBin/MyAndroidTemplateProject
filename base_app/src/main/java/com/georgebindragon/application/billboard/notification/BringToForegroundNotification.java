package com.georgebindragon.application.billboard.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.georgebindragon.application.billboard.notification.bean.ForegroundNotificationBean;
import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.R;
import com.georgebindragon.base.app.application.ActivitiesManager;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.system.software.ActivityUtil;
import com.georgebindragon.base.system.software.AppUtil;
import com.georgebindragon.base.system.software.ResourcesUtil;
import com.georgebindragon.base.system.software.WindowsUtil;
import com.georgebindragon.base.utils.EmptyUtil;
import com.jakewharton.rxrelay2.BehaviorRelay;

import androidx.annotation.RequiresPermission;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import io.reactivex.Observable;

/**
 * author：George
 *
 * description：
 * ForegroundNotification：notification、id、click action = bring app to foreground
 * ForegroundNotificationBean: switch、notification
 *
 * modification：
 */


public class BringToForegroundNotification extends BaseBroadcastNotification
{
	private static final String Broadcast_BringToForegroundNotification = "ForegroundNotification_" + AppUtil.getPackageName();
	public static final  int    ForegroundNotificationID                = 0x009;
	private static final String ForegroundNotificationChannelID         = "channelId_base_Foreground";

	private static final BringToForegroundNotification ourInstance = new BringToForegroundNotification();

	public static BringToForegroundNotification getInstance() { return ourInstance; }

	private BringToForegroundNotification() { super(); } // listen to broadcast


	public void init()
	{
		LogProxy.i(TAG, "init");
		createForegroundNotificationChannel(BaseUtils.getContext(), ForegroundNotificationChannelID, "前台服务", "描述");
		Notification notification = makeForegroundNotification("通知标题", "通知内容");
		update(true, notification);
	}

	/* ---------------------------------------- broadcast process ---------------------------------------- */

	@Override
	protected String getClickBroadcastString() { return Broadcast_BringToForegroundNotification; }

	@Override
	protected void onBroadcastReceive(Context context, Intent intent)
	{
		boolean result = bringMyselfBackToForeground(context);
		LogProxy.i(TAG, "onBroadcastReceive-->bringMyselfBackToForeground result=" + result);
	}

	@RequiresPermission(android.Manifest.permission.REORDER_TASKS)
	public boolean bringMyselfBackToForeground(Context context)
	{
		boolean bring = false;

		Activity currentActivity = ActivitiesManager.getInstance().getCurrentActivity();
		if (null != currentActivity)
		{
			int taskId = currentActivity.getTaskId();
			bring = WindowsUtil.moveAppToForeground(context, taskId);
		}

		if (!bring)
		{
			ActivityUtil.launchApp(context, context.getPackageName());
		}
		return bring;
	}

	/* ---------------------------------------- Foreground Notification ---------------------------------------- */

	private ForegroundNotificationBean emptyBean = new ForegroundNotificationBean(false, null);

	private BehaviorRelay<ForegroundNotificationBean> foregroundNotificationObservable = BehaviorRelay.createDefault(emptyBean);

	public Observable<ForegroundNotificationBean> getForegroundNotificationObservable() { return foregroundNotificationObservable; }

	private void setForegroundNotification(ForegroundNotificationBean currentNotification)
	{
		if (null != currentNotification) foregroundNotificationObservable.accept(currentNotification);
	}

	/* ---------------------------------------- api ---------------------------------------- */

	public static boolean isFunctionAvailable()
	{
		return true; // is this function available for current app
	}

	public int getID() { return ForegroundNotificationID; }

	boolean isOn;

	public boolean isOn() { return isFunctionAvailable() && isOn; }

	public void setOn(boolean on) { isOn = on; }

	public void update(boolean isOn, Notification notification)
	{
		setOn(isOn);
		ForegroundNotificationBean bean = new ForegroundNotificationBean(this.isOn, notification);
		setForegroundNotification(bean);
	}

	/* ---------------------------------------- inside ---------------------------------------- */

	// TODO：待转移
	public static String createForegroundNotificationChannel(Context context, String channelId, CharSequence channelName, String channelDescription)
	{
		if (null == context) context = BaseUtils.getContext();

		// NotificationChannels are required for Notifications on O (API 26) and above.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
		{
			// Initializes NotificationChannel.
			NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW);
			notificationChannel.setDescription(channelDescription);
			notificationChannel.enableVibration(false);
			notificationChannel.enableLights(false);
			notificationChannel.setShowBadge(false);
			notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);

			// Adds NotificationChannel to system. Attempting to create an existing notification
			// channel with its original values performs no operation, so it's safe to perform the
			// below sequence.
			NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
			notificationManager.createNotificationChannel(notificationChannel);
			return channelId;
		} else
		{
			return null;// Returns null for pre-O (26) devices.
		}
	}


	public Notification makeForegroundNotification(CharSequence title, CharSequence content)
	{
		LogProxy.i(TAG, "makeForegroundNotification-->title=" + title.toString(), "content=" + content.toString());

		NotificationCompat.Builder builder = new NotificationCompat.Builder(BaseUtils.getContext(), ForegroundNotificationChannelID);
		builder
				.setSmallIcon(R.drawable.qmui_icon_notify_done)//设置小图标
				.setLargeIcon(ResourcesUtil.getBitmapFromRes(R.drawable.qmui_icon_notify_done))

				.setContentTitle(EmptyUtil.isEmpty(title) ? "" : title)//设置通知标题
				.setContentText(EmptyUtil.isEmpty(content) ? "" : content)//设置通知内容
				.setShowWhen(false)//不显示 时间
				.setContentIntent(getBroadcastPendingIntent())//点击事件

				//避免被折叠
				.setGroupSummary(true)
				// .setGroup(channelId_Foreground+"group")

				.setOngoing(true);

		Notification notification = builder.build();
		if (null != notification)
		{
			notification.flags |= Notification.FLAG_NO_CLEAR;
			notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		}
		return notification;
	}

}

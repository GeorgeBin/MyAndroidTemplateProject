package com.georgebindragon.application.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;

import com.georgebindragon.application.billboard.notification.BringToForegroundNotification;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.rx.utils.RxCommonUtil;
import com.georgebindragon.base.service.BaseService;
import com.georgebindragon.base.utils.EmptyUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * author：George
 *
 * description：
 * foregroundServiceType="location"
 * action：
 * 1. BringToForegroundNotification
 *
 * modification：
 */


public class CommonToolService extends BaseService
{
	@Override
	protected int getServiceMode(Intent intent, int flags, int startId) { return Service.START_NOT_STICKY; }

	@Override
	public void onTaskRemoved(Intent rootIntent)
	{
		super.onTaskRemoved(rootIntent);
		// 处理，作为生命周期回调
	}

	@Override
	protected void initDataOnCreate()
	{
		super.initDataOnCreate();

		listenToForegroundNotification(); // 前台通知
	}

	private Disposable foregroundNotificationDisposable;

	private void listenToForegroundNotification()
	{
		boolean functionAvailable = BringToForegroundNotification.isFunctionAvailable();
		if (functionAvailable)
		{
			BringToForegroundNotification.getInstance().init();
			RxCommonUtil.dispose(foregroundNotificationDisposable);
			foregroundNotificationDisposable = BringToForegroundNotification.getInstance().getForegroundNotificationObservable()
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(notificationBean ->
					{
						LogProxy.i(TAG, "ForegroundNotification-->accept-->");
						if (notificationBean.isOn())
						{
							Notification notification = notificationBean.getNotification();
							if (EmptyUtil.notEmpty(notification)) startForeground(BringToForegroundNotification.getInstance().getID(), notification);
						} else
						{
							stopForeground(true);
						}
					});
			addDisposable(foregroundNotificationDisposable);
		}
	}

}

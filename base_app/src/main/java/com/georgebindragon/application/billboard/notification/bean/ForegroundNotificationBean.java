package com.georgebindragon.application.billboard.notification.bean;

import android.app.Notification;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改：
 */


public class ForegroundNotificationBean
{
	private boolean      isOn;
	private Notification notification;

	public ForegroundNotificationBean(boolean isOn, Notification notification)
	{
		this.isOn = isOn;
		this.notification = notification;
	}

	public ForegroundNotificationBean(Notification notification)
	{
		this.isOn = true;
		this.notification = notification;
	}

	public boolean isOn() { return isOn; }

	public Notification getNotification() { return notification; }
}

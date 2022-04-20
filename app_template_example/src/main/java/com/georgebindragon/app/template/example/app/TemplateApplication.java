package com.georgebindragon.app.template.example.app;

import android.app.Application;

import com.georgebindragon.app.template.example.BuildConfig;
import com.georgebindragon.app.template.example.publish.ChannelManager;
import com.georgebindragon.base.app.application.BaseApplication;
import com.georgebindragon.base.app.lifecycle.IAppLifeCycle;
import com.georgebindragon.lib.third.umeng.UMeng;
import com.meituan.android.walle.WalleChannelReader;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改：
 */


public class TemplateApplication extends BaseApplication
{
	private static final String Key_UMeng = "623c31cc0059ce2bad02afd8";

	@Override
	protected boolean isLogEnable() { return BuildConfig.DEBUG; }

	@Override
	protected IAppLifeCycle getAppLifeCycleImp()
	{
		return null;
	}

	@Override
	protected void initBaseInMainProcess(Application application)
	{
		ChannelManager.getInstance().init(application);

		UMeng.setLogEnabled(isLogEnable());
		UMeng.preInit(this, Key_UMeng, ChannelManager.getInstance().getChannel());
	}

	@Override
	protected void initInMultiProcess(Application application)
	{

	}

	@Override
	protected void initInMainProcess(Application application)
	{
		UMeng.init(application, Key_UMeng, WalleChannelReader.getChannel(application));
		// startService(new Intent(this, CommonToolService.class));
	}
}

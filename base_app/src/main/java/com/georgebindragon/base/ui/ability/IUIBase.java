package com.georgebindragon.base.ui.ability;

import android.app.Activity;
import android.content.Intent;

import io.reactivex.disposables.Disposable;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public interface IUIBase
{
	void beforeContentView();

	void initBase();

	void initView();

	void initData();

	void addDispose(Disposable disposable);

	void dispose(Disposable disposable);

	void jumpActivity(Class<? extends Activity> clasz, boolean needFinish);

	void jumpActivity(Intent intent, boolean needFinish);

	void jumpActivity(Class<? extends Activity> clasz, boolean needFinish, long delayMilliSeconds);

	void jumpActivity(Intent intent, boolean needFinish, long delayMilliSeconds);
}

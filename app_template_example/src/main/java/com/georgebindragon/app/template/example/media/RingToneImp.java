package com.georgebindragon.app.template.example.media;

import android.content.Context;
import android.net.Uri;

import com.georgebindragon.base.function.log.LogProxy;

/**
 * 创建人：George
 * 类名称：RingToneImp
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class RingToneImp extends BaseMediaPlayer
{
	private Uri uri;

	public RingToneImp(Context context, Uri uri, boolean loop, int streamType)
	{
		this.context = context;
		setLooping(loop);
		setStreamType(streamType);
		if (null != uri)
		{
			this.uri = uri;
			prepare();
		}
	}

	@Override
	public void setData()
	{
		try
		{
			mediaPlayer.setDataSource(context, uri);
		} catch (Exception e) { LogProxy.e(TAG, "setData", e); }
	}
}

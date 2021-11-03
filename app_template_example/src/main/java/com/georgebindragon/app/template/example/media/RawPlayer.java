package com.georgebindragon.app.template.example.media;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;

import com.georgebindragon.base.function.log.LogProxy;

import static android.content.Context.AUDIO_SERVICE;

/**
 * 创建人：George
 * 类名称：RawPlayer
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class RawPlayer extends BaseMediaPlayer
{
	private int rawId;

	public RawPlayer(Context context, int rawId, boolean loop, int streamType)
	{
		this.context = context;
		this.rawId = rawId;
		setLooping(loop);
		setStreamType(streamType);
		prepare();
	}

	@Override
	public void setData() { LogProxy.i(TAG, "setData-->"); }

	AudioManager audioManager;

	protected void prepare()
	{
		if (!preparing)
		{
			try
			{
				preparing = true;
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
				{
					AudioAttributes attributes = new AudioAttributes.Builder().setLegacyStreamType(streamType).build();
					if (null != context) audioManager = (AudioManager) context.getApplicationContext().getSystemService(AUDIO_SERVICE);
					int audioSessionId = audioManager.generateAudioSessionId();
					mediaPlayer = MediaPlayer.create(context, rawId, attributes, audioSessionId);
					LogProxy.i(TAG, "prepare-->1");
				} else
				{
					mediaPlayer = MediaPlayer.create(context, rawId);
					setPlayerStreamType(streamType);
					LogProxy.i(TAG, "prepare-->2");
				}
				mediaPlayer.setLooping(loop);
				mediaPlayer.setOnPreparedListener(mp ->
				{
					LogProxy.i(TAG, "onPreparedListener-->");
					prepared = true;
					preparing = false;
					if (needPlay) play();
				});
			} catch (Exception e) { LogProxy.e(TAG, "prepare", e); }
		}
	}

}

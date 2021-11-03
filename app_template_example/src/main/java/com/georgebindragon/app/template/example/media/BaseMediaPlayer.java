package com.georgebindragon.app.template.example.media;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;

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


public abstract class BaseMediaPlayer
{
	protected String TAG = "" + getClass().getSimpleName() + "-->";

	protected MediaPlayer mediaPlayer;
	protected Context     context;

	protected int     streamType;
	protected boolean loop;

	public void setLooping(boolean loop) { this.loop = loop; }

	public void setStreamType(int streamType) { this.streamType = streamType; }

	protected abstract void setData();

	protected boolean prepared  = false;
	protected boolean preparing = false;
	protected boolean needPlay  = false;

	protected MediaPlayer initPlayer()
	{
		if (null == mediaPlayer)
		{
			mediaPlayer = new MediaPlayer();
		} else
		{
			try
			{
				mediaPlayer.stop();
				mediaPlayer.reset();
			} catch (Exception e)
			{
				LogProxy.e(TAG, "initPlayer", e);
				mediaPlayer = new MediaPlayer();
			}
		}
		return mediaPlayer;
	}

	protected void prepare()
	{
		if (!preparing)
		{
			try
			{
				preparing = true;
				initPlayer();
				setData();
				setPlayerStreamType(streamType);
				mediaPlayer.setLooping(loop);
				// mediaPlayer.prepare(); // might take long! (for buffering, etc)
				mediaPlayer.prepareAsync(); // might take long! (for buffering, etc)
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


	protected void setPlayerStreamType(int streamType)
	{
		//1. 设置播放通道
		try
		{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			{
				AudioAttributes attributes = new AudioAttributes.Builder().setLegacyStreamType(streamType).build();
				try
				{
					if (null != mediaPlayer) mediaPlayer.setAudioAttributes(attributes);
				} catch (Exception e) { LogProxy.e(TAG, "setPlayerStreamType", e); }
			} else
			{
				mediaPlayer.setAudioStreamType(streamType);//Android 4.x 不需要手动 设置或切换 播放通道, 否则会没有声音出来
			}
		} catch (Exception e) {LogProxy.e(TAG, "setPlayerStreamType", e);}
	}

	public boolean isPlaying()
	{
		if (null != mediaPlayer) return mediaPlayer.isPlaying();
		return false;
	}

	public void play()
	{
		try
		{
			needPlay = true;
			if (!prepared) prepare();
			if (prepared && !mediaPlayer.isPlaying())
			{
				mediaPlayer.seekTo(0);
				mediaPlayer.start();
			}
		} catch (Exception e) { LogProxy.e(TAG, "play", e); }
	}

	/**
	 * 停止铃声
	 */
	public void stop()
	{
		try
		{
			needPlay = false;
			if (mediaPlayer.isPlaying())
			{
				// mediaPlayer.stop();
				mediaPlayer.pause();
			}
		} catch (Exception e) { LogProxy.e(TAG, "stop", e); }
	}

	/**
	 * 回收资源
	 */
	public void release()
	{
		try
		{
			needPlay = false;
			mediaPlayer.release();
			mediaPlayer = null;
			prepared = false;
		} catch (Exception e) { LogProxy.e(TAG, "release", e); }
	}
}

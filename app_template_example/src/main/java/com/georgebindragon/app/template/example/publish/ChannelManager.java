package com.georgebindragon.app.template.example.publish;

import android.content.Context;

import com.georgebindragon.app.template.example.app.constants.Constants_Channel;
import com.georgebindragon.base.BaseUtils;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;
import com.meituan.android.walle.WalleChannelReader;

import androidx.annotation.NonNull;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改：
 */


public class ChannelManager
{
	public static final String TAG = "ChannelManager-->";

	private static ChannelManager ourInstance = new ChannelManager();

	public static ChannelManager getInstance() { return ourInstance; }

	private ChannelManager() { }

	private String channel = "";//默认

	public void init(Context context)
	{
		init(context, Constants_Channel.DefaultChannel);
	}

	public void init(Context context, @NonNull String defaultChannel)
	{
		channel = defaultChannel;
		String channelByWalle = WalleChannelReader.getChannel(context.getApplicationContext());
		LogProxy.d(TAG, "init-->get channel from apk file by walle. channel=" + channelByWalle);
		if (EmptyUtil.notEmpty(channelByWalle)) channel = channelByWalle;
		LogProxy.d(TAG, "init-->real channel=" + channel);
	}

	public String getChannel(@NonNull String defaultChannel)
	{
		if (EmptyUtil.isEmpty(channel)) init(BaseUtils.getContext(), defaultChannel);
		return channel;
	}

	public String getChannel()
	{
		return getChannel(Constants_Channel.DefaultChannel);
	}
}
package com.georgebindragon.lib.serialport.example;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.lib.serialport.rx.RxMessageCodec;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.netty.util.ByteProcessor;

/**
 * author：
 *
 * description：
 * action：
 *
 * modification：
 */


class ExampleMessageCodec extends RxMessageCodec<ExampleStringBean>
{
	private static final String TAG = "ExampleMessageCodec-->";

	private static final ExampleMessageCodec ourInstance = new ExampleMessageCodec(1024, 100);// buffer capacity=1024, after 100ms process left

	protected static ExampleMessageCodec getInstance() { return ourInstance; }

	public ExampleMessageCodec(int capacity, int timeToWait)
	{
		super(capacity, timeToWait);
	}

	public static final byte EndByte = (byte) '\n'; // '\n' is line end

	ByteProcessor.IndexOfProcessor indexOfProcessor = new ByteProcessor.IndexOfProcessor(EndByte);

	@Override
	public ExampleStringBean onDataDecode(ByteBuf byteBuf)
	{
		int readableLength = byteBuf.readableBytes();
		LogProxy.d(TAG, "onDataDecode-->readableLength=" + readableLength);

		if (readableLength > 1)
		{
			int nextIndex = byteBuf.forEachByte(1, readableLength - 1, indexOfProcessor);
			LogProxy.d(TAG, "onDataDecode-->nextIndex=" + nextIndex);

			if (nextIndex > 0)
			{
				byte[] result = new byte[nextIndex];
				byteBuf.readBytes(result);
				byteBuf.skipBytes(1);// don't need '\n'
				byteBuf.discardReadBytes();

				String message = new String(result, StandardCharsets.UTF_8);
				LogProxy.d(TAG, "onDataDecode-->result byte[] length=" + result.length, "message=" + message);
				return new ExampleStringBean(message);
			} else
			{
				LogProxy.d(TAG, "onDataDecode-->not find end");
			}
		} else
		{
			LogProxy.d(TAG, "onDataDecode-->no more buffer data");
		}
		return null;
	}

	@Override
	public void onTimeOut(boolean hasDataLeft)
	{
		synchronized (lock2)
		{
			LogProxy.d(TAG, "onTimeOut-->hasDataLeft=" + hasDataLeft);
			int readableLength = byteBuf.readableBytes();
			LogProxy.d(TAG, "onTimeOut-->readableLength=" + readableLength);
			if (readableLength > 0)
			{
				byte[] result = new byte[readableLength];
				byteBuf.readBytes(result);
				byteBuf.discardReadBytes();

				String message = new String(result, StandardCharsets.UTF_8);
				LogProxy.d(TAG, "onTimeOut-->message=" + message);
				if (EmptyUtil.notEmpty(message))
				{
					onNewBean(new ExampleStringBean(message));
				}
			} else
			{
				LogProxy.d(TAG, "onTimeOut-->no more data");
			}
		}
	}
}

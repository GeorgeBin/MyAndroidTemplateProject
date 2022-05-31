package com.georgebindragon.lib.serialport.rx;

import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.rx.utils.RxCommonUtil;
import com.jakewharton.rxrelay2.PublishRelay;

import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：
 *
 * description：
 * action：
 * <B> bean的抽象类
 *
 * modification：
 */


public abstract class RxMessageCodec<B>
{
	protected String TAG = "Codec：" + getClass().getSimpleName() + "-->";

	protected       Disposable      serialPortDisposable;
	protected final PublishRelay<B> message = PublishRelay.create();
	protected final ByteBuf         byteBuf;
	protected final int             TimeToWait; // time for time out

	public RxMessageCodec(int capacity, int timeToWait)
	{
		byteBuf = Unpooled.directBuffer(capacity);
		TimeToWait = timeToWait;
	}

	/* ---------------------------------------- api ---------------------------------------- */

	public boolean start()
	{
		RxCommonUtil.dispose(serialPortDisposable);
		Observable<byte[]> observable = RxSerialPort.getInstance().startListen();
		if (null != observable)
		{
			serialPortDisposable = observable
					.subscribeOn(Schedulers.io())
					.observeOn(Schedulers.computation())
					.map(this::onDataProcess)
					.debounce(TimeToWait, TimeUnit.MILLISECONDS)
					.subscribe(this::onTimeOut);
			return true;
		} else
		{
			LogProxy.e(TAG, "start", "serial port open failed!!");
			return false;
		}
	}

	public Observable<B> getBeanObservable() { return message; }

	public void stop()
	{
		LogProxy.i(TAG, "stop-->");
		RxSerialPort.getInstance().stopListen();
		byteBuf.release();
	}

	/* ---------------------------------------- inside ---------------------------------------- */

	protected final Object lock2 = new Object();

	public boolean onDataProcess(byte[] incoming)
	{
		LogProxy.d(TAG, "onDataProcess-->begin");

		synchronized (lock2)
		{
			try
			{
				if (null == incoming || incoming.length == 0) return false;
				byteBuf.writeBytes(incoming);
				B bean;
				do
				{
					LogProxy.d(TAG, "onDataProcess-->while");
					bean = onDataDecode(byteBuf);
					onNewBean(bean);
				} while (null != bean);

				return byteBuf.readableBytes() > 0;
			} catch (Exception e)
			{
				LogProxy.e(TAG, "onDataProcess", e);
				byteBuf.release();
			}
		}
		LogProxy.d(TAG, "onDataProcess-->end");
		return false;
	}

	public void onNewBean(B bean) { if (null != bean) message.accept(bean); }

	/* ---------------------------------------- abstract ---------------------------------------- */

	/**
	 * decode message
	 *
	 * @param byteBuf byteBuf all buffer
	 * @return has a full message
	 */
	public abstract B onDataDecode(ByteBuf byteBuf);

	/**
	 * after timeout
	 *
	 * @param hasDataLeft has any data left
	 */
	public abstract void onTimeOut(boolean hasDataLeft);
}
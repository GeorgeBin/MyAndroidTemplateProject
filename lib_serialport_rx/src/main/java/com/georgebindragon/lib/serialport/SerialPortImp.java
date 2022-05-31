package com.georgebindragon.lib.serialport;

import android.content.Context;

import com.georgebindragon.base.data.trans.DigitalTransUtil;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.utils.EmptyUtil;
import com.georgebindragon.lib.serialport.rx.RxMessageCodec;
import com.georgebindragon.lib.serialport.rx.RxSerialPort;

import io.reactivex.Observable;

/**
 * author：
 *
 * description：
 * action：
 *
 * modification：
 */


public abstract class SerialPortImp<B> implements ISerialPort<B>
{
	protected String TAG = "SerialPort:" + getClass().getSimpleName() + "-->";

	private final String path;
	private final int    baudRate;

	public SerialPortImp(String path, int baudRate)
	{
		this.path = path;
		this.baudRate = baudRate;
	}

	@Override
	public boolean init(Context context)
	{
		RxSerialPort.getInstance().init();
		return SerialPortManager.getInstance().init(context, path, baudRate);
	}

	@Override
	public boolean start() { return getCodec().start(); }

	@Override
	public void stop() { getCodec().stop(); }

	@Override
	public Observable<B> getBeanObservable() { return getCodec().getBeanObservable(); }

	@Override
	public void send(byte[] data)
	{
		if (EmptyUtil.notEmpty(data))
		{
			RxSerialPort.getInstance().write(data);
			LogProxy.i(TAG, "send-->data.length=" + data.length, "Hex=" + DigitalTransUtil.byte2hex(data));
		} else
		{
			LogProxy.i(TAG, "send-->data=null");
		}
	}

	protected abstract RxMessageCodec<B> getCodec();
}
package com.georgebindragon.lib.serialport.example;

import com.georgebindragon.lib.serialport.SerialPortImp;
import com.georgebindragon.lib.serialport.rx.RxMessageCodec;

/**
 * author：
 *
 * description：
 * action：
 *
 * modification：
 */


class ExampleSerialPortSDK extends SerialPortImp<ExampleStringBean>
{
	private static final ExampleSerialPortSDK ourInstance = new ExampleSerialPortSDK("", 0);

	static ExampleSerialPortSDK getInstance() { return ourInstance; }

	public ExampleSerialPortSDK(String path, int baudRate)
	{
		super(path, baudRate);
	}

	@Override
	protected RxMessageCodec<ExampleStringBean> getCodec() { return ExampleMessageCodec.getInstance(); }
}

package com.georgebindragon.lib.serialport;

import android.content.Context;

import io.reactivex.Observable;

/**
 * author：
 *
 * description：
 * action：
 *
 * modification：
 */


interface ISerialPort<B>
{
	boolean init(Context context);

	boolean start();

	void stop();

	Observable<B> getBeanObservable();

	void send(byte[] data);
}

package com.georgebindragon.lib.serialport.rx;

/**
 * author：
 *
 * description：
 * action：
 *
 * modification：
 */


import android.serialport.SerialPort;

import com.georgebindragon.base.data.basic.BytesUtil;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.base.rx.utils.RxCommonUtil;
import com.georgebindragon.base.utils.StringUtil;
import com.georgebindragon.lib.serialport.SerialPortManager;
import com.jakewharton.rxrelay2.PublishRelay;

import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author：
 *
 * description：
 * action：
 *
 * modification：
 */


public class RxSerialPort
{
    private static final String TAG = "RxSerialPort-->";

    private static final int maxReadSize = 1024;

    private static final RxSerialPort ourInstance = new RxSerialPort();

    public static RxSerialPort getInstance() { return ourInstance; }

    private RxSerialPort() { }

    private boolean            isListening = true;
    private Disposable         sendDisposable;
    private Observable<byte[]> serialPortObservable;

    public boolean init()
    {
        LogProxy.i(TAG, "init-->");
        RxCommonUtil.dispose(sendDisposable);
        sendDisposable = send
                .observeOn(Schedulers.io())
                .subscribe(bytes ->
                {
                    boolean write = SerialPortManager.getInstance().write(bytes);
                    LogProxy.i(TAG, "accept-->发送：" + StringUtil.getSuccessString(write));
                });
        return true;
    }

    public Observable<byte[]> startListen()
    {
        LogProxy.i(TAG, "startListen-->");
        if (null == serialPortObservable)
        {
            boolean open = SerialPortManager.getInstance().open();
            if (open)
            {
                SerialPort        serialPort  = SerialPortManager.getInstance().getSerialPort();
                final InputStream inputStream = serialPort.getInputStream();
                serialPortObservable = Observable.create((ObservableOnSubscribe<byte[]>) emitter ->
                {
                    while (isListening)
                    {
                        try
                        {
                            byte[] buffer = new byte[maxReadSize];
                            int    size   = inputStream.read(buffer);
                            LogProxy.i(TAG, "subscribe-->size=" + size);

                            if (size > 0)
                            {
                                byte[] bytes = BytesUtil.subBytes(buffer, 0, size);
                                emitter.onNext(bytes);
                            }
                        } catch (Exception e)
                        {
                            emitter.onError(e);
                            e.printStackTrace();
                            LogProxy.e(TAG, "subscribe", e);
                            return;
                        }
                    }
                })
                // .subscribeOn(Schedulers.io())
                ;
            } else
            {
                LogProxy.e(TAG, "statListen", "inputStream == null");
            }
        }
        return serialPortObservable;
    }

    private final PublishRelay<byte[]> send = PublishRelay.create();

    public void write(byte[] data)
    {
        LogProxy.i(TAG, "write-->data=" + (data == null ? "null" : data.length));
        if (null != data) send.accept(data);
    }

    public void stopListen()
    {
        LogProxy.i(TAG, "stopListen-->");
        isListening = false;
        serialPortObservable = null;
        SerialPortManager.getInstance().close();
    }
}
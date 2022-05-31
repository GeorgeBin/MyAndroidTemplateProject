package com.georgebindragon.lib.serialport;

import android.content.Context;
import android.os.SystemClock;
import android.serialport.SerialPort;
import android.serialport.SerialPortFinder;
import android.util.Log;

import java.io.OutputStream;

/**
 * author：
 *
 * description：
 * action：
 *
 * modification：
 */


public class SerialPortManager
{
	private static final String TAG = "SerialPortManager-->";

	private String path = "";
	private int    baudRate;

	private static final SerialPortManager ourInstance = new SerialPortManager();

	public static SerialPortManager getInstance() { return ourInstance; }

	private SerialPortManager() { }

	private SerialPort mSerialPort = null;

	public SerialPort getSerialPort() { return mSerialPort; }

	private SerialPortFinder mSerialPortFinder = new SerialPortFinder();

	public boolean init(Context context, String path, int baudRate)
	{
		if (null != path && path.length() > 0)
		{
			this.path = path;
			this.baudRate = baudRate;

			String[] allDevicesPath = mSerialPortFinder.getAllDevicesPath();

			for (String devicePath : allDevicesPath)
			{
				Log.i(TAG, "getAllDevicesPath-->devicePath=" + devicePath);
				if (devicePath.equalsIgnoreCase(path)) return true;
			}
		}
		return false;
	}

	public boolean open()
	{
		if (mSerialPort == null)
		{
			try
			{
				mSerialPort = SerialPort //
						.newBuilder(path, baudRate) // 串口地址地址，波特率
						// 默认8N1(数据位=8，校验位=NONE，停止位=1)-->这里使用默认设置就可以
						// .dataBits(7) // 数据位,默认8；可选值为5~8
						// .parity(2) // 校验位；0:无校验位(NONE，默认)；1:奇校验位(ODD);2:偶校验位(EVEN)
						// .stopBits(2) // 停止位，默认1；1:1位停止位；2:2位停止位
						.build();
				return true;
			} catch (Exception e)
			{
				Log.e(TAG, "init", e);
				mSerialPort = null;
				return false;
			}
		}
		return true;
	}

	public boolean write(byte[] data)
	{
		Log.i(TAG, "write-->data=" + (null != data ? data.length : "NULL"));
		if (mSerialPort != null && null != data)
		{
			OutputStream outputStream = mSerialPort.getOutputStream();
			try
			{
				outputStream.write(data);
				SystemClock.sleep(50);// add some sleep, avoid sticky message
				return true;
			} catch (Exception e)
			{
				Log.e(TAG, "write", e);
				return false;
			}
		}
		return false;
	}

	public void close()
	{
		if (mSerialPort != null)
		{
			mSerialPort.close();
			mSerialPort = null;
		}
		if (mSerialPortFinder != null) mSerialPortFinder = null;
	}
}
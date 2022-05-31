package com.georgebindragon.lib.serialport.example;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
	byte[] test_data =
			{
					109, 101, 115, 115, 97, 103, 101, 48, 10,// message0 \n
					109, 101, 115, 115, 97, 103, 101, 49, 10,// message1 \n
					109, 101, 115, 115, 97, 103, 101, 50, 10,// message2 \n
					109, 101, 115, 115, 97, 103, 101, 51, 10,// message3 \n
					109, 101, 115, 115, 97, 103, 101, 52, 10,// message4 \n
			};

	@Test
	public void addition_isCorrect()
	{
		ExampleMessageCodec.getInstance().onDataProcess(test_data);
	}
}
package com.georgebindragon.app.template.example;

import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.georgebindragon.lib.locate.baidu.BaiduLocationFunction;
import com.georgebindragon.lib.locate.baidu.BaseBaiduMapLocationListener;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		BaiduLocationFunction.getInstance().init(this);
		LocationClientOption locationClientOption = BaiduLocationFunction.getInstance().makeDefaultClientOption(10);
		BaiduLocationFunction.getInstance().setOption(locationClientOption);
		BaiduLocationFunction.getInstance().registerLocationListener(new BaseBaiduMapLocationListener()
		{
			@Override
			public void onBDLocation(BDLocation bdLocation)
			{
				super.onBDLocation(bdLocation);
			}
		});


		BaiduLocationFunction.getInstance().start();
	}
}

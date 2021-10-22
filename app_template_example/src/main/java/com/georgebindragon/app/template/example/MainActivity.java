package com.georgebindragon.app.template.example;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.georgebindragon.lib.locate.baidu.BaiduLocationFunction;
import com.georgebindragon.lib.locate.baidu.BaseBaiduMapLocationListener;

import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
	private static final String TAG = "MainActivity-->";

	TextView test_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		test_tv = findViewById(R.id.ui_main_test_tv);

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
		test();
	}

	private void test()
	{
		ApplicationInfo applicationInfo;
		try
		{
			applicationInfo = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
			Bundle extras = applicationInfo.metaData;
			if (null != extras)
			{
				Object value     = extras.get("app.build.info.buildName");
				String buildName = String.valueOf(value);
				test_tv.setText("buildName=" + buildName);
			}
		} catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
	}


	public static void getBundleDetail(String tag, Bundle extras)
	{
		try
		{
			if (null != extras)
			{
				Set<String> keySet = extras.keySet();  //获取所有的Key
				if (null != keySet)
				{
					for (String key : keySet)//bundle.get(key);来获取对应的value
					{
						Object value = extras.get(key);
						String keyInfo =
								"key" + (null == key ? "" : "(" + key.getClass().getSimpleName() + ")") + "=" + key;
						String valueInfo =
								"value" + (null == value ? "" : "(" + value.getClass().getSimpleName() + ")") + "=" + value;

						System.out.println(tag + "getBroadcastIntentDetail-->extras：Bundle：" + keyInfo + "\t\t" + valueInfo);
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}

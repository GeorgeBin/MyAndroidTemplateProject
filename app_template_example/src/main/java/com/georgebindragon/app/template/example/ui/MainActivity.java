package com.georgebindragon.app.template.example.ui;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.georgebindragon.app.template.example.R;
import com.georgebindragon.base.function.log.LogProxy;
import com.georgebindragon.lib.locate.baidu.BaiduLocationFunction;
import com.georgebindragon.lib.locate.baidu.BaseBaiduMapLocationListener;

import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
	private static final String TAG = "MainActivity-->";

	TextView test_tv;
	Button   media_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		SplashScreen.installSplashScreen(this);

		setContentView(R.layout.activity_main);
		test_tv = findViewById(R.id.ui_main_test_tv);
		media_btn = findViewById(R.id.ui_main_media_btn);
		media_btn.setOnClickListener(this);

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
//				Object value     = extras.get("app.build.temp.buildName");
//				String buildName = String.valueOf(value);
//				test_tv.setText("buildName=" + buildName);
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

	@Override
	public void onClick(View v)
	{
		int id = v.getId();
		switch (id)
		{
			default:
				LogProxy.i(TAG, "onClick-->id=" + id);
				break;
			case R.id.ui_main_media_btn:
				Intent intent = new Intent(this, MediaActivity.class);
				//				ActivityUtil.jumpActivity(this, intent);
				startActivity(intent);
				break;
		}
	}

}

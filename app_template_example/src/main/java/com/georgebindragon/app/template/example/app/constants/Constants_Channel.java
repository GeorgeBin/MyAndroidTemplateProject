package com.georgebindragon.app.template.example.app.constants;

import com.georgebindragon.app.template.example.R;
import com.georgebindragon.base.system.software.ResourcesUtil;

/**
 * 创建人：George
 *
 * 描述：渠道：根据flavor
 *
 * 修改：
 */


public class Constants_Channel
{
	private static final String Channel_xxx      = "xxx"; //渠道：xxx
	private static final String Channel_Template = "Android_Major_Brand_Normal_Third";

	public static String DefaultChannel = ResourcesUtil.getStringFromRes(R.string.custom_default_channel, Channel_Template);

}

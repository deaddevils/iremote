package com.iremote.infraredtrans.zwavecommand;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;

public class HeatingControllerTemperatureProcessor extends AirCondtionerTemperatureProcessor
{

	@Override
	protected void setDeviceTemperature(float c, float f)
	{
		String s = zrb.getDevice().getStatuses();
		if ( s == null )
			s = Utils.getDeviceDefaultStatuses(IRemoteConstantDefine.DEVICE_TYPE_HEATING_CONTROLLER);
		JSONArray ja = JSON.parseArray(s);
		ja.set(0 , c);
		ja.set(1 , f);
		zrb.getDevice().setStatuses(ja.toJSONString());
		zrb.getDevice().setStatus(0);
		if(c > 18f){
			zrb.getDevice().setStatus(255);
		}
	}

}

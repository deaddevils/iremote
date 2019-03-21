package com.iremote.infraredtrans.zwavecommand;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;

public class AirCondtionerfanProcessor extends ZWaveReportBaseProcessor{

	public AirCondtionerfanProcessor() {
		super();
		super.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus() 
	{
		int status = zrb.getCommandvalue().getValue() ;

		String s = zrb.getDevice().getStatuses();
		if ( s == null )
			s = Utils.getDeviceDefaultStatuses(IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER);
		JSONArray ja = JSON.parseArray(s);
		ja.set(1 , status);
		zrb.getDevice().setStatuses(ja.toJSONString());
		
	}
	
	@Override
	public String getMessagetype() {
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}
}

package com.iremote.infraredtrans.zwavecommand;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;

public class AirConditionerProcessor extends ZWaveReportBaseProcessor{

	public AirConditionerProcessor() {
		super();
		super.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus() 
	{
		int status = zrb.getCommandvalue().getValue() ;
		if ( status == 0 )
			this.zrb.getDevice().setStatus(0);
		else 
			this.zrb.getDevice().setStatus(1);  // air conditioner on .
		
		if ( status != 0 && status != 5) 
		{
			String s = zrb.getDevice().getStatuses();
			if ( s == null )
				s =  Utils.getDeviceDefaultStatuses(zrb.getDevice().getDevicetype());
			JSONArray ja = JSON.parseArray(s);
			ja.set(0 , status);
			zrb.getDevice().setStatuses(ja.toJSONString());
		}
	}

	@Override
	public String getMessagetype() {
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}
}

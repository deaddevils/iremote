package com.iremote.common.jms.vo;

import java.util.Date;

import com.iremote.common.IRemoteConstantDefine;

public class ZWaveDeviceBatteryEvent extends ZWaveDeviceEvent {


	public ZWaveDeviceBatteryEvent() {
		super();
	}

	public ZWaveDeviceBatteryEvent(int zwavedeviceid, String deviceid, int nuid, int battery ,Date eventtime , long taskid) 
	{
		super(zwavedeviceid, deviceid, nuid,IRemoteConstantDefine.WARNING_TYPE_BATTERY, eventtime, taskid);
	}


	
	
	
}

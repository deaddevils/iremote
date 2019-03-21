package com.iremote.action.device;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;

public class QueryDeviceAction {

	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String deviceid;
	private int nuid ;
	private int zwavedeviceid ;
	private ZWaveDevice device;
	
	public String execute()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice zd = null;
		if ( zwavedeviceid != 0 )
			zd = zds.query(zwavedeviceid);
		else
			zd = zds.querybydeviceid(deviceid, nuid);
		
		if ( zd == null )
		{
			resultCode = ErrorCodeDefine.TARGET_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		device = new ZWaveDevice();
		device.setDeviceid(zd.getDeviceid());
		device.setNuid(zd.getNuid());
		device.setStatus(zd.getStatus());
		device.setFstatus(zd.getFstatus());
		device.setStatuses(zd.getStatuses());
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setNuid(int nuid) {
		this.nuid = nuid;
	}

	public ZWaveDevice getDevice() {
		return device;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}

	
	
}

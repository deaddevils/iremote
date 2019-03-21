package com.iremote.common.jms.vo;

import com.iremote.common.IRemoteConstantDefine;

import java.util.Date;

public class DeviceArmEvent extends ZWaveDeviceEvent {

	private int cameraid;
	private int armstatus;

	public DeviceArmEvent() {
	}

	private DeviceArmEvent(int phoneuserid,String deviceid,int armstatus ,int cameraid ,int zwavedeviceid){
		this.setPhoneuserid(phoneuserid);
		this.setDeviceid(deviceid);
		this.armstatus = armstatus;
		this.cameraid = cameraid;
		this.setZwavedeviceid(zwavedeviceid);
	}

	public int getCameraid() {
		return cameraid;
	}

	public void setCameraid(int cameraid) {
		this.cameraid = cameraid;
	}

	public int getArmstatus() {
		return armstatus;
	}

	public void setArmstatus(int armstatus) {
		this.armstatus = armstatus;
	}
}

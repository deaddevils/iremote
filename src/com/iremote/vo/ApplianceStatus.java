package com.iremote.vo;

import com.iremote.common.IRemoteConstantDefine;

import java.util.Date;
import java.util.List;

import com.iremote.domain.ZWaveSubDevice;

public class ApplianceStatus 
{
	private String deviceid;
	private String devicetype;
	private int nuid;
	private int zwavedeviceid;
	private Integer cameraid;
	private Integer status = 0 ;
	private String statuses;
	private Float fstatus;
	private Integer warningstatus;
	private String warningstatuses;
	private Integer battery;
	private String version3;
	private int enablestatus;
	private int armstatus = IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM;
	private String lastactivetime;

	private List<ZWaveSubDevice> subdevice;
	
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	public int getNuid() {
		return nuid;
	}
	public void setNuid(int nuid) {
		this.nuid = nuid;
	}
	public int getZwavedeviceid() {
		return zwavedeviceid;
	}
	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public Integer getCameraid() {
		return cameraid;
	}
	public void setCameraid(Integer cameraid) {
		this.cameraid = cameraid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatuses() {
		return statuses;
	}
	public void setStatuses(String statuses) {
		this.statuses = statuses;
	}
	public Float getFstatus() {
		return fstatus;
	}
	public void setFstatus(Float fstatus) {
		this.fstatus = fstatus;
	}

	public String getWarningstatuses() {
		return warningstatuses;
	}
	public void setWarningstatuses(String warningstatuses) {
		this.warningstatuses = warningstatuses;
	}
	public Integer getBattery() {
		return battery;
	}
	public void setBattery(Integer battery) {
		this.battery = battery;
	}
	public int getEnablestatus() {
		return enablestatus;
	}
	public void setEnablestatus(int enablestatus) {
		this.enablestatus = enablestatus;
	}
	public Integer getWarningstatus() {
		return warningstatus;
	}
	public void setWarningstatus(Integer warningstatus) {
		this.warningstatus = warningstatus;
	}
	public List<ZWaveSubDevice> getSubdevice() {
		return subdevice;
	}
	public void setSubdevice(List<ZWaveSubDevice> subdevice) {
		this.subdevice = subdevice;
	}

	public int getArmstatus() {
		return armstatus;
	}

	public void setArmstatus(int armstatus) {
		this.armstatus = armstatus;
	}

	public String getVersion3() {
		return version3;
	}

	public void setVersion3(String version3) {
		this.version3 = version3;
	}
	public String getLastactivetime() {
		return lastactivetime;
	}
	public void setLastactivetime(String lastactivetime) {
		this.lastactivetime = lastactivetime;
	}
	
}

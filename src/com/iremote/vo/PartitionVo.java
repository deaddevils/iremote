package com.iremote.vo;

import java.util.ArrayList;
import java.util.List;

public class PartitionVo 
{
	private int partitionid;
	private String phonenumber;
	private String name;
	private Integer dsczwavedeviceid;
	private Integer dscpartitionid;
	private int status ;
	private int armstatus;
	private int warningstatus;
	private Integer delay;
	
	private List<ZWaveSubDeviceVo> zwavedevices = new ArrayList<ZWaveSubDeviceVo>();

	public int getPartitionid() {
		return partitionid;
	}

	public void setPartitionid(int partitionid) {
		this.partitionid = partitionid;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDscpartitionid() {
		return dscpartitionid;
	}

	public void setDscpartitionid(Integer dscpartitionid) {
		this.dscpartitionid = dscpartitionid;
	}

	public int getArmstatus() {
		return armstatus;
	}

	public void setArmstatus(int armstatus) {
		this.armstatus = armstatus;
	}

	public int getWarningstatus() {
		return warningstatus;
	}

	public void setWarningstatus(int warningstatus) {
		this.warningstatus = warningstatus;
	}

	public List<ZWaveSubDeviceVo> getZwavedevices() {
		return zwavedevices;
	}

	public void setZwavedevices(List<ZWaveSubDeviceVo> zwavedevices) {
		this.zwavedevices = zwavedevices;
	}


	public Integer getDsczwavedeviceid() {
		return dsczwavedeviceid;
	}

	public void setDsczwavedeviceid(Integer dsczwavedeviceid) {
		this.dsczwavedeviceid = dsczwavedeviceid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}




}

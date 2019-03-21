package com.iremote.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name="devicecapability")
public class DeviceCapability {

	private int devicecapabilityid;
	private ZWaveDevice zwavedevice ;
	private Camera camera ;
	private InfraredDevice infrareddevice ;
	private int capabilitycode;
	private String capabilityvalue;

	public DeviceCapability()
	{
		super();
	}
	
	public DeviceCapability(ZWaveDevice zwavedevice, int capabilitycode)
	{
		super();
		this.zwavedevice = zwavedevice;
		this.capabilitycode = capabilitycode;
	}

	public DeviceCapability(ZWaveDevice zwavedevice, int capabilitycode, String capabilityvalue) {
		this.zwavedevice = zwavedevice;
		this.capabilitycode = capabilitycode;
		this.capabilityvalue = capabilityvalue;
	}

	public DeviceCapability(Camera camera, int capabilitycode) {
		super();
		this.camera = camera;
		this.capabilitycode = capabilitycode;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "generator", strategy = "increment")
	@Column(name = "devicecapabilityid")
	public int getDevicecapabilityid() {
		return devicecapabilityid;
	}
	public void setDevicecapabilityid(int devicecapabilityid) {
		this.devicecapabilityid = devicecapabilityid;
	}

	public int getCapabilitycode() {
		return capabilitycode;
	}
	public void setCapabilitycode(int capabilitycode) {
		this.capabilitycode = capabilitycode;
	}

	@JSON(serialize=false)
	@JSONField(serialize = false)
	@ManyToOne(targetEntity=ZWaveDevice.class,cascade={CascadeType.DETACH},fetch=FetchType.LAZY)           
	@JoinColumn(name="zwavedeviceid",referencedColumnName="zwavedeviceid",nullable=true)
	public ZWaveDevice getZwavedevice() {
		return zwavedevice;
	}
	public void setZwavedevice(ZWaveDevice zwavedevice) {
		this.zwavedevice = zwavedevice;
	}

	@JSON(serialize=false)
	@JSONField(serialize = false)
	@ManyToOne(targetEntity=Camera.class,cascade={CascadeType.DETACH},fetch=FetchType.LAZY)
	@JoinColumn(name="cameraid",referencedColumnName="cameraid",nullable=true)
	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	@JSON(serialize=false)
	@JSONField(serialize = false)
	@ManyToOne(targetEntity=InfraredDevice.class,cascade={CascadeType.DETACH},fetch=FetchType.LAZY)
	@JoinColumn(name="infrareddeviceid",referencedColumnName="infrareddeviceid",nullable=true)
	public InfraredDevice getInfrareddevice() {
		return infrareddevice;
	}

	public void setInfrareddevice(InfraredDevice infrareddevice) {
		this.infrareddevice = infrareddevice;
	}

	public String getCapabilityvalue() {
		return capabilityvalue;
	}

	public void setCapabilityvalue(String capabilityvalue) {
		this.capabilityvalue = capabilityvalue;
	}

	@Override
	public String toString() {
		return com.alibaba.fastjson.JSON.toJSONString(this);
	}
}

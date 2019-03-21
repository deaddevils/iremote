package com.iremote.domain;

import javax.persistence.*;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

@Entity
@Table(name="zwavesubdevice")
public class ZWaveSubDevice {
	private int zwavesubdeviceid;
	private int channelid;
	private String name; 
	private ZWaveDevice zwavedevice ;	
	private String subdevicetype;
	private Partition partition;
	private Integer status;
	private String warningstatuses;
	private int enablestatus;
	private String statuses;
	
	public ZWaveSubDevice(){
		
	}
	
	public ZWaveSubDevice(int channelid, String name, ZWaveDevice zwavedevice) {
		super();
		this.channelid = channelid;
		this.name = name;
		this.zwavedevice = zwavedevice;
	}

	public ZWaveSubDevice(int channelid, String name, ZWaveDevice zwavedevice, String statuses) {
		super();
		this.channelid = channelid;
		this.name = name;
		this.zwavedevice = zwavedevice;
		this.statuses = statuses;
	}

	public ZWaveSubDevice(int channelid, String name, ZWaveDevice zwavedevice, String subdevicetype,
			Partition partition) {
		super();
		this.channelid = channelid;
		this.name = name;
		this.zwavedevice = zwavedevice;
		this.subdevicetype = subdevicetype;
		this.partition = partition;
	}

	public ZWaveSubDevice(int channelid, String name, ZWaveDevice zwavedevice, String subdevicetype,
			Partition partition, Integer status, String warningstatuses, int enablestatus) {
		super();
		this.channelid = channelid;
		this.name = name;
		this.zwavedevice = zwavedevice;
		this.subdevicetype = subdevicetype;
		this.partition = partition;
		this.status = status;
		this.warningstatuses = warningstatuses;
		this.enablestatus = enablestatus;
	}

	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")  
	public int getZwavesubdeviceid() {
		return zwavesubdeviceid;
	}

	public void setZwavesubdeviceid(int zwavesubdeviceid) {
		this.zwavesubdeviceid = zwavesubdeviceid;
	}

	public int getChannelid() {
		return channelid;
	}

	public void setChannelid(int channelid) {
		this.channelid = channelid;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JSON(serialize=false)
	@JSONField(serialize = false)
	@ManyToOne(targetEntity=ZWaveDevice.class,cascade={CascadeType.DETACH},fetch = FetchType.LAZY)           
	@JoinColumn(name="zwavedeviceid",referencedColumnName="zwavedeviceid",nullable=true)
	public ZWaveDevice getZwavedevice() {
		return zwavedevice;
	}

	@JSON(serialize=false)
	@JSONField(serialize = false)
	@ManyToOne(targetEntity=Partition.class,cascade={CascadeType.DETACH},fetch = FetchType.LAZY)           
	@JoinColumn(name="partitionid",referencedColumnName="partitionid",nullable=true)
	public Partition getPartition() {
		return partition;
	}

	public void setPartition(Partition partition) {
		this.partition = partition;
	}

	public void setZwavedevice(ZWaveDevice zwavedevice) {
		this.zwavedevice = zwavedevice;
	}

	public String getSubdevicetype() {
		return subdevicetype;
	}

	public void setSubdevicetype(String subdevicetype) {
		this.subdevicetype = subdevicetype;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getWarningstatuses() {
		return warningstatuses;
	}

	public void setWarningstatuses(String warningstatuses) {
		this.warningstatuses = warningstatuses;
	}

	public int getEnablestatus() {
		return enablestatus;
	}

	public void setEnablestatus(int enablestatus) {
		this.enablestatus = enablestatus;
	}

	public String getStatuses() {
		return statuses;
	}

	public void setStatuses(String statuses) {
		this.statuses = statuses;
	}

	@Override
	public String toString() {
		return "ZWaveSubDevice{" +
				"zwavesubdeviceid=" + zwavesubdeviceid +
				", channelid=" + channelid +
				", name='" + name + '\'' +
				", zwavedevice=" + zwavedevice +
				", subdevicetype='" + subdevicetype + '\'' +
				", partition=" + partition +
				", status=" + status +
				", warningstatuses='" + warningstatuses + '\'' +
				", enablestatus=" + enablestatus +
				", statuses='" + statuses + '\'' +
				'}';
	}
}

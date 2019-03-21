package com.iremote.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name="roomappliance")
public class RoomAppliance {

	private int roomapplianceid ;
	private Room room ;
	private Integer zwavedeviceid ;
	private Integer infrareddeviceid ;
	private Integer cameraid ;
	private Integer subdeviceid;
	private Integer channelid;

	private String deviceid ;
	private String applianceid ;
	private String majortype;
	private String devicetype;
	private int nuid ;
	private String name ;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
	public int getRoomapplianceid() {
		return roomapplianceid;
	}
	public void setRoomapplianceid(int roomapplianceid) {
		this.roomapplianceid = roomapplianceid;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getApplianceid() {
		return applianceid;
	}
	public void setApplianceid(String applianceid) {
		this.applianceid = applianceid;
	}
	public String getMajortype() {
		return majortype;
	}
	public void setMajortype(String majortype) {
		this.majortype = majortype;
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
	@JSON(serialize=false)
	@JSONField(serialize = false)
	@ManyToOne(targetEntity=Room.class,cascade={CascadeType.DETACH})           
	@JoinColumn(name="roomdbid",referencedColumnName="roomdbid",nullable=false)
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getZwavedeviceid()
	{
		return zwavedeviceid;
	}
	public void setZwavedeviceid(Integer zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}
	public Integer getInfrareddeviceid()
	{
		return infrareddeviceid;
	}
	public void setInfrareddeviceid(Integer infrareddeviceid)
	{
		this.infrareddeviceid = infrareddeviceid;
	}
	public Integer getCameraid()
	{
		return cameraid;
	}
	public void setCameraid(Integer cameraid)
	{
		this.cameraid = cameraid;
	}

	public Integer getSubdeviceid() {
		return subdeviceid;
	}

	public void setSubdeviceid(Integer subdeviceid) {
		this.subdeviceid = subdeviceid;
	}

	public Integer getChannelid() {
		return channelid;
	}

	public void setChannelid(Integer channelid) {
		this.channelid = channelid;
	}
}

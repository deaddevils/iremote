package com.iremote.common.jms.vo;

import java.util.Date;

import com.iremote.common.IRemoteConstantDefine;

public class CameraEvent implements IApplianceEvent
{
	private int cameraid;
	private String deviceid;
	private String camerauuid ;
	private String eventtype;
	private String type;
	private String name ;
	private Integer status ;
	private Integer phoneuserid ;
	private Date eventtime;
	private Integer warningstatus;
	private String warningstatuses;
	private long taskid = System.currentTimeMillis();
	
	public CameraEvent()
	{
		super();
	}
	
	public CameraEvent(String camerauuid, String type)
	{
		super();
		this.camerauuid = camerauuid;
		this.type = type;
	}
	
	public String getCamerauuid()
	{
		return camerauuid;
	}
	public void setCamerauuid(String camerauuid)
	{
		this.camerauuid = camerauuid;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	
	public long getTaskIndentify() {
		return taskid;
	}

	public int getCameraid() {
		return cameraid;
	}

	public void setCameraid(int cameraid) {
		this.cameraid = cameraid;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getEventtype() {
		return eventtype;
	}

	public void setEventtype(String eventtype) {
		this.eventtype = eventtype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPhoneuserid() {
		return phoneuserid;
	}

	public void setPhoneuserid(int phoneuserid) {
		this.phoneuserid = phoneuserid;
	}

	public Date getEventtime() {
		return eventtime;
	}

	public void setEventtime(Date eventtime) {
		this.eventtime = eventtime;
	}

	public Integer getWarningstatus() {
		return warningstatus;
	}

	public void setWarningstatus(Integer warningstatus) {
		this.warningstatus = warningstatus;
	}

	public String getWarningstatuses() {
		return warningstatuses;
	}

	public void setWarningstatuses(String warningstatuses) {
		this.warningstatuses = warningstatuses;
	}

	public long getTaskid() {
		return taskid;
	}

	public void setTaskid(long taskid) {
		this.taskid = taskid;
	}
	
	@Override
	public String getMajortype()
	{
		return IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA;
	}

	@Override
	public Integer getApptokenid() {
		return null;
	}
}

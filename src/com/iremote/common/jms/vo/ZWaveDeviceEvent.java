package com.iremote.common.jms.vo;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;

public class ZWaveDeviceEvent implements IApplianceEvent
{
	private int zwavedeviceid;
	private String deviceid;
	private int nuid ;
	private String devicetype;
	private String applianceid ;
	private String eventtype;
	private Date eventtime;
	private Integer warningstatus;
	private String name ;
	private Integer status ;
	private Integer shadowstatus;
	private String statuses;
	private Float fstatus ;
	private String warningstatuses;
	private Integer battery ;
	private int channel = 0 ;
	private long taskIndentify ;
	private byte[] report ;
	private JSONObject appendmessage ;
	private Integer phoneuserid ;
	protected Integer apptokenid ;
	private int subdeviceid;
	private int enablestatus;
	private Integer channelid;
	private Date validfrom;
	private Date validthrough;
	private Integer usertype;
	
	public ZWaveDeviceEvent() {
		super();
	}

	public ZWaveDeviceEvent(int zwavedeviceid, String deviceid, int nuid, String eventtype, Date eventtime , long taskid) {
		super();
		this.zwavedeviceid = zwavedeviceid;
		this.deviceid = deviceid;
		this.eventtype = eventtype;
		this.nuid = nuid;
		this.eventtime = eventtime;
		this.taskIndentify = taskid ;
	}
	
	public long getTaskIndentify() {
		return taskIndentify;
	}
	
	public void setTaskIndentify(long taskIndentify) {
		this.taskIndentify = taskIndentify;
	}
	public int getZwavedeviceid() {
		return zwavedeviceid;
	}
	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public int getNuid() {
		return nuid;
	}
	public void setNuid(int nuid) {
		this.nuid = nuid;
	}
	public Date getEventtime() {
		return eventtime;
	}
	public void setEventtime(Date eventtime) {
		this.eventtime = eventtime;
	}
	public String getEventtype() {
		return eventtype;
	}
	public void setEventtype(String eventtype) {
		this.eventtype = eventtype;
	}
	public Integer getWarningstatus() {
		return warningstatus;
	}
	public void setWarningstatus(Integer warningstatus) {
		this.warningstatus = warningstatus;
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
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public byte[] getReport() {
		return report;
	}
	public void setReport(byte[] report) {
		this.report = report;
	}
	public JSONObject getAppendmessage() {
		return appendmessage;
	}
	public void setAppendmessage(JSONObject appendmessage) {
		this.appendmessage = appendmessage;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public String getApplianceid()
	{
		return applianceid;
	}

	public void setApplianceid(String applianceid)
	{
		this.applianceid = applianceid;
	}

	public Integer getShadowstatus()
	{
		return shadowstatus;
	}

	public void setShadowstatus(Integer shadowstatus)
	{
		this.shadowstatus = shadowstatus;
	}
	
	public Integer getApptokenid() {
		return apptokenid;
	}

	public void setApptokenid(Integer apptokenid) {
		this.apptokenid = apptokenid;
	}
	
	@Override
	public Integer getPhoneuserid() 
	{
		return phoneuserid;
	}

	@Override
	public String getMajortype()
	{
		return IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE;
	}

	public void setPhoneuserid(Integer phoneuserid) {
		this.phoneuserid = phoneuserid;
	}

	public int getSubdeviceid() {
		return subdeviceid;
	}

	public void setSubdeviceid(int subdeviceid) {
		this.subdeviceid = subdeviceid;
	}

	public int getEnablestatus() {
		return enablestatus;
	}

	public void setEnablestatus(int enablestatus) {
		this.enablestatus = enablestatus;
	}

	public Integer getChannelid() {
		return channelid;
	}

	public void setChannelid(Integer channelid) {
		this.channelid = channelid;
	}

	public Date getValidfrom() {
		return validfrom;
	}

	public void setValidfrom(Date validfrom) {
		this.validfrom = validfrom;
	}

	public Date getValidthrough() {
		return validthrough;
	}

	public void setValidthrough(Date validthrough) {
		this.validthrough = validthrough;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}
	
}

package com.iremote.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import org.apache.struts2.json.annotations.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.iremote.common.IRemoteConstantDefine;

@Entity
@Table(name="notification")
public class Notification {
	private int notificationid;
	private Integer phoneuserid;
	private Integer familyid ;
	private String deviceid;
	private int nuid;
	private Integer zwavedeviceid ;
	private Integer cameraid ;
	private String applianceid;
	private String name ;
	private String message;
	private String orimessage;
	private String majortype;
	private String devicetype;
	private Date reporttime;
	private int eclipseby;
	private int status ;
	private String unalarmphonenumber;
	private Integer unalarmphoneuserid;
	private Integer deleteflag = 0;
	private Integer deletephoneuserid;
	private Integer network;
	private Integer networkintensity;
	private String appendmessage;
	private JSONObject appendjson;
	private String appendjsonstring;
	private Integer warningstatus;

	public Notification() {
		super();
	}
	
	public Notification(ZWaveDevice zwdevice , String message) 
	{
		super();
		this.deviceid = zwdevice.getDeviceid();
		this.devicetype = zwdevice.getDevicetype();
		this.majortype = IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE;
		this.name = zwdevice.getName();
		this.nuid = zwdevice.getNuid();
		this.message = message;
		this.zwavedeviceid = zwdevice.getZwavedeviceid();
	}
	
	public Notification(Camera camera, String message) 
	{
		super();
		this.deviceid = camera.getDeviceid();
		this.devicetype = camera.getDevicetype();
		this.majortype = IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA;
		this.name = camera.getName();
		this.message = message;
		this.cameraid = camera.getCameraid();
	}
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "notificationid")  
	public int getNotificationid() {
		return notificationid;
	}
	public void setNotificationid(int notificationid) {
		this.notificationid = notificationid;
	}
    @Column(name = "deviceid")  
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
    @Column(name = "nuid")  
	public int getNuid() {
		return nuid;
	}
	public void setNuid(int nuid) {
		this.nuid = nuid;
	}
    @Column(name = "message")  
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    @Column(name = "orimessage")  
	public String getOrimessage() {
		return orimessage;
	}
	public void setOrimessage(String orimessage) {
		this.orimessage = orimessage;
	}
    @Column(name = "devicetype")  
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
    @Column(name = "reporttime")  
	public Date getReporttime() {
		return reporttime;
	}
	public void setReporttime(Date reporttime) {
		this.reporttime = reporttime;
	}
    @Column(name = "eclipseby")  
	public int getEclipseby() {
		return eclipseby;
	}
	public void setEclipseby(int eclipseby) {
		this.eclipseby = eclipseby;
	}
    @Column(name = "name")  
	public synchronized String getName() {
		return name;
	}
	public synchronized void setName(String name) {
		this.name = name;
	}
    @Column(name = "unalarmphonenumber")  
	public String getUnalarmphonenumber() {
		return unalarmphonenumber;
	}
	public void setUnalarmphonenumber(String unalarmphonenumber) {
		this.unalarmphonenumber = unalarmphonenumber;
	}
    @Column(name = "unalarmphoneuserid")  
	public Integer getUnalarmphoneuserid() {
		return unalarmphoneuserid;
	}
	public void setUnalarmphoneuserid(Integer unalarmphoneuserid) {
		this.unalarmphoneuserid = unalarmphoneuserid;
	}
    @Column(name = "deleteflag")  
	public Integer getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(Integer deleteflag) {
		this.deleteflag = deleteflag;
	}
    @Column(name = "deletephoneuserid")  
	public Integer getDeletephoneuserid() {
		return deletephoneuserid;
	}
	public void setDeletephoneuserid(Integer deletephoneuserid) {
		this.deletephoneuserid = deletephoneuserid;
	}
	
	@Transient
	public Integer getNetwork() {
		return network;
	}
	public void setNetwork(Integer network) {
		this.network = network;
	}
	public String getAppendmessage() {
		return appendmessage;
	}
	public void setAppendmessage(String appendmessage) {
		this.appendmessage = appendmessage;
	}
	public String getMajortype() {
		return majortype;
	}
	public void setMajortype(String majortype) {
		this.majortype = majortype;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getApplianceid() {
		return applianceid;
	}
	public void setApplianceid(String applianceid) {
		this.applianceid = applianceid;
	}

	@Transient
	public Integer getNetworkintensity() {
		return networkintensity;
	}

	public void setNetworkintensity(Integer networkintensity) {
		this.networkintensity = networkintensity;
	}

	public Integer getPhoneuserid() {
		return phoneuserid;
	}

	public void setPhoneuserid(Integer phoneuserid) {
		this.phoneuserid = phoneuserid;
	}

	public Integer getFamilyid() {
		return familyid;
	}

	public void setFamilyid(Integer familyid) {
		this.familyid = familyid;
	}

	public Integer getZwavedeviceid() {
		return zwavedeviceid;
	}

	public void setZwavedeviceid(Integer zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	@JSON(serialize=false)
	@JSONField(serialize = false)
	public String getAppendjsonstring()
	{
		return appendjsonstring;
	}

	public void setAppendjsonstring(String appendjsonstring)
	{
		this.appendjsonstring = appendjsonstring;
		if ( StringUtils.isNotBlank(this.appendjsonstring))
			appendjson = com.alibaba.fastjson.JSON.parseObject(this.appendjsonstring);
	}

	@Transient
	public JSONObject getAppendjson()
	{
		return appendjson;
	}

	public void setAppendjson(JSONObject appendjson)
	{
		this.appendjson = appendjson;
		if ( this.appendjson != null )
			this.appendjsonstring = this.appendjson.toJSONString();
	}

	public Integer getCameraid()
	{
		return cameraid;
	}

	public void setCameraid(Integer cameraid)
	{
		this.cameraid = cameraid;
	}

	@Transient
	public Integer getWarningstatus() {
		return warningstatus;
	}

	public void setWarningstatus(Integer warningstatus) {
		this.warningstatus = warningstatus;
	}
}

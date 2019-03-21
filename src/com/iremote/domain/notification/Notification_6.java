package com.iremote.domain.notification;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name="notification_6", catalog="iremotenotification")
public class Notification_6 implements INotification
{
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

	@Id    
	@GenericGenerator(name = "generator", strategy = "assigned")   
    @Column(name = "notificationid")  
	public int getNotificationid() {
		return notificationid;
	}
	public void setNotificationid(int notificationid) {
		this.notificationid = notificationid;
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
	public Integer getZwavedeviceid() {
		return zwavedeviceid;
	}
	public void setZwavedeviceid(Integer zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public Integer getCameraid() {
		return cameraid;
	}
	public void setCameraid(Integer cameraid) {
		this.cameraid = cameraid;
	}
	public String getApplianceid() {
		return applianceid;
	}
	public void setApplianceid(String applianceid) {
		this.applianceid = applianceid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOrimessage() {
		return orimessage;
	}
	public void setOrimessage(String orimessage) {
		this.orimessage = orimessage;
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
	public Date getReporttime() {
		return reporttime;
	}
	public void setReporttime(Date reporttime) {
		this.reporttime = reporttime;
	}
	public int getEclipseby() {
		return eclipseby;
	}
	public void setEclipseby(int eclipseby) {
		this.eclipseby = eclipseby;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUnalarmphonenumber() {
		return unalarmphonenumber;
	}
	public void setUnalarmphonenumber(String unalarmphonenumber) {
		this.unalarmphonenumber = unalarmphonenumber;
	}
	public Integer getUnalarmphoneuserid() {
		return unalarmphoneuserid;
	}
	public void setUnalarmphoneuserid(Integer unalarmphoneuserid) {
		this.unalarmphoneuserid = unalarmphoneuserid;
	}
	public Integer getDeleteflag() {
		return deleteflag;
	}
	public void setDeleteflag(Integer deleteflag) {
		this.deleteflag = deleteflag;
	}
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
	@Transient
	public Integer getNetworkintensity() {
		return networkintensity;
	}
	public void setNetworkintensity(Integer networkintensity) {
		this.networkintensity = networkintensity;
	}
	public String getAppendmessage() {
		return appendmessage;
	}
	public void setAppendmessage(String appendmessage) {
		this.appendmessage = appendmessage;
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
}

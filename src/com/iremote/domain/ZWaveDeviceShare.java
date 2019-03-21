package com.iremote.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;
import com.iremote.common.Utils;
import com.iremote.common.constant.DeviceShareSource;

@Entity
@Table(name="zwavedeviceshare")
public class ZWaveDeviceShare {

	private int id ;
	private int touserid;
	private String touser;
	private String username ;
	private String deviceid ;
	private Integer zwavedeviceid;
	private Integer infrareddeviceid;
	private Integer cameraid ;
	private String token ;
	private String securitycode ;
	private Date validfrom;
	private Date validthrough;
	private Integer validtype = 0 ; //0: valid in validation period , 1 : valid once in validation period , 2 : not valid 
	private Date createtime;
	private int shareowntype = DeviceShareSource.thirdpart.getSource() ;
	
	public ZWaveDeviceShare()
	{
		super();
	}
	
	public ZWaveDeviceShare(String deviceid , UserShare usershare , UserShareDevice sharedevice)
	{
		super();
		this.touserid = usershare.getTouserid();
		this.touser = usershare.getTouser();
		this.deviceid = deviceid ;
		this.zwavedeviceid = sharedevice.getZwavedeviceid();
		this.infrareddeviceid = sharedevice.getInfrareddeviceid();
		this.cameraid = sharedevice.getCameraid();
		this.shareowntype = DeviceShareSource.phoneuser.getSource();
		this.validfrom = new Date();
		this.validthrough = Utils.parseTime("2099-12-31 23:59:59");
	}
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTouserid() {
		return touserid;
	}
	public void setTouserid(int touserid) {
		this.touserid = touserid;
	}
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public Integer getZwavedeviceid() {
		return zwavedeviceid;
	}
	public void setZwavedeviceid(Integer zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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
	public Integer getInfrareddeviceid()
	{
		return infrareddeviceid;
	}
	public void setInfrareddeviceid(Integer infrareddeviceid)
	{
		this.infrareddeviceid = infrareddeviceid;
	}

	public int getShareowntype()
	{
		return shareowntype;
	}

	public void setShareowntype(int shareowntype)
	{
		this.shareowntype = shareowntype;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	@JSON(serialize=false)
	@JSONField(serialize = false)
	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}
	
	@JSON(serialize=false)
	@JSONField(serialize = false)
	public String getSecuritycode()
	{
		return securitycode;
	}

	public void setSecuritycode(String securitycode)
	{
		this.securitycode = securitycode;
	}

	public Integer getCameraid()
	{
		return cameraid;
	}

	public void setCameraid(Integer cameraid)
	{
		this.cameraid = cameraid;
	}

	public Integer getValidtype() {
		return validtype;
	}

	public void setValidtype(Integer validtype) {
		this.validtype = validtype;
	}
	
	
}

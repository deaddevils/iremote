package com.iremote.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="remote")
public class Remote implements Serializable{

	private static final long serialVersionUID = 1L;
	private String deviceid;
	private String password;
	private Integer phoneuserid;
	private String phonenumber;
	private String name ;
	private String ssid ;
	private String ip;
	private String mac ;
	private int longitude;
	private int latitude;
	private Integer homeid;
	private int status;
	private Date createtime;
	private Date lastupdatetime;
	private int platform; 
	private String version ;
	private int iversion;
	private byte[] zwavescuritykey;
	private byte[] secritykey;
	private String zwavescuritykeybase64;
	private String secritykeybase64;
	private int network;
	private int networkintensity;
	private int powertype;
	private Integer battery = 100;
	private String temperature;
	private Timezone timezone;
	private int remotetype;
	private List<GatewayCapability> capability = new ArrayList<GatewayCapability>();
	
	public Remote(String deviceid, String password) {
		super();
		this.deviceid = deviceid;
		this.password = password;
	}
	public Remote() {
		super();
	}
	
	@Id    
	@GenericGenerator(name = "generator", strategy = "assigned")   
    @Column(name = "deviceid")  
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	
	@Column(name = "password") 
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name = "phonenumber") 
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	@Column(name = "ssid") 
	public String getSsid() {
		return ssid;
	}
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	@Column(name = "longitude") 
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	@Column(name = "latitude") 
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	@Column(name = "ip") 
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(name = "name") 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "createtime") 
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Column(name = "lastupdatetime") 
	public Date getLastupdatetime() {
		return lastupdatetime;
	}
	public void setLastupdatetime(Date lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}
	@Column(name = "mac") 
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	@Column(name = "homeid") 
	public Integer getHomeid() {
		return homeid;
	}
	public void setHomeid(Integer homeid) {
		this.homeid = homeid;
	}
	@Column(name = "status") 
	public synchronized int getStatus() {
		return status;
	}
	public synchronized void setStatus(int status) {
		this.status = status;
	}
	@Column(name = "phoneuserid") 
	public Integer getPhoneuserid() {
		return phoneuserid;
	}
	public void setPhoneuserid(Integer phoneuserid) {
		this.phoneuserid = phoneuserid;
	}
	@Column(name = "platform") 
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getIversion() {
		return iversion;
	}
	public void setIversion(int iversion) {
		this.iversion = iversion;
	}
	@Transient
	public byte[] getZwavescuritykey() {
		return zwavescuritykey;
	}
	public void setZwavescuritykey(byte[] zwavescuritykey) {
		this.zwavescuritykey = zwavescuritykey;
		this.zwavescuritykeybase64 = Base64.encodeBase64String(this.zwavescuritykey);
	}
	@Transient
	public byte[] getSecritykey() {
		return secritykey;
	}
	public void setSecritykey(byte[] secritykey) {
		this.secritykey = secritykey;
		this.secritykeybase64 = Base64.encodeBase64String(this.secritykey);
	}
	public int getNetwork() {
		return network;
	}
	public void setNetwork(int network) {
		this.network = network;
	}
	public int getNetworkintensity() {
		return networkintensity;
	}
	public void setNetworkintensity(int networkintensity) {
		this.networkintensity = networkintensity;
	}
	public int getPowertype() {
		return powertype;
	}
	public void setPowertype(int powertype) {
		this.powertype = powertype;
	}
	public Integer getBattery() {
		return battery;
	}
	public void setBattery(Integer battery) {
		this.battery = battery;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getZwavescuritykeybase64() {
		return zwavescuritykeybase64;
	}
	public void setZwavescuritykeybase64(String zwavescuritykeybase64) {
		this.zwavescuritykeybase64 = zwavescuritykeybase64;
		this.zwavescuritykey = Base64.decodeBase64(this.zwavescuritykeybase64);
	}
	public String getSecritykeybase64() {
		return secritykeybase64;
	}
	public void setSecritykeybase64(String secritykeybase64) {
		this.secritykeybase64 = secritykeybase64;
		this.secritykey = Base64.decodeBase64(this.secritykeybase64);
	}
	@OneToOne(cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true)  
    @JoinColumn(name="timezoneid")  
	public Timezone getTimezone() {
		return timezone;
	}
	public void setTimezone(Timezone timezone) {
		this.timezone = timezone;
	}
	public int getRemotetype()
	{
		return remotetype;
	}
	public void setRemotetype(int remotetype)
	{
		this.remotetype = remotetype;
	}
	
	@OneToMany(cascade={CascadeType.ALL},orphanRemoval=true,fetch=FetchType.LAZY,mappedBy="remote")
	@BatchSize(size=300)
	public List<GatewayCapability> getCapability()
	{
		return capability;
	}
	public void setCapability(List<GatewayCapability> capability)
	{
		this.capability = capability;
	}

}

package com.iremote.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.alibaba.fastjson.annotation.JSONField;
import com.iremote.common.constant.GatewayCapabilityType;
import com.iremote.domain.GatewayCapability;
import com.iremote.domain.Timezone;

public class RemoteData {

	private String deviceid;
	private String phonenumber;
	private String name;
	private int type ;
	private String ssid ;
	private String ip;
	private int status;
	private String version ;
	private int iversion;
	private int network;
	private int networkintensity;
	private int powertype;
	private Integer battery = 100;
	private Integer remotetype;
	private Timezone timezone = new Timezone();
	private List<Appliance> appliancelist = new ArrayList<Appliance>();
	private List<GatewayCapability> capability = new ArrayList<GatewayCapability>();
	private boolean isexistdsc;
	private String zwavedeviceid;
	
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
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
	public List<Appliance> getAppliancelist() {
		return appliancelist;
	}
	public void setAppliancelist(List<Appliance> appliancelist) {
		this.appliancelist = appliancelist;
	}
	public Timezone getTimezone() {
		return timezone;
	}
	public void setTimezone(Timezone timezone) {
		this.timezone = timezone;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public Integer getRemotetype() {
		return remotetype;
	}
	public void setRemotetype(Integer remotetype) {
		this.remotetype = remotetype;
	}
	public List<GatewayCapability> getCapability()
	{
		return capability;
	}
	public void setCapability(List<GatewayCapability> capability)
	{
		this.capability = capability;
	}
	public String getSsid()
	{
		return ssid;
	}
	public void setSsid(String ssid)
	{
		this.ssid = ssid;
	}
	public String getIp()
	{
		return ip;
	}
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public int getNetwork()
	{
		return network;
	}
	public void setNetwork(int network)
	{
		this.network = network;
	}
	public int getNetworkintensity()
	{
		return networkintensity;
	}
	public void setNetworkintensity(int networkintensity)
	{
		this.networkintensity = networkintensity;
	}
	public Integer getBattery()
	{
		return battery;
	}
	public void setBattery(Integer battery)
	{
		this.battery = battery;
	}
	public String getVersion()
	{
		return version;
	}
	public void setVersion(String version)
	{
		this.version = version;
	}
	public int getIversion()
	{
		return iversion;
	}
	public void setIversion(int iversion)
	{
		this.iversion = iversion;
	}
	public int getPowertype()
	{
		return powertype;
	}
	public void setPowertype(int powertype)
	{
		this.powertype = powertype;
	}
	
	@JSONField(serialize = false)
	@JSON(serialize=false)
	public boolean isAirconditionGateway()
	{
		return this.hascapability(GatewayCapabilityType.aircondition);
	}
	
	@JSONField(serialize = false)
	@JSON(serialize=false)
	public boolean isDehumidityGateway()
	{
		return this.hascapability(GatewayCapabilityType.dehumidity);
	}
	
	@JSONField(serialize = false)
	@JSON(serialize=false)
	public boolean isFreshAirGateway()
	{
		return this.hascapability(GatewayCapabilityType.freshair);
	}
	
	@JSONField(serialize = false)
	@JSON(serialize=false)
	public boolean isDscGateway()
	{
		return this.hascapability(GatewayCapabilityType.dsc);
	}
	
	private boolean hascapability(GatewayCapabilityType capability)
	{		
		if ( this.getCapability() == null || this.getCapability().size() == 0 )
			return false ;
		for ( GatewayCapability c : this.getCapability() )
			if ( c.getCapabilitycode() == capability.getCapabilitycode())
				return true;
		
		return false ;
	}
	public boolean isIsexistdsc() {
		return isexistdsc;
	}
	public void setIsexistdsc(boolean isexistdsc) {
		this.isexistdsc = isexistdsc;
	}
	public String getZwavedeviceid() {
		return zwavedeviceid;
	}
	public void setZwavedeviceid(String zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	
}

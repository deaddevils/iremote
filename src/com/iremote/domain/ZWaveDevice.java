package com.iremote.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import com.iremote.common.IRemoteConstantDefine;


@Entity
@Table(name="zwavedevice")
public class ZWaveDevice {

	private int zwavedeviceid;
	private String deviceid;
	private int nuid ;
	private String applianceid;
	private String devicetype;
	private String name ;
	private Integer status ;
	private String statuses;
	private Float fstatus ;
	private String warningstatuses;
	private Integer battery = 100 ;
	private Integer shadowstatus;
	private int enablestatus;
	private String productor;
	private String model;
	private String version1 ;
	private String productor2;
	private String model2;
	private String version2 ;
	private String version3 ;
	private String functionversion;
	private int armstatus = IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM;
	private List<Timer> timer ;
	private List<Associationscene> associationscenelist ;
	private List<DeviceCapability> capability;
	private List<ZWaveSubDevice> zWaveSubDevices;
	private List<Partition> partitions;
	private Integer partitionid;
	private Date createtime;
	private Date lastactivetime;

	private String majortype = IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE;
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "zwavedeviceid")  
	public int getZwavedeviceid() {
		return zwavedeviceid;
	}
	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
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
    @Column(name = "devicetype")  
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
    @Column(name = "name")  
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    @Column(name = "status")  
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name = "battery")  
	public Integer getBattery() {
		return battery;
	}
	public void setBattery(Integer battery) {
		this.battery = battery;
	}
	@Column(name = "statuses")  
	public String getStatuses() {
		return statuses;
	}
	public void setStatuses(String statuses) {
		this.statuses = statuses;
	}
	@Column(name = "shadowstatus")  
	public Integer getShadowstatus() {
		return shadowstatus;
	}
	public void setShadowstatus(Integer shadowstatus) {
		this.shadowstatus = shadowstatus;
	}
	
	@JSON(serialize=false)
	@JSONField(serialize = false)
	@OneToMany(targetEntity=Associationscene.class,cascade={CascadeType.DETACH,CascadeType.REFRESH},orphanRemoval=false,fetch=FetchType.LAZY,mappedBy="zwavedevice")   
	@BatchSize(size=300)
	public List<Associationscene> getAssociationscenelist() {
		return associationscenelist;
	}
	public void setAssociationscenelist(List<Associationscene> associationscenelist) {
		this.associationscenelist = associationscenelist;
	}
	
	public int getEnablestatus() {
		return enablestatus;
	}
	public void setEnablestatus(int enablestatus) {
		this.enablestatus = enablestatus;
	}
	public Float getFstatus() {
		return fstatus;
	}
	public void setFstatus(Float fstatus) {
		this.fstatus = fstatus;
	}
	public String getApplianceid() {
		return applianceid;
	}
	public void setApplianceid(String applianceid) {
		this.applianceid = applianceid;
	}
	@Transient
	public String getMajortype() {
		return majortype;
	}
	@JSON(serialize=false)
	@JSONField(serialize = false)
	@OneToMany(targetEntity=Timer.class,cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true,fetch=FetchType.LAZY,mappedBy="zwavedevice")   
	@BatchSize(size=300)
	public List<Timer> getTimer() {
		return timer;
	}
	public void setTimer(List<Timer> timer) {
		this.timer = timer;
	}
	@JSON(serialize=false)
	@JSONField(serialize = false)
	@OneToMany(targetEntity=DeviceCapability.class,cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true,fetch=FetchType.LAZY,mappedBy="zwavedevice")   
	@BatchSize(size=300)
	public List<DeviceCapability> getCapability() {
		return capability;
	}
	public void setCapability(List<DeviceCapability> capability) {
		this.capability = capability;
	}
	
	@JSON(serialize=false)
	@JSONField(serialize = false)
	@OneToMany(targetEntity=ZWaveSubDevice.class,cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true,fetch=FetchType.LAZY,mappedBy="zwavedevice")   
	@BatchSize(size=300)
	public List<ZWaveSubDevice> getzWaveSubDevices() {
		return zWaveSubDevices;
	}
	public void setzWaveSubDevices(List<ZWaveSubDevice> zWaveSubDevices) {
		this.zWaveSubDevices = zWaveSubDevices;
	}
	@JSON(serialize=false)
	@JSONField(serialize = false)
	@OneToMany(targetEntity=Partition.class,cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true,fetch=FetchType.LAZY,mappedBy="zwavedevice")
	@BatchSize(size=300)
	public List<Partition> getPartitions() {
		return partitions;
	}
	public void setPartitions(List<Partition> partitions) {
		this.partitions = partitions;
	}

	@Transient
	public Integer getWarningstatus() {
		if ( warningstatuses == null || warningstatuses.length() == 0 )
			return null ;
		JSONArray ja = com.alibaba.fastjson.JSON.parseArray(warningstatuses);
		if ( ja.size() == 0 )
			return null ;
		return ja.getInteger(ja.size() -1);
	}
	public String getWarningstatuses() {
		return warningstatuses;
	}
	public void setWarningstatuses(String waringstatuses) {
		this.warningstatuses = waringstatuses;
	}
	public String getVersion1()
	{
		return version1;
	}
	public void setVersion1(String version1)
	{
		this.version1 = version1;
	}
	public String getVersion2()
	{
		return version2;
	}
	public void setVersion2(String version2)
	{
		this.version2 = version2;
	}
	public String getModel()
	{
		return model;
	}
	public void setModel(String model)
	{
		this.model = model;
	}
	public String getProductor()
	{
		return productor;
	}
	public void setProductor(String productor)
	{
		this.productor = productor;
	}
	public String getFunctionversion()
	{
		return functionversion;
	}
	public void setFunctionversion(String functionversion)
	{
		this.functionversion = functionversion;
	}
	public String getProductor2()
	{
		return productor2;
	}
	public void setProductor2(String productor2)
	{
		this.productor2 = productor2;
	}
	public String getModel2()
	{
		return model2;
	}
	public void setModel2(String model2)
	{
		this.model2 = model2;
	}
	public Integer getPartitionid() {
		return partitionid;
	}
	public void setPartitionid(Integer partitionid) {
		this.partitionid = partitionid;
	}

	public int getArmstatus() {
		return armstatus;
	}

	public void setArmstatus(int armstatus) {
		this.armstatus = armstatus;
	}

	public String getVersion3() {
		return version3;
	}

	public void setVersion3(String version3) {
		this.version3 = version3;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getLastactivetime() {
		return lastactivetime;
	}
	public void setLastactivetime(Date lastactivetime) {
		this.lastactivetime = lastactivetime;
	}
	
}

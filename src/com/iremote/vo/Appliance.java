package com.iremote.vo;

import java.util.ArrayList;
import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Associationscene;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.DeviceRawCmd;
import com.iremote.domain.Timer;

public class Appliance {

	private String deviceid;
	private String name;
	private String applianceid;
	private Integer zwavedeviceid;
	private Integer infrareddeviceid;
	private Integer cameraid;
	private String majortype;
	private String devicetype;
	private String codeid = "";
	private int[] codelibery;
	private int nuid;
	private Integer status ;
	private String statuses;
	private String productorid;
	private String controlmodeid;
	private int codeindex;
	private String applianceuuid;
	private int wakeuptype = 1 ;
	private Integer battery = 100 ;
	private int[] appendmessage;
	private String version3;
	private int armstatus = IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM;
	private List<Timer> timer = new ArrayList<Timer>();
	private List<Associationscene> associationscenelist ;
	private List<DeviceCapability> capability;
	private List<SubDevice> subdevice;
	private Integer partitionid;
	private List<DeviceRawCmd> rawcmd;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getCodeid() {
		return codeid;
	}
	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}
	public int[] getCodelibery() {
		return codelibery;
	}
	public void setCodelibery(int[] codelibery) {
		this.codelibery = codelibery;
	}
	public int getNuid() {
		return nuid;
	}
	public void setNuid(int nuid) {
		this.nuid = nuid;
	}
	public List<Timer> getTimer() {
		return timer;
	}
	public void setTimer(List<Timer> timer) {
		this.timer = timer;
	}
	public List<Associationscene> getAssociationscenelist() {
		return associationscenelist;
	}
	public void setAssociationscenelist(List<Associationscene> associationscenelist) {
		this.associationscenelist = associationscenelist;
	}
	public String getProductorid() {
		return productorid;
	}
	public void setProductorid(String productorid) {
		this.productorid = productorid;
	}
	public String getControlmodeid() {
		return controlmodeid;
	}
	public void setControlmodeid(String controlmodeid) {
		this.controlmodeid = controlmodeid;
	}
	public int getCodeindex() {
		return codeindex;
	}
	public void setCodeindex(int codeindex) {
		this.codeindex = codeindex;
	}
	public String getApplianceuuid() {
		return applianceuuid;
	}
	public void setApplianceuuid(String applianceuuid) {
		this.applianceuuid = applianceuuid;
	}
	public List<DeviceCapability> getCapability() {
		return capability;
	}
	public void setCapability(List<DeviceCapability> capability) {
		this.capability = capability;
	}
	public int getWakeuptype()
	{
		return wakeuptype;
	}
	public void setWakeuptype(int wakeuptype)
	{
		this.wakeuptype = wakeuptype;
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
	public List<SubDevice> getSubdevice()
	{
		return subdevice;
	}
	public void setSubdevice(List<SubDevice> subdevice)
	{
		this.subdevice = subdevice;
	}
	public int[] getAppendmessage()
	{
		return appendmessage;
	}
	public void setAppendmessage(int[] appendmessage)
	{
		this.appendmessage = appendmessage;
	}
	public Integer getStatus()
	{
		return status;
	}
	public void setStatus(Integer status)
	{
		this.status = status;
	}
	public String getStatuses()
	{
		return statuses;
	}
	public void setStatuses(String statuses)
	{
		this.statuses = statuses;
	}
	public Integer getCameraid()
	{
		return cameraid;
	}
	public void setCameraid(Integer cameraid)
	{
		this.cameraid = cameraid;
	}
	public Integer getBattery()
	{
		return battery;
	}
	public void setBattery(Integer battery)
	{
		this.battery = battery;
	}
	public void setPartitionid(Integer partitionid) {
		this.partitionid = partitionid;
	}
	public Integer getPartitionid() {
		return partitionid;
	}

	public int getArmstatus() {
		return armstatus;
	}

	public void setArmstatus(int armstatus) {
		this.armstatus = armstatus;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getVersion3() {
		return version3;
	}

	public void setVersion3(String version3) {
		this.version3 = version3;
	}

	public List<DeviceRawCmd> getRawcmd() {
		return rawcmd;
	}

	public void setRawcmd(List<DeviceRawCmd> rawcmd) {
		this.rawcmd = rawcmd;
	}
}

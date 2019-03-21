package com.iremote.domain;

import java.util.List;

import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;

@Entity
@Table(name="infrareddevice")
public class InfraredDevice {
	
	private int infrareddeviceid;
	private String deviceid;
	private String applianceid ;
	private String devicetype;
	private String name ;
	private String majortype = IRemoteConstantDefine.DEVICE_MAJORTYPE_INFRARED;
	private String codeid;
	private int[] codelibery;
	private String codeliberyjson;
	private String productorid;
	private String controlmodeid;
	private Integer codeindex;
	private List<Timer> timer ;
//	private List<Command> commandlist;
	private String statuses;
	private List<DeviceCapability> capability;

	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "infrareddeviceid")  
	public int getInfrareddeviceid() {
		return infrareddeviceid;
	}
	public void setInfrareddeviceid(int infrareddeviceid) {
		this.infrareddeviceid = infrareddeviceid;
	}
    @Column(name = "deviceid")  
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
    @Column(name = "applianceid")  
	public String getApplianceid() {
		return applianceid;
	}
	public void setApplianceid(String applianceid) {
		this.applianceid = applianceid;
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

	@Transient
	public String getMajortype() {
		return majortype;
	}
	public String getCodeid() {
		return codeid;
	}
	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}
	@Transient
	public int[] getCodelibery() {
		return codelibery;
	}
	public void setCodelibery(int[] codelibery) {
		this.codelibery = codelibery;
		this.codeliberyjson = JSONObject.toJSONString(codelibery);
	}
//	@OneToMany(targetEntity=Command.class,cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true,mappedBy="associationscene")   
//	public List<Command> getCommandlist() {
//		return commandlist;
//	}
//	public void setCommandlist(List<Command> commandlist) {
//		this.commandlist = commandlist;
//	}
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
	public Integer getCodeindex() {
		return codeindex;
	}
	public void setCodeindex(Integer codeindex) {
		this.codeindex = codeindex;
	}
	public void setMajortype(String majortype) {
		this.majortype = majortype;
	}
	public String getCodeliberyjson() {
		return codeliberyjson;
	}
	public void setCodeliberyjson(String codeliberyjson) {
		this.codeliberyjson = codeliberyjson;
		this.codelibery = null ;
		
		if ( this.codeliberyjson == null || this.codeliberyjson.length() == 0 )
			return ;
		Integer[] ia = com.alibaba.fastjson.JSON.parseArray(codeliberyjson).toArray(new Integer[0]);
		if ( ia == null || ia.length == 0)
			return ;
		
		this.codelibery = new int[ia.length];
		for ( int i = 0 ; i < ia.length ; i ++ )
			this.codelibery[i] = ia[i] ;
	}
	@JSON(serialize=false)
	@OneToMany(targetEntity=Timer.class,cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true,mappedBy="infrareddevice")   
	public List<Timer> getTimer() {
		return timer;
	}
	public void setTimer(List<Timer> timer) {
		this.timer = timer;
	}
	public String getStatuses() {
		return statuses;
	}
	public void setStatuses(String statuses) {
		this.statuses = statuses;
	}
	@JSON(serialize=false)
	@JSONField(serialize = false)
	@OneToMany(targetEntity=DeviceCapability.class,cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true,fetch= FetchType.LAZY,mappedBy="infrareddevice")
	public List<DeviceCapability> getCapability() {
		return capability;
	}
	public void setCapability(List<DeviceCapability> capability) {
		this.capability = capability;
	}

}

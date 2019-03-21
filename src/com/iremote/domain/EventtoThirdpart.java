package com.iremote.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="td_eventforthirdpart")
public class EventtoThirdpart 
{

	private int id ;
	private int thirdpartid;
	private String type;
	private String deviceid ;
	private int zwavedeviceid ;
	private int intparam;
	private Float floatparam;
	private Integer warningstatus;
	private String warningstatuses;
	private String objparam;
	private Date eventtime;
	
	
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public int getZwavedeviceid() {
		return zwavedeviceid;
	}
	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}
	public int getIntparam() {
		return intparam;
	}
	public void setIntparam(int intparam) {
		this.intparam = intparam;
	}
	public String getObjparam() {
		return objparam;
	}
	public void setObjparam(String objparam) {
		this.objparam = objparam;
	}
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getEventtime() {
		return eventtime;
	}
	public void setEventtime(Date eventtime) {
		this.eventtime = eventtime;
	}
	public int getThirdpartid() {
		return thirdpartid;
	}
	public void setThirdpartid(int thirdpartid) {
		this.thirdpartid = thirdpartid;
	}
	public Float getFloatparam() {
		return floatparam;
	}
	public void setFloatparam(Float floatparam) {
		this.floatparam = floatparam;
	}
	public Integer getWarningstatus()
	{
		return warningstatus;
	}
	public void setWarningstatus(Integer warningstatus)
	{
		this.warningstatus = warningstatus;
	}
	public String getWarningstatuses()
	{
		return warningstatuses;
	}
	public void setWarningstatuses(String warningstatuses)
	{
		this.warningstatuses = warningstatuses;
	}
	
}

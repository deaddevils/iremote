package com.iremote.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name="operationlog")
public class OperationLog {

	private int operationlogid;
	private String username;
	private String userip;
	private String requesturl;
	private String requestdata;
	private String deviceid ;
	private String zwavedeviceid ;
	private String result;
	private Integer resultCode ;

	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "operationlogid")  
	public int getOperationlogid() {
		return operationlogid;
	}
	public void setOperationlogid(int operationlogid) {
		this.operationlogid = operationlogid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserip() {
		return userip;
	}
	public void setUserip(String userip) {
		this.userip = userip;
	}
	public String getRequesturl() {
		return requesturl;
	}
	public void setRequesturl(String requesturl) {
		this.requesturl = requesturl;
	}
	@Type(type="text") 
	public String getRequestdata() {
		return requestdata;
	}
	public void setRequestdata(String requestdata) {
		this.requestdata = requestdata;
	}
	@Type(type="text") 
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Integer getResultCode()
	{
		return resultCode;
	}
	public void setResultCode(Integer resultCode)
	{
		this.resultCode = resultCode;
	}
	public String getDeviceid()
	{
		return deviceid;
	}
	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}
	public String getZwavedeviceid()
	{
		return zwavedeviceid;
	}
	public void setZwavedeviceid(String zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}
	
	
}

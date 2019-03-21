package com.iremote.asiainfo.vo;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.annotation.JSONField;

public class AsiainfoMessage 
{
	private int protocol = 0x54;
	private int version = 1 ;
	private int messageid;
	private int encryptflag = 0 ;
	private int messagelength;
	private int messageinfolength;
	private String sequence;
	private int packagecount = 0 ;
	private int packagenumber = 0 ;
	private String clientid = "50000";
	private String manufactorid = "50000";
	private int servicetype = 0x20 ;
	private byte[] message;
	private String messageinfo;
	private byte[] bytemessageinfo;
	
	public int getMessageid() {
		return messageid;
	}
	public void setMessageid(int messageid) {
		this.messageid = messageid;
	}
	public int getEncryptflag() {
		return encryptflag;
	}
	public void setEncryptflag(int encryptflag) {
		this.encryptflag = encryptflag;
	}
	public int getMessagelength() {
		return messagelength;
	}
	public void setMessagelength(int messagelength) {
		this.messagelength = messagelength;
	}
	public int getMessageinfolength() {
		return messageinfolength;
	}
	public void setMessageinfolength(int messageinfolength) {
		this.messageinfolength = messageinfolength;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public int getPackagecount() {
		return packagecount;
	}
	public void setPackagecount(int packagecount) {
		this.packagecount = packagecount;
	}
	public int getPackagenumber() {
		return packagenumber;
	}
	public void setPackagenumber(int packagenumber) {
		this.packagenumber = packagenumber;
	}
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getManufactorid() {
		return manufactorid;
	}
	public void setManufactorid(String manufactorid) {
		this.manufactorid = manufactorid;
	}
	public int getServicetype() {
		return servicetype;
	}
	public void setServicetype(int servicetype) {
		this.servicetype = servicetype;
	}
	public byte[] getMessage() {
		return message;
	}
	public void setMessage(byte[] message) {
		if ( message == null )
			message = new byte[0];
		this.messagelength = message.length ;
		this.message = message;
	}
	public String getMessageinfo() {
		return messageinfo;
	}
	public void setMessageinfo(String messageinfo) {
		if ( messageinfo == null )
			messageinfo = "";
		this.messageinfo = messageinfo;
		
		try {
			this.bytemessageinfo = messageinfo.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		this.messageinfolength  = this.bytemessageinfo.length;
		
	}
	
	@JSONField(serialize = false)
	public byte[] getBytemessageinfo() {
		return bytemessageinfo;
	}
	public int getProtocol() {
		return protocol;
	}
	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
}

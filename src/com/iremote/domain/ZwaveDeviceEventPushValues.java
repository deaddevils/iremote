package com.iremote.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="td_zwavedeviceeventpushvalues")
public class ZwaveDeviceEventPushValues {

	private int zwavedeviceeventpushvaluesid;
	private int zwavedeviceid ;
	private float metervalue;
	private Float floatappendparam;
	private Date createtime = new Date();
	private Date lastsendtime ;
	@Id    
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "zwavedeviceeventpushvaluesid")  
	public int getZwavedeviceeventpushvaluesid() {
		return zwavedeviceeventpushvaluesid;
	}
	public void setZwavedeviceeventpushvaluesid(int zwavedeviceeventpushvaluesid) {
		this.zwavedeviceeventpushvaluesid = zwavedeviceeventpushvaluesid;
	}
	public int getZwavedeviceid() {
		return zwavedeviceid;
	}
	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public float getMetervalue() {
		return metervalue;
	}
	public void setMetervalue(float metervalue) {
		this.metervalue = metervalue;
	}
	public Float getFloatappendparam() {
		return floatappendparam;
	}
	public void setFloatappendparam(Float floatappendparam) {
		this.floatappendparam = floatappendparam;
	}
	public Date getLastsendtime() {
		return lastsendtime;
	}
	public void setLastsendtime(Date lastsendtime) {
		this.lastsendtime = lastsendtime;
	}
	
	
}

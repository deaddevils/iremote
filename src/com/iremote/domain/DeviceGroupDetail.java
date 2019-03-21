package com.iremote.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import com.iremote.common.Utils;

@Entity
@Table(name="devicegroupdetail")
public class DeviceGroupDetail
{
	private int devicegroupdetailid;
	private DeviceGroup devicegroup;
	private int zwavedeviceid ;
	private String channelidsstr ;
	private int[] channelids;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
	public int getDevicegroupdetailid()
	{
		return devicegroupdetailid;
	}
	public void setDevicegroupdetailid(int devicegroupdetailid)
	{
		this.devicegroupdetailid = devicegroupdetailid;
	}
	public int getZwavedeviceid()
	{
		return zwavedeviceid;
	}
	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}
	@JSONField(serialize = false)
	@JSON(serialize=false)
	@Column(name = "channelids")
	public String getChannelidsstr()
	{
		return channelidsstr;
	}
	public void setChannelidsstr(String channelids)
	{
		this.channelidsstr = channelids;
		if ( StringUtils.isBlank(this.channelidsstr))
			this.channelids = null ;
		else 
			this.channelids = Utils.jsontoIntArray(this.channelidsstr);
	}
	@Transient
	public int[] getChannelids()
	{
		return channelids;
	}
	public void setChannelids(int[] channelidsary)
	{
		this.channelids = channelidsary;
		if ( this.channelids == null )
			this.channelidsstr = null ;
		else 
			this.channelidsstr = JSONArray.toJSONString(this.channelids);
	}
	@JSONField(serialize = false)
	@JSON(serialize=false)
	@ManyToOne(targetEntity=DeviceGroup.class,cascade={CascadeType.DETACH})           
	@JoinColumn(name="devicegroupid",referencedColumnName="devicegroupid",nullable=true)
	public DeviceGroup getDevicegroup()
	{
		return devicegroup;
	}
	public void setDevicegroup(DeviceGroup devicegroup)
	{
		this.devicegroup = devicegroup;
	}
	
	
	
}

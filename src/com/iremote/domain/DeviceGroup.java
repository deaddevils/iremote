package com.iremote.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name="devicegroup")
public class DeviceGroup
{
	private int devicegroupid;
	private String devicegroupname;
	private int phoneuserid;
	private String devicetype;
	private String icon;
	private Date createtime;
	private List<DeviceGroupDetail> zwavedevices;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)   
	@GenericGenerator(name = "generator", strategy = "increment")   
    @Column(name = "devicegroupid")  
	public int getDevicegroupid()
	{
		return devicegroupid;
	}
	public void setDevicegroupid(int devicegroupid)
	{
		this.devicegroupid = devicegroupid;
	}
	public String getDevicegroupname()
	{
		return devicegroupname;
	}
	public void setDevicegroupname(String devicegroupname)
	{
		this.devicegroupname = devicegroupname;
	}
	public int getPhoneuserid()
	{
		return phoneuserid;
	}
	public void setPhoneuserid(int phoneuserid)
	{
		this.phoneuserid = phoneuserid;
	}
	public String getIcon()
	{
		return icon;
	}
	public void setIcon(String icon)
	{
		this.icon = icon;
	}
	@JSONField(serialize = false)
	@JSON(serialize=false)
	public Date getCreatetime()
	{
		return createtime;
	}
	public void setCreatetime(Date createtime)
	{
		this.createtime = createtime;
	}
	@OneToMany(targetEntity=DeviceGroupDetail.class,cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true,mappedBy="devicegroup")
	public List<DeviceGroupDetail> getZwavedevices()
	{
		return zwavedevices;
	}
	public void setZwavedevices(List<DeviceGroupDetail> zwavedevices)
	{
		this.zwavedevices = zwavedevices;
	}
	public String getDevicetype()
	{
		return devicetype;
	}
	public void setDevicetype(String devicetype)
	{
		this.devicetype = devicetype;
	}
	
	
}

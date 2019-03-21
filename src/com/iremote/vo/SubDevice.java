package com.iremote.vo;

import com.iremote.domain.DeviceCapability;
import com.iremote.domain.ZWaveSubDevice;

import java.util.List;

public class SubDevice
{
	private int subdeviceid;
	private int channelid;
	private String name;

	public SubDevice()
	{
		super();
	}
	public SubDevice(ZWaveSubDevice zds)
	{
		this.subdeviceid = zds.getZwavesubdeviceid();
		this.channelid = zds.getChannelid();
		this.name = zds.getName();
	}

	public int getSubdeviceid()
	{
		return subdeviceid;
	}
	public void setSubdeviceid(int subdeviceid)
	{
		this.subdeviceid = subdeviceid;
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getChannelid()
	{
		return channelid;
	}
	public void setChannelid(int channelid)
	{
		this.channelid = channelid;
	}

}

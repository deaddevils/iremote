package com.iremote.common.jms.vo;

public class ZWaveDeviceStatusChange extends ZWaveDeviceEvent {

	protected Integer oldstatus ;
	protected Integer oldshadowstatus;
	protected String oldstatuses;
	
	

	public ZWaveDeviceStatusChange() {
		super();
	}

	public Integer getOldstatus()
	{
		return oldstatus;
	}

	public void setOldstatus(Integer oldstatus)
	{
		this.oldstatus = oldstatus;
	}

	public Integer getOldshadowstatus()
	{
		return oldshadowstatus;
	}

	public void setOldshadowstatus(Integer oldshadowstatus)
	{
		this.oldshadowstatus = oldshadowstatus;
	}

	public String getOldstatuses()
	{
		return oldstatuses;
	}

	public void setOldstatuses(String oldstatuses)
	{
		this.oldstatuses = oldstatuses;
	}



}

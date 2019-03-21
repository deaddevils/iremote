package com.iremote.event.gateway;

public class AutoCreateDeviceMetadata
{
	private int nuid ;
	private String devicetype ;
	private int[] requiredcapabilitycode ;
	private int[] initcapabilitycode;
	private int defaultstatus;
	private String defaultstatuses;
	
	public AutoCreateDeviceMetadata(int nuid, String devicetype, int[] requiredcapabilitycode, int[] initcapabilitycode,
			int defaultstatus, String defaultstatuses)
	{
		super();
		this.nuid = nuid;
		this.devicetype = devicetype;
		this.requiredcapabilitycode = requiredcapabilitycode;
		this.initcapabilitycode = initcapabilitycode;
		this.defaultstatus = defaultstatus;
		this.defaultstatuses = defaultstatuses;
	}
	public int getNuid()
	{
		return nuid;
	}
	public String getDevicetype()
	{
		return devicetype;
	}
	public int getDefaultstatus()
	{
		return defaultstatus;
	}
	public String getDefaultstatuses()
	{
		return defaultstatuses;
	}
	public int[] getRequiredcapabilitycode()
	{
		return requiredcapabilitycode;
	}
	public int[] getInitcapabilitycode()
	{
		return initcapabilitycode;
	} 
	
	
}

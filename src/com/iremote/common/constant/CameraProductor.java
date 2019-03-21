package com.iremote.common.constant;

public enum CameraProductor
{
	dahualechange("dahualechange" , "2"),
	lechangeabroad("lechangeabroad" , "9");

	private String productor;
	private String devicetype ;

	private CameraProductor(String productor , String devicetype)
	{
		this.productor = productor;
		this.devicetype = devicetype;
	}

	public String getProductor()
	{
		return productor;
	}

	public String getDevicetype()
	{
		return devicetype;
	}
	
}

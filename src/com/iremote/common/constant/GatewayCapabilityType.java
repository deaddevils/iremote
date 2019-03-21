package com.iremote.common.constant;

public enum GatewayCapabilityType
{
	noinfrared(1),
	nozwave(2),
	apnetconfigration(3),
	addzwavedevicefromserver(4),
	batchzwavecommand(5),
	devicegroupcommand(6),
	dsc(7),
	powerfreedevice(10),
	tentemppassword(10001),
	onetemppassword(10002),
	aircondition(10028 , 14 , 28),
	freshair(10038,40,38),
	dehumidity(10036 , 36 , 37);
	
	private int capabilitycode;
	private int innerdevicetype ;
	private int outdevicetype;

	private GatewayCapabilityType(int capabilitycode)
	{
		this.capabilitycode = capabilitycode;
	}

	private GatewayCapabilityType(int capabilitycode, int innerdevicetype, int outdevicetype)
	{
		this.capabilitycode = capabilitycode;
		this.innerdevicetype = innerdevicetype;
		this.outdevicetype = outdevicetype;
	}

	public static GatewayCapabilityType valueof(int capabilitycode)
	{
		for ( GatewayCapabilityType t : values())
			if ( t.capabilitycode == capabilitycode )
				return t ;
		return null ;
	}
	
	public int getCapabilitycode()
	{
		return capabilitycode;
	}

	public int getInnerdevicetype()
	{
		return innerdevicetype;
	}

	public int getOutdevicetype()
	{
		return outdevicetype;
	}
}

package com.iremote.common.constant;

public enum GatewayType
{
	normal(0),
	cobbewifi(1),
	tongxinwifi(2),
	accesscontroler(3),
	airquality(23),
	aircondition(28);
	
	private int type ;

	private GatewayType(int type)
	{
		this.type = type;
	}

	public int getType()
	{
		return type;
	}
	
}

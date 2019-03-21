package com.iremote.common.sms;

@Deprecated
public class TencentSender8 extends TencentSender
{
	private static final String SMSSIGN = "\u3010Keemple\u3011";
	
	public TencentSender8()
	{
		super(SMSSIGN);
	}


}

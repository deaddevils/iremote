package com.iremote.common.sms;

@Deprecated
public class TencentSender4 extends TencentSender
{
	private static final String SMSSIGN = "\u3010Tecus\u3011";
	
	public TencentSender4()
	{
		super(SMSSIGN);
	}


}

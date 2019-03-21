package com.iremote.common.sms;

@Deprecated
public class TencentSender6 extends TencentSender
{
	private static final String SMSSIGN = "\u3010\u5C0F\u864E\u667A\u6167\u5BB6\u3011";
	
	public TencentSender6()
	{
		super(SMSSIGN);
	}


}

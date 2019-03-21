package com.iremote.common.sms;

@Deprecated
public class TencentSender2 extends TencentSender
{
	private static final String SMSSIGN = "\u3010\u521B\u4F73\u667A\u5C45\u3011";
	
	public TencentSender2()
	{
		super(SMSSIGN);
	}


}

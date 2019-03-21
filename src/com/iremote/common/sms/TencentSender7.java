package com.iremote.common.sms;

@Deprecated
public class TencentSender7 extends TencentSender
{
	private static final String SMSSIGN = "\u3010iSurpass\u3011";
	
	public TencentSender7()
	{
		super(SMSSIGN);
	}


}

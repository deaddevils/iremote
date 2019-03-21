package com.iremote.common.sms;

@Deprecated
public class TencentSender3 extends TencentSender
{

	private static final String SMSSIGN = "\u3010\u591A\u7075\u6167\u5C45\u3011";
	
	public TencentSender3()
	{
		super(SMSSIGN);
	}


}

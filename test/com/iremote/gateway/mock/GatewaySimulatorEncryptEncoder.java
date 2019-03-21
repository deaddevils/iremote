package com.iremote.gateway.mock;

import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.tlv.CommandTlv;

import cn.com.isurpass.gateway.server.processor.BaseEncryptEncoder;

public class GatewaySimulatorEncryptEncoder extends BaseEncryptEncoder {

	@Override
	protected int getEncryptTag() 
	{
		return 0;
	}

	@Override
	protected byte[] encrypt(Remoter remoter, byte[] content) 
	{
		return null;
	}

	@Override
	protected boolean needEncrypte(CommandTlv content)
	{
		return false ;
	}
}

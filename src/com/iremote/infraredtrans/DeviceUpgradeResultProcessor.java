package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;

import com.iremote.common.TagDefine;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;

public class DeviceUpgradeResultProcessor implements IRemoteRequestProcessor
{

	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException
	{
		Integer rst = TlvWrap.readInteter(request, TagDefine.TAG_RESULT, TagDefine.TAG_HEAD_LENGTH);
		if ( rst == null || rst != 0 )
			return null ;

		return new CommandTlv(TagDefine.COMMAND_CLASS_GATEWAY_CONNECT,TagDefine.COMMAND_SUB_CLASS_GATEWAY_INFO_REQUEST);
	}

}

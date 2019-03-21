package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;

import com.iremote.infraredtrans.tlv.CommandTlv;

public class RemoteSleepProcessor implements IRemoteRequestProcessor
{
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException
	{
		ConnectionManager.removeConnection(nbc);
		return null;
	}

}

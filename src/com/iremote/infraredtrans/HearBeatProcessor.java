package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;
import com.iremote.common.Utils;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;

public class HearBeatProcessor implements IRemoteRequestProcessor{

	private static final CommandTlv tlv = new CommandTlv(100 , 5);
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc)
			throws BufferOverflowException, IOException {
		
		int timeout = TlvWrap.readInt(request, 105 , 4);
		if ( timeout > 0 )
			nbc.setIdleTimeoutMillis( Utils.calculateTimeout(timeout));
		
		return tlv;
	}


}

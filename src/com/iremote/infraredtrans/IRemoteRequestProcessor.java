package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;
import com.iremote.infraredtrans.tlv.CommandTlv;

public interface IRemoteRequestProcessor {

	CommandTlv process(byte[] request ,IConnectionContext nbc) throws BufferOverflowException, IOException ;
}

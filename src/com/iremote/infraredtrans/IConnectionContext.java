package com.iremote.infraredtrans;

import java.io.IOException;

import com.iremote.infraredtrans.tlv.CommandTlv;

public interface IConnectionContext 
{
	void setAttachment(Remoter obj);
	Remoter getAttachment();
	String getDeviceid();
	String getRemoteAddress();
	int getRemotePort();
	void setIdleTimeoutMillis(int timeoutmillis);
	long getIdleTimeoutMillis();
	boolean isTimeout();
		
	boolean isOpen();
	void close()throws IOException ;
	
	void sendData(CommandTlv ct) ;
	void flush() ;
	
	int getConnectionHashCode();
}

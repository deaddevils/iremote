package com.iremote.infraredcode;

import java.io.IOException;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class ConnectionContextMock implements IConnectionContext {

	private Remoter attach;
	private String deviceid ;
	
	public ConnectionContextMock(String deviceid , Remoter attach) {
		super();
		this.attach = attach;
		this.deviceid = deviceid;
	}

	@Override
	public void close() throws IOException {

	}

	@Override
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void flush(){

	}

	@Override
	public Remoter getAttachment() {
		return attach;
	}

	@Override
	public String getDeviceid() {
		return deviceid;
	}

	@Override
	public String getRemoteAddress() {
		return null;
	}

	@Override
	public void setIdleTimeoutMillis(int timeoutmillis) {

	}

	@Override
	public boolean isOpen() {
		
		return false;
	}

	@Override
	public void sendData(CommandTlv ct){
		
		
	}

	@Override
	public long getIdleTimeoutMillis() {
		
		return 0;
	}
	
	@Override
	public void setAttachment(Remoter obj) {
		
	}
	
	@Override
	public boolean isTimeout()
	{
		return false;
	}

	@Override
	public int getConnectionHashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

}

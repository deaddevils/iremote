package com.iremote.mock;

import java.io.IOException;

import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class TestConnectionContext implements IConnectionContext {

	private String deviceid = "iRemote3006200000010";

	private Remoter remoter = new Remoter();
	
	public TestConnectionContext() {
		super();
	}

	public TestConnectionContext(String deviceid) {
		super();
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
	public Remoter getAttachment() {
		return remoter;
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
	public void flush(){
		

	}


	@Override
	public boolean isOpen() {
		
		return false;
	}

	@Override
	public void sendData(CommandTlv ct) {
		
		
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

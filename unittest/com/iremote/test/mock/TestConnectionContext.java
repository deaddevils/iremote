package com.iremote.test.mock;

import java.io.IOException;

import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.test.data.ConstDefine;

public class TestConnectionContext implements IConnectionContext {

	private String deviceid = ConstDefine.DEVICE_ID;

	private Remoter remote = new Remoter();
	public TestConnectionContext() {
		super();
	}

	public TestConnectionContext(String deviceid) {
		super();
		this.deviceid = deviceid;
		remote.setUuid(deviceid);
	}

	@Override
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void close() throws IOException {
		

	}

	@Override
	public Remoter getAttachment() {
		return remote;
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

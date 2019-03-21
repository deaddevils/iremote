package com.iremote.asiainfo.request;

import java.io.IOException;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class AsiainfoConnectionConext implements IConnectionContext {

	private String deviceid ;

	public AsiainfoConnectionConext(String deviceid) {
		super();
		this.deviceid = deviceid;
	}

	@Override
	public void close() throws IOException 
	{
		//Do nothing
	}

	@Override
	public void flush()
	{
		//Do nothing
	}


	@Override
	public Remoter getAttachment() 
	{
		return null;
	}

	@Override
	public String getDeviceid() 
	{
		return deviceid;
	}

	@Override
	public String getRemoteAddress() 	
	{
		//Do nothing
		return "asininfo" ;
	}

	@Override
	public void setIdleTimeoutMillis(int timeoutmillis) 
	{
		//Do nothing
	}
	
//	@Override
//	public void onresponse(byte[] content)
//	{
//		int sq = TlvWrap.readInt(content , 31 , TlvWrap.TAGLENGTH_LENGTH);
//		if ( sq !=  Integer.MIN_VALUE)
//		{
//			String id = deviceid + String.valueOf(sq);
//			SynchronizeRequestManager.response(id,"Asininfo_request", content);
//		}
//	}
	
	@Override
	public boolean isOpen() 
	{
		return false;
	}

	@Override
	public void sendData(CommandTlv ct)  {
		
		
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
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getConnectionHashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

}

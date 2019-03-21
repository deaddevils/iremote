package com.iremote.action.device.doorlock;

import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class DoorlockCommandCache implements IDoorlockOperationProcessor
{	
	private String deviceid ;
	private boolean hassendcommand = false ;
	private long expiretime = System.currentTimeMillis() + 30 * 1000;
	private CommandTlv ct;

	public DoorlockCommandCache(String deviceid, CommandTlv ct)
	{
		super();
		this.deviceid = deviceid;
		this.ct = ct;
	}

	@Override
	public void reportArrive(String deviceid, int nuid, byte[] report)
	{

	}

	@Override
	public long getExpireTime()
	{
		return expiretime;
	}

	@Override
	public void onGatewayOnline()
	{
		sendcommand();
	}

	@Override
	public void onGatewayOffline()
	{

	}

	@Override
	public int getStatus()
	{
		return 0;
	}

	@Override
	public String getMessage()
	{
		return null;
	}

	@Override
	public void init()
	{

	}

	@Override
	public void sendcommand()
	{
		if ( hassendcommand == true )
			return ;
		hassendcommand = true ;
		
		SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct , 10);
		this.expiretime = System.currentTimeMillis();
	}
	
	@Override
	public boolean isFinished()
	{
		return hassendcommand;
	}

}

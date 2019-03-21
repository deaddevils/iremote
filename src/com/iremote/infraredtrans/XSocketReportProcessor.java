package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;

import com.iremote.common.ServerRuntime;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;

public class XSocketReportProcessor extends ReportProcessor
{

	private static XSocketReportProcessor instance = new XSocketReportProcessor();
	private XSocketReportProcessor()
	{
		
	}
	
	public static XSocketReportProcessor getInstance()
	{
		return instance;
	}
	
	protected IRemoteRequestProcessor getProcessor(byte[] b) 
	{
		return ProcessorStore.getInstance().getProcessor(b , 0);
	}
	
	@Override
	protected void sendLoginRequest(IConnectionContext nbc)
	{
		if ( nbc.isOpen() == false )
			return ;
		
		LoginProcessor lp = new LoginProcessor();
		CommandTlv ct = lp.createLoginRquest();
		
		Remoter r = new Remoter();
		r.setToken(lp.getToken());
		r.setSoftversion(2);

		nbc.setAttachment(r);
		
		ct.addOrReplaceUnit(new TlvIntUnit(31 , nbc.getAttachment().getSequence() , 1));
		ct.addUnit(new TlvIntUnit(105 , ServerRuntime.getInstance().getGatewayheartbeattime() , 2));

		nbc.sendData(ct);
	}

}

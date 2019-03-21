package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.Utils;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class ReportProcessorRunner implements Runnable {

	private static Log log = LogFactory.getLog(ReportProcessorRunner.class);
	
	private IRemoteRequestProcessor pro;
	private String deviceid ;
	private byte[] request ;
	private IConnectionContext connection;
	private long taskIndentify ;

	public ReportProcessorRunner(IRemoteRequestProcessor pro, String deviceid , byte[] request, IConnectionContext connection , long taskIndentify) {
		super();
		this.pro = pro;
		this.deviceid = deviceid;
		this.request = request;
		this.connection = connection;
		this.taskIndentify = taskIndentify;
	}

	@Override
	public void run() 
	{
		CommandTlv ct = null ;
		try {
			log.info(pro.getClass().getName());
			ct = pro.process(request, connection);
		} catch (Throwable t) {
			log.error(t.getMessage(), t);
		} 
		if ( ct == null )
			return;
		
		try
		{
			connection.sendData(ct);
		}
		catch(Throwable t )
		{
			log.error("", t);
		}
	}
	
	public long getTaskIndentify()
	{
		return taskIndentify;
	}
}

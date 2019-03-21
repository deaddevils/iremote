package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.asycresponse.IAsyncResponse;
import com.iremote.common.taskmanager.StatusfulTaskManager;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;

public class SynchronizeRequestHelper {

	private static Log log = LogFactory.getLog(SynchronizeRequestHelper.class);
	private static StatusfulTaskManager<RemoteSendData> taskmgr = new StatusfulTaskManager<RemoteSendData>();
	
	public static IAsyncResponse asynchronizeRequest(String deviceid , CommandTlv ct , int timeoutsecond)
	{
		return asynchronizeRequest(deviceid, ct , timeoutsecond , 0 );
	}
	
	public static IAsyncResponse asynchronizeRequest(String deviceid , CommandTlv ct , int timeoutsecond , long taskid )
	{
		if ( timeoutsecond > IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND
				|| timeoutsecond == 0)
			timeoutsecond = IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND;
		
		ct.setDeviceid(deviceid);
		RemoteSendData rsd = new RemoteSendData(deviceid , ct  , timeoutsecond , taskid);
		asynchronizeRequest(rsd);
		return rsd ;
	}
	
	public static byte[] synchronizeRequest(String deviceid , CommandTlv ct , int timeoutsecond )
	{
		if ( timeoutsecond > IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND 
				|| timeoutsecond == 0)
			timeoutsecond = IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND;

		ct.setDeviceid(deviceid);
		RemoteSendData rsd = new RemoteSendData(deviceid , ct  , timeoutsecond , 0);
		if ( asynchronizeRequest(rsd) == false )
			return null ;
		
		Object rst = rsd.getAckResponse(timeoutsecond);
		
		if ( rst == null )
			return null ;
		return (byte[]) rst ;
	}
	
	public static boolean asynchronizeRequest(RemoteSendData rsd )
	{
		IConnectionContext nbc = ConnectionManager.getConnection(rsd.getDeviceid());
		if ( nbc == null || nbc.getAttachment() == null )
		{
			rsd.setAckresult(ErrorCodeDefine.DEVICE_OFFLINE);
			log.info("remote offline");
			return false;
		}

		Remoter remoter = (Remoter)nbc.getAttachment();

		int sequence = remoter.getSequence();
		
		log.info(sequence);
		rsd.getCommand().addOrReplaceUnit(new TlvIntUnit(31 , sequence , 2));
		
		long tid = System.currentTimeMillis();
		log.info(tid);
		rsd.setTaskIndentify(tid);
		SynchronizeRequestHelper.registRequest(rsd.getDeviceid(), sequence, rsd);
		
		taskmgr.addTask(rsd.getDeviceid(), rsd);
		
		return true;
	}
	
	public static void onSynchronizeResponse(IConnectionContext nbc , byte[] content)
	{
		if ( nbc == null )
			return ;
		Remoter remoter = nbc.getAttachment();
		if ( remoter == null )
		{
			log.trace("remoter is null");
			return  ;
		}
		
		int sequence = TlvWrap.readInt(content , 31 , TlvWrap.TAGLENGTH_LENGTH);
		log.trace(sequence);
		
		IAsyncResponse svo = remoter.getSynchronizeObject(sequence);
		if ( svo == null )
		{
			log.trace("svo is null");
			return ;
		}
		
		if ( log.isTraceEnabled())
			Utils.print("ack response", content);
		svo.onAckResponse(content);
		synchronized(svo)
		{
			svo.notifyAll();
		}
	}
	
	public static void registRequest(String deviceid , int sequence , IAsyncResponse requestobj )
	{
		IConnectionContext nbc = ConnectionManager.getConnection(deviceid);
		if ( nbc == null || nbc.getAttachment() == null )
		{
			log.info("remote offline");
			return ;
		}

		Remoter remoter = (Remoter)nbc.getAttachment();
		
		registRequest(remoter , sequence , requestobj);
		
	}
	
	private static void registRequest(Remoter remoter , int sequence , IAsyncResponse requestobj)
	{
		remoter.addSynchronizeObject(sequence , requestobj);
	}
	
	public static void sendData(String deviceid , CommandTlv ct) throws BufferOverflowException, IOException
	{
		IConnectionContext nbc = ConnectionManager.getConnection(deviceid);
		if ( nbc == null || nbc.getAttachment() == null )
		{
			log.info("remote offline");
			return ;
		}
		nbc.sendData(ct);
	}
	
	public static void shutdown()
	{
		taskmgr.shutdown();
	}

}

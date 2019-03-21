package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.taskmanager.MultiReportTaskManager;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.infraredtrans.zwavecommand.BatteryReportProcessor;
import com.iremote.infraredtrans.zwavecommand.IZwaveReportProcessor;
import com.iremote.infraredtrans.zwavecommand.ZwaveCommandProcessorStore;
import com.iremote.infraredtrans.zwavecommand.ZwaveReportBean;
import com.iremote.infraredtrans.zwavecommand.notifiy.ZwaveReportNotifyProcessor;
import com.iremote.service.ZWaveDeviceService;

public class ZWaveReportProcessor implements IRemoteRequestProcessor {
	
	private static Log log = LogFactory.getLog(ZWaveReportProcessor.class);
	private static MultiReportTaskManager<IZwaveReportProcessor> taskmgr = new MultiReportTaskManager<IZwaveReportProcessor>();

	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException 
	{
		ZwaveReportBean zrb = new ZwaveReportBean();
		ZWaveDevice zd = null ;
		
		try
		{
			
			log.info(zrb.getReportid());
			
			String deviceid = readdeviceid(request, nbc);
			zd = queryZwaveDevice(deviceid , request);
			
			if ( deviceid == null )
			{
				log.info("deviceid is null ");
				return createResult(request);
			}
			
			String devicetype;
			if ( zd != null )
				devicetype = zd.getDevicetype();
			else 
				devicetype = DeviceHelper.getDeviceType(TlvWrap.readInt(request, 71, 4));
			if ( devicetype == null )
				devicetype = "0";
			
			zrb.setRemoter((Remoter)nbc.getAttachment());
			zrb.setReport(deviceid , request) ;
			
			if ( zrb.getCommandvalue() == null )
			{
				log.info("report is null ");
				return createResult(request);
			}
			
			IZwaveReportProcessor pro = ZwaveCommandProcessorStore.getInstance().getProcessor(zrb.getCommandvalue().getCmd(), devicetype);
			
			if ( pro != null )
			{
				if ( zd != null && zd.getBattery() != null && zd.getBattery() <= 10 && !(pro instanceof BatteryReportProcessor))
					log.warn("discards report from lowbattery device.");
				else 
				{
					pro.setReport(zrb);
					pro.setNoSessionZwaveDevice(zd);
					taskmgr.addTask(deviceid, pro);
				}
			}
			else 
				log.info("Processor is null ");
			
			notifyReport(deviceid , zrb);
		}
		catch(Throwable t )
		{
			log.error(t.getMessage() , t);
		}
		
		if ( isCobbeBadLock(zrb))
			return null ;
				
		return createResult(request);
	}
	
	private static final Byte[] OPEN_DOORLOCK_REPORT = new Byte[]{(byte)0x80 , 0x07 , 0x00 , (byte)0xB1};
	private boolean isCobbeBadLock(ZwaveReportBean zrb)
	{
		if ( zrb.getDeviceid().startsWith("iRemote30061"))
		{
			if ( Utils.isByteMatch(OPEN_DOORLOCK_REPORT , zrb.getCmd())
					&& ( (zrb.getCmd()[8] & 0xff) != IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_PASSWORD  // not password user 
					|| !Utils.isLockTempPassordforSMSSend(zrb.getCmd()[9] & 0xff) ))  //not temp password 
			{	
				int id = 0 ; 
				try
				{
					id = Integer.valueOf(zrb.getDeviceid().substring(12));
					if ( ( id >= 100 && id <= 426 ) ||  id == 9 || id == 10 )
						return true ;  //do not response
				}
				catch(Throwable t)
				{
					log.error(t.getMessage() , t);
				}
				 
			}
		}
		return false ;
	}
	
	private void notifyReport(String deviceid ,ZwaveReportBean zrb)
	{
		ZwaveReportNotifyProcessor pro = new ZwaveReportNotifyProcessor();
		pro.setReport(zrb);
		
		taskmgr.addTask(deviceid, pro);
	}

	private ZWaveDevice queryZwaveDevice(String deviceid , byte[] request)
	{
		if ( deviceid == null )
			return null ;
		int nuid = TlvWrap.readInt(request, 71, 4);
		
		ZWaveDeviceService svr = new ZWaveDeviceService();
		return svr.querybydeviceid(deviceid , nuid);
	}
	
	private String readdeviceid(byte[] request, IConnectionContext nbc)
	{
		String deviceid = TlvWrap.readString(request, 2, 4);
		if ( deviceid == null || deviceid.length() == 0 )
			deviceid = nbc.getDeviceid();
		
		return deviceid ;
	}
	
	private CommandTlv createResult(byte[] request)
	{
		byte[] sequence = TlvWrap.readTag(request, 31, 4);
		CommandTlv rst = new CommandTlv(30 , 10);
		
		if (sequence != null)
			rst.addOrReplaceUnit(new TlvByteUnit(31,sequence));
		return rst ;
	}
	
	public static void shutdown()
	{
		taskmgr.shutdown();
	}
}

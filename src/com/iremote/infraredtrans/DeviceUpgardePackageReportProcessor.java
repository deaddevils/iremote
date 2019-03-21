package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.device.doorlock.DoorlockOperationStore;
import com.iremote.action.device.doorlock.IDoorlockOperationProcessor;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.ZWaveDeviceService;

public class DeviceUpgardePackageReportProcessor implements IRemoteRequestProcessor
{

	private static Log log = LogFactory.getLog(DeviceUpgardePackageReportProcessor.class);
	
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException
	{
		String deviceid = nbc.getDeviceid();
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		
		List<ZWaveDevice> lst = zds.querybydeviceid(deviceid);
		
		for ( ZWaveDevice zd : lst  )
		{
			IDoorlockOperationProcessor setter = DoorlockOperationStore.getInstance().get(String.valueOf(zd.getZwavedeviceid()));
			if ( setter != null )
				setter.reportArrive(deviceid, zd.getNuid(), request);
			else 
				log.info("no upgrade processor");
		}
		
		return null;
	}

}

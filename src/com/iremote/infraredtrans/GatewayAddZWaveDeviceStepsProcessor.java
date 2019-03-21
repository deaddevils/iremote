package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.Calendar;
import java.util.Date;

import com.iremote.action.device.DeleteZwaveDeviceAction;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.constant.GatewayAddZWaveDeviceSteps;
import com.iremote.common.jms.JMSUtil;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.event.gateway.AddzWaveDeviceStepEvent;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.DeviceOperationStepsService;

public class GatewayAddZWaveDeviceStepsProcessor implements IRemoteRequestProcessor
{
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException 
	{
		Integer step = TlvWrap.readInteter(request, TagDefine.TAG_GATEWAY_ADD_ZWAVE_DEVICE_STEP, TagDefine.TAG_HEAD_LENGTH);

		if ( step == null )
			return null;
		
		String deviceid = nbc.getDeviceid();
		
		DeviceOperationStepsService doss = new DeviceOperationStepsService();
		DeviceOperationSteps dos = doss.querybydeviceidandtype(deviceid,DeviceOperationType.addzWavedevice);
		
		if ( dos == null )
		{
			dos = new DeviceOperationSteps();
			dos.setStarttime(new Date());
			dos.setDeviceid(deviceid);
			dos.setOptype(DeviceOperationType.addzWavedevice.ordinal());
		}
		
		if ( step != GatewayAddZWaveDeviceSteps.finished.getStep() )
			dos.setStatus(step);

		if ( step == GatewayAddZWaveDeviceSteps.failed.getStep())
		{
			dos.setFinished(true);
			dos.setExpiretime(new Date());
			dos.setAppendmessage(null);
		}
		else 
		{
			dos.setFinished(false);
			Calendar c = Calendar.getInstance();
			
			if ( step == GatewayAddZWaveDeviceSteps.delete.getStep())
			{
				c.add(Calendar.SECOND, 30);
				dos.setExpiretime(c.getTime());
			}
			else if ( step == GatewayAddZWaveDeviceSteps.add.getStep())
			{
				c.add(Calendar.SECOND, 90);
				dos.setExpiretime(c.getTime());
			}
		}
		
		doss.saveOrUpdate(dos);
		
		if ( step == GatewayAddZWaveDeviceSteps.add.getStep() )
		{
			Integer nuid = TlvWrap.readInteter(request, TagDefine.TAG_NUID, TagDefine.TAG_HEAD_LENGTH);
			if ( nuid != null )
				deleteZwaveDevice(nbc.getDeviceid() , nuid);
		}
		
		JMSUtil.sendmessage(IRemoteConstantDefine.EVENT_Add_ZWAVE_DEVICE_STEP, new AddzWaveDeviceStepEvent(deviceid , new Date() , step , System.currentTimeMillis() ));
		
		return null;
	}
	
	private void deleteZwaveDevice(String deviceid , int nuid)
	{
		DeleteZwaveDeviceAction action = new DeleteZwaveDeviceAction();
		action.deleteZwaveDevice(deviceid, nuid);
	}

}

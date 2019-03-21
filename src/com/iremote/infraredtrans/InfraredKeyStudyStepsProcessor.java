package com.iremote.infraredtrans;

import com.iremote.action.device.DeleteZwaveDeviceAction;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.constant.GatewayAddZWaveDeviceSteps;
import com.iremote.common.constant.GatewayInfrareddeviceStudySteps;
import com.iremote.common.jms.JMSUtil;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.InfraredKey;
import com.iremote.event.gateway.AddzWaveDeviceStepEvent;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.DeviceOperationStepsService;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.InfraredKeyService;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.Calendar;
import java.util.Date;

public class InfraredKeyStudyStepsProcessor implements IRemoteRequestProcessor
{
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException 
	{
		Integer step = TlvWrap.readInteter(request, TagDefine.TAG_GATEWAY_INFRAREKEYSTUDY_STEP, TagDefine.TAG_HEAD_LENGTH);

		if ( step == null )
			return null;
		
		String deviceid = nbc.getDeviceid();
		DeviceOperationStepsService doss = new DeviceOperationStepsService();
		DeviceOperationSteps dos = doss.querybydeviceidandtype(deviceid,DeviceOperationType.remoteInfrareddeviceKey);
		if(dos == null)
			return null;

		InfraredDeviceService ids = new InfraredDeviceService();
		InfraredDevice infraredDevice = ids.query(dos.getInfrareddeviceid());
		if(infraredDevice == null)
			return null;

		dos.setExpiretime(new Date());
		dos.setAppendmessage(null);
		dos.setStatus(GatewayInfrareddeviceStudySteps.study.ordinal());
		doss.saveOrUpdate(dos);

		return null;
	}
	
}

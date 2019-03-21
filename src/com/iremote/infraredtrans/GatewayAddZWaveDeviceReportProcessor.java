package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.action.device.AddZWaveDeviceAction;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.common.TagDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.common.constant.GatewayAddZWaveDeviceSteps;
import com.iremote.domain.DeviceInitSetting;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.service.DeviceOperationStepsService;

public class GatewayAddZWaveDeviceReportProcessor implements IRemoteRequestProcessor
{
	private String name ;
	private DeviceOperationSteps dos;
	
	@Override
	public CommandTlv process(byte[] request, IConnectionContext nbc) throws BufferOverflowException, IOException 
	{
		CommandTlv rct = new CommandTlv(TagDefine.COMMAND_CLASS_DEVICE_MANAGER , TagDefine.COMMAND_SUB_CLASS_ADD_ZWAVEDEVICE_RESPONSE);
		
		Integer sequence = TlvWrap.readInteter(request, TagDefine.TAG_SEQUENCE,TagDefine.TAG_HEAD_LENGTH);
		if ( sequence != null )
			rct.addUnit(new TlvIntUnit(TagDefine.TAG_SEQUENCE , sequence , TagDefine.TAG_LENGTH_2));
		
		Integer nuid = TlvWrap.readInteter(request, TagDefine.TAG_NUID, TagDefine.TAG_HEAD_LENGTH);
		if ( nuid == null )
		{
			rct.addUnit(new TlvIntUnit(TagDefine.TAG_RESULT , ErrorCodeDefine.PARMETER_ERROR , TagDefine.TAG_LENGTH_2));
			return rct ;
		}

		queryDeviceName(nbc.getDeviceid());
		
		AddZWaveDeviceAction action = new AddZWaveDeviceAction();
		action.setDeviceid(nbc.getDeviceid());
		action.setNuid(nuid);
		action.setName(name);
		int rst = action.addZWaveDevice(request);
		dos.setZwavedeviceid(action.getZwavedeviceid() != 0 ? action.getZwavedeviceid() : 0);

		sendInitCommand(nbc.getDeviceid() , nuid , action.getDeviceinitsetting());
		
		updateDeviceOperationSteps( rst == ErrorCodeDefine.SUCCESS, action.getDeviceinitsetting());
		
		rct.addUnit(new TlvIntUnit(TagDefine.TAG_RESULT ,rst , TagDefine.TAG_LENGTH_2));
		return rct ;
	}
	
	private void queryDeviceName(String deviceid)
	{
		DeviceOperationStepsService doss = new DeviceOperationStepsService();
		dos = doss.querybydeviceidandtype(deviceid,DeviceOperationType.addzWavedevice);
		
		if ( dos == null )
			return ;
		name = dos.getAppendmessage();
		dos.setAppendmessage(null);
	}
	
	private void updateDeviceOperationSteps( boolean success, DeviceInitSetting dis)
	{
		if ( dos == null )
			return ;
		dos.setFinished(true);
		
		if ( success )
			dos.setStatus(GatewayAddZWaveDeviceSteps.finished.getStep());
		else if ( dis != null )
			dos.setStatus(GatewayAddZWaveDeviceSteps.failed.getStep());
		else if ( dis == null )
			dos.setStatus(GatewayAddZWaveDeviceSteps.unknowndevice.getStep());
	}

	private void sendInitCommand(String deviceid , int nuid , DeviceInitSetting dis )
	{
		if ( dis == null )
			return ;
		
		String ic = dis.getInitcmds();
		
		if ( StringUtils.isBlank(ic))
			return ;
		
		JSONArray ja = JSON.parseArray(ic);
		
		if ( ja == null || ja.size() == 0 )
			return ;
		
		for ( int i = 0 ; i < ja.size() ; i ++ )
		{
			String sc = ja.getString(i);
			byte[] b = JWStringUtils.hexStringtobyteArray(sc);
			CommandTlv ct = CommandUtil.createCommandTlv(b, nuid);
			SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct, 1);
		}
			
	}
	
}

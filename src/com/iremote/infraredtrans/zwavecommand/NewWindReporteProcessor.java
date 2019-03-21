package com.iremote.infraredtrans.zwavecommand;

import java.util.Arrays;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;

public class NewWindReporteProcessor extends ZWaveReportBaseProcessor{
	
	public NewWindReporteProcessor() {
		super();
		super.dontsavenotification();
	}
	
	@Override
	public String getMessagetype() {
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}
	
	@Override
	protected void updateDeviceStatus() {
		ZWaveDevice zWaveDevice = zrb.getDevice();
		
		int type = zrb.getCommandvalue().getChannelid();
		
		if(type == IRemoteConstantDefine.DEVICE_NEW_WIND_SWITCH){
			
			zWaveDevice.setStatus(getStatus());
			zrb.getCommandvalue().setChannelid(0);
			
		}else if(type == IRemoteConstantDefine.DEVICE_NEW_WIND_GEAR){
			
			zWaveDevice.setStatuses(getStatuses());
			zrb.getCommandvalue().setChannelid(0);
		}
		
		
	}
	
	public String getStatuses(){
		int value = zrb.getCommandvalue().getCmd()[2] & 0xff;
		
		String statuses[] = new String[1];
		
		statuses[0] = String.valueOf(value);
		return Arrays.toString(statuses).replace(" ", "");
	}
	
	public int getStatus(){
		return zrb.getCommandvalue().getCmd()[2]  & 0xff;
	}
	
	public String getStatu(){
		return null;
	}
}

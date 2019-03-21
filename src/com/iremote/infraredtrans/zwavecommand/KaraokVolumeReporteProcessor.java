package com.iremote.infraredtrans.zwavecommand;

import java.util.Arrays;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;

public class KaraokVolumeReporteProcessor extends ZWaveReportBaseProcessor{

	public int volumeType;
	public int volume;
	public ZWaveDevice zWaveDevice;
	
	@Override
	public String getMessagetype() {
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}
	
	public KaraokVolumeReporteProcessor() {
		super();
		super.dontsavenotification();
	}
	
	@Override
	protected void updateDeviceStatus() {
		ZWaveDeviceService zWaveDeviceService = new ZWaveDeviceService();
		zWaveDevice = zrb.getDevice();
		zWaveDevice.setStatuses(getStatuses());
		
		zWaveDeviceService.saveOrUpdate(zWaveDevice);
	}
	
	public String getStatuses(){
		//volume = zrb.getCmd()[2];
		volumeType = zrb.getCommandvalue().getChannelid();
		volume = zrb.getCommandvalue().getCmd()[2];
		String statuses = zWaveDevice.getStatuses();
		String statusess[] = null;
		if(statuses == null || statuses.length() < 1){
			statuses = IRemoteConstantDefine.DEVICE_KARAOK_DEFAULT_STATUES;
		}
		statuses = statuses.substring(1, statuses.length() - 1);	
		statusess = statuses.split(",");
		
		if(volumeType == IRemoteConstantDefine.DEVICE_KARAOK_VOLUME_MUSIC){
			statusess[1] = String.valueOf(volume);
		}else if(volumeType == IRemoteConstantDefine.DEVICE_KARAOK_VOLUME_MIC){
			statusess[2] = String.valueOf(volume);
		}else if(volumeType == IRemoteConstantDefine.DEVICE_KARAOK_VOLUME_EFFECTS){
			statusess[3] = String.valueOf(volume);
		}
		
		return Arrays.toString(statusess).replace(" ", "");
	}
}

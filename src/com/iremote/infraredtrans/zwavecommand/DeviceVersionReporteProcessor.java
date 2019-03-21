package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.ZWaveDevice;

import java.util.Arrays;

public class DeviceVersionReporteProcessor extends ZWaveReportBaseProcessor{

	public DeviceVersionReporteProcessor() {
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
		if(zWaveDevice == null)
			return;
		String version = null;
		byte[] b = zrb.getCommandvalue().getCmd();
		if(b.length > 6){
			String application_version = String.valueOf(b[5] & 0xff);
			String application_sub_version = String.valueOf(b[6] & 0xff);
			version = application_version + "." + application_sub_version;
			zrb.getDevice().setVersion3(version);
		}
	}
	
}

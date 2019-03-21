package com.iremote.infraredtrans.zwavecommand;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iremote.action.helper.DeviceHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.ZWaveSubDevice;

public class TwoChannelSwitchScenePanelBinarySwitchReportProcessor extends ZWaveReportBaseProcessor {
	
	public TwoChannelSwitchScenePanelBinarySwitchReportProcessor() {
		super();
		super.dontsavenotification();
	}

	protected void updateDeviceStatus()
	{
		if ( zrb.getPhoneuser() != null )
			DeviceHelper.createSubDevice(zrb.getDevice(), zrb.getRemote().getPlatform(), zrb.getPhoneuser().getLanguage());
		int status = zrb.getCommandvalue().getValue() ;
		
		if ( status != 0xff && status != 0x00 )
			return;
		
		String s = zrb.getDevice().getStatuses();
		
		if ( s == null )
			s = Utils.getDeviceDefaultStatuses(zrb.getDevice().getDevicetype());
		
		JSONArray ja = JSON.parseArray(s);

		int channel = zrb.getCommandvalue().getChannelid();
		if ( channel == 0 )  
			channel = 1 ;
		zrb.getCommandvalue().setChannelid(channel);
		ja.set( channel - 1 , status);
		
		zrb.getDevice().setStatuses(ja.toJSONString());
		
		ZWaveSubDevice zsd = DeviceHelper.findZWaveSubDevice(zrb.getDevice().getzWaveSubDevices(), channel);
		if ( zsd != null )
			zsd.setStatus(status);
	}

	@Override
	public String getMessagetype() {
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}
	
}

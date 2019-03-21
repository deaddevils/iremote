package com.iremote.event.device.doorlock;

import java.util.List;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteOnlineEvent;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;

public class QueryDoorlockFunctionVersionProcessor extends RemoteOnlineEvent implements ITextMessageProcessor
{

	@Override
	public void run()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> lst = zds.querybydeviceidtypelist(super.getDeviceid(), IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK);
		
		if ( lst == null || lst.size() == 0 )
			return ;
		
		for ( ZWaveDevice zd : lst )
			DeviceHelper.readDeviceProductor(zd);
	}

	@Override
	public String getTaskKey()
	{
		return super.getDeviceid();
	}

}

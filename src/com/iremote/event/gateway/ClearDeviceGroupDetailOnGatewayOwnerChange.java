package com.iremote.event.gateway;

import java.util.List;

import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteEvent;
import com.iremote.domain.DeviceGroupDetail;
import com.iremote.service.DeviceGroupDetailService;
import com.iremote.service.DeviceGroupService;
import com.iremote.service.ZWaveDeviceService;

public class ClearDeviceGroupDetailOnGatewayOwnerChange extends RemoteEvent implements ITextMessageProcessor
{

	@Override
	public void run()
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<Integer> zdidl = zds.queryidbydeviceid(super.getDeviceid());
		
		DeviceGroupDetailService dgds = new DeviceGroupDetailService();
		List<DeviceGroupDetail> dgdl = dgds.querybyZwavedeviceid(zdidl);
		
		if ( dgdl == null || dgdl.size() == 0 )
			return ;
		
		DeviceGroupService dgs = new DeviceGroupService();
		for ( DeviceGroupDetail dgd : dgdl  )
		{
			dgd.getDevicegroup().getZwavedevices().remove(dgd);
			if ( dgd.getDevicegroup().getZwavedevices().isEmpty())
				dgs.delete(dgd.getDevicegroup());
		}
	}

	@Override
	public String getTaskKey()
	{
		return super.getDeviceid();
	}

}

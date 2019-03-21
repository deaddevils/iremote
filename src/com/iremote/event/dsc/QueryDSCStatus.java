package com.iremote.event.dsc;

import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteOnlineEvent;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.service.ZWaveDeviceService;

public class QueryDSCStatus extends RemoteOnlineEvent implements ITextMessageProcessor{

	@Override
	public void run() 
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> lst = zds.querybydeviceidtype(super.getDeviceid(), IRemoteConstantDefine.DEVICE_TYPE_DSC);
		
		if ( lst == null || lst.size() == 0 )
			return ;
				
		SynchronizeRequestHelper.asynchronizeRequest(super.getDeviceid(), CommandUtil.createDscCommand("001"), 1);
	}

	@Override
	public String getTaskKey() 
	{	
		return super.getDeviceid();
	}

}

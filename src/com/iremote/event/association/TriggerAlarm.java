package com.iremote.event.association;

import java.util.List;

import com.iremote.common.commandclass.CommandUtil;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.ZWaveDeviceService;

public class TriggerAlarm extends ZWaveDeviceEvent implements ITextMessageProcessor{
	
	@Override
	public void run() 
	{
		ZWaveDeviceService zds = new ZWaveDeviceService();
		List<ZWaveDevice> lst = zds.queryAlarmDevice(getDeviceid());
		if ( lst == null || lst.size() == 0 )
			return ;
		
		for ( ZWaveDevice d : lst )
		{
			CommandTlv ct = CommandUtil.createAlarmCommand(d.getNuid());
			SynchronizeRequestHelper.synchronizeRequest(d.getDeviceid(), ct , 0);
		}
	}


	@Override
	public String getTaskKey() 
	{
		return getDeviceid();
	}

}

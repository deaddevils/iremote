package com.iremote.thirdpart.dorlink.event;

import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceUnalarmEvent;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

@Deprecated
public class DorlinkUserUnwarningEventProcessor extends ZWaveDeviceUnalarmEvent implements ITextMessageProcessor
{
	@Override
	public void run()
	{
		if ( !DorlinkEventHelper.isDorlinkGateway(super.getDeviceid()))
			return ;

		int tpid = DorlinkEventHelper.queryThirdpartid();
		
		if ( tpid == 0 )
			return ;
		
		EventtoThirdpart etd = new EventtoThirdpart();
		
		etd.setThirdpartid(tpid);
		etd.setType(getEventtype());
		etd.setDeviceid(getDeviceid());
		etd.setEventtime(getEventtime());
		etd.setZwavedeviceid(getZwavedeviceid());
		
		EventtoThirdpartService svr = new EventtoThirdpartService();
		svr.save(etd);
	}

	@Override
	public String getTaskKey()
	{
		return getDeviceid();
	}

}

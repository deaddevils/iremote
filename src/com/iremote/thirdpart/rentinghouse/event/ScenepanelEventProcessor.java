package com.iremote.thirdpart.rentinghouse.event;

import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

public class ScenepanelEventProcessor extends ZWaveDeviceEvent implements ITextMessageProcessor
{

	@Override
	public void run()
	{
		List<Integer> tidlst = ThirdPartHelper.queryThirdpartid(super.getDeviceid());
		
		for ( Integer tpid : tidlst )
		{
			if ( tpid == null || tpid == 0 )
				continue ;
		
			EventtoThirdpart etd = new EventtoThirdpart();
			
			etd.setThirdpartid(tpid);
			etd.setType(IRemoteConstantDefine.MESSGAE_TYPE_TRIGGER);
			etd.setDeviceid(getDeviceid());
			etd.setEventtime(getEventtime());
			etd.setZwavedeviceid(getZwavedeviceid());
			etd.setIntparam(this.getChannel());
			
			EventtoThirdpartService svr = new EventtoThirdpartService();
			svr.save(etd);
		}
		
	}

	@Override
	public String getTaskKey()
	{
		return getDeviceid();
	}

}

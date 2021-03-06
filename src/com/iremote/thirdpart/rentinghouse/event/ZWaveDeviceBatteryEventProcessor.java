package com.iremote.thirdpart.rentinghouse.event;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceBatteryEvent;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

public class ZWaveDeviceBatteryEventProcessor extends ZWaveDeviceBatteryEvent implements ITextMessageProcessor {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(ZWaveDeviceBatteryEventProcessor.class);
	
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
			etd.setType(getEventtype());
			etd.setDeviceid(getDeviceid());
			etd.setEventtime(getEventtime());
			etd.setIntparam(getBattery());
			etd.setZwavedeviceid(getZwavedeviceid());
			
			EventtoThirdpartService svr = new EventtoThirdpartService();
			svr.save(etd);
		}
	}

	@Override
	public String getTaskKey() 
	{	
		return getDeviceid();
	}
	
	@Deprecated
	protected Integer queryThirdpartid()
	{
		ComunityRemoteService crs = new ComunityRemoteService();
		ComunityRemote cr = crs.querybyDeviceid(getDeviceid());
		if ( cr == null )
			return null;
		return cr.getThirdpartid();
	}

}

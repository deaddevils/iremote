package com.iremote.thirdpart.rentinghouse.event;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

public class DeviceStatusChangeProcessor extends ZWaveDeviceEvent implements ITextMessageProcessor {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(DeviceStatusChangeProcessor.class);
	

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
			etd.setZwavedeviceid(getZwavedeviceid());
			etd.setIntparam(getStatus());
			etd.setFloatparam(getFstatus());
			etd.setEventtime(getEventtime());
			etd.setObjparam(super.getStatuses());
			etd.setWarningstatus(super.getWarningstatus());
			etd.setWarningstatuses(super.getWarningstatuses());
			
			EventtoThirdpartService svr = new EventtoThirdpartService();
			svr.save(etd);
		}
	}


	@Override
	public String getTaskKey() {
		return getDeviceid();
	}

}

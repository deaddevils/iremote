package com.iremote.thirdpart.rentinghouse.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.domain.ZwaveDeviceEventPushValues;
import com.iremote.service.ZwaveDeviceEventPushValuesService;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

public class MeterStatusChangeProcessor extends ZWaveDeviceEvent implements ITextMessageProcessor{

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(MeterStatusChangeProcessor.class);
		
	@Override
	public void run() 
	{	
		ComunityRemoteService crs = new ComunityRemoteService();
		ComunityRemote cr = crs.querybyDeviceid(getDeviceid());
		if ( cr == null )
			return ;
		
		int zwdid = getZwavedeviceid();
		
//		if ( pv.getLastsendtime() != null 
//				&& pv.getLastsendtime().getTime() > ( System.currentTimeMillis() - 10 * 60 * 1000 )
//				&& Math.abs(getFstatus() - pv.getMetervalue()) < 0.5)
//			return ;
		
		EventtoThirdpart etd = new EventtoThirdpart();
		
		etd.setThirdpartid(cr.getThirdpartid());
		etd.setType(getEventtype());
		etd.setDeviceid(getDeviceid());
		etd.setZwavedeviceid(getZwavedeviceid());
		etd.setIntparam(getStatus());
		etd.setFloatparam(getFstatus());
		etd.setEventtime(getEventtime());
		
		EventtoThirdpartService svr = new EventtoThirdpartService();
		svr.save(etd);
		
		ZwaveDeviceEventPushValuesService pvsvr = new ZwaveDeviceEventPushValuesService();
		ZwaveDeviceEventPushValues pv = pvsvr.querybyZwavedeviceid(zwdid);
		
		if ( pv == null )
			return ;
		
		if ( pv.getFloatappendparam() != null )
		{
			EventtoThirdpart cetd = new EventtoThirdpart();
			
			cetd.setThirdpartid(cr.getThirdpartid());
			cetd.setType(IRemoteConstantDefine.WARNING_TYPE_ELECTRIC_CURRENT);
			cetd.setDeviceid(getDeviceid());
			cetd.setZwavedeviceid(getZwavedeviceid());
			cetd.setIntparam(getStatus());
			cetd.setFloatparam(pv.getFloatappendparam());
			cetd.setEventtime(getEventtime());
			
			svr.save(cetd);
		}
	}

	@Override
	public String getTaskKey() {
		return getDeviceid();
	}
}

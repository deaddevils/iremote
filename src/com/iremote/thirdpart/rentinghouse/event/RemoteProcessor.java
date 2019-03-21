package com.iremote.thirdpart.rentinghouse.event;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteEvent;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

public abstract class RemoteProcessor extends RemoteEvent implements ITextMessageProcessor {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(RemoteProcessor.class);
	
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
			etd.setType(getType());
			etd.setDeviceid(getDeviceid());
			etd.setEventtime(getEventtime());
			
			EventtoThirdpartService svr = new EventtoThirdpartService();
			svr.save(etd);
		}

	}
	
	@Deprecated
	protected Integer queryThirdpartid()
	{
		ComunityRemoteService crs = new ComunityRemoteService();
		ComunityRemote comnunityremote = crs.querybyDeviceid(getDeviceid());
		if ( comnunityremote == null )
			return null;
		return comnunityremote.getThirdpartid();
	}
	
	protected abstract String getType();

	@Override
	public String getTaskKey() 
	{
		return getDeviceid();
	}

}

package com.iremote.thirdpart.rentinghouse.event;

import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteOnlineEvent;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

public class RemoteOnlineProcessor extends RemoteOnlineEvent implements ITextMessageProcessor
{
	@Override
	public void run()
	{
		if ( this.isHaslogout() == false )
			return ;
		
		List<Integer> tidlst = ThirdPartHelper.queryThirdpartid(super.getDeviceid());
		
		for ( Integer tpid : tidlst )
		{
			if ( tpid == null || tpid == 0 )
				continue ;
							
			EventtoThirdpart etd = new EventtoThirdpart();
			
			etd.setThirdpartid(tpid);
			etd.setType(IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE);
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

	@Override
	public String getTaskKey()
	{
		return getDeviceid();
	}

}

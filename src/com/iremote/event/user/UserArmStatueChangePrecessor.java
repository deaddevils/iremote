package com.iremote.event.user;

import java.util.Date;
import java.util.List;

import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.UserArmEvent;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.domain.Remote;
import com.iremote.service.RemoteService;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

public class UserArmStatueChangePrecessor extends UserArmEvent implements ITextMessageProcessor
{

	@Override
	public void run() 
	{
		RemoteService rs = new RemoteService();
		List<Remote> lst = rs.querybyPhoneUserid(super.getPhoneuserid());
		
		if ( lst == null || lst.size() == 0 )
			return ;
		
		for ( Remote r : lst )
		{
	    	List<Integer> tidlst = ThirdPartHelper.queryThirdpartid(r.getDeviceid());
			
	    	if ( tidlst == null || tidlst.size() == 0)
	    		return ;
	
			for ( Integer tpid : tidlst )
			{
				if ( tpid == null || tpid == 0 )
					continue ;
				
				EventtoThirdpart etd = new EventtoThirdpart();
				
				etd.setThirdpartid(tpid);
				etd.setType(super.getArmtype());
				etd.setDeviceid(r.getDeviceid());
				etd.setEventtime(new Date());
				etd.setIntparam(super.getArmstatus());
	
				EventtoThirdpartService svr = new EventtoThirdpartService();
				svr.save(etd);
			}
		}
	}

	@Override
	public String getTaskKey() 
	{
		return String.valueOf(super.getPhoneuserid());
	}

}

package com.iremote.thirdpart.rentinghouse.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.push.PushMessage;
import com.iremote.domain.PhoneUser;
import com.iremote.service.PhoneUserService;
import com.iremote.service.ZWaveDeviceShareService;

public class ExpireZwaveDeviceShareTask implements Runnable {

	private static Log log = LogFactory.getLog(ExpireZwaveDeviceShareTask.class);
	
	@Override
	public void run() 
	{
		
		for ( ; ;  )
		{
			try
			{
				HibernateUtil.prepareSession(IRemoteConstantDefine.HIBERNATE_SESSION_REMOTE);
				process();
			}
			catch(Throwable t )
			{
				log.error(t.getMessage() , t);
			}
			finally
			{
				HibernateUtil.closeSession();
			}
			
			try {
				Thread.sleep(10 * 60 * 1000);
			} catch (InterruptedException e) {
				return ;
			}
		}
		

	}

	private void process()
	{
		ZWaveDeviceShareService svr = new ZWaveDeviceShareService();
		List<Integer> lst = svr.queryPhoneUseridofJustExpiredShare();
		
		Set<Integer> set = new HashSet<Integer>();
		set.addAll(lst);
		
		PhoneUserService pus = new PhoneUserService();
		List<PhoneUser> pul = pus.query(set);

		Map<Integer , List<String>> map = groupUserAlias(pul) ;
		
		for ( Entry<Integer , List<String>> e : map.entrySet())
			PushMessage.pushInfoChangedMessage(e.getValue().toArray(new String[0]) , e.getKey());
		
	}
	
	private Map<Integer , List<String>> groupUserAlias(List<PhoneUser> pul)
	{
		Map<Integer , List<String>> map = new HashMap<Integer , List<String>>();
		
		for ( PhoneUser pu : pul )
		{
			List<String> l = map.get(pu.getPlatform());
			if ( l == null )
			{
				 l = new ArrayList<String>();
				 map.put(pu.getPlatform(), l);
			}
			l.add(pu.getAlias());
		}
		return map;
	}
}

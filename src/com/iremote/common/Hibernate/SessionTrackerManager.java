package com.iremote.common.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.iremote.common.Utils;


public class SessionTrackerManager implements Runnable {

	private static Log log = LogFactory.getLog(SessionTrackerManager.class);
	
	private static SessionTrackerManager instance = new SessionTrackerManager();
	
	private Map<Session , SessionTracker> map = new ConcurrentHashMap<Session , SessionTracker>();
	
	private static final int EXPIRE_TIME = 2 ;
	
	private SessionTrackerManager()
	{
		
	}
	
	public static SessionTrackerManager getInstance()
	{
		return instance ;
	}
	
	public void track(SessionTrackException e, Session session)
	{
		map.put(session, new SessionTracker(e));
	}
	
	public void remove(Session session)
	{
		if ( session == null )
			return ;
		map.remove(session);
	}

	@Override
	public void run() 
	{
		for ( ; ; )
		{
			try {
				Thread.sleep(EXPIRE_TIME * 60 * 1000);
			} catch (InterruptedException e) {
				return ;
			}
			
			long t = System.currentTimeMillis() - EXPIRE_TIME * 60 * 1000;
			long tt = System.currentTimeMillis() - 4 * EXPIRE_TIME * 60 * 1000;
			List<Session> rmlst = new ArrayList<Session>();
			
			try
			{
				for ( Map.Entry<Session,  SessionTracker> me : map.entrySet() )
				{
					SessionTracker st = me.getValue();
					
					if ( st.getTime().getTime() < tt )
					{
						log.error(String.format("force release session open at %s , %s " , Utils.formatTime(st.getTime()) , st.getTracker().getMessage()), st.getTracker());
						Session s = me.getKey();
						if ( s.isOpen() && s.getTransaction() != null )
						{
							try
							{
								s.getTransaction().rollback();
							}
							catch(Throwable th){}
						}
						try
						{
							s.close();
						}
						catch(Throwable th){}
						rmlst.add(s);
					}
					else if ( st.getTime().getTime() <= t )
					{
						log.error(String.format("Session open at %s is still not released , %s " , Utils.formatTime(st.getTime()), st.getTracker().getMessage()), st.getTracker());
					}
				}
			}
			catch(Throwable ta)
			{
				log.error(ta.getMessage() , ta);
			}
			for ( Session s : rmlst)
				map.remove(s);
		}
	}
	
}

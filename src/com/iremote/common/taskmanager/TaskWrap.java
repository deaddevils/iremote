package com.iremote.common.taskmanager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.jms.JMSUtil;
import com.iremote.common.taskmanager.db.IDbSession;
import com.iremote.common.taskmanager.db.IDbSessionFactory;

public class TaskWrap implements Runnable {

	private static Log log = LogFactory.getLog(TaskWrap.class);
	
	private Runnable task ;
	private IDbSessionFactory sessionFactory ;

	public TaskWrap(Runnable task , IDbSessionFactory sessionFactory) {
		super();
		this.task = task;
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void run() {
		
		ThreadNameHelper.changeThreadname(task);
		
		IDbSession dbc = sessionFactory.createDbSession() ;
		
		dbc.beginSession();
		dbc.beginTransaction();
		
		try
		{
			task.run();
			dbc.commit();
		}
		catch(Throwable t)
		{
			try
			{
				log.error(t.getMessage(), t);
				dbc.rollback();
			}
			catch(Throwable t1){};
		}
		dbc.closeSession();

		JMSUtil.commitmessage();
		ThreadNameHelper.clearThreadname();
	}

}

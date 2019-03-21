package com.iremote.common.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.jms.JMSUtil;

public class QuertzJob implements Job{

	private static Log log = LogFactory.getLog(QuertzJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException 
	{
		if ( log.isInfoEnabled() )
		{
			JobKey jk = context.getJobDetail().getKey();
			log.info(String.format("Timer task fired, key = %s_%s", jk.getName() , jk.getGroup()));
		}
		
		JobDataMap data = context.getJobDetail().getJobDataMap();
		Object obj = data.get("task");
		if ( !( obj instanceof Runnable) )
			return ;

		try
		{
			HibernateUtil.prepareSession(IRemoteConstantDefine.HIBERNATE_SESSION_REMOTE);
			HibernateUtil.beginTransaction();
			
			Runnable run = (Runnable)obj;
			run.run();	// quertz job is running in a thread;
			
			HibernateUtil.commit();
		}
		catch(Throwable t)
		{
			HibernateUtil.rollback();
			log.error(t.getMessage() ,t);
		}
		finally
		{
			HibernateUtil.closeSession();
			JMSUtil.commitmessage();
		}
	
	}

}

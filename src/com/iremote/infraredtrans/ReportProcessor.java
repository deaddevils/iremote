package com.iremote.infraredtrans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.taskmanager.QueueTaskManager;
import com.iremote.common.taskmanager.db.MockDbSessionFactory;
import com.iremote.common.taskmanager.queue.SingleTaskQueueFactory;

public abstract class ReportProcessor 
{
	private static Log log = LogFactory.getLog(ReportProcessor.class);
	
	private static QueueTaskManager<Runnable> logintaskmgt = new QueueTaskManager<Runnable>(1);
	private static QueueTaskManager<Runnable> heartbeattaskmgt = new QueueTaskManager<Runnable>(2 , true , new MockDbSessionFactory(), new SingleTaskQueueFactory<Runnable>());
	private static QueueTaskManager<Runnable> reporttaskmgt = new QueueTaskManager<Runnable>(3);

	public void processRequest(byte[] rq , IConnectionContext nbc) 
	{
		Remoter remoter = (Remoter)nbc.getAttachment();

		if ( log.isInfoEnabled())
			Utils.print(String.format("Receive data from %s(%d)", nbc.getDeviceid() , nbc.getConnectionHashCode()),rq);
		
		long taskIndentify = System.currentTimeMillis();
		log.info(taskIndentify);
		
		IRemoteRequestProcessor pro = getProcessor(rq);
		if ( pro == null )
		{
			log.info("process is null ");
			return ;
		}
		
		String deviceid = nbc.getDeviceid();
		if ( pro instanceof CurrentTimeProcessor || pro instanceof HearBeatProcessor)
		{
			if ( nbc.getAttachment().isHaslogin() == false 
					&& ( remoter.getSequence() % 4 ) == 1)  //1 login change every 1 minutes 
				sendLoginRequest(nbc);
			else 
				heartbeattaskmgt.addTask(deviceid , new ReportProcessorRunner(pro ,deviceid, rq ,  nbc ,taskIndentify));
			if ( nbc.getAttachment().isHaslogin() == false )
				return ;
		}
		else 
		{
			if ( pro instanceof LoginProcessor )  
			{
				logintaskmgt.addTask("loginprocess" , new ReportProcessorRunner(pro , deviceid ,rq ,  nbc,taskIndentify));
				return ;
			}
			else if ( nbc.getAttachment().isHaslogin() == true && ConnectionManager.contants(deviceid) )
			{
				reporttaskmgt.addTask(deviceid , new ReportProcessorRunner(pro ,deviceid, rq ,  nbc,taskIndentify));
			}
			//WIFI lock may sometimes report messages after server has say good bye to it and has delete it from ConnectionManager  
			else if ( nbc.getAttachment().isHaslogin() == true && !ConnectionManager.contants(deviceid) )
			{
				try
				{
					HibernateUtil.prepareSession(IRemoteConstantDefine.HIBERNATE_SESSION_REMOTE);
					if ( GatewayUtils.isCobbeLock(deviceid))
						reporttaskmgt.addTask(deviceid , new ReportProcessorRunner(pro ,deviceid, rq ,  nbc,taskIndentify));
				}
				catch(Throwable t )
				{
					log.error(t.getMessage() , t);
				}
				finally
				{
					HibernateUtil.closeSession();
				}
			}
			else if ( nbc.getAttachment().isHaslogin())
			{
				remoter.setHaslogin(false);
			}
		}
	}
	
	public void addTask(String key , Runnable task )  
	{
		reporttaskmgt.addTask(key, task);
	}
	
	protected abstract IRemoteRequestProcessor getProcessor(byte[] b); 
	protected abstract void sendLoginRequest(IConnectionContext nbc) ;
	
	public static void shutdown()
	{
		heartbeattaskmgt.shutdown();
		logintaskmgt.shutdown();
		reporttaskmgt.shutdown();
	}
}

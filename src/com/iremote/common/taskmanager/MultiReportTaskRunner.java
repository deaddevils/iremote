package com.iremote.common.taskmanager;

import java.util.concurrent.BlockingDeque;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.taskmanager.db.DefaultDbSessionFactory;
import com.iremote.common.taskmanager.queue.ITaskQueue;

@Deprecated
public class MultiReportTaskRunner extends QueueTaskRunner<IMulitReportTask> {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(MultiReportTaskRunner.class);
	
	public MultiReportTaskRunner(BlockingDeque<ITaskQueue<IMulitReportTask>> taskqueue ,Object waitobj) {
		super(taskqueue ,waitobj , new DefaultDbSessionFactory() );
	}
	
//	@Override
//	protected IMulitReportTask getTask(ITaskQueue<IMulitReportTask> tq)
//	{
//		return tq.peekTask();
//	}
	
//	@Override
//	protected void runTask(ITaskQueue<IMulitReportTask> tq , IMulitReportTask r)
//	{
//		if ( r != null )
//		{
//			if ( r.isFinished() )
//			{
//				tq.remove();
//			}
//			if ( r.isReady() && !r.isExecuted()) 
//			{
//				if ( log.isInfoEnabled())
//					log.info(r.getClass().getName());
//				TaskWrap tw = new TaskWrap(r, super.sessionFactory);
//				tw.run();
//			}
//		}
//	}
}

package com.iremote.common.taskmanager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.iremote.common.taskmanager.queue.TaskQueue;

public class MultiReportTaskQueue extends TaskQueue<IMulitReportTask> 
{
	private static Log log = LogFactory.getLog(MultiReportTaskQueue.class);
	
	public boolean merge(IMulitReportTask task )
	{
		lock();
		try
		{
			for ( IMulitReportTask mtw : que)
				if ( mtw.merge(task) == true )
				{
					if ( log.isInfoEnabled())
					{
						log.info("task merge");
						log.info(JSON.toJSONString(task));
					}
					return true ;
				}
			return false ;
		}
		catch(Throwable t)
		{
			log.error(t.getMessage() , t);
		}
		finally
		{
			release();
		}
		return false ;
	}
	
}

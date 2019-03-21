package com.iremote.common.taskmanager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.iremote.common.IRemoteConstantDefine;

public class StatusfulTaskWrap implements IStatusfulTask {

	private static Log log = LogFactory.getLog(StatusfulTaskWrap.class);
	
	private IStatusfulTask task;
	private boolean executed = false ;
	private long timeouttime ;
	
	public StatusfulTaskWrap(IStatusfulTask task) {
		super();
		this.task = task;
		this.timeouttime = System.currentTimeMillis() + IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND * 4 * 1000L ;
	}

	@Override
	public boolean isFinished() 
	{
		if ( System.currentTimeMillis() > this.timeouttime  )
		{
			log.info("task dose not start in 16 seconds, discard task");
			log.info(this.timeouttime);
			if ( log.isInfoEnabled())
				log.info(JSON.toJSON(task));
			return true ;
		}
		return task.isFinished();
	}

	@Override
	public void run() 
	{
		ThreadNameHelper.changeThreadname(task);
		executed = true;
		task.run();
		ThreadNameHelper.clearThreadname();
	}
	
	public boolean isExecuted()
	{
		return executed;
	}

}

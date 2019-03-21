package com.iremote.common.schedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScheduleJob implements Runnable
{
	private static Log log = LogFactory.getLog(ScheduleJob.class);
	private long index ;
	
	
	public ScheduleJob(long index)
	{
		super();
		this.index = index;
	}

	@Override
	public void run()
	{
		//log.error(String.valueOf(index));

	}

}

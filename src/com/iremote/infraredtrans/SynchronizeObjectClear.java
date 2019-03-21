package com.iremote.infraredtrans;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SynchronizeObjectClear implements Runnable
{
	private static Log log = LogFactory.getLog(SynchronizeObjectClear.class);
	
	@Override
	public void run()
	{
		for ( ; ; )
		{
			try {   
				Thread.sleep(1 * 60 * 1000);
			} catch (InterruptedException e) {
				return ;
			}
			
			log.info("clean.....");
			for ( List<IConnectionContext> lst: ConnectionManager.getAllConnection())
			{
				for ( IConnectionContext cc : lst)
					cc.getAttachment().clearSynchronizeObject();
			}
			
			if ( Thread.currentThread().isInterrupted())
				return ;
		}
	}

}

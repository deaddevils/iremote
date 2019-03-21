package com.iremote.infraredtrans;

import com.iremote.common.GatewayUtils;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.task.device.RemoteDisconnectTaskProcessor;

public class DisconnectionProcessor implements Runnable 
{
	private IConnectionContext nbc ;
	private String deviceid ;

	public DisconnectionProcessor(IConnectionContext nbc, String deviceid)
	{
		super();
		this.nbc = nbc;
		this.deviceid = deviceid;
	}

	@Override
	public void run()
	{
		if ( GatewayUtils.isCobbeLock(deviceid))
		{		
			if ( ConnectionManager.removeConnection(nbc) == true )
				RemoteHandler.sendOfflineNotification(deviceid);
		}
		else 
			ScheduleManager.excutein((int)(nbc.getIdleTimeoutMillis() / 1000) , new RemoteDisconnectTaskProcessor(nbc , deviceid));
	}

}

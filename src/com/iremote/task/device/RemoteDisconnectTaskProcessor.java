package com.iremote.task.device;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.constant.GatewayConnectionStatus;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.RemoteHandler;

public class RemoteDisconnectTaskProcessor implements Runnable {
	
	private static Log log = LogFactory.getLog(RemoteDisconnectTaskProcessor.class);
	
	private IConnectionContext nbc ;
	private String uuid;

	public RemoteDisconnectTaskProcessor(IConnectionContext nbc, String uuid) {
		super();
		this.nbc = nbc;
		this.uuid = uuid;
	}

	@Override
	public void run() 
	{
		if ( nbc.getAttachment().getStatus() == GatewayConnectionStatus.hasDisconnected )
		{
			if ( log.isInfoEnabled())
				log.info(String.format("%s(%d) has disconnected" , uuid , nbc.getConnectionHashCode()));
			return ;
		}
		if ( log.isInfoEnabled())
			log.info(String.format("Process disconnect %s(%d)" , uuid , nbc.getConnectionHashCode()));
		if ( ConnectionManager.removeConnection(nbc) == true )
		{
			RemoteHandler.sendOfflineNotification(uuid);
			nbc.getAttachment().setStatus(GatewayConnectionStatus.hasDisconnected);
		}
	}
	
	
}

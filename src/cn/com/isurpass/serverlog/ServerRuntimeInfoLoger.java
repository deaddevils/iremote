package cn.com.isurpass.serverlog;

import com.iremote.domain.ServerRuntimeLog;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.service.ServerRuntimeLogService;

public class ServerRuntimeInfoLoger implements Runnable
{
	@Override
	public void run()
	{
		ServerRuntimeLog rsl = new ServerRuntimeLog();
		rsl.setOnlinegatewaycount(ConnectionManager.getNumberofOnlineGateway());
		
		ServerRuntimeLogService svr = new ServerRuntimeLogService();
		svr.save(rsl);
	}

}

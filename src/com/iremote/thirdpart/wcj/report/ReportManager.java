package com.iremote.thirdpart.wcj.report;

import com.iremote.common.jms.vo.ZWaveDeviceEvent;
import com.iremote.common.taskmanager.QueueTaskManager;
import com.iremote.domain.Notification;
import com.iremote.domain.Remote;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.service.ComunityRemoteService;

public class ReportManager {

	private static QueueTaskManager<Runnable> taskmanager = new QueueTaskManager<Runnable>();
	
	public static void addReport(Remote remote , Notification notification)
	{
		ComunityRemoteService crs = new ComunityRemoteService();
		ComunityRemote cr = crs.querybyDeviceid(remote.getDeviceid());
		if ( cr == null )
			return ;
		taskmanager.addTask(String.valueOf(cr.getThirdpartid()), new DoorlockWarningMessageSender(remote , cr , notification));
	}
	
	public static void addReport(ZWaveDeviceEvent event)
	{
		
		ComunityRemoteService crs = new ComunityRemoteService();
		ComunityRemote cr = crs.querybyDeviceid(event.getDeviceid());
		if ( cr == null )
			return ;

		taskmanager.addTask(String.valueOf(cr.getThirdpartid()), new DoorlockWarningMessageSender(cr , event.getZwavedeviceid(),event.getEventtype() , event.getDeviceid()));
	}
	
	public static void shutdown()
	{
		taskmanager.shutdown();
	}
}

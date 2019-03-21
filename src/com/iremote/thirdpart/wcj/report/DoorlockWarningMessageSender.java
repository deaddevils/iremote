package com.iremote.thirdpart.wcj.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Notification;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.connection.ConnectionManager;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;
import com.iremote.thirdpart.wcj.domain.WcjServer;
import com.iremote.thirdpart.wcj.service.WcjServerService;

public class DoorlockWarningMessageSender implements Runnable {

	private WcjServer server;
	private String uri = "/vkyapi_lock_event.php" ;
	private ComunityRemote comunityremote;
	private String eventtype ;
	private String deviceid ;
	private List<Integer> lstlockid ;
	
	public DoorlockWarningMessageSender( ComunityRemote comunityremote, int lockid , String eventtype , String deviceid) 
	{
		super();
		this.lstlockid = Arrays.asList(lockid);
		this.deviceid = deviceid ;
		this.comunityremote = comunityremote;
		this.eventtype = eventtype;
	}
	
	public DoorlockWarningMessageSender(Remote remote,ComunityRemote comunityremote,Notification notification) {
		super();
		this.comunityremote = comunityremote;
		this.eventtype = notification.getMessage();
		this.deviceid = remote.getDeviceid();
	}

	@Override
	public void run() 
	{
		if ( comunityremote == null )
			return ;

		WcjServerService wss = new WcjServerService();
		server = wss.querybythridpartid(comunityremote.getThirdpartid());
		
		if ( server == null )
			return ;
		
		if ( lstlockid == null )
		{
			ZWaveDeviceService zds = new ZWaveDeviceService();
			List<ZWaveDevice> zdl = zds.querybydeviceidtypelist(deviceid, IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK);
			if ( zdl == null || zdl.size() == 0 )
				return ;
			lstlockid = new ArrayList<Integer>(zdl.size());
			for ( ZWaveDevice zd : zdl )
				lstlockid.add(zd.getZwavedeviceid());
		}
		
		for ( int lockid : lstlockid)
		{
			JSONObject report = new JSONObject();
			report.put("lockid", lockid);
			
			if ( IRemoteConstantDefine.WARNING_TYPE_LOW_BATTERY.equals(eventtype))
				report.put("type", 2);
			else if ( IRemoteConstantDefine.WARNING_TYPE_TAMPLE.equals(eventtype))
				report.put("type", 2);
			else if ( IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES.equals(eventtype))
				report.put("type", 3);
			else if ( IRemoteConstantDefine.WARNING_TYPE_REMOTE_OFFLINE.equals(eventtype) 
					|| IRemoteConstantDefine.WARNING_TYPE_MALFUNCTION.equals(eventtype))
			{
				report.put("type", 4);
				report.put("status", 1);
			}
			else if ( IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE.equals(eventtype)
					|| IRemoteConstantDefine.WARNING_TYPE_RECOVER.equals(eventtype))
			{
				report.put("type", 4);
				report.put("status", 0);
			}
			else 
				return ;
	
			ConnectionManager.getInstance().getConnection(server.getThirdpartid()).sendreport(server, uri, report);
		}
	}

}

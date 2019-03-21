package com.iremote.asiainfo.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.asiainfo.helper.AsiainfoHttpHelper;
import com.iremote.asiainfo.vo.AsiainfoMessage;
import com.iremote.common.Utils;
import com.iremote.domain.Remote;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.IremotepasswordService;
import com.iremote.service.ZWaveDeviceService;

public class RemoteResetTaskProcessor implements Runnable {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(RemoteResetTaskProcessor.class);
	
	private String deviceid;
	private AsiainfoMessage message;

	public RemoteResetTaskProcessor(String deviceid , AsiainfoMessage message) {
		super();
		this.deviceid = deviceid ;
		this.message = message;
	}

	@Override
	public void run() 
	{
		clearDevice(deviceid);
		
		AsiainfoHttpHelper.CommonResponse(message);	
	}
	
	
	private void clearDevice(String deviceid)
	{
		if ( deviceid == null || deviceid.length() == 0 )
			return ;
		IremotepasswordService svr = new IremotepasswordService();
		Remote r = svr.getIremotepassword(deviceid);
		if ( r == null )
			return ;
		r.setName(Utils.getGatewayDefaultName(deviceid));
		r.setPassword("");
		r.setSsid("");
		
		ZWaveDeviceService zsvr = new ZWaveDeviceService();
		zsvr.delete(deviceid);
		
		InfraredDeviceService isvr = new InfraredDeviceService();
		isvr.delete(deviceid);
	}
	
}

package com.iremote.thirdpart.wcj.action;

import java.util.Calendar;
import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;

public class DoorlockPasswordManagerThread implements Runnable{

	private Integer zwavedeviceid ;
	
	public DoorlockPasswordManagerThread(Integer zwavedeviceid) {
		super();
		this.zwavedeviceid = zwavedeviceid;
	}

	@Override
	public void run() 
	{
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		if ( hour >= 20 || hour <= 8 )
			return ;
		
		DoorlockPasswordService svr = new DoorlockPasswordService();
		List<DoorlockPassword> lst = svr.queryWaitingPassword(zwavedeviceid);
		if ( lst == null || lst.size() == 0 )
			return ;
		
		ZWaveDeviceService zds = new ZWaveDeviceService();
		
		ZWaveDevice zd = null;
		for ( DoorlockPassword dp : lst )
		{
			if ( dp.getPasswordtype() == IRemoteConstantDefine.DOOR_LOCK_PASSWORD_TYPE_COBBE_TEMP)
				continue;
			
			if ( zd == null || zd.getZwavedeviceid() != dp.getZwavedeviceid())
				zd = zds.query(dp.getZwavedeviceid());	
		
			if ( zd == null || ( zd.getStatus() != null && zd.getStatus() == -1) )
				continue;

			DoorlockPasswordTaskManager.addTask(zd.getDeviceid() , new SetPasswordThread(dp.getZwavedeviceid(), (byte)dp.getUsercode()));
		}
	}

}

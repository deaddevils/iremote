package com.iremote.thirdpart.cobbe.event;

import java.util.Calendar;
import java.util.Date;

import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.encrypt.AES;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.RemoteEvent;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;

public class DoorLockPasswordHelper
{
	public static boolean isDoorlockTempPassord(String deviceid)
	{
		//int type = Utils.getRemoteType(deviceid);
		
		if ( !GatewayUtils.isCobbeLock(deviceid) )
			return false;
		
		
		
		return true;
	}
	
	public static DoorlockPassword createLockTempPassword(int zwavedeviceid , int usercode , String password)
	{
		DoorlockPassword doorlockpassword = new DoorlockPassword();
		ZWaveDeviceService zds = new ZWaveDeviceService();
		ZWaveDevice lock = zds.query(zwavedeviceid);

		doorlockpassword.setZwavedeviceid(zwavedeviceid);
		doorlockpassword.setPassword(AES.encrypt2Str(password));
		doorlockpassword.setValidfrom(new Date());
		doorlockpassword.setValidthrough(Utils.currentTimeAdd(Calendar.YEAR, 30));
		doorlockpassword.setStatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE);
		doorlockpassword.setSynstatus(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING);
		doorlockpassword.setPasswordtype(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_TYPE_COBBE_TEMP);
		doorlockpassword.setUsercode(usercode );
		if(lock!=null&&lock.getNuid()<256){
			doorlockpassword.setLocktype(1);
		}else{
			doorlockpassword.setLocktype(2);
		}
		
		return doorlockpassword;
	}
	
	public static void BroadcaseSendPassowordEvent(String deviceid)
	{
		RemoteEvent event = new RemoteEvent();
		event.setDeviceid(deviceid);
		event.setEventtime(new Date());
		
		JMSUtil.sendmessage(IRemoteConstantDefine.DOOR_LOCK_SEND_PASSOWRD, event);
	}
}

package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.iremote.action.device.doorlock.DoorlockOperationStore;
import com.iremote.action.device.doorlock.IDoorlockOperationProcessor;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.message.MessageManager;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;

public class AddDoorlockPasswordUserProcessor extends ZWaveReportBaseProcessor
{
	protected int usercode ;
	
	public AddDoorlockPasswordUserProcessor()
	{
		super();
		this.dontpusmessage();
		this.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus()
	{
		String zid = String.valueOf(zrb.getDevice().getZwavedeviceid());
		IDoorlockOperationProcessor setter = (IDoorlockOperationProcessor)DoorlockOperationStore.getInstance().get(zid);
		
		if ( setter != null )
			return ;
		
		if ( zrb.getCmd()[8] != 1 )
			return;
		
		usercode = zrb.getCmd()[7] & 0xff;
		
		if ( Utils.isLockTempPassordforSMSSend(usercode) 
				|| Utils.isTempPassword(usercode))
			return ;
		
		ScheduleManager.excutein(5, new CreateDoorlockUserinneedTask(zrb.getDevice().getZwavedeviceid() , getUserType(), usercode , getUserName()));
	}

	protected int getUserType()
	{
		return IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_PASSWORD;
	}
	
	protected String getUserName()
	{
		return String.format("%s%d",MessageManager.getmessage(IRemoteConstantDefine.MESSAGE_KEY_DOOR_LOCK_PASSWORD_USER,0, PhoneUserHelper.getLanguange(zrb.getPhoneuser())) , usercode);
	}
	
	@Override
	public String getMessagetype()
	{
		return null;
	}

}

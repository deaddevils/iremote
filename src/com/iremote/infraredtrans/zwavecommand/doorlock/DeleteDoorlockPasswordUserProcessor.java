package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.iremote.action.device.doorlock.DoorlockOperationStore;
import com.iremote.action.device.doorlock.IDoorlockOperationProcessor;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageManager;
import com.iremote.domain.DoorlockUser;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;
import com.iremote.service.DoorlockUserService;

public class DeleteDoorlockPasswordUserProcessor extends ZWaveReportBaseProcessor
{
	protected int usercode ;
	
	public DeleteDoorlockPasswordUserProcessor()
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
		
		if ( setter != null  )
			return ;
		
		if ( zrb.getCmd()[8] != 1 )
			return;
		
		usercode = zrb.getCmd()[7] ;
		
		DoorlockUserService dus = new DoorlockUserService();
		DoorlockUser du = dus.query(zrb.getDevice().getZwavedeviceid(), getUserType(), usercode);
		
		if ( du == null )
			return ;
		
		dus.delete(du);
		
	}

	protected int getUserType()
	{
		return IRemoteConstantDefine.DOOR_LOCK_USER_TYPE_PASSWORD;
	}

	@Override
	public String getMessagetype()
	{
		return null;
	}
}

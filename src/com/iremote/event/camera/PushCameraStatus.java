package com.iremote.event.camera;

import com.iremote.action.helper.NotificationHelper;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.CameraEvent;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.service.PhoneUserService;
import com.iremote.service.RemoteService;

public class PushCameraStatus extends CameraEvent  implements ITextMessageProcessor{

	@Override
	public void run() 
	{
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(super.getDeviceid());
		
		if ( r == null )
			return ;

		NotificationHelper.pushCameraStatus(this, getEventtime() , r.getPlatform());
	}

	@Override
	public String getTaskKey() 
	{
		return super.getDeviceid();
	}

}

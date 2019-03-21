package com.iremote.thirdpart.rentinghouse.event;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.event.gateway.AddzWaveDeviceStepEvent;
import com.iremote.thirdpart.common.ThirdPartHelper;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;

public class AddzWaveDeviceStepProcessor extends AddzWaveDeviceStepEvent implements ITextMessageProcessor
{

	@Override
	public void run() 
	{
		ComunityRemote cr = ThirdPartHelper.queryThirdpart(super.getDeviceid());
		if ( cr == null )
			return ;
		
		ThirdPartHelper.saveEventtoThirdpart(cr.getThirdpartid(), 
				IRemoteConstantDefine.EVENT_Add_ZWAVE_DEVICE_STEP, 
				getDeviceid(),
				0, super.getStatus(), null, null, getEventtime());
	}

	@Override
	public String getTaskKey() 
	{
		return super.getDeviceid();
	}
	
}

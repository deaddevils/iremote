package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;

public class DoorlockSelfDefineWaringReportProcessor  extends ZWaveReportBaseProcessor
{	
	private String message = null;
	@Override
	protected void updateDeviceStatus()
	{
		
		int s = 0 ;
		if ( zrb.getCmd()[7] == 1 )
		{
			message = IRemoteConstantDefine.WARNING_TYPE_TAMPLE;
			s = IRemoteConstantDefine.DEVICE_STATUS_TAMPLE_ALARM;
		}
		else if ( zrb.getCmd()[8] == 1 )
		{
			message = IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_ERROR;;
			s = IRemoteConstantDefine.LOCK_KEY_ERROR;
		}
		else if ( zrb.getCmd()[9] == 1 )
		{
			message = IRemoteConstantDefine.WARNING_TYPE_PASSWORD_ERROR_5_TIMES;;
			s = IRemoteConstantDefine.LOCK_PASSWORD_ERROR_5_TIMES;
		}
		else if ( zrb.getCmd()[14] == 1)
		{
			message = IRemoteConstantDefine.WARNING_TYPE_LOCK_NOT_CLOSE;
			s = IRemoteConstantDefine.LOCK_NOT_CLOSE;
		}
		else if ( zrb.getCmd()[15] == 1)
		{
			message = IRemoteConstantDefine.WARNING_TYPE_LOCK_BULLY;;
			s = IRemoteConstantDefine.LOCK_PASSWORD_LOCK_BULLY;
		}
		else if ( zrb.getCmd()[16] == 1)
		{
			message = IRemoteConstantDefine.WARNING_TYPE_LOCK_KEY_EVENT;;
			s = IRemoteConstantDefine.LOCK_KEY_EVENT;
		}
		else if ( zrb.getCmd()[17] == 1)
		{
			message = IRemoteConstantDefine.WARNING_TYPE_LOCK_LOCK_ERROR;;
			s = IRemoteConstantDefine.LOCK_LOCK_ERROR;
		}
		if ( message == null )
			return ;
		
		super.appendWarningstatus(s);
		
		if ( s == IRemoteConstantDefine.DEVICE_STATUS_TAMPLE_ALARM  //for forward compatible 
				|| s == IRemoteConstantDefine.LOCK_KEY_ERROR
				|| s == IRemoteConstantDefine.LOCK_PASSWORD_ERROR_5_TIMES)
			zrb.getDevice().setStatus(s);

	}

	@Override
	public String getMessagetype() {
		return message;
	}
}


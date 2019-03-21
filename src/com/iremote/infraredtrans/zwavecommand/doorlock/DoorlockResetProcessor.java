package com.iremote.infraredtrans.zwavecommand.doorlock;

import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.DoorlockUser;
import com.iremote.infraredtrans.zwavecommand.ZWaveReportBaseProcessor;
import com.iremote.service.DoorlockUserService;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;

public class DoorlockResetProcessor extends ZWaveReportBaseProcessor
{
	
	@Override
	protected void updateDeviceStatus() 
	{
		DoorlockPasswordService dpsvr = new DoorlockPasswordService();
		List<DoorlockPassword> elst = dpsvr.queryByZwavedeviceid(zrb.getDevice().getZwavedeviceid());
		
		for ( DoorlockPassword dp : elst )
			dpsvr.delete(dp);
		
		
		DoorlockUserService dus = new DoorlockUserService();
		List<DoorlockUser> lst = dus.querybyZwavedeviceid(zrb.getDevice().getZwavedeviceid());
		for ( DoorlockUser du : lst )
			dus.delete(du);
	}

	@Override
	public String getMessagetype() 
	{
		return IRemoteConstantDefine.MESSAGE_TYPE_LOCK_RESET;
	}

}

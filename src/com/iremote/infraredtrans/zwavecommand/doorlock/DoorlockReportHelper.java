package com.iremote.infraredtrans.zwavecommand.doorlock;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.ZWaveDevice;

public class DoorlockReportHelper {

	public static boolean isDoorlockWarning(ZWaveDevice lock)
	{
		if ( lock.getStatus() == null 
				|| ( ( lock.getStatus() != IRemoteConstantDefine.DEVICE_STATUS_TAMPLE_ALARM )
					&& (lock.getStatus() < 300 )) )
			return false ;
		return true;
		
	}
	
	public static boolean isSupportJwzhProtocol(ZWaveDevice lock)
	{
		if ( lock == null )
			return false ;
		if ( StringUtils.isNotBlank(lock.getFunctionversion()))
			return true;
		if ( StringUtils.isNotBlank(lock.getProductor())
				&& lock.getProductor().toLowerCase().startsWith(IRemoteConstantDefine.JWZH_ZWAVE_PRODUCTOR.toLowerCase()))
			return true;
		return false ;
	}
}

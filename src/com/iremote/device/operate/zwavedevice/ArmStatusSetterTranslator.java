package com.iremote.device.operate.zwavedevice;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.device.operate.OperationTranslatorBase;

public class ArmStatusSetterTranslator extends OperationTranslatorBase{

	@Override
	public String getDeviceStatus() 
	{
		if ( StringUtils.isNotBlank(this.devicestatus))
			return this.devicestatus;
		if ( this.status != null )
		{
			if ( this.status == IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN )
				this.devicestatus = IRemoteConstantDefine.DEVICE_STATUS_OPEN;
			else if ( this.status == IRemoteConstantDefine.DEVICE_STATUS_SWITCH_CLOSE)
				this.devicestatus = IRemoteConstantDefine.DEVICE_STATUS_CLOSE;
		}
		return this.devicestatus;
	}

	@Override
	public String getCommandjson() 
	{
		return null;
	}

	@Override
	public Integer getValue() 
	{
		if ( this.status != null )
			return this.status;
		if ( StringUtils.isNotBlank(this.devicestatus))
		{
			if ( IRemoteConstantDefine.WARNING_TYPE_SWITCH_OPEN.equals(this.devicestatus) )
				this.status = IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN ;
			else if ( IRemoteConstantDefine.WARNING_TYPE_SWITCH_CLOSE.equals(this.devicestatus) )
				this.status = IRemoteConstantDefine.DEVICE_STATUS_SWITCH_CLOSE ;
		}
		return this.status;
	}

}

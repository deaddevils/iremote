package com.iremote.device.operate.zwavedevice;

import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.domain.DeviceCapability;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class LockOpenCommand extends ZwaveDeviceOperateCommandBase {

	@Override
	public CommandTlv createCommand() 
	{
		super.status = 0 ;
		List<DeviceCapability> lst = super.zwavedevice.getCapability();
		if ( lst != null )
		{
			for ( DeviceCapability dc : lst )
				if ( dc.getCapabilitycode() == IRemoteConstantDefine.DEVICE_CAPABILITY_LOCK_AUTO_LOCK)
				{
					super.status = 1 ;
					break;
				}
		}
		return CommandUtil.createLockCommand(zwavedevice.getNuid(), status);
	}

}

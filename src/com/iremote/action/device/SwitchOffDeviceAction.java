package com.iremote.action.device;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.device.operate.zwavedevice.LockLockCommad;
import com.iremote.infraredtrans.tlv.CommandTlv;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "device", parameters = {"zwavedeviceid" , "deviceid" , "nuid"})
public class SwitchOffDeviceAction extends SwitchOnDeviceAction
{
	protected CommandTlv[] createCommandTlv()
	{		
		if ( IRemoteConstantDefine.DEVICE_TYPE_BINARY_SWITCH.equals(device.getDevicetype())
				|| IRemoteConstantDefine.DEVICE_TYPE_OUTLET.equals(device.getDevicetype()) 
				|| IRemoteConstantDefine.DEVICE_TYPE_CURTAIN.equals(device.getDevicetype())
				|| IRemoteConstantDefine.DEVICE_TYPE_TUJIA_ELECTRIC_METER.equals(device.getDevicetype())
				|| IRemoteConstantDefine.DEVICE_TYPE_ROBOTIC_ARM.equals(device.getDevicetype())
				|| IRemoteConstantDefine.DEVICE_TYPE_RGB_COLOR_SWITCH.equals(device.getDevicetype()))
			return new CommandTlv[]{CommandUtil.createSwitchCommand(device.getNuid(), (byte)0)};
		else if ( IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_BINARY_SWITCH.equals(device.getDevicetype()))
			return new CommandTlv[]{CommandUtil.createSwitchCommand(device.getNuid(), (byte)channel , (byte)0)};
		else if ( IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_BINARY_SWITCH.equals(device.getDevicetype()))
			return new CommandTlv[]{CommandUtil.createSwitchCommand(device.getNuid(), (byte)channel , (byte)0)};
		else if ( IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER.equals(device.getDevicetype()) )
			return  new CommandTlv[]{CommandUtil.createAirconditionCommand(device.getNuid(), (byte)0)};
		else if ( IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(device.getDevicetype()))
		{
			LockLockCommad loc = new LockLockCommad();
			loc.setZwavedevice(super.device);
			return  new CommandTlv[]{loc.createCommand()};
		}
		else if ( IRemoteConstantDefine.DEVICE_TYPE_ALARM.equals(device.getDevicetype()))
			return new CommandTlv[]{CommandUtil.createAlarmCommand(device.getNuid(), (byte)0)};
		return null ;
	}
}

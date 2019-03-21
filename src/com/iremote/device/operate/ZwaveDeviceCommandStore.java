package com.iremote.device.operate;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.processorstore.ClassMapper;
import com.iremote.device.operate.zwavedevice.*;

public class ZwaveDeviceCommandStore extends ClassMapper<ZwaveDeviceOperateCommandBase>
{
	private static ZwaveDeviceCommandStore instance = new ZwaveDeviceCommandStore();
	
	public static ZwaveDeviceCommandStore getInstance()
	{
		return instance ;
	}
	
	private ZwaveDeviceCommandStore()
	{
		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_BINARY_SWITCH , IRemoteConstantDefine.DEVICE_OPERATION_ON), BinarySwitchOnCommand.class);
		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_BINARY_SWITCH , IRemoteConstantDefine.DEVICE_OPERATION_OFF), BinarySwitchOffCommand.class);
		
		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_BINARY_SWITCH , IRemoteConstantDefine.DEVICE_OPERATION_ON), BinarySwitchOnCommand.class);
		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_BINARY_SWITCH , IRemoteConstantDefine.DEVICE_OPERATION_OFF), BinarySwitchOffCommand.class);

		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_BINARY_SWITCH , IRemoteConstantDefine.DEVICE_OPERATION_ON), BinarySwitchOnCommand.class);
		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_BINARY_SWITCH , IRemoteConstantDefine.DEVICE_OPERATION_OFF), BinarySwitchOffCommand.class);

		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_OUTLET , IRemoteConstantDefine.DEVICE_OPERATION_ON), BinarySwitchOnCommand.class);
		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_OUTLET , IRemoteConstantDefine.DEVICE_OPERATION_OFF), BinarySwitchOffCommand.class);
		
		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_CURTAIN , IRemoteConstantDefine.DEVICE_OPERATION_OPEN), MultilevelSwitchOpenCommand.class);
		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_CURTAIN , IRemoteConstantDefine.DEVICE_OPERATION_CLOSE), MultilevelSwitchCloseCommand.class);
		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_CURTAIN , IRemoteConstantDefine.DEVICE_OPERATION_STOP), MotorStopCommand.class);
		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_CURTAIN , IRemoteConstantDefine.DEVICE_OPERATION_SET_STATUS), MultilevelSwitchSetStatusCommand.class);
	
		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK , IRemoteConstantDefine.DEVICE_OPERATION_OPEN), LockOpenCommand.class);
		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_DOOR_LOCK , IRemoteConstantDefine.DEVICE_OPERATION_CLOSE), LockLockCommad.class);

		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER , IRemoteConstantDefine.DEVICE_OPERATION_OPEN), AirConditionOnCommand.class);
		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER , IRemoteConstantDefine.DEVICE_OPERATION_CLOSE), AirConditionOffCommand.class);

		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_WALLREADER , IRemoteConstantDefine.DEVICE_OPERATION_OPEN), LockOpenCommand.class);
		super.registProcessor(makekey(IRemoteConstantDefine.DEVICE_TYPE_WALLREADER , IRemoteConstantDefine.DEVICE_OPERATION_CLOSE), LockLockCommad.class);

	}
	
	public ZwaveDeviceOperateCommandBase getProcessor(String devicetype , String operate)
	{
		return super.getProcessor(makekey(devicetype , operate));
	}
	
	private String makekey(String devicetype , String operate)
	{
		return String.format("%s_%s", devicetype , operate);
	}
	
}

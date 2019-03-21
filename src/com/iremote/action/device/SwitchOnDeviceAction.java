package com.iremote.action.device;

import com.iremote.action.device.doorlock.DoorlockPasswordHelper;
import com.iremote.action.device.handler.DoorLockRemoteOpenForbiddenReportHandler;
import com.iremote.action.helper.DoorlockHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.device.operate.zwavedevice.LockOpenCommand;
import com.iremote.domain.DeviceCapability;
import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.zwavecommand.notifiy.ZwaveReportNotifyManager;
import com.iremote.service.DeviceCapabilityService;


@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "device", parameters = {"zwavedeviceid" , "deviceid" , "nuid"})
public class SwitchOnDeviceAction extends DeviceOperationBaseAction {
	
	protected int channel = 1 ;
	
	protected CommandTlv[] createCommandTlv()
	{
		if ( IRemoteConstantDefine.DEVICE_TYPE_BINARY_SWITCH.equals(device.getDevicetype())
				|| IRemoteConstantDefine.DEVICE_TYPE_OUTLET.equals(device.getDevicetype()) 
				|| IRemoteConstantDefine.DEVICE_TYPE_CURTAIN.equals(device.getDevicetype())
				|| IRemoteConstantDefine.DEVICE_TYPE_TUJIA_ELECTRIC_METER.equals(device.getDevicetype())
				|| IRemoteConstantDefine.DEVICE_TYPE_ROBOTIC_ARM.equals(device.getDevicetype())
				|| IRemoteConstantDefine.DEVICE_TYPE_RGB_COLOR_SWITCH.equals(device.getDevicetype()))
			return new CommandTlv[]{CommandUtil.createSwitchCommand(device.getNuid(), (byte)255)};
		else if ( IRemoteConstantDefine.DEVICE_TYPE_2_CHANNEL_BINARY_SWITCH.equals(device.getDevicetype()))
			return new CommandTlv[]{CommandUtil.createSwitchCommand(device.getNuid(), (byte)channel , (byte)255)};
		else if ( IRemoteConstantDefine.DEVICE_TYPE_3_CHANNEL_BINARY_SWITCH.equals(device.getDevicetype()))
			return new CommandTlv[]{CommandUtil.createSwitchCommand(device.getNuid(), (byte)channel , (byte)255)};
		else if ( IRemoteConstantDefine.DEVICE_TYPE_AIR_CONDITIONER.equals(device.getDevicetype()) )
			return  new CommandTlv[]{CommandUtil.createAirconditionCommand(device.getNuid(), (byte)5)};
		else if ( IRemoteConstantDefine.DEVICE_TYPE_LIST_LIKE_DOOR_LOCK.contains(device.getDevicetype()))
		{
            return getDoorLockCommand();
		}
		return null ;
	}

    private CommandTlv[] getDoorLockCommand() {
        DeviceCapabilityService dcs = new DeviceCapabilityService();
        DeviceCapability forbidopendoor = dcs.query(super.device, IRemoteConstantDefine.DOOR_LOCK_CAPABILITY_FORBID_REMOTE_OPEN);

        if (forbidopendoor != null){
			byte[] requestRemoteOpenDoorSetting = new byte[]{(byte) 0x80, 0x07, 0x00, 0x60, 0x10, 0x01, 0x07};
			CommandTlv ct = DoorlockPasswordHelper.createCommandTlv(requestRemoteOpenDoorSetting, super.device.getNuid());
			
			if ( DoorlockHelper.isWifiLock(device))
				return new CommandTlv[]{ct} ;

			SynchronizeRequestHelper.asynchronizeRequest(device.getDeviceid(), ct, 1);
            hasResult = true;
            resultCode = ErrorCodeDefine.DOORLOCK_FORBID_REMOTE_OPEN;
            return null;
        }

		Byte[] remoteOpenDoorResponse = new Byte[]{(byte) 0x80, 0x07, 0x00, 0x60, 0x10, 0x01, 0x08, 0x00};
		ZwaveReportNotifyManager.getInstance().regist(device.getDeviceid(), super.device.getNuid(),
				remoteOpenDoorResponse, 16, new DoorLockRemoteOpenForbiddenReportHandler());
        LockOpenCommand loc = new LockOpenCommand();
        loc.setZwavedevice(super.device);
        loc.setStatus((byte)0);
        return  new CommandTlv[]{loc.createCommand()};
    }

    public void setChannel(int channel)
	{
		this.channel = channel;
	}


}

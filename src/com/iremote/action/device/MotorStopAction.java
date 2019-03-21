package com.iremote.action.device;

import com.iremote.common.commandclass.CommandUtil;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.infraredtrans.tlv.CommandTlv;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.OPERATION, domain = "device", parameters = {"zwavedeviceid" , "deviceid" , "nuid"})
public class MotorStopAction extends DeviceOperationBaseAction{

	@Override
	protected CommandTlv[] createCommandTlv() {
		
		return new CommandTlv[]{CommandUtil.createMotorStopCommand(device.getNuid())};
	}

	
}

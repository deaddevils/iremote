package com.iremote.device.operate.zwavedevice;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.device.operate.OperationTranslatorBase;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class DoorsensorOperationTranslator extends OperationTranslatorBase{

	@Override
	public String getDeviceStatus() {
		if ( StringUtils.isNoneBlank(this.devicestatus))
			return this.devicestatus;
		if ( this.status != null && this.status == IRemoteConstantDefine.DEVICE_STATUS_DOOR_OPEN)
			this.devicestatus = IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN;
		else if ( this.status != null && this.status == IRemoteConstantDefine.DEVICE_STATUS_DOOR_CLOSE)
			this.devicestatus = IRemoteConstantDefine.WARNING_TYPE_DOOR_CLOSE;
		return devicestatus;
	}

	@Override
	public String getCommandjson() {
		if ( StringUtils.isNotBlank(this.commandjson))
			return this.commandjson;
		return null;
	}

	@Override
	public Integer getValue() {
		if ( this.status != null )
			return this.status;
		if ( StringUtils.isNotBlank(this.devicestatus))
		{
			if ( IRemoteConstantDefine.WARNING_TYPE_DOOR_OPEN.equals(this.devicestatus) )
				this.status = IRemoteConstantDefine.DEVICE_STATUS_DOOR_OPEN ;
			else if ( IRemoteConstantDefine.WARNING_TYPE_DOOR_CLOSE.equals(this.devicestatus) )
				this.status = IRemoteConstantDefine.DEVICE_STATUS_DOOR_CLOSE ;
		}
		return this.status;
	}

	@Override
	public byte[] getCommand() {
		return null;
	}

	@Override
	public byte[] getCommands() {
		return null;
	}

	@Override
	public List<CommandTlv> getCommandTlv()
	{
		return null;
	}

}

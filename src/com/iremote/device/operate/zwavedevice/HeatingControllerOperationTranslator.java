package com.iremote.device.operate.zwavedevice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.device.operate.OperationTranslatorBase;
import com.iremote.infraredtrans.tlv.CommandTlv;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class HeatingControllerOperationTranslator extends OperationTranslatorBase
{

	@Override
	public String getDeviceStatus() {
		if ( StringUtils.isNotBlank(this.devicestatus))
			return this.devicestatus;
		return null;
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
			if ( IRemoteConstantDefine.WARNING_TYPE_SWITCH_OPEN.equals(this.devicestatus) )
				this.status = IRemoteConstantDefine.DEVICE_STATUS_SWITCH_OPEN ;
			else if ( IRemoteConstantDefine.WARNING_TYPE_SWITCH_CLOSE.equals(this.devicestatus) )
				this.status = IRemoteConstantDefine.DEVICE_STATUS_SWITCH_CLOSE ;
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
		if ( commandtlvlst != null )
			return commandtlvlst;
		if ( this.zwavedevice != null &&  StringUtils.isNotBlank(this.commandjson))
		{
			JSONArray ja = parseJSONArray(this.commandjson);
			if ( ja.size() == 0 )
				return null ;
			JSONObject json = ja.getJSONObject(0);

			commandtlvlst = new ArrayList<CommandTlv>();

			if ( json.containsKey(IRemoteConstantDefine.POWER)){
				commandtlvlst.add(CommandUtil.createHeatingControllerCommand(
						this.zwavedevice.getNuid(), json.getIntValue(IRemoteConstantDefine.POWER) == 255 ? 24 : 18));
			}else if (json.containsKey("temperature")){
				commandtlvlst.add(CommandUtil.createHeatingControllerCommand(this.zwavedevice.getNuid(), json.getByteValue("temperature")));
			}
		}

		return commandtlvlst;
	}

}

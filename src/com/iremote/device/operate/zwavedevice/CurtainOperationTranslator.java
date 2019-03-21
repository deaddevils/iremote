package com.iremote.device.operate.zwavedevice;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.device.operate.OperationTranslatorBase;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;

public class CurtainOperationTranslator extends OperationTranslatorBase{

	@Override
	public String getDeviceStatus() {
		if ( StringUtils.isNoneBlank(this.devicestatus))
			return this.devicestatus;
		if ( this.status != null && this.status > IRemoteConstantDefine.DEVICE_STATUS_CURTAIN_CLOSE )
			this.devicestatus = IRemoteConstantDefine.WARNING_TYPE_CURTAIN_OPEN;
		else if ( this.status != null && this.status == IRemoteConstantDefine.DEVICE_STATUS_CURTAIN_CLOSE)
			this.devicestatus = IRemoteConstantDefine.WARNING_TYPE_CURTAIN_CLOSE;
		return devicestatus;
	}

	@Override
	public String getCommandjson() 
	{
		if ( StringUtils.isNotBlank(this.commandjson))
			return this.commandjson;
		if ( this.command != null )
		{
			byte[] v = TlvWrap.readTag(this.command, TagDefine.TAG_ZWAVE_COMMAND, 0);
			if ( v == null || v.length <= 2 )
				return null; 
	
			this.setDeviceStatus(null);
			this.setStatus((v[2] & 0xff));
			
			JSONObject json = new JSONObject();
			json.put(IRemoteConstantDefine.OPERATION, this.getDeviceStatus());
			json.put(IRemoteConstantDefine.VALUE, v[2] & 0xff);
			
			JSONArray ja = new JSONArray();
			ja.add(json);

			this.commandjson = ja.toJSONString();
		}
		return this.commandjson;
	}

	@Override
	public Integer getValue() {
		if ( this.status != null )
			return this.status;
		if ( StringUtils.isNotBlank(this.devicestatus))
		{
			if ( IRemoteConstantDefine.WARNING_TYPE_CURTAIN_OPEN.equals(this.devicestatus) )
				this.status = IRemoteConstantDefine.DEVICE_STATUS_CURTAIN_OPEN ;
			else if ( IRemoteConstantDefine.WARNING_TYPE_CURTAIN_CLOSE.equals(this.devicestatus) )
				this.status = IRemoteConstantDefine.DEVICE_STATUS_CURTAIN_CLOSE ;
		}
		return this.status;
	}

	@Override
	protected CommandTlv createCommandTlv(JSONObject json)
	{
		return CommandUtil.createMultilevelSwitchCommand(this.zwavedevice.getNuid(), json.getByteValue(IRemoteConstantDefine.VALUE));
	}

}

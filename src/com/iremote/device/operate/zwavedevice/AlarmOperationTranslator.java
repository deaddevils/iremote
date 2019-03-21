package com.iremote.device.operate.zwavedevice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.device.operate.OperationTranslatorBase;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;

public class AlarmOperationTranslator extends OperationTranslatorBase
{
	@Override
	public String getDeviceStatus() {
		if ( StringUtils.isNotBlank(this.devicestatus))
			return this.devicestatus;
		if ( this.status != null )
		{
			if ( this.status == IRemoteConstantDefine.DEVICE_STATUS_ALARM_ALARM )
				this.devicestatus = IRemoteConstantDefine.DEVICE_OPERATION_OPEN;
			else if ( this.status == IRemoteConstantDefine.DEVICE_STATUS_ALARM_CLOSE)
				this.devicestatus = IRemoteConstantDefine.DEVICE_OPERATION_CLOSE;
		}
		return this.devicestatus;
	}

	@Override
	public String getCommandjson() {
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
			if ( IRemoteConstantDefine.DEVICE_OPERATION_OPEN.equals(this.devicestatus) )
				this.status = IRemoteConstantDefine.DEVICE_STATUS_ALARM_ALARM ;
			else if ( IRemoteConstantDefine.DEVICE_OPERATION_CLOSE.equals(this.devicestatus) )
				this.status = IRemoteConstantDefine.DEVICE_STATUS_ALARM_CLOSE ;
		}
		return this.status;
	}
	
	@Override
	public List<CommandTlv> getCommandTlv()
	{
		if ( this.commandtlvlst != null )
			return commandtlvlst;

		if ( this.zwavedevice != null &&  StringUtils.isNotBlank(this.commandjson))
		{
			JSONArray ja = parseJSONArray(this.commandjson);
			if ( ja.size() == 0 )
				return null ;
			
			JSONObject json = ja.getJSONObject(0);

			if ( json.containsKey(IRemoteConstantDefine.OPERATION))
			{
				CommandTlv ct = null ;
				if ( IRemoteConstantDefine.DEVICE_OPERATION_CLOSE.equals(json.getString(IRemoteConstantDefine.OPERATION)))
					ct = CommandUtil.createAlarmCommand( super.zwavedevice.getNuid(), (byte)IRemoteConstantDefine.DEVICE_STATUS_ALARM_CLOSE);
				else if ( IRemoteConstantDefine.DEVICE_OPERATION_OPEN.equals(json.getString(IRemoteConstantDefine.OPERATION)) )
					ct = CommandUtil.createAlarmCommand( super.zwavedevice.getNuid(), (byte)IRemoteConstantDefine.DEVICE_STATUS_ALARM_ALARM);
				if ( ct != null && this.operationtype != null )
					ct.addUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE ,operationtype ,TagDefine.TAG_LENGTH_1 ));
				if ( ct != null )
				{
					commandtlvlst = new ArrayList<CommandTlv>();
					commandtlvlst.add(ct);
				}
			}	
		}
		return commandtlvlst;
	}
	
}

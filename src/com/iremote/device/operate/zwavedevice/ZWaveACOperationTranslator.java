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

public class ZWaveACOperationTranslator extends OperationTranslatorBase
{
	@Override
	public String getDeviceStatus()
	{
		if ( StringUtils.isNotBlank(this.devicestatus))
			return this.devicestatus;
		if ( this.status != null )
		{
			if ( this.status == IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN )
				this.devicestatus = IRemoteConstantDefine.DEVICE_STATUS_OPEN;
			else if ( this.status == IRemoteConstantDefine.DEVICE_STATUS_DOOR_SENSOR_CLOSE)
				this.devicestatus = IRemoteConstantDefine.DEVICE_STATUS_CLOSE;
		}
		return this.devicestatus;
	}

	@Override
	public String getCommandjson()
	{
		if ( StringUtils.isNotBlank(this.commandjson))
			return this.commandjson;
		if ( this.command != null )
		{
			JSONObject json = new JSONObject();
			
			byte[][] b = TlvWrap.splitTag(command, 0);
			for ( int i = 0 ; i < b.length ; i ++ )
			{
				if ( TlvWrap.getTag(b[i] ,0 ) != TagDefine.TAG_ZWAVE_COMMAND)
					continue;
				
				byte[] v = TlvWrap.readTag(b[i], TagDefine.TAG_ZWAVE_COMMAND, 0);

				if ( v[0] == 0x40 )
				{
					if ( v[2] == 0 || v[2] == 5 )
						json.put("power", v[2]);
					else 
						json.put("mode", v[2]);
				}
				else if ( v[0] == 0x44 )
					json.put("fan", v[2]);
				else if ( v[0] == 0x43 )
				{
					json.put("temperatureunit", v[3]);
					json.put("temperature", v[4]);
				}
			}
			
			JSONArray ja = new JSONArray();
			ja.add(json);
			this.commandjson = ja.toJSONString();
			
		}
		return this.commandjson;
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

			if ( json.containsKey("power") && json.getIntValue("power") == 0 )
			{
				commandtlvlst.add(CommandUtil.createAirconditionCommand(this.zwavedevice.getNuid() , (byte)0));
			}
			else
			{
				if ( json.containsKey("power"))
					commandtlvlst.add(CommandUtil.createAirconditionCommand(this.zwavedevice.getNuid() , (byte)5));
				if ( json.containsKey("mode"))
					commandtlvlst.add(CommandUtil.createAirconditionCommand(this.zwavedevice.getNuid() , json.getByteValue("mode")));
				if ( json.containsKey("fan"))
					commandtlvlst.add(CommandUtil.createAirconditionFanCommand(this.zwavedevice.getNuid() , json.getByteValue("fan")));
				byte temperatureunit = 1 ;
				if ( json.containsKey("temperatureunit"))
					temperatureunit = json.getByteValue("temperatureunit");
				if ( json.containsKey("temperature") && json.containsKey("mode"))
					commandtlvlst.add(CommandUtil.createAirconditionTemperatureCommand(this.zwavedevice.getNuid() , json.getByteValue("mode") ,  temperatureunit  , json.getByteValue("temperature")));
			}
			if ( this.operationtype != null && commandtlvlst.size() > 0  )
			{
				for ( CommandTlv ct : commandtlvlst )
					ct.addUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE ,operationtype ,TagDefine.TAG_LENGTH_1 ));
			}
			for ( CommandTlv ct : commandtlvlst )
				ct.setDeviceid(this.zwavedevice.getDeviceid());
		}
		
		return commandtlvlst;
	}

}

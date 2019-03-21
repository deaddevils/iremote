package com.iremote.device.operate.zwavedevice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.device.operate.OperationTranslatorBase;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;

public class FreshAirOperationTranslator extends OperationTranslatorBase
{
	@Override
	public String getDeviceStatus()
	{
		if ( StringUtils.isNoneBlank(this.devicestatus))
			return this.devicestatus;
		Integer v = getValue();
		if ( v == null || v == 0 )
			return IRemoteConstantDefine.DEVICE_OPERATION_CLOSE ;
		
		return IRemoteConstantDefine.DEVICE_OPERATION_OPEN;
	}

	@Override
	public String getCommandjson()
	{
		if ( StringUtils.isNotBlank(this.commandjson))
			return this.commandjson;
		if ( this.command == null )
			return null ;
		
		JSONObject json = new JSONObject();
		byte[][] b = TlvWrap.splitTag(command, 0);
		for ( int i = 0 ; i < b.length ; i ++ )
		{
			if ( TlvWrap.getTag(b[i] ,0 ) != TagDefine.TAG_ZWAVE_COMMAND)
				continue;
			byte[] v = TlvWrap.readTag(b[i], TagDefine.TAG_ZWAVE_COMMAND, 0);
			
			if ( v == null )
				continue ;
			
			if ( v[0] == 0x40 )
			{
				if ( v[2] == 0 )
				{
					json.clear();
					json.put("power", 0);
					break;
				}
				else if ( v[2] == 5 )
					json.put("power", 255);
				else 
					json.put("mode", v[2]);
			}
			
			if ( v != null && v[0] == 0x60)
			{
				if ( v.length < 7 )
					continue;
				int channelid = v[3] ;
				if ( channelid == 2 )
					json.put("infan", v[6] & 0xff);
				else if ( channelid == 3 )
					json.put("outfan", v[6] & 0xff);
			}
		}
		
		JSONArray ja = new JSONArray();
		ja.add(json);
		this.commandjson = ja.toJSONString();
		
		return commandjson;
	}

	@Override
	public Integer getValue()
	{
		if ( this.status != null )
			return this.status;
		
		if ( StringUtils.isNotBlank(this.devicestatus))
		{
			if ( IRemoteConstantDefine.WARNING_TYPE_CURTAIN_OPEN.equals(this.devicestatus) )
				this.status = IRemoteConstantDefine.DEVICE_STATUS_DOOR_SENSOR_OPEN ;
			else if ( IRemoteConstantDefine.WARNING_TYPE_CURTAIN_CLOSE.equals(this.devicestatus) )
				this.status = IRemoteConstantDefine.DEVICE_STATUS_DOOR_SENSOR_CLOSE ;
		}
		
		return status;
	}

	@Override
	public List<CommandTlv> getCommandTlv()
	{
		if ( commandtlvlst != null )
			return commandtlvlst;

		if ( StringUtils.isBlank(this.commandjson))
			return null; 
		
		JSONArray ja = parseJSONArray(this.commandjson);
		
		if ( ja.size() == 0 )
			return null ;
		
		JSONObject json = ja.getJSONObject(0);
		
		commandtlvlst = new ArrayList<CommandTlv>();

		if ( json.containsKey("power") && json.getIntValue("power") == 0 )
		{
			commandtlvlst.add(CommandUtil.createAirconditionCommand(this.zwavedevice.getNuid(), (byte)0 ));
		}
		else 
		{
			if ( json.containsKey("power") && json.getIntValue("power") == 255 )
				commandtlvlst.add(CommandUtil.createAirconditionCommand(this.zwavedevice.getNuid(), (byte)5 ));
			if ( json.containsKey("mode"))
				commandtlvlst.add(CommandUtil.createAirconditionCommand(this.zwavedevice.getNuid(), json.getByteValue("mode") ));
			if ( json.containsKey("infan"))
				commandtlvlst.add(CommandUtil.createFreshAirFanCommand(this.zwavedevice.getNuid(),2, json.getByteValue("infan") ));
			if ( json.containsKey("outfan"))
				commandtlvlst.add(CommandUtil.createFreshAirFanCommand(this.zwavedevice.getNuid(),3, json.getByteValue("outfan") ));
		}
		if ( this.operationtype != null )
		{
			for ( CommandTlv ct : commandtlvlst )
				ct.addUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE ,operationtype ,TagDefine.TAG_LENGTH_1 ));
		}
		
		return commandtlvlst;
	}

}

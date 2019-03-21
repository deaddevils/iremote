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

public class DehumidifyOperationTranslator extends OperationTranslatorBase
{
	private static String OPERATION[] = new String[]{"" , "power" , "fan" , "freshair","internalcirculate"};
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
			if ( v != null && v[0] == 0x60)
			{
				if ( v.length < 7 )
					continue;
				int channelid = v[3] ;
				if ( channelid <= 0 || channelid >= OPERATION.length )
					continue;
				json.put(OPERATION[channelid], v[6] & 0xff);
			}
			else if ( v != null && v[0] == 0x26 )
			{
				json.put("humidity", v[2] & 0xff);
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
		for ( int i = 1 ; i < OPERATION.length ; i ++ )
		{
			if ( !json.containsKey(OPERATION[i]))
				continue;
			CommandTlv ct = CommandUtil.createSwitchCommand(this.zwavedevice.getNuid(), (byte)i, json.getByteValue(OPERATION[i]) );
			if ( this.operationtype != null )
				ct.addUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE ,operationtype ,TagDefine.TAG_LENGTH_1 ));
			commandtlvlst.add(ct);
		}
		
		if ( json.containsKey("humidity"))
		{
			CommandTlv ct = CommandUtil.createMultilevelSwitchCommand(this.zwavedevice.getNuid(), json.getByteValue("humidity") );
			if ( this.operationtype != null )
				ct.addUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE ,operationtype ,TagDefine.TAG_LENGTH_1 ));
			commandtlvlst.add(ct);
		}
		
		for (CommandTlv ct : commandtlvlst )
			ct.setDeviceid(this.zwavedevice.getDeviceid());
		
		return commandtlvlst;
	}

}

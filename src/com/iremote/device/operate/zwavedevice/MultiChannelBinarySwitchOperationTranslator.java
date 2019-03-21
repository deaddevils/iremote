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

public class MultiChannelBinarySwitchOperationTranslator extends OperationTranslatorBase
{
	@Override
	public String getDeviceStatus()
	{
		if ( StringUtils.isNotBlank(this.devicestatus))
			return this.devicestatus;
		if ( this.status != null )
		{
			if ( this.status == IRemoteConstantDefine.DEVICE_STATUS_DOOR_SENSOR_OPEN )
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
			JSONArray ja = new JSONArray();
			byte[][] b = TlvWrap.splitTag(command, 0);
			for ( int i = 0 ; i < b.length ; i ++ )
			{
				if ( TlvWrap.getTag(b[i] ,0 ) != TagDefine.TAG_ZWAVE_COMMAND)
					continue;
				
				byte[] v = TlvWrap.readTag(b[i], TagDefine.TAG_ZWAVE_COMMAND, 0);
				
				JSONObject json = new JSONObject();
				
				if ( v.length > 6 )
				{
					json.put(IRemoteConstantDefine.CHANNELID, (v[3] & 0xff));
					
					this.setDeviceStatus(null);
					this.setStatus((v[6] & 0xff));
				}
				else 
				{
					json.put(IRemoteConstantDefine.CHANNELID, 1);
					
					this.setDeviceStatus(null);
					this.setStatus((v[2] & 0xff));
				}
				
				json.put(IRemoteConstantDefine.OPERATION, this.getDeviceStatus());
				
				ja.add(json);
			}
			
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
			if ( IRemoteConstantDefine.DEVICE_STATUS_OPEN.equals(this.devicestatus) )
				this.status = IRemoteConstantDefine.DEVICE_STATUS_DOOR_SENSOR_OPEN ;
			else if ( IRemoteConstantDefine.DEVICE_STATUS_CLOSE.equals(this.devicestatus))
				this.status = IRemoteConstantDefine.DEVICE_STATUS_DOOR_SENSOR_CLOSE ;
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
			
			commandtlvlst = new ArrayList<CommandTlv>();
			
			for ( int i = 0 ; i < ja.size() ; i ++ )
			{
				JSONObject json = ja.getJSONObject(i);
				
				if ( !json.containsKey(IRemoteConstantDefine.OPERATION))
					continue;

				this.setStatus(null);
				this.setDeviceStatus(json.getString(IRemoteConstantDefine.OPERATION));
				
				CommandTlv ct = CommandUtil.createSwitchCommand(this.zwavedevice.getNuid(), json.getByteValue(IRemoteConstantDefine.CHANNELID), this.getValue().byteValue() );
				if ( this.operationtype != null )
					ct.addUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE ,operationtype ,TagDefine.TAG_LENGTH_1 ));
				ct.setDeviceid(zwavedevice.getDeviceid());
				commandtlvlst.add(ct);
			}
		}
		
		return commandtlvlst;
	}

}

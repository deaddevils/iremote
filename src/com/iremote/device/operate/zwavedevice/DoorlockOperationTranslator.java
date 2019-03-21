package com.iremote.device.operate.zwavedevice;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.device.operate.OperationTranslatorBase;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.ZWaveDevice;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvWrap;

public class DoorlockOperationTranslator extends OperationTranslatorBase{

	@Override
	public String getDeviceStatus() {
		if ( StringUtils.isNoneBlank(this.devicestatus))
			return this.devicestatus;
		if ( this.status != null && this.status != IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_CLOSE)
			this.devicestatus = IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN;
		else if ( this.status != null && this.status == IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_CLOSE)
			this.devicestatus = IRemoteConstantDefine.EVENT_TYPE_DOOR_LOCK_CLOSE;
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

			if ( (v[2] & 0xff) == 255 )
			{
				this.setDeviceStatus(null);
				this.setStatus((v[2] & 0xff));
			}
			else 
			{
				this.setDeviceStatus(null);
				this.setStatus(IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN_0);
			}
			
			JSONObject json = new JSONObject();
			json.put(IRemoteConstantDefine.OPERATION, this.getDeviceStatus());
			//json.put(IRemoteConstantDefine.VALUE, v[2] & 0xff);
			
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
			if ( IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN.equals(this.devicestatus) )
				this.status = IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_OPEN_0 ;
			else if ( IRemoteConstantDefine.EVENT_TYPE_DOOR_LOCK_CLOSE.equals(this.devicestatus) )
				this.status = IRemoteConstantDefine.DEVICE_STATUS_DOOR_LOCK_CLOSE ;
			else if (IRemoteConstantDefine.WARNING_TYPE_DOOR_BELL_RING.equals(this.devicestatus) )
				this.status = IRemoteConstantDefine.DOOR_LOCK_BELL_RING;
		}
		return this.status;
	}

	@Override
	public void setCommandjson(String json)
	{
		super.setCommandjson(json);
		rewritejson();
	}

	@Override
	public void setZWavedevice(ZWaveDevice zd)
	{
		super.setZWavedevice(zd);
		rewritejson();
	}

	private void rewritejson()
	{
		if ( this.zwavedevice == null || StringUtils.isBlank(this.commandjson))
			return ;
		JSONArray ja = parseJSONArray(this.commandjson);
		JSONObject jn = ja.getJSONObject(0);

		if ( !jn.containsKey(IRemoteConstantDefine.OPERATION)
				|| !IRemoteConstantDefine.WARNING_TYPE_DOOR_LOCK_OPEN.equals(jn.getString(IRemoteConstantDefine.OPERATION)))
			return ;
		
		int s = 0 ;
		for ( DeviceCapability dc : this.zwavedevice.getCapability())
			if ( dc.getCapabilitycode() == 1 )
				s = 1;
		jn.put(IRemoteConstantDefine.VALUE, s);
		
		ja = new JSONArray();
		ja.add(jn);
		super.setCommandjson(ja.toJSONString());

	}
	
	@Override
	protected CommandTlv createCommandTlv(JSONObject json)
	{
		return CommandUtil.createLockCommand(this.zwavedevice.getNuid(), json.getByteValue(IRemoteConstantDefine.VALUE));
	}
}

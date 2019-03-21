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
import com.iremote.infraredtrans.tlv.TlvWrap;

public class KaraoOperationTranslator extends OperationTranslatorBase{

	@Override
	public String getDeviceStatus() {
		Integer value = getValue();
		if(value != null){
			if(value == 255){
				this.devicestatus = IRemoteConstantDefine.DEVICE_OPERATION_OPEN;
			}else if(value == 0){
				this.devicestatus = IRemoteConstantDefine.DEVICE_OPERATION_CLOSE;
			}
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
				if(v[0] == 0x2b){
					json.put("mode", v[2] & 0xff);
				}else if(v[0] == 0x60){
					if ( v[3] == 0x01){
						json.put("musicvolume", v[6] & 0xff);
					}else  if( v[3] == 0x02){
						json.put("micvolume", v[6] & 0xff);
					}else  if( v[3] == 0x03){
						json.put("effectsvolume", v[6] & 0xff);
					}
				}else if(v[0] == 0x25){
					json.put("power", v[2] & 0xff);
				}
				
			
			}
			JSONArray ja = new JSONArray();
			ja.add(json);
			this.commandjson = ja.toJSONString();
		}
		return this.commandjson;
	}

	@Override
	public Integer getValue() {
		Integer value = null;
		if(this.devicestatus != null){
			
			if(IRemoteConstantDefine.DEVICE_OPERATION_OPEN.equals(this.devicestatus)){
				value = 255;
			}else if(IRemoteConstantDefine.DEVICE_OPERATION_CLOSE.equals(this.devicestatus)){
				value = 0;
			}
			return value;
		}
		String commandJson = getCommandjson();
		if(commandJson == null)
			return null;
		JSONArray ja = JSONArray.parseArray(commandJson);
		JSONObject json = ja.getJSONObject(0);
		if ( json.containsKey("power")){
			value = json.getInteger("power");
		}
		return value;
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
			
			commandtlvlst = new ArrayList<CommandTlv>();
			JSONObject json = ja.getJSONObject(0);
			
			if ( json.containsKey("power") && json.getIntValue("power") == 0 ){
				commandtlvlst.add(CommandUtil.createKaraoPowereCommand(this.zwavedevice.getNuid() , (byte)0));
			}else{
				if ( json.containsKey("power")){
					commandtlvlst.add(CommandUtil.createKaraoPowereCommand(this.zwavedevice.getNuid(), json.getIntValue("power")));
				}
				if ( json.containsKey("mode")){
					commandtlvlst.add(CommandUtil.createKaraoModeCommand(this.zwavedevice.getNuid(), json.getIntValue("mode")));
				}
				if ( json.containsKey("musicvolume")){
					commandtlvlst.add(CommandUtil.createKaraoVolumeCommand(this.zwavedevice.getNuid(), 0x01, json.getIntValue("musicvolume")));
				}
				if(json.containsKey("micvolume")){
					commandtlvlst.add(CommandUtil.createKaraoVolumeCommand(this.zwavedevice.getNuid(), 0x02, json.getIntValue("micvolume")));
				}
				if(json.containsKey("effectsvolume")){
					commandtlvlst.add(CommandUtil.createKaraoVolumeCommand(this.zwavedevice.getNuid(), 0x03, json.getIntValue("effectsvolume")));
				}
			}
			for (CommandTlv ct : commandtlvlst )
				ct.setDeviceid(this.zwavedevice.getDeviceid());
		}
		return commandtlvlst;
	}

}

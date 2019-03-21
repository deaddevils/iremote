package com.iremote.device.operate.zwavedevice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.device.operate.OperationTranslatorBase;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class BackgroudMusicTranslator extends OperationTranslatorBase{

	@Override
	public String getDeviceStatus() 
	{
		return null;
	}

	@Override
	public String getCommandjson() 
	{
		return null;
	}

	@Override
	public Integer getValue() 
	{
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
	public List<CommandTlv> getCommandTlv() 
	{
		if ( commandtlvlst != null )
			return commandtlvlst;
		
		if ( this.zwavedevice != null &&  StringUtils.isNotBlank(this.commandjson))
		{
			int nuid = zwavedevice.getNuid();
			JSONArray ja = parseJSONArray(this.commandjson);

			if ( ja == null || ja.size() == 0 )
				return null ;
			
			JSONObject json = ja.getJSONObject(0);
			
			commandtlvlst = new ArrayList<CommandTlv>();
						
			if ( json.containsKey("power"))
				commandtlvlst.add(CommandUtil.createSwitchCommand(nuid, json.getByteValue("power")));
			if ( json.containsKey("pause"))
				commandtlvlst.add(CommandUtil.createPauseCommand(nuid, json.getByteValue("pause")));
			if ( json.containsKey("mute"))
				commandtlvlst.add(CommandUtil.createMuteCommand(nuid, json.getByteValue("mute")));
			if ( json.containsKey("volumn"))
				commandtlvlst.add(CommandUtil.createVolumnCommand(nuid, json.getByteValue("volumn")));
			if ( json.containsKey("looptype"))
				commandtlvlst.add(CommandUtil.createLooptypeCommand(nuid, json.getByteValue("looptype")));
			if ( json.containsKey("source"))
				commandtlvlst.add(CommandUtil.createSourceCommand(nuid, json.getByteValue("source")));
			if ( json.containsKey("song"))
				commandtlvlst.add(CommandUtil.createSongCommand(nuid, json.getByteValue("song")));
		}
		
		
		return commandtlvlst;
	}

	
}

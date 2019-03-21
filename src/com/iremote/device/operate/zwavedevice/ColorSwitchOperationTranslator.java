package com.iremote.device.operate.zwavedevice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.commandclass.CommandUtil;
import com.iremote.device.operate.OperationTranslatorBase;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;

public class ColorSwitchOperationTranslator extends OperationTranslatorBase{
	
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
			JSONArray ja = parseJSONArray(this.commandjson);
			if ( ja.size() == 0 )
				return null ;
			JSONObject json = ja.getJSONObject(0);

			commandtlvlst = new ArrayList<CommandTlv>();

			if ( json.containsKey("bright") && json.getIntValue("bright") >= 1 )
				commandtlvlst.add(CommandUtil.CreateColorSwitchCommand(this.zwavedevice.getNuid() ,json.getInteger("bright").intValue()));
			else if ( json.containsKey("power") )
			{
				commandtlvlst.add(CommandUtil.CreateColorSwitchOpenCommand(this.zwavedevice.getNuid() ,json.getInteger("power")));
			}

			CommandTlv ctv = CommandUtil.createColorSwitchCommand(this.zwavedevice.getNuid(), 
					new Byte[]{json.getByte("warm"),json.getByte("bright"),json.getByte("red"),json.getByte("green"),json.getByte("blue") , null , null , null , null , null , json.getByte("alpha")}
					,this.zwavedevice.getStatuses());
			if ( ctv != null )
				commandtlvlst.add(ctv);
			
			if ( this.operationtype != null && commandtlvlst.size() > 0  )
			{
				for ( CommandTlv ct : commandtlvlst )
					ct.addUnit(new TlvIntUnit(TagDefine.TAG_OPERATION_TYPE ,operationtype ,TagDefine.TAG_LENGTH_1 ));
			}
			for (CommandTlv ct : commandtlvlst )
				ct.setDeviceid(this.zwavedevice.getDeviceid());
		}
		
		return commandtlvlst;
	}

}

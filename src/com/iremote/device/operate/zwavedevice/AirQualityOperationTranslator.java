package com.iremote.device.operate.zwavedevice;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.constant.AirQualityLevel;
import com.iremote.common.constant.ArithmeticOperator;
import com.iremote.device.operate.OperationTranslatorBase;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class AirQualityOperationTranslator  extends OperationTranslatorBase
{

	@Override
	public String getDeviceStatus()
	{
		if ( StringUtils.isNotBlank(this.devicestatus))
			return this.devicestatus;
		if ( this.status != null )
		{
			if ( this.status <= AirQualityLevel.moderate.getLevel() )
				this.devicestatus = IRemoteConstantDefine.AIR_QUALITY_STATUS_CLEAR;
			else
				this.devicestatus = IRemoteConstantDefine.AIR_QUALITY_STATUS_POLLUTE;
		}
		return this.devicestatus;
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
			if ( IRemoteConstantDefine.AIR_QUALITY_STATUS_CLEAR.equals(this.devicestatus) )
				this.status = AirQualityLevel.moderate.getLevel() ;
			else 
				this.status = AirQualityLevel.unhealthy_for_sensitive_groups.getLevel() ;
		}
		return this.status;
	}

	@Override
	public byte[] getCommand()
	{
		return null;
	}

	@Override
	public byte[] getCommands()
	{
		return null;
	}

	@Override
	public String getOperator()
	{
		if ( getValue() != null )
		{
			if ( this.status <= AirQualityLevel.moderate.getLevel() )
				return ArithmeticOperator.le.name();
			else
				return ArithmeticOperator.ge.name();
		}
		return null ;
	}

	@Override
	public List<CommandTlv> getCommandTlv()
	{
		return null;
	}
	
	
}

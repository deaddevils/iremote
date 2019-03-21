package com.iremote.infraredtrans.zwavecommand.cache;

import com.iremote.common.commandclass.CommandValue;

public class ZwaveReportCache implements IZwaveReportCache
{
	private CommandValue commandvalue;
	private String cachekey ;
	private String value ;
	
	public ZwaveReportCache(CommandValue commandvalue, String cachekey, Integer value)
	{
		super();
		this.commandvalue = commandvalue;
		this.cachekey = cachekey;
		this.value = String.valueOf(value);
	}
	
	public ZwaveReportCache(CommandValue commandvalue, String cachekey, String value)
	{
		super();
		this.commandvalue = commandvalue;
		this.cachekey = cachekey;
		this.value = value;
	}

	public ZwaveReportCache(CommandValue commandvalue)
	{
		super();
		this.commandvalue = commandvalue;
		this.value = String.valueOf(this.commandvalue.getValue());
		this.cachekey = String.format("%d_%d_%d", commandvalue.getChannelid(), commandvalue.getCommandclass() , commandvalue.getCommand() );
	}

	public ZwaveReportCache(CommandValue commandvalue, String value)
	{
		super();
		this.commandvalue = commandvalue;
		this.value = value;
		this.cachekey = String.format("%d_%d_%d", commandvalue.getChannelid(), commandvalue.getCommandclass() , commandvalue.getCommand() );
	}

	@Override
	public String getCacheKey()
	{
		return cachekey;
	}

	@Override
	public String getValue()
	{
		return value;
	}


}

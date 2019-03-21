package com.iremote.infraredtrans.zwavecommand;

import com.iremote.common.GatewayUtils;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.infraredtrans.zwavecommand.cache.IZwaveReportCache;
import com.iremote.infraredtrans.zwavecommand.cache.ZwaveReportCache;

public class BatteryReportProcessor extends ZWaveReportBaseProcessor 
{
	protected Integer battery;
	
	@Override
	protected void parseReport()
	{
		if ( zrb.getCmd() == null || zrb.getCmd().length <= 2 )
			return ;

			battery = zrb.getCommandvalue().getValue();
	}
	
	@Override
	protected void updateDeviceStatus() 
	{
		if ( battery == null )
			return ;
		if ( battery <= 100 )
			processBatteryReport();
		else if ( battery == 255 )
			processLowBatteryReport();
		else 
		{
			this.dontsavenotification();
			this.dontpusmessage();
		}
		
		if ( GatewayUtils.isLockGateway(zrb.getRemote())
				|| GatewayUtils.isAirCondition(zrb.getRemote()))
			zrb.getRemote().setBattery(zrb.getDevice().getBattery());
	}

	private void processBatteryReport()
	{
		zrb.getDevice().setBattery(zrb.getCommandvalue().getValue());
		if ( zrb.getCommandvalue().getValue() > 10 )
			this.dontsavenotification();
	}
	
	private void processLowBatteryReport()
	{
		zrb.getDevice().setBattery(10);
	}

	@Override
	public String getMessagetype() {
		if ( zrb.getDevice().getBattery() != null && zrb.getDevice().getBattery() <= 10 )
			return IRemoteConstantDefine.WARNING_TYPE_LOW_BATTERY;
		return IRemoteConstantDefine.WARNING_TYPE_BATTERY;
	}

	@Override
	protected IZwaveReportCache createCacheReport()
	{
		if ( battery == null )
			return null;
		return new ZwaveReportCache(zrb.getCommandvalue());
	}


}


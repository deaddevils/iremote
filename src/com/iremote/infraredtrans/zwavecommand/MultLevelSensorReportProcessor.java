package com.iremote.infraredtrans.zwavecommand;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;

public class MultLevelSensorReportProcessor extends ZWaveReportBaseProcessor 
{
	private static Log log = LogFactory.getLog(MultLevelSensorReportProcessor.class);
	
	protected int sensortype ;
	protected float sensorvalue ;
	protected int scale ;

	public MultLevelSensorReportProcessor()
	{
		super();
		this.dontsavenotification();
	}

	@Override
	protected void updateDeviceStatus()
	{
		sensortype = zrb.getCmd()[2] & 0xff ;
		int tmp = zrb.getCmd()[3] & 0xff ;
		int size = tmp & 0x7 ;
		scale = ( tmp >> 3 ) & 0x3 ;
		int precision = ( tmp >> 5 ) & 0x7;
		
		
		sensorvalue = Utils.readsignedint(zrb.getCmd(), 4, 4 + size);
		sensorvalue /= Math.pow(10, precision);
		
		if ( log.isInfoEnabled())
			log.info(String.format("sensortype:%d,scale:%d,sensorvalue:%f", sensortype,scale,sensorvalue));
		
		updateStatus();
	}
	
	
	protected void updateStatus()
	{
		String ss = zrb.getDevice().getStatuses();
		if ( StringUtils.isBlank(ss))
			ss = Utils.getDeviceDefaultStatuses(zrb.getDevice().getDevicetype());
		
		if ( sensortype == 1 )  //Air Temperature
		{
			float f = 0 ;
			float c = 0 ;
			if ( scale == 1 ) //Fahrenheit
			{
				f = sensorvalue;
				c = Utils.fahrenheit2celsius(sensorvalue);
			}
			else if ( scale == 0 ) //Celsius (C)
			{
				f = Utils.celsius2fahrenheit(sensorvalue);
				c = sensorvalue;
			}
			ss = Utils.setJsonArrayValue(ss, 0, c);
			ss = Utils.setJsonArrayValue(ss, 1, f);
		}
		else if ( sensortype == 3 ) //Luminance 
		{
			if ( scale == 1 )
				ss = Utils.setJsonArrayValue(ss, 2, sensorvalue);
		}
		else if ( sensortype == 5 ) //Humidity 
		{
			if ( scale == 0 ) //Percentage value
				ss = Utils.setJsonArrayValue(ss, 4, sensorvalue);
		}
		zrb.getDevice().setStatuses(ss);
	}

	@Override
	public String getMessagetype()
	{
		return IRemoteConstantDefine.WARNING_TYPE_DEVICE_STATUS;
	}

}

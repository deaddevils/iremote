package com.iremote.action.device.authorize;

import org.apache.commons.lang.StringUtils;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveDeviceShare;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveDeviceShareService;
import com.opensymphony.xwork2.Action;

public class AuthorizeDeviceOperatePageAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private String token;
	private ZWaveDeviceShare zwavedeviceshare ;
	private ZWaveDevice zwavedevice ;
	private int day ;
	private int hour ;
	private int min ;
	private int vt = 0 ;
	private String phonenumber;
	
	public String execute() {
		if(StringUtils.isNotBlank(token)){
			init();
		}
		return Action.SUCCESS;
	}
	
	private void init() {
			
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		zwavedeviceshare = zdss.querybytoken(token.substring(0, 16));

		if (zwavedeviceshare == null || zwavedeviceshare.getZwavedeviceid() == null) {
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return;
		}

		ZWaveDeviceService zds = new ZWaveDeviceService();
		zwavedevice = zds.query(zwavedeviceshare.getZwavedeviceid());
		phonenumber = zwavedeviceshare.getTouser();
		if (zwavedevice == null) {
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return;
		}

	}

	public int getResultCode()
	{
		return resultCode;
	}
	
	public String getDevicename()
	{
		if ( zwavedevice == null )
			return "" ;
		return this.zwavedevice.getName();
	}
	
	public int getValidtype()
	{
		if ( vt != 0 )
			return vt ;
		if ( zwavedeviceshare == null )
			return 0 ;
		if ( zwavedeviceshare.getValidfrom().getTime() >= System.currentTimeMillis() )
		{
			timetranslate( zwavedeviceshare.getValidfrom().getTime() - System.currentTimeMillis() ) ;
			vt = 1 ;
		}
		else if ( zwavedeviceshare.getValidthrough().getTime() > System.currentTimeMillis())
		{
			timetranslate( zwavedeviceshare.getValidthrough().getTime() - System.currentTimeMillis() );
			vt = 2 ;
		}
		else
			vt = 3 ;
		return vt ;
	}
	
	private void timetranslate(long t)
	{
		if ( ( t % 60000 ) != 0 ) 
			t = ( t / 60000 + 1 ) * 60000 ;
		t = t / 1000 ;
		day = (int) (t / ( 24 * 60 * 60 ));
		hour = (int) ((t / ( 60 * 60 )) % 24 );
		min = (int) (( t / 60 ) % 60 );
	}

	public int getDay()
	{
		return day;
	}

	public int getHour()
	{
		return hour;
	}

	public int getMin()
	{
		return min;
	}

	public void setTt(String tt)
	{
		token = tt ;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public String getPhonenumber() {
		return phonenumber;
	}
	
}

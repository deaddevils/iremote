package com.iremote.action.device.authorize;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.iremote.action.device.DeviceOperationBaseAction;
import com.iremote.action.helper.GatewayHelper;
import com.iremote.action.helper.TimeZoneHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.constant.DeviceAuthorizeType;
import com.iremote.device.operate.ZwaveDeviceCommandStore;
import com.iremote.device.operate.zwavedevice.ZwaveDeviceOperateCommandBase;
import com.iremote.domain.ZWaveDevice;
import com.iremote.domain.ZWaveDeviceShare;
import com.iremote.event.gateway.GatewayEventConsumerStore;
import com.iremote.event.gateway.IGatewayEventConsumer;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.service.UserService;
import com.iremote.service.ZWaveDeviceService;
import com.iremote.service.ZWaveDeviceShareService;
import com.opensymphony.xwork2.Action;

public class AuthorizeDeviceOperateAction extends DeviceOperationBaseAction implements IGatewayEventConsumer
{
	private String securitycode;
	private String phonenumber ;
	private String operation ;
	private Integer channel; 
	private boolean wakeupdevice = false ;
	private ZWaveDeviceShare zwavedeviceshare ;
	private Date validfrom ;
	private Date validthrought;
	private long expiretime = System.currentTimeMillis() + 30 * 1000 ;
	private int day ;
	private int hour ;
	private int min ;
	private int vt = 0 ;
	
	public String execute() {
		if (init()  == false )
			return Action.SUCCESS;

		if ( checkValidtime() == false )
			return Action.SUCCESS;
		if(securitycode.length()<=6){
			setCookie();
		}

		if ( !ConnectionManager.contants(deviceid))
		{
			resultCode = ErrorCodeDefine.DEVICE_OFFLINE;
			
			ZWaveDeviceService zds = new ZWaveDeviceService();
			ZWaveDevice zd = zds.query(zwavedeviceshare.getZwavedeviceid());
			
			if ( zd.getNuid() > 10000 )
			{
				GatewayEventConsumerStore.getInstance().put(deviceid, this);
				this.wakeupdevice = true;
			}
		}
		else 
			super.execute();
		
		if ( zwavedeviceshare.getValidtype() != null 
				&& zwavedeviceshare.getValidtype() == DeviceAuthorizeType.validonce.getValidtype())
			zwavedeviceshare.setValidtype(DeviceAuthorizeType.notvalid.getValidtype());
		
		return Action.SUCCESS;
	}
	
	protected void setCookie()
	{
		Cookie c = new Cookie("authtoken" , securitycode);
		
		Calendar dn = Calendar.getInstance();
		int ma = ( int )( (validthrought.getTime() - dn.getTimeInMillis()) / 1000);
		
		if ( ma <= 0 )
			ma = 1 ;
		
		c.setMaxAge(ma);
		c.setPath("/iremote");
		ServletActionContext.getResponse().addCookie(c);
		
		c = new Cookie("phonenumber" , phonenumber);
		c.setMaxAge(10*365*24*3600);
		c.setPath("/iremote");
		ServletActionContext.getResponse().addCookie(c);
	}

	@Override
	public void onGatewayOnline()
	{	
		super.execute();
		GatewayEventConsumerStore.getInstance().remove(deviceid, this);
	}
	
	private boolean checkValidtime()
	{
		intiValidtype();
		
		if ( vt == 2 )
			return true;
		
		resultCode = ErrorCodeDefine.NO_PRIVILEGE;
		return false ;
	}
	
	private boolean init()
	{
		if ( StringUtils.isBlank(securitycode))
		{
			super.resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return false ;
		}
			
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		List<ZWaveDeviceShare> lst = null;
		if(securitycode.length()<=6){
			lst = zdss.querybytoken(phonenumber ,securitycode.substring(0, 2));
		}else if(securitycode.length()==32){
			lst = zdss.queryonlybytoken(securitycode.substring(0,16));
		}
		
		if ( lst == null || lst.size() == 0 )
		{
			super.resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return false ;
		}

		UserService svr = new UserService();
		for ( ZWaveDeviceShare zds : lst )
		{
			if (StringUtils.isBlank(zds.getSecuritycode()))
			{
				if ( securitycode.equals(zds.getToken()) )
				{
					zwavedeviceshare = zds ;
					break;
				}
			}
			else if ( svr.checkPassword(phonenumber, securitycode, zds.getSecuritycode())||svr.checkPassword("", securitycode, zds.getSecuritycode()))
			{
				zwavedeviceshare = zds ;
				break;
			}
		}
			
		if ( zwavedeviceshare == null || zwavedeviceshare.getZwavedeviceid() == null)
		{
			super.resultCode = ErrorCodeDefine.SECURITYECODE_PHONENUMBER_NOT_MATCH;
			return false ;
		}
		
		if ( zwavedeviceshare.getValidtype() != null 
				&&  zwavedeviceshare.getValidtype() == DeviceAuthorizeType.notvalid.getValidtype())
		{
			super.resultCode = ErrorCodeDefine.NO_PRIVILEGE_3;
			return false ;
		}

		super.setZwavedeviceid(zwavedeviceshare.getZwavedeviceid());

		if ( !StringUtils.isBlank(zwavedeviceshare.getUsername()))
			super.setOperator(String.format("%s",zwavedeviceshare.getTouser()));
		else
			super.setOperator(zwavedeviceshare.getTouser());
		
		deviceid = zwavedeviceshare.getDeviceid();
		
		return true;
	}
	
	@Override
	protected CommandTlv[] createCommandTlv()
	{
		ZwaveDeviceOperateCommandBase zdc = ZwaveDeviceCommandStore.getInstance().getProcessor(super.device.getDevicetype(), operation);
		if ( zdc == null )
		{
			resultCode = ErrorCodeDefine.NOT_SUPPORT;
			return null;
		}
		
		zdc.setZwavedevice(super.device);
		if ( this.channel != null )
			zdc.setChannel(this.channel.byteValue());
		CommandTlv ct = zdc.createCommand();
		
		return new CommandTlv[]{ct};
	}

	private int intiValidtype()
	{
		if ( vt != 0 )
			return vt ;
		if ( zwavedeviceshare == null )
			return 0 ;
		
		validfrom = zwavedeviceshare.getValidfrom();
		validthrought = zwavedeviceshare.getValidthrough();
		
		String tzid = GatewayHelper.getRemoteTimezoneId(zwavedeviceshare.getDeviceid());
		if ( StringUtils.isNotBlank(tzid))
		{
			validfrom = TimeZoneHelper.timezoneTranslate(validfrom, TimeZone.getTimeZone(tzid), TimeZone.getDefault());
			validthrought = TimeZoneHelper.timezoneTranslate(validthrought, TimeZone.getTimeZone(tzid), TimeZone.getDefault());
		}
		
		if ( validfrom.getTime() >= System.currentTimeMillis() )
		{
			timetranslate( validfrom.getTime() - System.currentTimeMillis() ) ;
			vt = 1 ;
		}
		else if ( validthrought.getTime() > System.currentTimeMillis())
		{
			timetranslate( validthrought.getTime() - System.currentTimeMillis() );
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

	public int getValidtype()
	{
		return vt ;
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
	
	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	public void setChannel(Integer channel)
	{
		this.channel = channel;
	}

	public int getZwavedeviceid()
	{
		return zwavedeviceid;
	}

	@Override
	public long getExpireTime()
	{
		return expiretime;
	}

	@Override
	public void onGatewayOffline()
	{		
		
	}

	public boolean isWakeupdevice()
	{
		return wakeupdevice;
	}

	public void setSecuritycode(String securitycode)
	{
		this.securitycode = securitycode;
	}

	public void setPhonenumber(String phonenumber)
	{
		this.phonenumber = phonenumber;
	}

}

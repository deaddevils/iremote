package com.iremote.action.device.doorlock;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.iremote.action.helper.DeviceHelper;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.DoorlockUserService;
import com.iremote.service.ZWaveDeviceService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class ListLockUserAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private List<DoorlockUser> doorlockuserlst;
	private PhoneUser phoneuser ;
	private String deviceid ;
	private int nuid ;
	private int zwavedeviceid ;
	private Set<Integer> capability ;
	
	public String execute()
	{
		//changeLanguage();
		ZWaveDeviceService zds = new ZWaveDeviceService();
		
		ZWaveDevice zd ;
		if ( zwavedeviceid == 0 )	
			zd = zds.querybydeviceid(deviceid, nuid);
		else 
			zd = zds.query(zwavedeviceid);
		
		if ( zd == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.ERROR;
		}
		
		nuid = zd.getNuid();
		
		zwavedeviceid = zd.getZwavedeviceid();
		
		if ( PhoneUserHelper.checkPrivilege(phoneuser, zd) == false )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.ERROR;
		}
		
		DoorlockUserService dus = new DoorlockUserService();
		doorlockuserlst = dus.querybyZwavedeviceid(zd.getZwavedeviceid());
		
		capability = DeviceHelper.initDeviceCapability(zd);
		
		return Action.SUCCESS;
	}

    public void changeLanguage() {
        Locale locale = Locale.getDefault();  
        if(phoneuser.getLanguage() != null){
        	String[] internationalization =  phoneuser.getLanguage().split("_");
        	if(internationalization != null && internationalization.length == 2){
        		String language = internationalization[0];
            	String country = internationalization[1];
            	locale = new Locale(language, country);
        	}
        }
        ActionContext.getContext().setLocale(locale);  
    }  
	
	public boolean isSupportAddUser()
	{
		if ( capability == null )
			return false ;
		return ( this.capability.contains(5) 
				|| this.capability.contains(6)
				|| this.capability.contains(7));
	}
	
	public List<DoorlockUser> getDoorlockuserlst()
	{
		return doorlockuserlst;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public void setZwavedeviceid(int zwavedeviceid)
	{
		this.zwavedeviceid = zwavedeviceid;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public int getZwavedeviceid()
	{
		return zwavedeviceid;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public void setNuid(int nuid)
	{
		this.nuid = nuid;
	}
	
	public int getNuid()
	{
		return nuid;
	}
}

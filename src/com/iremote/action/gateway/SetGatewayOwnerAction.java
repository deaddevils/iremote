package com.iremote.action.gateway;

import java.util.Date;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.RemoteOwnerChangeEvent;
import com.iremote.domain.GatewayInfo;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.service.GatewayInfoService;
import com.iremote.service.RemoteService;
import com.opensymphony.xwork2.Action;

public class SetGatewayOwnerAction
{
	protected int resultCode = ErrorCodeDefine.SUCCESS;
	private String deviceid ;
	private PhoneUser phoneuser;
	private String key;
	private String name;
	private String ssid;
	
	public String execute()
	{
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
		
		if ( r == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		if ( r.getPhoneuserid() != null && r.getPhoneuserid() != 0 )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}
		
		if ( r.getPlatform() != phoneuser.getPlatform() )
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}
		
		GatewayInfoService gs = new GatewayInfoService();
		GatewayInfo gi = gs.querybykey(key);
		if ( gi == null || !gi.getDeviceid().equals(deviceid))
		{
			resultCode = ErrorCodeDefine.NO_PRIVILEGE;
			return Action.SUCCESS;
		}
		
		r.setPhonenumber(phoneuser.getPhonenumber());
		r.setPhoneuserid(phoneuser.getPhoneuserid());
		r.setName(name);
		r.setSsid(ssid);
		
		this.phoneuser.setLastupdatetime(new Date());
		
		RemoteOwnerChangeEvent re = new RemoteOwnerChangeEvent(r.getDeviceid() , new Date() , 0 , phoneuser.getPhoneuserid() , null , phoneuser.getPhonenumber() , System.currentTimeMillis());
		re.setRemote(r);
		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_OWNER_CHANGED,re );
		PhoneUserHelper.sendInfoChangeMessage(phoneuser);

		return Action.SUCCESS;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public void setDeviceid(String deviceid)
	{
		this.deviceid = deviceid;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setSsid(String ssid)
	{
		this.ssid = ssid;
	}
	
	
}

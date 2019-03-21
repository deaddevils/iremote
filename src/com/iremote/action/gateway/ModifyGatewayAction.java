package com.iremote.action.gateway;

import java.util.Date;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.InfoChange;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.service.RemoteService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "gateway", parameter = "deviceid")
public class ModifyGatewayAction
{
	protected int resultCode = ErrorCodeDefine.SUCCESS;
	private String deviceid ;
	private String name ;
	private PhoneUser phoneuser;
	
	public String execute()
	{
		RemoteService rs = new RemoteService();
		Remote r = rs.getIremotepassword(deviceid);
		
		if ( r == null )
		{
			resultCode = ErrorCodeDefine.DEVICE_NOT_EXSIT;
			return Action.SUCCESS;
		}
		
		r.setName(name);
		r.setLastupdatetime(new Date());
		
		phoneuser.setLastupdatetime(new Date());
		PhoneUserHelper.sendInfoChangeMessage(phoneuser , "editgateway");

		JMSUtil.sendmessage(IRemoteConstantDefine.NOTIFICATION_INFO_CHANGED, new InfoChange(deviceid , new Date() , 0) );
		
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

	public void setName(String name)
	{
		this.name = name;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
	
	
}

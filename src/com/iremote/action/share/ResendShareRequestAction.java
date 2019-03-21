package com.iremote.action.share;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.UserShare;
import com.iremote.service.PhoneUserService;
import com.iremote.service.UserShareService;
import com.opensymphony.xwork2.Action;

@DataPrivilege(dataprivilgetype = DataPrivilegeType.MODIFY, domain = "usershare", parameter = "shareid")
public class ResendShareRequestAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private int shareid ;
	private PhoneUser phoneuser ;

	public String execute()
	{	
		UserShareService svr = new UserShareService();
		UserShare su = svr.query(shareid) ;
		
		if ( su == null )
		{
			resultCode = ErrorCodeDefine.SHARE_INVITATION_DELETED ;
			return Action.SUCCESS;
		}
		if ( su.getStatus() != IRemoteConstantDefine.USER_SHARE_STATUS_WAIT_FOR_RESPONSE)
		{
			resultCode = ErrorCodeDefine.SHARE_INVITATION_ACCEPTED ;
			return Action.SUCCESS;
		}

		sendPushMessage(su);
		return Action.SUCCESS;
	}
	
	private void sendPushMessage(UserShare su)
	{
		PhoneUserService pus = new PhoneUserService();
		PhoneUser touser = pus.query(su.getTouserid());
		
		int r = ShareHelper.sendPushMessage(phoneuser, touser);
		if ( r != 0 )
			resultCode = ErrorCodeDefine.PUSH_MESSAGE_SEND_FAILED;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public void setShareid(int shareid)
	{
		this.shareid = shareid;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
	
	
}

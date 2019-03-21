package com.iremote.action.camera.lechange;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.PhoneUser;
import com.opensymphony.xwork2.Action;

public class LeChangeRandCodeAction 
{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private PhoneUser phoneuser ;
	private String lechangecode;
	private String lechangemsg;
	
	public String execute()
	{
		LeChangeInterface lcr = LeChangeRequestManagerStore.getInstance().getProcessor(phoneuser.getPlatform());
		
		if ( lcr == null )
		{
			resultCode = ErrorCodeDefine.NOT_SUPPORT;
			return Action.SUCCESS;
		}
		
		JSONObject rst = lcr.userBindSms(phoneuser.getPhonenumber());
		
		if ( rst == null )
		{
			resultCode = ErrorCodeDefine.THIRDPART_NETWORK_FAILED;
			return Action.SUCCESS;
		}
		
		lechangecode = lcr.getResultCode(rst);
		lechangemsg = lcr.getResultMsg(rst);
		
		if ( !String.valueOf(ErrorCodeDefine.SUCCESS).equals(lechangecode))
			resultCode = ErrorCodeDefine.THIRDPART_CALL_FAILED;
		
		return Action.SUCCESS;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public String getLechangecode()
	{
		return lechangecode;
	}

	public String getLechangemsg()
	{
		return lechangemsg;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
	
	
}

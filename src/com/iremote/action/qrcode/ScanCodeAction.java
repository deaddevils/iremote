package com.iremote.action.qrcode;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.PhoneUser;
import com.opensymphony.xwork2.Action;

public class ScanCodeAction implements IScanQrCodeProcessor
{
	protected int resultCode = ErrorCodeDefine.PARMETER_ERROR;
	private String message;
	private String appendmessage;
	private PhoneUser phoneuser;
	private String timezoneid;
	private IScanQrCodeProcessor action;

	public String execute()
	{
		if ( StringUtils.isBlank(message))
			return Action.SUCCESS;
		
		JSONObject json = JSON.parseObject(message);
		if ( json == null || !json.containsKey("type"))
			return Action.SUCCESS;
		
		action = ScanCodeActionProcessorStore.getInstance().getProcessor(json.getString("type"));
		
		if ( action == null )
		{
			this.resultCode = ErrorCodeDefine.NOT_SUPPORT;
			action = new BaseQrCodeProcessor();
			return Action.SUCCESS;
		}
		
		action.setAppendmessage(appendmessage);
		action.setMessage(message);
		action.setPhoneuser(phoneuser);
		action.setTimezoneid(timezoneid);
		action.execute();
		
		this.resultCode = action.getResultCode();
		
		return Action.SUCCESS;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public void setAppendmessage(String appendmessage)
	{
		this.appendmessage = appendmessage;
	}

	public Object getAction()
	{
		return action;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public void setTimezoneid(String timezoneid) {
		this.timezoneid = timezoneid;
	}

	@Override
	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}
	
	public String getTip()
	{
		return "The result to app is only the \"action\" filed";
	}
}

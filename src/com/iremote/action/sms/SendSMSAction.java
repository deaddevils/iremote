package com.iremote.action.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.message.MessageParser;
import com.iremote.common.sms.SMSManageThread;
import com.iremote.common.sms.SMSVo;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.SmsHistory;
import com.iremote.domain.ThirdPart;
import com.iremote.service.SmsHistoryService;
import com.opensymphony.xwork2.Action;

public class SendSMSAction {

	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String countrycode = IRemoteConstantDefine.DEFAULT_COUNTRYCODE;
	private String phonenumber;
	private String message ;
	private String templatecode;
	private String parameter;
	private boolean sendoverseasms = false ;
	private PhoneUser phoneuser;
	private ThirdPart thirdpart ;
	private int platform = IRemoteConstantDefine.PLATFORM_ZHJW;
	
	public String execute()
	{
		SmsHistory his = new  SmsHistory();
		his.setCountrycode(countrycode);
		his.setMessage(message);
		his.setPhonenumber(phonenumber);
		
		SmsHistoryService svr = new SmsHistoryService();
		svr.save(his);
		
		JSONObject json = null ;
		if ( parameter != null )
			json = JSON.parseObject(parameter);
		MessageParser mp = new MessageParser(message , templatecode , json);
		
		setplatform();
		
		SMSManageThread.addSMS(new SMSVo(countrycode , phonenumber,mp , platform ,sendoverseasms));
		
		return Action.SUCCESS;
	}

	private void setplatform()
	{
		if ( phoneuser != null )
			platform = phoneuser.getPlatform();
		else if ( thirdpart != null )
			platform = thirdpart.getPlatform();
	}
	
	public int getResultCode() {
		return resultCode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public void setThirdpart(ThirdPart thirdpart)
	{
		this.thirdpart = thirdpart;
	}

	public void setSendoverseasms(boolean sendoverseasms)
	{
		this.sendoverseasms = sendoverseasms;
	}
	
	
}

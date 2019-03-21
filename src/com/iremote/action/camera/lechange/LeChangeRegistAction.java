package com.iremote.action.camera.lechange;

import com.alibaba.fastjson.JSONObject;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.PhoneUserAttribute;
import com.iremote.service.PhoneUserAttributeService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang.StringUtils;

public class LeChangeRegistAction
{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String smsCode ;
	private PhoneUser phoneuser ;
	private String lechangecode;
	private String lechangemsg;
	private String devicetype = IRemoteConstantDefine.CAMERA_DEVICE_TYPE_DOMESTIC;
	private String email;
	
	public String execute()
	{
		if (!check()) {
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}

		LeChangeInterface lcr = LeChangeRequestManagerStore.getInstance().getProcessor(phoneuser.getPlatform(), devicetype);
		
		if ( lcr == null )
		{
			resultCode = ErrorCodeDefine.NOT_SUPPORT;
			return Action.SUCCESS;
		}
		
		JSONObject rst = lcr.userBind(phoneuser.getPhonenumber() , smsCode, email);

		if ( rst == null )
		{
			resultCode = ErrorCodeDefine.THIRDPART_NETWORK_FAILED;
			return Action.SUCCESS;
		}
		
		lechangecode = lcr.getResultCode(rst);
		lechangemsg = lcr.getResultMsg(rst);
		
		if ( !ErrorCodeDefine.SUCCESS_STR.equals(lechangecode))
		{
			resultCode = ErrorCodeDefine.THIRDPART_CALL_FAILED;
			return Action.SUCCESS;
		}

		PhoneUserAttributeService puas = new PhoneUserAttributeService();
		createPhoneUserAttribute(getCode(devicetype), "true", puas);

		return Action.SUCCESS;
	}

	private String getCode(String devicetype) {
		return IRemoteConstantDefine.CAMERA_DEVICE_TYPE_ABROAD.equals(devicetype)
				? "lechangeosuser" : "lechangeuser";
	}

	private void createPhoneUserAttribute(String code, String value, PhoneUserAttributeService service) {
		PhoneUserAttribute attribute = new PhoneUserAttribute();
		attribute.setCode(code);
		attribute.setValue(value);
		attribute.setPhoneuserid(phoneuser.getPhoneuserid());

		service.save(attribute);
		PhoneUserHelper.sendAtributeChangeMessage(phoneuser , attribute.getCode() , attribute.getValue());
	}

	private boolean check() {
		if ((devicetype == null || IRemoteConstantDefine.CAMERA_DEVICE_TYPE_DOMESTIC.equals(devicetype))
				&& StringUtils.isBlank(smsCode)) {
			return false;
		}
		if (IRemoteConstantDefine.CAMERA_DEVICE_TYPE_ABROAD.equals(devicetype)
				&& StringUtils.isBlank(email)) {
			return false;
		}
		return true;
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
	public void setSmsCode(String smsCode)
	{
		this.smsCode = smsCode;
	}
	public void setPhoneuser(PhoneUser phoneuser)
	{
		this.phoneuser = phoneuser;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

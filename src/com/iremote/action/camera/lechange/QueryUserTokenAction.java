package com.iremote.action.camera.lechange;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.PhoneUser;
import com.opensymphony.xwork2.Action;
import org.apache.log4j.spi.ErrorCode;

public class QueryUserTokenAction 
{
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String token;
	private PhoneUser phoneuser ;
	private String lechangecode;
	private String lechangemsg;
	private String devicetype = IRemoteConstantDefine.CAMERA_DEVICE_TYPE_DOMESTIC;
	
	public String execute()
	{
		LeChangeUserTokenManager tm = new LeChangeUserTokenManager();
		tm.setDevicetype(devicetype);
		token = tm.getToken(phoneuser);
		this.lechangecode = tm.getLechangecode();
		this.lechangemsg = tm.getLechangemsg();
		this.resultCode = tm.getResultCode();

		return Action.SUCCESS;
	}
	
	public String getLechangecode() {
		return lechangecode;
	}

	public String getLechangemsg() {
		return lechangemsg;
	}

	public int getResultCode() {
		return resultCode;
	}

	public String getToken() {
		return token;
	}
	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
}

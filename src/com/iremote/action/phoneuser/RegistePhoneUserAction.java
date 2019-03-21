package com.iremote.action.phoneuser;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.sms.RandCodeHelper;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.domain.NotificationSetting;
import com.iremote.domain.PhoneUser;
import com.iremote.service.NotificationSettingService;
import com.iremote.service.PhoneUserService;
import com.iremote.service.SystemParameterService;
import com.iremote.service.UserService;
import com.opensymphony.xwork2.Action;

public class RegistePhoneUserAction {

	//private static Log log = LogFactory.getLog(RegistePhoneUserAction.class);
			
	private String countrycode = IRemoteConstantDefine.DEFAULT_COUNTRYCODE;
	private String phonenumber;
	private int platform;
	private int phoneuserid;
	//private String name;
	private String randcode;
	private String password;
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	
	private PhoneUserService us = new PhoneUserService();
	private PhoneUser phoneuser ;
	
	public String execute()
	{
		if ( StringUtils.isNotBlank(this.phonenumber ))
			this.phonenumber = this.phonenumber.trim();
		resultCode = RandCodeHelper.checkRandcode(countrycode , phonenumber, 1, randcode , platform);
		if ( resultCode != ErrorCodeDefine.SUCCESS )
			return Action.SUCCESS;
		if ( checkReqiste() == true )
		{
			resultCode = ErrorCodeDefine.USER_HAS_REGISTED;
			return Action.SUCCESS;
		}
		
		savePhoneUser();
		saveDefaultNotificationSetting();
		return Action.SUCCESS;
	}
	
	private void savePhoneUser()
	{
		phoneuser = new PhoneUser();
		phoneuser.setCountrycode(countrycode);
		phoneuser.setPhonenumber(phonenumber);
		phoneuser.setPlatform(platform);
		
		SystemParameterService sps = new SystemParameterService();
		
		phoneuser.setArmstatus( sps.getIntValue("defaultarmstatus", IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM));
	
		UserService svr = new UserService();
		String ep = svr.encryptPassword(phonenumber, password);
		
		phoneuser.setPassword(ep);
		phoneuser.setCreatetime(new Date());
		phoneuser.setLastupdatetime(new Date());
		
		phoneuserid = us.save(phoneuser);
		phoneuser.setAlias(Utils.createAlias(phoneuserid));
	}
	
	private void saveDefaultNotificationSetting()
	{
		NotificationSetting s = new NotificationSetting();
		s.setPhonenumber(phonenumber);
		
		s.setNotificationtype(4);
		s.setAthome(IRemoteConstantDefine.NOTIFICATION_NOTIFY_ME);
		if ( PhoneUserHelper.hasArmFunction(phoneuser))
		{
			s.setStarttime("00:00");
			s.setEndtime("00:00");			
		}
		else 
		{
			s.setStarttime("00:00");
			s.setEndtime("23:59");
		}
		s.setPhoneuserid(phoneuserid);
		NotificationSettingService svr = new NotificationSettingService();
		svr.saveorUpdate(s);
	}
	
	private boolean checkReqiste()
	{
		if ( us.query(countrycode ,phonenumber , platform) != null )
			return true;
		return false ;
	}
	
	public int getResultCode() {
		return resultCode;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

//	public void setName(String name) {
//		this.name = name;
//	}

	public void setRandcode(String randcode) {
		this.randcode = randcode;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
}

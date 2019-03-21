package com.iremote.action.phoneuser;

import com.alibaba.fastjson.annotation.JSONField;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.ServerRuntime;
import com.iremote.common.Utils;
import com.iremote.domain.Family;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.PhoneUserAttribute;
import com.iremote.domain.UserToken;
import com.iremote.interceptor.SessionInterceptor;
import com.iremote.service.*;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class PhoneUserLoginAction  {

	protected int resultCode = ErrorCodeDefine.SUCCESS ;
	protected String countrycode = IRemoteConstantDefine.DEFAULT_COUNTRYCODE;
	protected String phonenumber;
	protected int platform;
	protected String password;
	protected String alias;
	protected String oldtoken;
	protected int smsnumber;
	protected int callnumber;
	protected int armstatus;
	protected String language;
	protected int timezone = ServerRuntime.getInstance().getTimezone() ;
	protected String timezoneid = TimeZone.getDefault().getID();
	protected List<PhoneUserAttribute> attributes;
	protected PhoneUserService us = new PhoneUserService();
	protected UserService svr = new UserService();
	protected PhoneUser user;

	public String execute()
	{
		getPhoneUser();

		if ( user == null )
		{
			resultCode = ErrorCodeDefine.USERNAME_NOT_EXSIT;
			return Action.SUCCESS;
		}

		if(checkpassword() != ErrorCodeDefine.SUCCESS){
			return Action.SUCCESS;
		}

		if ( user.getAlias() == null || user.getAlias().length() == 0 )
		{
			user.setAlias(Utils.createAlias(user.getPhoneuserid()));
		}
		user.setLanguage(Utils.getUserLanguage(platform, language));
		if ( user.getPlatform() == IRemoteConstantDefine.PLATFORM_AMETA 
				&& IRemoteConstantDefine.DEFAULT_LANGUAGE.equals(user.getLanguage()))
			user.setLanguage(IRemoteConstantDefine.DEFAULT_UNCH_LANGUAGE);

		if ( user.getFamilyid() != null )
		{
			FamilyService fs = new FamilyService();
			Family f = fs.query(user.getFamilyid());
			armstatus = f.getArmstatus();
		}
		else
			armstatus = user.getArmstatus();
		alias = user.getAlias();

		smsnumber = ServerRuntime.getInstance().getDefaultsmscount() -  user.getSmscount();
		if ( smsnumber < 0 ) smsnumber = 0 ;
		callnumber = 0;//ServerRuntime.defaultcallcount - user.getCallcount();
		if ( callnumber < 0 ) callnumber = 0 ;

		PhoneUserAttributeService puas = new PhoneUserAttributeService();
		attributes = puas.querybyphoneuserid(user.getPhoneuserid());

		if (!("86".equals(user.getCountrycode()) && user.getUsertype() == 0)) {
			if (attributes == null) {
				attributes = new ArrayList<PhoneUserAttribute>();
			}
			PhoneUserAttribute pua = new PhoneUserAttribute();
			pua.setPhoneuserid(user.getPhoneuserid());
			pua.setCode("lechangeuser");
			pua.setValue("true");
			attributes.add(pua);
		}

		ActionContext.getContext().getSession().put(IRemoteConstantDefine.SESSION_USER, user);

		oldtoken = SessionInterceptor.getCookie("Usertoken");

		String token = Utils.createtoken();
		String securitytoken = Utils.createtoken();
		
		UserToken ut = new UserToken();
		ut.setPhoneuserid(user.getPhoneuserid());
		ut.setToken(token);
		ut.setSecuritytoken(svr.encryptPassword(user.getPhonenumber(), securitytoken));
		
		UserTokenService uts = new UserTokenService();
		uts.save(ut);
		setCookie(token , securitytoken ,ut.getTokenid());

		if ( oldtoken != null && oldtoken.length() > 0 )
		{
			UserToken ou = uts.querybytoken(oldtoken);
			if ( ou != null )
				uts.delete(ou);
		}

		return Action.SUCCESS;
	}

	protected int checkpassword(){
		if ( password == null
				|| !svr.checkPassword(user.getPhonenumber() , password, user.getPassword()))
		{
			resultCode = ErrorCodeDefine.USERNAME_OR_PASSWORD_WRONG;
		}
		return resultCode;
	}

	public void getPhoneUser(){
		if("0".equals(countrycode)){
			user = us.query(phonenumber, IRemoteConstantDefine.USER_STATUS_ENABLED , platform);
		}else{
			user = us.query(countrycode , phonenumber , platform);
		}
	}

	protected void setCookie(String token , String securitytoken, int tokenid)
	{
		Cookie c = new Cookie("Usertoken" , token);
		c.setMaxAge(getCookieMaxAge());
		c.setPath("/iremote");
		//c.setHttpOnly(true);
		//c.setSecure(true);
		ServletActionContext.getResponse().addCookie(c);

		Cookie ci = new Cookie("tokenid" , String.valueOf(tokenid));
		ci.setMaxAge(getCookieMaxAge());
		ci.setPath("/iremote");
		ServletActionContext.getResponse().addCookie(ci);
		
		Cookie cs = new Cookie("securitytoken" , String.valueOf(securitytoken));
		cs.setMaxAge(getCookieMaxAge());
		cs.setPath("/iremote");
		ServletActionContext.getResponse().addCookie(cs);
	}
	
	@org.apache.struts2.json.annotations.JSON(serialize=false)
	@JSONField(serialize = false)
	protected int getCookieMaxAge()
	{
		return 10*365*24*3600;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getAlias() {
		return alias;
	}

	public int getSmsnumber() {
		return smsnumber;
	}

	public int getCallnumber() {
		return callnumber;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public int getArmstatus() {
		return armstatus;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getTimezone() {
		return timezone;
	}

	public List<PhoneUserAttribute> getAttributes()
	{
		return attributes;
	}

	public String getTimezoneid() {
		return timezoneid;
	}


}

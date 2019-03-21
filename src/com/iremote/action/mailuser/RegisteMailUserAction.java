package com.iremote.action.mailuser;

import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.common.Utils;
import com.iremote.common.mail.EmailUtil;
import com.iremote.common.mail.MailInterface;
import com.iremote.common.mail.MailSendVo;
import com.iremote.common.message.MessageManager;
import com.iremote.common.message.MessageParser;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Randcode;
import com.iremote.service.*;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;

public class RegisteMailUserAction {
	private static Log log = LogFactory.getLog(RegisteMailUserAction.class);
	private String mail;
	private int platform;
	private String password;
	private int phoneuserid;
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String language;
	private String name;

	private PhoneUserService us = new PhoneUserService();
	private RandcodeService rs = new RandcodeService();
	private UserService svr = new UserService();
	private SystemParameterService sps = new SystemParameterService();

	private PhoneUser phoneuser ;
	
	public String execute()
	{
		if ( StringUtils.isNotBlank(this.mail ))
			this.mail = this.mail.trim();

		if(StringUtils.isEmpty(mail) || StringUtils.isEmpty(password)){
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}

		phoneuser = us.querybymail(mail,platform);

		if(!JWStringUtils.checkEmail(mail)){
			resultCode = ErrorCodeDefine.MAIL_FORMAT_ERROR;
			return Action.SUCCESS;
		}
		if(StringUtils.isEmpty(language)){
			language = IRemoteConstantDefine.DEFAULT_LANGUAGE;
		}

		if(phoneuser != null){
			if(IRemoteConstantDefine.USER_STATUS_ENABLED == phoneuser.getStatus()){
				resultCode = ErrorCodeDefine.MAIL_HAS_REGISTED;
				return Action.SUCCESS;
			}
		}else{
			phoneuser = new PhoneUser();
			phoneuser.setMail(mail);
			phoneuser.setPhonenumber(mail);
			phoneuser.setName(name);
			phoneuser.setCountrycode("0");
			phoneuser.setPlatform(platform);
			phoneuser.setLanguage(language);
			SystemParameterService sps = new SystemParameterService();
			phoneuser.setArmstatus( sps.getIntValue("defaultarmstatus", IRemoteConstantDefine.PHONEUSER_ARM_STATUS_ARM));
			phoneuser.setCreatetime(new Date());
		}
		phoneuser.setLastupdatetime(new Date());
		String ep = svr.encryptPassword(mail, password);
		phoneuser.setPassword(ep);
		phoneuser.setStatus(IRemoteConstantDefine.USER_STATUS_DISABLE);
		phoneuserid = us.save(phoneuser);
		phoneuser.setAlias(Utils.createAlias(phoneuserid));
		phoneuser.setUsertype(IRemoteConstantDefine.PHONEUSER_USER_TYPE_MAIL);
		String rc = createRandcode();

		if(!sendMail(rc)){
			resultCode = ErrorCodeDefine.MAIL_SEND_FAIL;
			return Action.SUCCESS;
		}
		return Action.SUCCESS;
	}

	private String createRandcode(){
		String rc = Utils.createsecuritytoken(32);
		Randcode r = new Randcode(rc, mail ,IRemoteConstantDefine.RANDCODE_TYPE_MAILUSER_REGISTE );
		r.setPlatform(platform);
        List<Randcode> randcodedel = rs.querybymail(mail ,IRemoteConstantDefine.RANDCODE_TYPE_MAILUSER_REGISTE, platform);
        if(randcodedel != null){
            for(Randcode delR : randcodedel){
                rs.delete(delR);
            }
        }
		rs.save(r);
		return rc;
	}

	private boolean sendMail(String code){
		String subject = "";
		String content = "";
		MessageParser mp = MessageManager.getMessageParser(IRemoteConstantDefine.MESSGAE_MAILREGIST_SUBJECT, platform, phoneuser.getLanguage());
		if(mp != null){
			subject = mp.getMessage();
		}
		content = creatContent(code);
		return MailInterface.sendUserMail(mail,subject,content);
	}


	private String creatContent(String code){
		String content = "";
		StringBuffer value = new StringBuffer();
		value.append("mail=" + mail + "&");
		value.append("platform=" + platform + "&");
		value.append("randcode=" + code);

		JSONObject json = new JSONObject();
		json.put("url",sps.getStringValue(IRemoteConstantDefine.SYSTEMPARAMETER_DOMAIN_NAME));
		json.put("value",value);
		MessageParser mp = MessageManager.getMessageParser(IRemoteConstantDefine.MESSGAE_MAILREGIST_CONTENT, platform, phoneuser.getLanguage(), json);
		if(mp != null){
			content = mp.getMessage();
		}
		return content;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

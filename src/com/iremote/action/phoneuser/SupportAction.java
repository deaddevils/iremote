package com.iremote.action.phoneuser;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.JWStringUtils;
import com.iremote.common.mail.EmailUtil;
import com.iremote.common.mail.MailInterface;
import com.iremote.common.mail.MailSendVo;
import com.iremote.domain.PhoneUser;
import com.iremote.service.SystemParameterService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SupportAction
{
	private static Log log = LogFactory.getLog(SupportAction.class);
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private PhoneUser phoneuser;
	private String mail	;
	private String topic ;
	private String message ;
	private SystemParameterService sps = new SystemParameterService();

	public String execute()
	{
		if ( !StringUtils.isNotBlank(this.mail ) || !StringUtils.isNotBlank(this.topic ) || !StringUtils.isNotBlank(this.message )){
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}

		if(!JWStringUtils.checkEmail(mail)){
			resultCode = ErrorCodeDefine.MAIL_FORMAT_ERROR;
			return Action.SUCCESS;
		}

		if(!sendMail()){
			resultCode = ErrorCodeDefine.MAIL_SEND_FAIL;
			return Action.SUCCESS;
		}
		return Action.SUCCESS;
	}

	private boolean sendMail(){
		return MailInterface.sendSupportMail(topic,createContent());
	}

	private String createContent(){
		String content = message;
		content += "<br><br>user:" + (StringUtils.isEmpty(mail) ? phoneuser.getMail() : mail);
		return content;
	}

	public int getResultCode()
	{
		return resultCode;
	}

	public void setPhoneuser(PhoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

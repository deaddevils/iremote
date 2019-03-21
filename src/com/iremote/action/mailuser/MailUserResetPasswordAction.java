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
import com.iremote.service.PhoneUserService;
import com.iremote.service.RandcodeService;
import com.iremote.service.SystemParameterService;
import com.iremote.service.UserService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class MailUserResetPasswordAction {
	private static Log log = LogFactory.getLog(MailUserResetPasswordAction.class);

	private String mail;
	private int platform;
	private int resultCode = ErrorCodeDefine.SUCCESS ;

	private PhoneUserService us = new PhoneUserService();
	private RandcodeService rs = new RandcodeService();
	private UserService svr = new UserService();
	private SystemParameterService sps = new SystemParameterService();

	private PhoneUser phoneuser ;
	
	public String execute()
	{
		if ( StringUtils.isNotBlank(this.mail ))
			this.mail = this.mail.trim();

		if(!JWStringUtils.checkEmail(mail)){
			resultCode = ErrorCodeDefine.MAIL_FORMAT_ERROR;
			return Action.SUCCESS;
		}

		phoneuser = us.query(mail, IRemoteConstantDefine.USER_STATUS_ENABLED, platform);
		if ( phoneuser == null )
		{
			resultCode = ErrorCodeDefine.USERNAME_NOT_EXSIT;
			return Action.SUCCESS;
		}
		String rc = createRandcode();
		if(!sendMail(rc)){
			resultCode = ErrorCodeDefine.MAIL_SEND_FAIL;
			return Action.SUCCESS;
		}
		return Action.SUCCESS;
	}

    private String createRandcode(){
        String rc = Utils.createsecuritytoken(32);
        Randcode r = new Randcode(rc, mail ,IRemoteConstantDefine.RANDCODE_TYPE_MAILUSER_RESETPASSWORD);
        r.setPlatform(platform);
        //delete old randcode,only one randcode
        List<Randcode> randcodedel = rs.querybymail(mail ,IRemoteConstantDefine.RANDCODE_TYPE_MAILUSER_RESETPASSWORD, platform);
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
		MessageParser mp = MessageManager.getMessageParser(IRemoteConstantDefine.MESSGAE_MAILRESETPASSWORD_SUBJECT, platform, phoneuser.getLanguage());
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
		MessageParser mp = MessageManager.getMessageParser(IRemoteConstantDefine.MESSGAE_MAILRESETPASSWORD_CONTENT, platform, phoneuser.getLanguage(), json);
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

	public int getResultCode() {
		return resultCode;
	}
}

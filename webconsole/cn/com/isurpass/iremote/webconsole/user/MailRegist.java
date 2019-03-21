package cn.com.isurpass.iremote.webconsole.user;

import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.mailuser.MailUserLoginAction;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.Cookie;

public class MailRegist
{
	public String execute()
	{
		return Action.SUCCESS;
	}
}

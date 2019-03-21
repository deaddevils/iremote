package cn.com.isurpass.iremote.webconsole.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.iremote.action.helper.PhoneUserHelper;
import com.iremote.action.phoneuser.PhoneUserLoginAction;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.IRemoteConstantDefine;
import com.opensymphony.xwork2.Action;

public class Login extends PhoneUserLoginAction
{
	public String execute()
	{
		super.execute();

		if ( super.getResultCode() == ErrorCodeDefine.SUCCESS)
		{
			if ( PhoneUserHelper.isAdminUser(getAttributes()))
				return "manage";
			return Action.SUCCESS;
		}
		if ( super.platform == IRemoteConstantDefine.PLATFORM_DORLINK)
			return "dorlinklogin";
		if ( super.platform == IRemoteConstantDefine.PLATFORM_AMETA)
			return "ametalogin";
		return Action.ERROR;
	}
	
	@Override
	@org.apache.struts2.json.annotations.JSON(serialize=false)
	@JSONField(serialize = false)
	protected int getCookieMaxAge()
	{
		return -1;
	}
}

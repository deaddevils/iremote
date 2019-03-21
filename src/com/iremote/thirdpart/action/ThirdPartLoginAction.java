package com.iremote.thirdpart.action;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.common.Utils;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ThirdPartToken;
import com.iremote.service.ThirdPartService;
import com.iremote.service.ThirdPartTokenService;
import com.opensymphony.xwork2.Action;

public class ThirdPartLoginAction {
	
	private int resultCode = ErrorCodeDefine.SUCCESS ;
	private String code ;
	private String password;
	private String token;
	
	public String execute()
	{
		if ( StringUtils.isBlank(code) || StringUtils.isBlank(password))
		{
			resultCode = ErrorCodeDefine.PARMETER_ERROR;
			return Action.SUCCESS;
		}
		
		ThirdPartService svr = new ThirdPartService();

		ThirdPart tp = svr.query(code);
		
		if ( tp == null || password == null || svr.checkPassword(code, password, tp.getPassword()) == false )
		{
			resultCode = ErrorCodeDefine.USERNAME_OR_PASSWORD_WRONG_3;
			return Action.SUCCESS;
		}
		
		token = Utils.createtoken();
		
		ThirdPartTokenService tsvr = new ThirdPartTokenService();
		ThirdPartToken tt = tsvr.query(code);
		
		if ( tt == null )
		{
			tt = new ThirdPartToken();
			tt.setCode(code);
			tt.setThirdpartid(tp.getThirdpartid());
			tt.setCreatetime(new Date());
		}
		tt.setToken(token);
		tt.setLastupdatetime(new Date());
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, 2);
		tt.setValidtime(c.getTime());
		
		tsvr.saveOrUpdate(tt);
		
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public String getToken() {
		return token;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	
	
}

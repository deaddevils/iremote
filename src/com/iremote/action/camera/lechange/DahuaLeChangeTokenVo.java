package com.iremote.action.camera.lechange;

import com.iremote.common.IRemoteConstantDefine;

import java.util.Calendar;
import java.util.Date;

public class DahuaLeChangeTokenVo
{
	private String token;
	private Date expiretime;
	
	public DahuaLeChangeTokenVo(String token)
	{
		super();
		this.token = token;
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, IRemoteConstantDefine.TOKEN_CACHE_EXPIRE_TIME);
		this.expiretime = c.getTime();
	}

	public String getToken()
	{
		return token;
	}

	public Date getExpiretime()
	{
		return expiretime;
	}

}

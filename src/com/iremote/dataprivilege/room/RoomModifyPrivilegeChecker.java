package com.iremote.dataprivilege.room;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.iremote.dataprivilege.interceptorchecker.IURLDataPrivilegeChecker;

public abstract class RoomModifyPrivilegeChecker<T> implements IURLDataPrivilegeChecker<T>
{
	protected T user ;
	protected Integer roomdbid ;
	
	@Override
	public void setUser(T user)
	{
		this.user = user;
	}

	@Override
	public void setParameter(String parameter)
	{
		if ( StringUtils.isNotBlank(parameter))
			roomdbid = Integer.valueOf(parameter);
	}

	@Override
	public void SetParameters(Map<String, String> parameters)
	{		
	}


}

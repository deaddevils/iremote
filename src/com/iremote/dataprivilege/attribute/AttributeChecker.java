package com.iremote.dataprivilege.attribute;

import java.util.Map;

import com.iremote.dataprivilege.interceptorchecker.IURLDataPrivilegeChecker;

public abstract class AttributeChecker<T> implements IURLDataPrivilegeChecker<T>
{
	protected T user ;
	protected String deviceid ;

	@Override
	public void setUser(T user)
	{
		this.user = user ;
	}

	@Override
	public void setParameter(String parameter) 
	{
	}

	@Override
	public void SetParameters(Map<String, String> parameters) 
	{
	}

}

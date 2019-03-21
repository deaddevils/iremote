package com.iremote.dataprivilege.infrareddevices;

import java.util.Map;

import com.iremote.dataprivilege.interceptorchecker.IURLDataPrivilegeChecker;

public abstract class InfraredDevicesModifyPrivilegeChecker<T> implements IURLDataPrivilegeChecker<T>
{
	protected T user ;
	protected String infrareddeviceids;
	
	@Override
	public void setUser(T user)
	{
		this.user = user ;
		
	}

	@Override
	public void setParameter(String parameter)
	{
		infrareddeviceids = parameter;
	}

	@Override
	public void SetParameters(Map<String, String> parameters)
	{
	}



}

package com.iremote.dataprivilege.infrareddevice;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.iremote.dataprivilege.interceptorchecker.IURLDataPrivilegeChecker;

public abstract class InfraredDevicePrivilegeChecker<T> implements IURLDataPrivilegeChecker<T>
{
	protected T user ;
	protected Integer infrareddeviceid;
	
	@Override
	public void setUser(T user)
	{
		this.user = user ;	
	}

	@Override
	public void setParameter(String parameter)
	{
		if ( StringUtils.isNotBlank(parameter))
			infrareddeviceid = Integer.valueOf(parameter);
	}

	@Override
	public void SetParameters(Map<String, String> parameters)
	{
		
	}


}

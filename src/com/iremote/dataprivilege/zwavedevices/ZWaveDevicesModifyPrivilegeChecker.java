package com.iremote.dataprivilege.zwavedevices;

import java.util.Map;

import com.iremote.dataprivilege.interceptorchecker.IURLDataPrivilegeChecker;

public abstract class ZWaveDevicesModifyPrivilegeChecker<T> implements IURLDataPrivilegeChecker<T>
{
	protected T user ;
	protected String zwavedeviceids;
	
	@Override
	public void setUser(T user)
	{
		this.user = user ;
		
	}

	@Override
	public void setParameter(String parameter)
	{
		zwavedeviceids = parameter;
	}

	@Override
	public void SetParameters(Map<String, String> parameters)
	{
	}



}

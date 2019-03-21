package com.iremote.dataprivilege.camera;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.iremote.dataprivilege.interceptorchecker.IURLDataPrivilegeChecker;

public  abstract class CameraPrivilegeChecker<T> implements IURLDataPrivilegeChecker<T>
{
	protected T user ;
	protected int cameraid ;
	
	@Override
	public void setUser(T user)
	{
		this.user = user ;
	}

	@Override
	public void setParameter(String parameter)
	{
		if ( StringUtils.isNotBlank(parameter))
			cameraid = Integer.valueOf(parameter);
	}

	@Override
	public void SetParameters(Map<String, String> parameters)
	{
		if ( parameters.containsKey("cameraid") && StringUtils.isNotBlank(parameters.get("cameraid")))
			cameraid = Integer.valueOf(parameters.get("cameraid"));
	}
}

package com.iremote.dataprivilege.cameras;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.Utils;
import com.iremote.dataprivilege.interceptorchecker.IURLDataPrivilegeChecker;

public  abstract class CamerasPrivilegeChecker<T> implements IURLDataPrivilegeChecker<T>
{
	protected T user ;
	protected List<Integer> cameraids ;
	
	@Override
	public void setUser(T user)
	{
		this.user = user ;
	}

	@Override
	public void setParameter(String parameter)
	{
		if ( StringUtils.isNotBlank(parameter))
			cameraids = Utils.jsontoIntList(parameter);
	}

	@Override
	public void SetParameters(Map<String, String> parameters)
	{
	}
}

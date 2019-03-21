package com.iremote.dataprivilege.zwavedevice;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.iremote.dataprivilege.interceptorchecker.IURLDataPrivilegeChecker;
import com.iremote.domain.ZWaveDevice;

public abstract class DeviceOperationPrivilegeChecker<T> implements IURLDataPrivilegeChecker<T>
{
	protected T user ;
	protected String deviceid ;
	protected int nuid ;
	protected int sharedevicetype ;
	protected int zwavedeviceid ;
	protected ZWaveDevice device ;
	protected String sharedevice ;
	
	@Override
	public void setUser(T user)
	{
		this.user = user ;
	}

	@Override
	public void setParameter(String parameter)
	{
		if ( StringUtils.isNotBlank(parameter))
			zwavedeviceid = Integer.valueOf(parameter);
	}

	@Override
	public void SetParameters(Map<String , String> parameters)
	{
		deviceid = parameters.get("deviceid");
		if ( StringUtils.isNotBlank(parameters.get("nuid")) )
			nuid = Integer.valueOf(parameters.get("nuid"));
		if ( StringUtils.isNotBlank(parameters.get("zwavedeviceid")))
			zwavedeviceid = Integer.valueOf(parameters.get("zwavedeviceid"));
		if ( StringUtils.isNotBlank(parameters.get("sharedevice")))
			sharedevice = parameters.get("sharedevice");
		if ( StringUtils.isNotBlank(parameters.get("sharedevicetype")))
			sharedevicetype = Integer.valueOf(parameters.get("sharedevicetype"));
	}

}

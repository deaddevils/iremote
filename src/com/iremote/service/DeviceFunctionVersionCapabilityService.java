package com.iremote.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.DeviceFunctionVersionCapability;

public class DeviceFunctionVersionCapabilityService
{
	public List<DeviceFunctionVersionCapability> queryByVersion(String devicetype , String functionversion)
	{
		if ( StringUtils.isBlank(devicetype) || StringUtils.isBlank(functionversion))
			return null ;
		CriteriaWrap cw = new CriteriaWrap(DeviceFunctionVersionCapability.class.getName());
		cw.add(ExpWrap.eq("devicetype", devicetype));
		cw.add(ExpWrap.eq("functionversion", functionversion));
		
		return cw.list();
	}
}

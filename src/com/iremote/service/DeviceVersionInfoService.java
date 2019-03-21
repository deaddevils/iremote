package com.iremote.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.DeviceVersionInfo;

public class DeviceVersionInfoService
{
	public DeviceVersionInfo querybyVersion(String version)
	{
		CriteriaWrap cw = new CriteriaWrap(DeviceVersionInfo.class.getName());
		cw.add(ExpWrap.eq("version", version));
		List<DeviceVersionInfo> lst = cw.list();
		
		if ( lst != null && lst.size() == 1)
			return lst.get(0);
		return null;
	}
	
	public DeviceVersionInfo query(String productor , String model , String version)
	{
		if ( StringUtils.isBlank(productor)
				|| StringUtils.isBlank(model)
				|| StringUtils.isBlank(version))
			return null ;
		CriteriaWrap cw = new CriteriaWrap(DeviceVersionInfo.class.getName());
		cw.add(ExpWrap.eq("productor", productor));
		cw.add(ExpWrap.eq("model", model));
		cw.add(ExpWrap.eq("version", version));
		return cw.uniqueResult();
	}  
}

package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.InfreredDeviceProductor;

public class InfreredDeviceProductorService
{
	public List<InfreredDeviceProductor> queryByDeviceType(String devicetype)
	{
		CriteriaWrap cw = new CriteriaWrap(InfreredDeviceProductor.class.getName());
		cw.addifNotNull(ExpWrap.ignoreCaseEq("devicetype", devicetype));
		return cw.list();
	}
	
	public List<InfreredDeviceProductor> queryByDeviceTypeandProductor(String devicetype , String productor)
	{
		CriteriaWrap cw = new CriteriaWrap(InfreredDeviceProductor.class.getName());
		cw.addifNotNull(ExpWrap.ignoreCaseEq("devicetype", devicetype));
		cw.addifNotNull(ExpWrap.ignoreCaseEq("productor", productor));
		return cw.list();
	}
}

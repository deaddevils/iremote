package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.InfreredDeviceProductorMap;

public class InfreredDeviceProductorMapService
{
	public List<String> queryMapProductor(String productor , String devicetype)
	{
		CriteriaWrap cw = new CriteriaWrap(InfreredDeviceProductorMap.class.getName());
		cw.add(ExpWrap.ignoreCaseEq("productor", productor));
		cw.add(ExpWrap.ignoreCaseEq("devicetype", devicetype));
		cw.addFields(new String[]{"productormap"});
		return cw.list();
	}
}

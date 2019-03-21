package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.InfreredDeviceProductorCodeMap;

public class InfreredDeviceProductorCodeMapService
{
	public List<String> queryCodeidsByProductor(List<String> productor , String devicetype)
	{
		CriteriaWrap cw = new CriteriaWrap(InfreredDeviceProductorCodeMap.class.getName());
		cw.add(ExpWrap.in("productor", productor));
		cw.add(ExpWrap.ignoreCaseEq("devicetype", devicetype));
		cw.addFields(new String[]{"codeids"});
		return cw.list();
	} 
}

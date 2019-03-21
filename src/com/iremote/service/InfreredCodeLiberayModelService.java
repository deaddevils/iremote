package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.InfreredCodeLiberayModel;

public class InfreredCodeLiberayModelService 
{
	public List<InfreredCodeLiberayModel> queryByDevicetypeandProductor(String devicetype , String productor)
	{
		CriteriaWrap cw = new CriteriaWrap(InfreredCodeLiberayModel.class.getName());
		cw.addifNotNull(ExpWrap.ignoreCaseEq("devicetype", devicetype));
		cw.addifNotNull(ExpWrap.ignoreCaseEq("productor", productor));
		return cw.list();
	}
	
}

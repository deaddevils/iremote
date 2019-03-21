package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.InfreredCodeLiberay;

public class InfreredCodeLiberayService
{
	public List<InfreredCodeLiberay> query()
	{
		CriteriaWrap cw = new CriteriaWrap(InfreredCodeLiberay.class.getName());
		return cw.list();
	}
	
	public String queryByCodeid(int codeid , String devicetype)
	{
		CriteriaWrap cw = new CriteriaWrap(InfreredCodeLiberay.class.getName());
		cw.add(ExpWrap.eq("codeid", codeid));
		cw.add(ExpWrap.eq("devicetype", devicetype));
		cw.addFields(new String[]{"code"});
		return cw.uniqueResult();
	}
}

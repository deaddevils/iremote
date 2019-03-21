package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.OemProductor;

public class OemProductorService
{
	public List<OemProductor> query()
	{
		CriteriaWrap cw = new CriteriaWrap(OemProductor.class.getName());
		return cw.list();
	}
	
	public OemProductor querybyplatform(int platform)
	{
		CriteriaWrap cw = new CriteriaWrap(OemProductor.class.getName());
		cw.add(ExpWrap.eq("platform", platform));
		return cw.uniqueResult();
	}
	
}

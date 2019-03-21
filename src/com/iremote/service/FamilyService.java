package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.Family;

public class FamilyService {
	
	public Family query(int familyid)
	{
		CriteriaWrap cw = new CriteriaWrap(Family.class.getName());
		cw.add(ExpWrap.eq("familyid", familyid));
		return cw.uniqueResult();
	}
	
	public Integer save(Family family)
	{
		return (Integer)HibernateUtil.getSession().save(family);
	}
	
	public void delete(Family family)
	{
		HibernateUtil.getSession().delete(family);
	}
}

package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.ThirdPartToken;

public class ThirdPartTokenService {

	public void saveOrUpdate(ThirdPartToken phoneuser)
	{
		HibernateUtil.getSession().saveOrUpdate(phoneuser);
	}
	
	public ThirdPartToken query(String code)
	{
		CriteriaWrap cw = new CriteriaWrap(ThirdPartToken.class.getName());
		cw.add(ExpWrap.eq("code", code));
		return cw.uniqueResult();
	}
	
	public ThirdPartToken querybytoken(String token)
	{
		CriteriaWrap cw = new CriteriaWrap(ThirdPartToken.class.getName());
		cw.add(ExpWrap.eq("token", token));
		return cw.uniqueResult();
	}
}

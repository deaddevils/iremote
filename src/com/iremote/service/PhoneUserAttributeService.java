package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.PhoneUserAttribute;

public class PhoneUserAttributeService extends BaseService<PhoneUserAttribute>
{
	public List<PhoneUserAttribute> querybyphoneuserid(int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(PhoneUserAttribute.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
		return cw.list();
	}
	
	public boolean isAdmin(int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(PhoneUserAttribute.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
		cw.add(ExpWrap.eq("code", "admin"));
		cw.add(ExpWrap.eq("value", "true"));
		return ( cw.list().size() > 0 );
	}
	
	public PhoneUserAttribute querybyphoneuseridandcode(int phoneuserid,String code){
		CriteriaWrap cw = new CriteriaWrap(PhoneUserAttribute.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
		cw.add(ExpWrap.eq("code", code));
		return cw.uniqueResult();
	}
}

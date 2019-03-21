package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.ThirdPartAttribute;

public class ThirdPartAttributeService 
{
	public List<ThirdPartAttribute> querybyphoneuserid(int thirdpartid)
	{
		CriteriaWrap cw = new CriteriaWrap(ThirdPartAttribute.class.getName());
		cw.add(ExpWrap.eq("thirdpartid", thirdpartid));
		return cw.list();
	}
}

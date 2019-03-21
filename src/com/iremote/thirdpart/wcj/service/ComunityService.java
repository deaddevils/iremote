package com.iremote.thirdpart.wcj.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.thirdpart.wcj.domain.Comunity;

public class ComunityService {

	public Comunity query(int comunityid)
	{
		CriteriaWrap cw = new CriteriaWrap(Comunity.class.getName());
		cw.add(ExpWrap.eq("comunityid", comunityid));
		return cw.uniqueResult();
	}
	
	public List<Comunity> queryByThirdpartid(int thirdpartid)
	{
		CriteriaWrap cw = new CriteriaWrap(Comunity.class.getName());
		cw.add(ExpWrap.eq("thirdpartid", thirdpartid));
		return cw.list();
	}
	

}

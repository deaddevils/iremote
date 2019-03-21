package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.CommunityAdministrator;

public class CommunityAdministratorService {

	public void save(CommunityAdministrator ca)
	{
		HibernateUtil.getSession().save(ca);
	}
	
	public CommunityAdministrator querybyloginname(String loginname)
	{
		CriteriaWrap cw = new CriteriaWrap(CommunityAdministrator.class.getName());
		cw.add(ExpWrap.eq("logicname", loginname));
		
		return cw.uniqueResult();
		
	}
	
	public CommunityAdministrator querybyphoneuserid(int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(CommunityAdministrator.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
		
		return cw.uniqueResult();
	}

	public Integer queryByThirdPartIdandLoginName(int thirdPartId, String loginName){
		CriteriaWrap cw = new CriteriaWrap(CommunityAdministrator.class.getName());
		cw.add(ExpWrap.eq("logicname", loginName));
		cw.add(ExpWrap.eq("thirdpartid", thirdPartId));

		cw.addFields(new String[]{"phoneuserid"});
		return cw.uniqueResult();
	}
}

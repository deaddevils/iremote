package com.iremote.service;

import java.util.List;

import org.hibernate.criterion.Order;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.AppVersion;

public class AppVersionService extends BaseService<AppVersion>
{

	public List<AppVersion> query()
	{
		CriteriaWrap cw = new CriteriaWrap(AppVersion.class.getName());
		cw.addOrder(Order.asc("platform"));
		return cw.list();
	}
	
	public AppVersion query(int platform)
	{
		CriteriaWrap cw = new CriteriaWrap(AppVersion.class.getName());
		cw.add(ExpWrap.eq("platform" , platform));
		
		return cw.uniqueResult();
	}
}

package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.UserServiceMap;

public class UserServiceMapService {

	public UserServiceMap query(int phoneuserid , int platform , int serviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(UserServiceMap.class.getName());
		
		cw.add(ExpWrap.eq("phoneuserid" , phoneuserid));
		cw.add(ExpWrap.eq("platform" , platform));
		cw.add(ExpWrap.eq("serviceid" , serviceid));
		
		return cw.uniqueResult();
	}
}

package com.iremote.thirdpart.wcj.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.thirdpart.wcj.domain.WcjServer;

public class WcjServerService {

	public WcjServer querybythridpartid(int thirdpartid)
	{
		CriteriaWrap cw = new CriteriaWrap(WcjServer.class.getName());
		cw.add(ExpWrap.eq("thirdpartid", thirdpartid));
		return cw.uniqueResult();
	}
}

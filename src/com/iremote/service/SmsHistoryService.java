package com.iremote.service;

import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.SmsHistory;

public class SmsHistoryService {

	public void save(SmsHistory smshitory)
	{
		HibernateUtil.getSession().save(smshitory);
	}
}

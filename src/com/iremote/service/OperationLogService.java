package com.iremote.service;

import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.OperationLog;

public class OperationLogService {

	public void save(OperationLog log)
	{
		HibernateUtil.getSession().save(log);
	}
}

package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.domain.DeviceOperationSteps;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.InfraredKey;

import java.util.List;

public class InfraredKeyService {

	public void saveOrUpdate(InfraredKey ik)
	{
		HibernateUtil.getSession().saveOrUpdate(ik);
	}
	
	public InfraredKey querybyinfrareddeviceidandkeyindex(int infrareddeviceid,int keyindex)
	{
		CriteriaWrap cw = new CriteriaWrap(InfraredKey.class.getName());
		cw.add(ExpWrap.eq("keyindex", keyindex));
		cw.add(ExpWrap.eq("infrareddeviceid", infrareddeviceid));
		return cw.uniqueResult();
	}

	public List<InfraredKey> querybyinfrareddeviceid(int infrareddeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(DeviceOperationSteps.class.getName());
		cw.add(ExpWrap.eq("infrareddeviceid", infrareddeviceid));
		return cw.list();
	}

	public void delete(InfraredKey infraredKey)
	{
		HibernateUtil.getSession().delete(infraredKey);
	}
}

package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.ZwaveDeviceEventPushValues;

public class ZwaveDeviceEventPushValuesService {

	public void save(ZwaveDeviceEventPushValues pv)
	{
		HibernateUtil.getSession().save(pv);
	}
	
	public ZwaveDeviceEventPushValues querybyZwavedeviceid(int zwavedeviceid )
	{
		CriteriaWrap cw = new CriteriaWrap(ZwaveDeviceEventPushValues.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		return cw.uniqueResult();
	}
}

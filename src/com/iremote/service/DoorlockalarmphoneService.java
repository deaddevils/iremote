package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.DoorlockUser;
import com.iremote.domain.Doorlockalarmphone;

public class DoorlockalarmphoneService {
	public void save(Doorlockalarmphone doorlockalarmphone)
	{
		HibernateUtil.getSession().save(doorlockalarmphone);
	}
	
	public void delete(Doorlockalarmphone doorlockalarmphone)
	{
		HibernateUtil.getSession().delete(doorlockalarmphone);
	}
	
	public Doorlockalarmphone query(int doorlockalarmphoneid)
	{
		CriteriaWrap cw = new CriteriaWrap(Doorlockalarmphone.class.getName());
		cw.add(ExpWrap.eq("doorlockalarmphoneid", doorlockalarmphoneid));
		return cw.uniqueResult();
	}
	
	public List<Doorlockalarmphone> querybydoorlockuser(DoorlockUser doorlockuser)
	{
		CriteriaWrap cw = new CriteriaWrap(Doorlockalarmphone.class.getName());
		cw.add(ExpWrap.eq("doorlockuser", doorlockuser));
		return cw.list();
	}
}

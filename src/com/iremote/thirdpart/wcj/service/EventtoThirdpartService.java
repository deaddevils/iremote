package com.iremote.thirdpart.wcj.service;

import java.util.List;

import org.hibernate.criterion.Order;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.EventtoThirdpart;

public class EventtoThirdpartService {

	public void save(EventtoThirdpart etd)
	{
		HibernateUtil.getSession().save(etd);
	}
	
	public List<EventtoThirdpart> query(int thirdpartid , int lastid)
	{
		CriteriaWrap cw = new CriteriaWrap(EventtoThirdpart.class.getName());
		cw.add(ExpWrap.eq("thirdpartid", thirdpartid));
		cw.add(ExpWrap.gt("id", lastid));
		cw.setMaxResults(100);
		cw.addFields(new String[]{"id" , "type" , "deviceid" , "zwavedeviceid" , "objparam" ,"eventtime"});
		
		return cw.list(EventtoThirdpart.class);
	}
	
	public int queryMaxId()
	{
		CriteriaWrap cw = new CriteriaWrap(EventtoThirdpart.class.getName());
		cw.addOrder(Order.desc("id"));
		cw.setMaxResults(1);
		cw.addFields(new String[]{"id"});
		List<Integer> lst = cw.list();
		
		if ( lst == null || lst.size() == 0 )
			return 0 ;
		return lst.get(0);
	}
	
	public List<EventtoThirdpart> query(int thirdpartid , int lastid , int maxid , int max)
	{
		CriteriaWrap cw = new CriteriaWrap(EventtoThirdpart.class.getName());
		cw.add(ExpWrap.eq("thirdpartid", thirdpartid));
		cw.add(ExpWrap.gt("id", lastid));
		cw.add(ExpWrap.le("id", maxid));
		cw.setMaxResults(max);
		cw.addOrder(Order.asc("id"));
		//cw.addFields(new String[]{"id" , "type" , "deviceid" , "zwavedeviceid" , "objparam" ,"eventtime"});
		
		return cw.list();
	}
}

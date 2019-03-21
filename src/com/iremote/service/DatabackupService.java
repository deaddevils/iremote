package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.Hibernate.HqlWrap;
import com.iremote.domain.Databackup;

public class DatabackupService {

	public void save(Databackup dbu)
	{
		HibernateUtil.getSession().save(dbu);
	}
	
	public void delete(Databackup dbu)
	{
		HibernateUtil.getSession().delete(dbu);
	}
	
	public String[] queryFilenameList(int userid)
	{
		HqlWrap hw = new HqlWrap();
		hw.addifnotnull("Select filename from Databackup where userid=?" , Integer.valueOf(userid));
		
		List<String> lst = hw.list() ;
		
		return lst.toArray(new String[0]);
	}
	
	public Databackup loadDatabackup(int userid , String filename)
	{
		CriteriaWrap cw = new CriteriaWrap(Databackup.class.getName());
		cw.add(ExpWrap.eq("userid", userid));
		cw.add(ExpWrap.eq("filename", filename));
		
		List<Databackup> lst = cw.list();
		if ( lst == null || lst.size() == 0 )
			return null ;
		return lst.get(0);
	}
}

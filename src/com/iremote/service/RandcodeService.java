package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.Randcode;
import com.iremote.domain.Room;

public class RandcodeService {

	public void save(Randcode randcode)
	{
		HibernateUtil.getSession().save(randcode);
	}
	
	public List<Randcode> querybyphonenumber(String countrycode ,String phonenumber , int type , int platform)
	{
		CriteriaWrap cw = new CriteriaWrap(Randcode.class.getName());
		cw.add(ExpWrap.eq("countrycode", countrycode));
		cw.add(ExpWrap.eq("phonenumber", phonenumber));
		cw.add(ExpWrap.eq("type", type));
		cw.add(ExpWrap.eq("platform", platform));
		return cw.list();
	}

	public List<Randcode> querybymail(String mail , int type , int platform)
	{
		CriteriaWrap cw = new CriteriaWrap(Randcode.class.getName());
		cw.add(ExpWrap.eq("mail", mail));
		cw.add(ExpWrap.eq("type", type));
		cw.add(ExpWrap.eq("platform", platform));
		return cw.list();
	}

    public Randcode querybycode(String code)
    {
        CriteriaWrap cw = new CriteriaWrap(Randcode.class.getName());
        cw.add(ExpWrap.eq("randcode", code));
        return cw.first();
    }

	public void delete(Randcode randcode)
	{
		HibernateUtil.getSession().delete(randcode);
	}

}

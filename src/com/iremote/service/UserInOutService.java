package com.iremote.service;

import java.util.Collection;
import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.UserInOut;

public class UserInOutService {

	public void save(UserInOut inout)
	{
		HibernateUtil.getSession().save(inout);
	}
	
	public void saveOrUpdate(UserInOut inout)
	{
		HibernateUtil.getSession().saveOrUpdate(inout);
	}
	
	public void delete(UserInOut inout)
	{
		HibernateUtil.getSession().delete(inout);
	}
	
	public List<UserInOut> query(int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(UserInOut.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
		return cw.list();
	}
	
	public List<Integer> queryinhomeuser(List<Integer> phoneuserid , String deviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(UserInOut.class.getName());
		cw.add(ExpWrap.in("phoneuserid", phoneuserid));
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.eq("action", IRemoteConstantDefine.USDRINOUT_IN));
		cw.addFields(new String[]{"phoneuserid"});
		return cw.list();
	}
	
	public UserInOut query(int phoneuserid ,String deviceid )
	{
		CriteriaWrap cw = new CriteriaWrap(UserInOut.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
		cw.add(ExpWrap.eq("deviceid", deviceid));
		return cw.uniqueResult();
	}
	
	public List<String> queryUserInhomeDevice(int phoneuserid ,Collection<String> deviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(UserInOut.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceid));
		cw.add(ExpWrap.not(ExpWrap.eq("phoneuserid", phoneuserid)));
		cw.add(ExpWrap.eq("action", IRemoteConstantDefine.USDRINOUT_IN));
		cw.addFields(new String[]{"deviceid"});
		return cw.list();
	}
}

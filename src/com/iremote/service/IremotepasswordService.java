package com.iremote.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.jasypt.digest.PooledStringDigester;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.Remote;

public class IremotepasswordService {

	private static PooledStringDigester digester = new PooledStringDigester();
	
	static 
	{
		digester.setPoolSize(8);
		digester.setIterations(1034);
	}
	
	public void save(Remote iremotepassword)
	{
		HibernateUtil.getSession().save(iremotepassword);
	}
	
	public void saveOrUpdate(Remote iremotepassword)
	{
		HibernateUtil.getSession().saveOrUpdate(iremotepassword);
	}
	
	public void delete(Remote iremotepassword)
	{
		HibernateUtil.getSession().delete(iremotepassword);
	}
	
	public Remote getIremotepassword(String deviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		cw.add(ExpWrap.eq("deviceid" , deviceid));
		List<Remote>  lst = cw.list();
		
		if ( lst == null || lst.size() == 0 )
			return null ;
		return lst.get(0);
	}
	
	public List<Remote> getIremotepassword(Collection<String> deviceid)
	{
		if ( deviceid == null || deviceid.size() == 0 )
			return new ArrayList<Remote>();
		
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		cw.add(ExpWrap.in("deviceid" , deviceid));
		return cw.list();
	}
	
	public List<String> queryDeviceidbyPhoneUserid(int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
		cw.addFields(new String[]{"deviceid"});
		return cw.list();
	}
	
	public List<String> queryDeviceidbyPhoneUserid(Collection<Integer> phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		cw.add(ExpWrap.in("phoneuserid", phoneuserid));
		cw.addFields(new String[]{"deviceid"});
		return cw.list();
	}
	
	public List<Remote> querybyPhoneUserid(int phoneuserid)
	{
		return this.querybyPhoneUserid(Arrays.asList(new Integer[]{phoneuserid}));
	}
	
	public List<Remote> querybyPhoneUserid(Collection<Integer> phoneuserid)
	{
		if ( phoneuserid == null || phoneuserid.size() == 0 )
			return new ArrayList<Remote>();
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		cw.add(ExpWrap.in("phoneuserid", phoneuserid));
		return cw.list();
	}
	
	public List<Remote> filterbyPhoneUserid(Collection<Remote> remotelist , Collection<Integer> phoneuserid)
	{
		List<Remote> lst = new ArrayList<Remote>();
		
		if ( remotelist == null || remotelist.size() == 0 
				|| phoneuserid == null || phoneuserid.size() == 0 )
			return lst ;
		
		for ( Remote r : remotelist )
			if ( r.getPhoneuserid() != null && phoneuserid.contains(r.getPhoneuserid()))
				lst.add(r);
		return lst ;
	}
	
	public List<String> queryDeviceid(List<Integer> phoneuserid , int longitude , int latitude)
	{
		if ( phoneuserid == null || phoneuserid.size() == 0 )
			return new ArrayList<String>();
		
		String hql = " select deviceid from Remote where phoneuserid in :phoneuserid and abs(longitude - :longitude) < 400 and abs (latitude - :latitude) < 400";
		Query query = HibernateUtil.getSession().createQuery(hql);
		query.setParameterList("phoneuserid", phoneuserid);
		query.setParameter("longitude", longitude);
		query.setParameter("latitude", latitude);
		
		return query.list();
	}
	
	public void initRemoteStatus()
	{
		String hql = " update Remote set status = 0 ";
		Query query = HibernateUtil.getSession().createQuery(hql);
		query.executeUpdate();
	}
	
	public String encryptPassword(String username , String password)
	{		
		return digester.digest(username + password);
	}
	
	public boolean checkRemotePassword(String deviceid , String password)
	{
		Remote u = getIremotepassword(deviceid);
		if ( u == null )
			return false ;
		return checkPassword(deviceid , password, u.getPassword());
	}
	
	public boolean checkPassword(String deviceid , String password , String enpassword)
	{
		return digester.matches(deviceid + password, enpassword);
	}

	public List<String> queryDeviceidListPhoneUserid(Collection<Integer> phoneuserid)
	{
		if ( phoneuserid == null || phoneuserid.size() == 0 )
			return new ArrayList<String>();
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		cw.add(ExpWrap.in("phoneuserid", phoneuserid));
		cw.addFields(new String[]{"deviceid"});
		return cw.list();
	}
}

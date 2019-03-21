package com.iremote.service;

import java.util.Date;
import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.Hibernate.HqlWrap;
import com.iremote.domain.DoorlockUser;
import org.hibernate.criterion.Order;

public class DoorlockUserService 
{
	public void save(DoorlockUser doorlockuser)
	{
		HibernateUtil.getSession().save(doorlockuser);
	}
	
	public void delete(DoorlockUser doorlockuser)
	{
		HibernateUtil.getSession().delete(doorlockuser);
	}
	
	public void delete(int zwavedeviceid , int usertype , int usercode)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockUser.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("usertype", usertype));
		cw.add(ExpWrap.eq("usercode", usercode));
		
		List<DoorlockUser> lst = cw.list();
		if ( lst == null || lst.size() == 0 )
			return ;
		
		for ( DoorlockUser du : lst )
			delete(du);
	}
	
	public DoorlockUser query(int doorlockuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockUser.class.getName());
		cw.add(ExpWrap.eq("doorlockuserid", doorlockuserid));
		return cw.uniqueResult();
	}
	
	public DoorlockUser query(int zwavedeviceid , int usertype , int usercode)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockUser.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("usertype", usertype));
		cw.add(ExpWrap.eq("usercode", usercode));
		List<DoorlockUser> lst = cw.list();
		if ( lst == null || lst.size() == 0 )
			return null ;
		return lst.get(0);
	}
	
	public List<DoorlockUser> querybyZwavedeviceid(int zwavedeviceid )
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockUser.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		return cw.list();
	}
	
	@Deprecated
	public String queryUsername(int zwavedeviceid , int usercode)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockUser.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("usercode", usercode));
		cw.addFields(new String[]{"username"});
		return cw.uniqueResult();
	}
	
	public List<DoorlockUser> queryValidUser(int zwavedeviceid , int usercode  , int usertype)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockUser.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("usercode", usercode));
		//cw.add(ExpWrap.eq("username", username));
		cw.add(ExpWrap.eq("usertype", usertype));
		cw.add(ExpWrap.or(ExpWrap.isnull("validthrough") , ExpWrap.ge("validthrough", new Date())));
		return cw.list();
	}
	
	public void deletebyZwavedeviceid(int zwavedeviceid)
	{
		HqlWrap hw = new HqlWrap();
		hw.addifnotnull("delete from DoorlockUser where zwavedeviceid=?" ,zwavedeviceid );
		hw.executeUpdate();
	}
	
	public List<Integer> queryUsercode(int zwavedeviceid , int usertype)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockUser.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("usertype", usertype));
		cw.addFields(new String[]{"usercode"});
		return cw.list();

	}
	
	public DoorlockUser querybyCardid(int zwavedeviceid , int cardid)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockUser.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("cardid", cardid));
		
		List<DoorlockUser> lst = cw.list();
		
		if ( lst == null || lst.size() == 0 )
			return null ;
		return lst.get(0);
	}

    public DoorlockUser querybyZwavedeviceidAndUsercode(int zwavedeviceid, int usercode) {
		CriteriaWrap cw = new CriteriaWrap(DoorlockUser.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("usercode", usercode));
		return cw.uniqueResult();
    }

	public List<Integer> queryUsercode(int zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockUser.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.addOrder(Order.asc("usercode"));
		cw.addFields(new String[]{"usercode"});
		return cw.list();

	}
}

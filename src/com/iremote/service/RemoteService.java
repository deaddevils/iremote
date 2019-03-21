package com.iremote.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.iremote.common.Hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.Remote;

public class RemoteService extends IremotepasswordService {

	public Integer queryOwnerId(String deviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.addFields(new String[]{"phoneuserid"});
		return cw.uniqueResult();
	}
	
	public List<Integer> queryOwnerId(Collection<String> deviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceid));
		cw.addFields(new String[]{"phoneuserid"});
		return cw.list();
	}
	
	public List<Remote> queryRecentlyOfflineWakeupGateway()
	{
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		List<Criterion> clst = new ArrayList<Criterion>();
		
		for ( String idp : Utils.WAKEUP_GATEWAY_ID_PREFIX)
			clst.add(ExpWrap.like("deviceid", idp+"%"));
		
		cw.add(ExpWrap.or(clst));
		cw.add(ExpWrap.le("lastupdatetime", Utils.currentTimeAdd(Calendar.DAY_OF_MONTH, -2)));
		cw.add(ExpWrap.eq("status", IRemoteConstantDefine.REMOTE_STATUS_ONLINE));
		return cw.list();
	}
	
	public List<Remote> queryRemoteByPhoneuserid(int phoneuserid){
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
		return cw.list();
	}
	
	public int queryRemotecountByPhoneuserid(int phoneuserid){
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
		return cw.count();
	}

	public List<String> queryDeviceidByPhoneuserid(int phoneuserid){
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
		cw.addFields(new String[]{"deviceid"});
		return cw.list();
	}
	
	public Remote querybyDeviceid(String deviceid){
		CriteriaWrap cw = new CriteriaWrap(Remote.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		return cw.uniqueResult();
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
	
	public List<String> getDeviceId(Collection<Remote> remotecollection)
	{
		List<String> lst = new ArrayList<String>();
		if ( remotecollection == null || remotecollection.size() == 0 )
			return lst ;
		for ( Remote r : remotecollection )
			lst.add(r.getDeviceid());
		return lst ;
	}

	public void changeOwner(int dest, String destphonenumber, int orgl) {
		String hql = "update Remote set phoneuserid = :dest,phonenumber = :destphonenumber where phoneuserid = :orgl";
		Query query = HibernateUtil.getSession().createQuery(hql);
		query.setInteger("dest",  dest);
		query.setInteger("orgl",  orgl);
		query.setString("destphonenumber",  destphonenumber);
		query.executeUpdate();
	}
}

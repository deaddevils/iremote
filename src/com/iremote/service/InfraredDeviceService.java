package com.iremote.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.Hibernate.HqlWrap;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.ZWaveDevice;

public class InfraredDeviceService extends BaseService<InfraredDevice>{
	
	public InfraredDevice query(Integer infrareddeviceid)
	{
		if ( infrareddeviceid == null )
			return null ;
		return (InfraredDevice)HibernateUtil.getSession().get(InfraredDevice.class, infrareddeviceid);
	}
	
	public List<InfraredDevice> query(Collection<Integer> infrareddeviceid)
	{
		if ( infrareddeviceid == null || infrareddeviceid.size() == 0 )
			return new ArrayList<InfraredDevice>();
		CriteriaWrap cw = new CriteriaWrap(InfraredDevice.class.getName());
		cw.add(ExpWrap.in("infrareddeviceid", infrareddeviceid));
		return cw.list();
	}
	
	public Integer queryid(String deviceid , String applianceid)
	{
		CriteriaWrap cw = new CriteriaWrap(InfraredDevice.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.eq("applianceid", applianceid));
		cw.addFields(new String[]{"infrareddeviceid"});
		return cw.uniqueResult();
	}
	
	public List<Integer> queryidbydeviceid(Collection<String> deviceid)
	{
		if ( deviceid == null || deviceid.size() == 0)
			return new ArrayList<Integer>() ;
		CriteriaWrap cw = new CriteriaWrap(InfraredDevice.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceid));
		cw.addFields(new String[]{"infrareddeviceid"});
		return cw.list();
	}
	
	public InfraredDevice query(String deviceid , String applianceid)
	{
		CriteriaWrap cw = new CriteriaWrap(InfraredDevice.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.eq("applianceid", applianceid));
		return cw.uniqueResult();
	}
	
	public List<InfraredDevice> querybydeviceid(Collection<String> deviceid)
	{
		if ( deviceid == null || deviceid.size() == 0 )
			return new ArrayList<InfraredDevice>();
		CriteriaWrap cw = new CriteriaWrap(InfraredDevice.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceid));
		return cw.list();
	}

	public List<InfraredDevice> querybydeviceid(String deviceid)
	{
		return querybydeviceid(Arrays.asList(deviceid));
	}
	
	public List<InfraredDevice> filterByDeviceid(Collection<InfraredDevice> infraredevicecollection , String deviceid)
	{
		List<InfraredDevice> lst = new ArrayList<InfraredDevice>();
		
		if ( infraredevicecollection == null || infraredevicecollection.size() == 0 || StringUtils.isBlank(deviceid))
			return lst ;
		
		for ( InfraredDevice id : infraredevicecollection)
			if ( deviceid.equals(id.getDeviceid()))
				lst.add(id);
		return lst ;
	}
	
	public List<InfraredDevice> querybydeviceidandName(Collection<String> deviceid , String name)
	{
		CriteriaWrap cw = new CriteriaWrap(InfraredDevice.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceid));
		cw.add(ExpWrap.eq("name", name));
		return cw.list();
	}
	
	public List<InfraredDevice> querybyidandName(Collection<Integer> idlst , String name)
	{
		CriteriaWrap cw = new CriteriaWrap(InfraredDevice.class.getName());
		cw.add(ExpWrap.in("infrareddeviceid", idlst));
		cw.add(ExpWrap.eq("name", name));
		return cw.list();
	}
	
	public void delete(String deviceid)
	{
		HqlWrap hql = new HqlWrap();
		hql.addifnotnull("delete from InfraredDevice where deviceid = ?" , deviceid);
		hql.executeUpdate();
	}
	
	public int queryCountBySharedeviceAndRemode(List<String> deviceids,List<Integer> infrareddevices)
	{
		CriteriaWrap cw = new CriteriaWrap(InfraredDevice.class.getName());
		cw.add(ExpWrap.in("deviceid" , deviceids));
		cw.add(ExpWrap.in("infrareddeviceid" , infrareddevices));
		return cw.list().size();
	}
	
}

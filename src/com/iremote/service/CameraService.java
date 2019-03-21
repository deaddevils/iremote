package com.iremote.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.Hibernate.HqlWrap;
import com.iremote.domain.Camera;

public class CameraService {
	
	public Camera query(int cameraid)
	{
		CriteriaWrap cw = new CriteriaWrap(Camera.class.getName());
		cw.add(ExpWrap.eq("cameraid", cameraid));
		return cw.uniqueResult();
	}
	
	public Camera query(String deviceid , String applianceid)
	{
		CriteriaWrap cw = new CriteriaWrap(Camera.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.eq("applianceid", applianceid));
		return cw.uniqueResult();
	}
	
	public List<Camera> query(Collection<Integer> cameraid)
	{
		if ( cameraid == null || cameraid.size() == 0 )
			return new ArrayList<Camera>();
		CriteriaWrap cw = new CriteriaWrap(Camera.class.getName());
		cw.add(ExpWrap.in("cameraid", cameraid));
		return cw.list();
	}
	
	public List<Camera> querybydeviceid(String deviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(Camera.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		return cw.list();
	}
	
	public List<Camera> querybydeviceid(Collection<String> deviceids)
	{
		if ( deviceids == null || deviceids.size() == 0 )
			return new ArrayList<Camera>();
		CriteriaWrap cw = new CriteriaWrap(Camera.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceids));
		return cw.list();
	}

	public List<Integer> queryidbydeviceid(Collection<String> deviceids)
	{
		if ( deviceids == null || deviceids.size() == 0 )
			return new ArrayList<>();
		CriteriaWrap cw = new CriteriaWrap(Camera.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceids));
		cw.addFields(new String[]{"cameraid"});
		return cw.list();
	}

	public List<Camera> filterByDeviceid(Collection<Camera> cameracollection , String deviceid)
	{
		List<Camera> lst = new ArrayList<Camera>();
		
		if ( cameracollection == null || cameracollection.size() == 0 || StringUtils.isBlank(deviceid))
			return lst ;
		
		for ( Camera id : cameracollection)
			if ( deviceid.equals(id.getDeviceid()))
				lst.add(id);
		return lst ;
	}
	
	public void saveOrUpdate(Camera camera)
	{
		HibernateUtil.getSession().saveOrUpdate(camera);
	}
	
	public void delete(Camera camera)
	{
		HibernateUtil.getSession().delete(camera);
	}
	
	public void delete(String deviceid)
	{
		HqlWrap hql = new HqlWrap();
		hql.addifnotnull("delete from Camera where deviceid = ?" , deviceid);
		hql.executeUpdate();
	}
	
	public Camera querybyapplianceuuid(String applianceuuid)
	{
		CriteriaWrap cw = new CriteriaWrap(Camera.class.getName());
		cw.add(ExpWrap.eq("applianceuuid", applianceuuid));
		return cw.uniqueResult();
	}
	
	public int queryCountBySharedeviceAndRemode(List<String> deviceids,List<Integer> cameraids)
	{
		CriteriaWrap cw = new CriteriaWrap(Camera.class.getName());
		cw.add(ExpWrap.in("deviceid" , deviceids));
		cw.add(ExpWrap.in("cameraid" , cameraids));
		return cw.list().size();
	}

	public List<Camera> queryByDeviceidAndName(List<String> deviceid , String name)
	{
		if ( deviceid == null || deviceid.size() == 0)
			return new ArrayList<Camera>() ;
		CriteriaWrap cw = new CriteriaWrap(Camera.class.getName());
		cw.add(ExpWrap.in("deviceid", deviceid));
		cw.add(ExpWrap.eq("name", name));
		return cw.list();
	}
	
	public List<Camera> querybypartitionid(int partitionid){
		CriteriaWrap cw = new CriteriaWrap(Camera.class.getName());
		cw.add(ExpWrap.eq("partitionid", partitionid));
		return cw.list();
	}

	public List<Camera> queryWarningDevice() {
		CriteriaWrap cw = new CriteriaWrap(Camera.class.getName());
		cw.add(ExpWrap.eq("enablestatus" , 0));
		cw.add(ExpWrap.not(ExpWrap.eq("warningstatuses", "[]")));
		cw.add(ExpWrap.not(ExpWrap.eq("warningstatuses", "[0]")));
		cw.add(ExpWrap.not(ExpWrap.isnull("warningstatuses")));
		return cw.list();
	}
}

package com.iremote.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.iremote.common.Hibernate.HqlWrap;
import org.hibernate.criterion.Order;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.constant.DeviceAuthorizeType;
import com.iremote.common.constant.DeviceShareSource;
import com.iremote.domain.ZWaveDeviceShare;

public class ZWaveDeviceShareService {

	public void save(ZWaveDeviceShare zds)
	{
		HibernateUtil.getSession().save(zds);
	}
	
	public List<ZWaveDeviceShare> query(int zwavedeviceid , int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("touserid", phoneuserid));
		cw.add(ExpWrap.le("validfrom", new Date()));
		cw.add(ExpWrap.ge("validthrough", new Date()));		
		return cw.list();
	}
	
	public ZWaveDeviceShare queryByid(int id)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.eq("id", id));
		return cw.uniqueResult();
	}
	
	public List<ZWaveDeviceShare> query(String deviceid ,int zwavedeviceid , int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("touserid", phoneuserid));
		cw.add(ExpWrap.le("validfrom", new Date()));
		cw.add(ExpWrap.ge("validthrough", new Date()));		
		return cw.list();
	}
	
	public List<ZWaveDeviceShare> query(int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.eq("touserid", phoneuserid));
		cw.add(ExpWrap.le("validfrom", new Date()));
		cw.add(ExpWrap.ge("validthrough", new Date()));		
		return cw.list();
	}
	
	public ZWaveDeviceShare queryRemoteShare(String deviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.eq("zwavedeviceid", 0));
		cw.add(ExpWrap.le("validfrom", new Date()));
		cw.add(ExpWrap.ge("validthrough", new Date()));		
		return cw.uniqueResult();
	}
	
	public List<Integer> queryPhoneUseridofJustExpiredShare()
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.le("validthrough", new Date()));
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -20);
		cw.add(ExpWrap.ge("validthrough", c.getTime()));
		
		cw.addFields(new String[]{"touserid"});
		return cw.list();
	}
	
	public void delete(ZWaveDeviceShare zds)
	{
		if (zds != null) {
			HibernateUtil.getSession().delete(zds);
		}
	}
	
	public void delete(List<ZWaveDeviceShare> zdsl)
	{
		for ( ZWaveDeviceShare zds : zdsl)
			delete(zds);
	}
	
	public List<String> querySharedRemoteid(int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.eq("touserid", phoneuserid));
		cw.add(ExpWrap.eq("zwavedeviceid", 0));
		cw.add(ExpWrap.le("validfrom", new Date()));
		cw.add(ExpWrap.ge("validthrough", new Date()));		
		cw.addFields(new String[]{"deviceid"});
		return cw.list();
	}
	
	public List<Integer> querySharedZwavedeviceid( int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.eq("touserid", phoneuserid));
		cw.add(ExpWrap.not(ExpWrap.eq("zwavedeviceid", 0)));
		cw.add(ExpWrap.le("validfrom", new Date()));
		cw.add(ExpWrap.ge("validthrough", new Date()));		
		cw.addFields(new String[]{"zwavedeviceid"});
		return cw.list();
	}
	
	public List<Integer> querySharedInfrareddeviceid( int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.eq("touserid", phoneuserid));
		cw.add(ExpWrap.not(ExpWrap.eq("zwavedeviceid", 0)));
		cw.add(ExpWrap.le("validfrom", new Date()));
		cw.add(ExpWrap.ge("validthrough", new Date()));		
		cw.addFields(new String[]{"infrareddeviceid"});
		return cw.list();
	}

	public List<Integer> queryPhoneuseidbydevice(String deviceid , Integer zwavedeviceid)
	{
		if ( deviceid == null || deviceid.length() == 0) 
			return new ArrayList<Integer>();
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		if ( zwavedeviceid != null )
		{
			if ( zwavedeviceid != 0 )
				cw.add(ExpWrap.or(ExpWrap.eq("zwavedeviceid", 0),ExpWrap.eq("zwavedeviceid", zwavedeviceid)));
			else 
				cw.add(ExpWrap.eq("zwavedeviceid", 0));
		}
		cw.addFields(new String[]{"touserid"});
		cw.add(ExpWrap.le("validfrom", new Date()));
		cw.add(ExpWrap.ge("validthrough", new Date()));		
		return cw.list();
	}
	
	public List<Integer> queryPhoneuseridbyCameraid(Integer cameraid)
	{
		if ( cameraid == null )
			return new ArrayList<Integer>();
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.eq("cameraid", cameraid));
		cw.addFields(new String[]{"touserid"});
		cw.add(ExpWrap.le("validfrom", new Date()));
		cw.add(ExpWrap.ge("validthrough", new Date()));		
		return cw.list();
	}
	
	public List<ZWaveDeviceShare> querybyZwaveDeviceid(int zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		return cw.list();
	}
	
	public List<ZWaveDeviceShare> querybyInfrareddeviceid(int infrareddeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.eq("infrareddeviceid", infrareddeviceid));
		return cw.list();
	}
	
	public List<ZWaveDeviceShare> querybyCameraid(int cameraid)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.eq("cameraid", cameraid));
		return cw.list();
	}
	
	public List<ZWaveDeviceShare> querybyZwaveDeviceid(int zwavedeviceid , DeviceShareSource sharesource )
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("shareowntype", sharesource.getSource()));
		cw.add(ExpWrap.ge("validthrough", new Date()));		
		cw.add(ExpWrap.not(ExpWrap.eq("validtype", DeviceAuthorizeType.notvalid.getValidtype())));
		cw.addOrder(Order.asc("validfrom"));
		return cw.list();
	}
	
	public List<ZWaveDeviceShare> querybytoken(String phonenumber , String token)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.eq("touser", phonenumber));
		cw.add(ExpWrap.like("token", token));
		return cw.list();
	}
	
	public ZWaveDeviceShare querybytoken(String token){
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.like("token", token));
		return cw.uniqueResult();
	}
	
	public List<ZWaveDeviceShare> queryonlybytoken(String token)
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.like("token", token));
		return cw.list();
	}

	public ZWaveDeviceShare queryThirdPartShareByToUserId(int zwavedeviceid, int touserid, DeviceShareSource sharesource )
	{
		CriteriaWrap cw = new CriteriaWrap(ZWaveDeviceShare.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("shareowntype", sharesource.getSource()));
		cw.add(ExpWrap.eq("touserid", touserid));
		return cw.uniqueResult();
	}
}

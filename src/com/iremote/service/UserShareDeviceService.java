package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.UserShare;
import com.iremote.domain.UserShareDevice;

public class UserShareDeviceService {

	public int save(UserShareDevice usd)
	{
		return (Integer)HibernateUtil.getSession().save(usd);
	}
	
	public void delete(UserShareDevice usd)
	{
		HibernateUtil.getSession().delete(usd);
	}
	
	public UserShareDevice query(int usersharedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShareDevice.class.getName());
		cw.add(ExpWrap.eq("usersharedeviceid", usersharedeviceid));
		
		List<UserShareDevice> lst = cw.list();
		if ( lst == null || lst.size() != 1 )
			return null ;
		return lst.get(0);
	}
	
	public List<UserShareDevice> query(UserShare userShare)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShareDevice.class.getName());
		cw.add(ExpWrap.eq("userShare", userShare));
		return cw.list();
	}
	
	public List<UserShareDevice> queryByZwavedeviceid(UserShare userShare,Integer zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShareDevice.class.getName());
		cw.add(ExpWrap.eq("userShare", userShare));
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		return cw.list();
	}
	public List<UserShareDevice> queryByZwavedeviceid(Integer zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShareDevice.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		return cw.list();
	}
	
	public List<UserShareDevice> queryByInfrareddeviceid(UserShare userShare,Integer infrareddeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShareDevice.class.getName());
		cw.add(ExpWrap.eq("userShare", userShare));
		cw.add(ExpWrap.eq("infrareddeviceid", infrareddeviceid));
		return cw.list();
	}
	
	public List<UserShareDevice> queryByInfrareddeviceid(Integer infrareddeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShareDevice.class.getName());
		cw.add(ExpWrap.eq("infrareddeviceid", infrareddeviceid));
		return cw.list();
	}
	
	public List<UserShareDevice> queryCameraid(Integer cameraid)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShareDevice.class.getName());
		cw.add(ExpWrap.eq("cameraid", cameraid));
		return cw.list();
	}
}

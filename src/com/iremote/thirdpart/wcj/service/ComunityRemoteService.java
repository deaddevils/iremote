package com.iremote.thirdpart.wcj.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.Remote;
import com.iremote.thirdpart.wcj.domain.ComunityRemote;

public class ComunityRemoteService {

	public void saveOrUpdate(ComunityRemote cr)
	{
		HibernateUtil.getSession().saveOrUpdate(cr);
	}
	
	public void delete(ComunityRemote cr)
	{
		HibernateUtil.getSession().delete(cr);
	}
	
	public List<ComunityRemote> query(int thirdpartid )
	{
		CriteriaWrap cw = new CriteriaWrap(ComunityRemote.class.getName());
		cw.add(ExpWrap.eq("thirdpartid", thirdpartid));
		return cw.list();
	}
	
	public ComunityRemote query(int thirdpartid , String deviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(ComunityRemote.class.getName());
		cw.add(ExpWrap.eq("thirdpartid", thirdpartid));
		cw.add(ExpWrap.eq("deviceid", deviceid));
		return cw.uniqueResult();
	}

	public ComunityRemote querybyDeviceid(String deviceid)
	{
		if ( StringUtils.isBlank(deviceid))
			return null ;
		CriteriaWrap cw = new CriteriaWrap(ComunityRemote.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		return cw.uniqueResult();
	}
	
	public List<Remote> queryRemote(int comunityid , Date start , Date end)
	{
		CriteriaWrap cw = new CriteriaWrap(ComunityRemote.class.getName());
		cw.add(ExpWrap.eq("comunityid", comunityid));
		cw.addFields(new String[]{"deviceid"});
		List<String> didl = cw.list();
		
		cw = new CriteriaWrap(Remote.class.getName());
		cw.add(ExpWrap.bw("lastupdatetime", start, end));
		cw.add(ExpWrap.in("deviceid", didl));
		
		return cw.list();
	}
	
	
}

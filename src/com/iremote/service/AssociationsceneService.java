package com.iremote.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.Hibernate.HqlWrap;
import com.iremote.domain.Associationscene;
import com.iremote.domain.ZWaveDevice;

public class AssociationsceneService extends BaseService<Associationscene>{

	public Associationscene query(int associationsceneid)
	{
		CriteriaWrap cw = new CriteriaWrap(Associationscene.class.getName());
		cw.add(ExpWrap.eq("associationsceneid" , associationsceneid));
		return cw.uniqueResult();
	}
	
	public Associationscene query(ZWaveDevice device , int channel , int status)
	{
		CriteriaWrap cw = new CriteriaWrap(Associationscene.class.getName());
		cw.add(ExpWrap.eq("zwavedevice" , device));
		cw.add(ExpWrap.eq("channelid" , channel));
		cw.add(ExpWrap.eq("status" , status));

		return cw.uniqueResult();
	}
	
	public List<Associationscene>  queryAssociationscene(Integer zwavedeviceid , Integer cameraid , Integer channelid , String devicestatus)
	{
		if ( zwavedeviceid == null && cameraid == null )
			return null ;
		
		CriteriaWrap cw = new CriteriaWrap(Associationscene.class.getName());
		cw.addifNotNull(ExpWrap.eq("channelid", channelid));
		cw.addifNotNull(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.addifNotNull(ExpWrap.eq("devicestatus", devicestatus));
		cw.addifNotNull(ExpWrap.eq("cameraid", cameraid));
		return cw.list();
	}
	
	public List<Associationscene> queryAssociationscene2(Associationscene associationscene)
	{
		CriteriaWrap cw = new CriteriaWrap(Associationscene.class.getName());
		cw.addifNotNull(ExpWrap.eq("channelid", associationscene.getChannelid()));
		
		if ( (associationscene.getZwavedeviceid() == null || associationscene.getZwavedeviceid() == 0 )
				&& ( associationscene.getCameraid() == null || associationscene.getCameraid() == 0 ) )
			return null ;
		
		if ( associationscene.getZwavedeviceid() != null && associationscene.getZwavedeviceid() != 0 )
			cw.addifNotNull(ExpWrap.eq("zwavedeviceid", associationscene.getZwavedeviceid()));
		if ( associationscene.getCameraid() != null && associationscene.getCameraid() != 0 )
			cw.addifNotNull(ExpWrap.eq("cameraid", associationscene.getCameraid()));
		cw.addifNotNull(ExpWrap.eq("devicestatus", associationscene.getDevicestatus()));
		cw.addifNotNull(ExpWrap.eq("scenetype", associationscene.getScenetype()));
		return cw.list();
	}
	
	public List<Associationscene> query(ZWaveDevice device , int channel , int[] status)
	{
		CriteriaWrap cw = new CriteriaWrap(Associationscene.class.getName());
		cw.add(ExpWrap.eq("zwavedevice" , device));
		cw.add(ExpWrap.eq("channelid" , channel));
		cw.add(ExpWrap.in("status" , status));

		return cw.list();
	}
	
	public List<Associationscene> querybyCameraid(int cameraid)
	{
		CriteriaWrap cw = new CriteriaWrap(Associationscene.class.getName());
		cw.add(ExpWrap.eq("cameraid" , cameraid));
		return cw.list();
	}
	
	public List<Associationscene> querybyZwaveDeviceid(Collection<Integer> zwavedeviceid)
	{
		if ( zwavedeviceid == null || zwavedeviceid.size() == 0 )
			return new ArrayList<Associationscene>();
		CriteriaWrap cw = new CriteriaWrap(Associationscene.class.getName());
		cw.add(ExpWrap.in("zwavedeviceid" , zwavedeviceid));
		return cw.list();
	}

	public List<Associationscene> querybyCameraids(Collection cameraids)
	{
		CriteriaWrap cw = new CriteriaWrap(Associationscene.class.getName());
		cw.add(ExpWrap.in("cameraid" , cameraids));
		return cw.list();
	}
	
	public List<Associationscene> query(int first , int max)
	{
		CriteriaWrap cw = new CriteriaWrap(Associationscene.class.getName());
		cw.setFirstResult(first);
		cw.setMaxResults(max);
		return cw.list();
	}
	
	public void delete(Associationscene associationscene)
	{
		HibernateUtil.getSession().delete(associationscene);
	}
	
	public void deletebyZwavedevicidandtype(int zwavedeviceid , int scenetype)
	{
		HqlWrap hw = new HqlWrap();
		hw.addifnotnull("delete from Associationscene where zwavedeviceid = ? ", zwavedeviceid);
		hw.addifnotnull(" and scenetype = ? " , scenetype);
		hw.executeUpdate();
	}
}

package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HqlWrap;
import com.iremote.domain.RoomAppliance;

public class RoomApplianceService extends BaseService<RoomAppliance>{

	public List<RoomAppliance> query(String deviceid , String applianceid)
	{
		CriteriaWrap cw = new CriteriaWrap(RoomAppliance.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.eq("applianceid", applianceid));
		return cw.list();
	}

	public List<RoomAppliance> querybyZwavedeviceid(int zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(RoomAppliance.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		return cw.list();
	}
	
	public RoomAppliance querybyZwavedeviceidAndChannelid(int zwavedeviceid,int channelid)
	{
		CriteriaWrap cw = new CriteriaWrap(RoomAppliance.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("channelid", channelid));
		return cw.uniqueResult();
	}
	
	public RoomAppliance querybyCameraid(int cameraid)
	{
		CriteriaWrap cw = new CriteriaWrap(RoomAppliance.class.getName());
		cw.add(ExpWrap.eq("cameraid", cameraid));
		return cw.uniqueResult();
	}
	
	public RoomAppliance querybyInfraredid(int infrareddeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(RoomAppliance.class.getName());
		cw.add(ExpWrap.eq("infrareddeviceid", infrareddeviceid));
		return cw.uniqueResult();
	}

	public void delete(String deviceid , int nuid)
	{
		if ( deviceid == null )
			return ;
		HqlWrap hw = new HqlWrap();
		hw.add(" Delete from RoomAppliance where ");
		hw.addifnotnull(" deviceid = ? ", deviceid );
		hw.addifnotnull(" and nuid = ? ", nuid );
		
		hw.executeUpdate();
	}
	
	public void delete(String deviceid , String applianceid)
	{
		if ( deviceid == null )
			return ;
		HqlWrap hw = new HqlWrap();
		hw.add(" Delete from RoomAppliance where ");
		hw.addifnotnull(" deviceid = ? ", deviceid );
		hw.addifnotnull(" and applianceid = ? ", applianceid );
		
		hw.executeUpdate();
	}
}

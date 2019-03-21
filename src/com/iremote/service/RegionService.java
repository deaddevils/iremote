package com.iremote.service;

import java.util.List;

import org.hibernate.criterion.Order;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.Region;

public class RegionService extends BaseService<Region>{

	public List<Region> queryAllRegion(){
		CriteriaWrap cw = new CriteriaWrap(Region.class.getName());
		cw.addOrder(Order.asc("regionid"));
		return cw.list();
	}
	
	public Region queryByRegionid(Integer regionid){
		if(regionid==null){
			return null;
		}
		CriteriaWrap cw = new CriteriaWrap(Region.class.getName());
		cw.add(ExpWrap.eq("regionid", regionid));
		return cw.uniqueResult();
	}
}

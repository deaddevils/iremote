package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.Province;

public class ProvinceService extends BaseService<Province>{

	public List<Province> queryByRegionid(Integer regionid){
		CriteriaWrap cw = new CriteriaWrap(Province.class.getName());
		cw.add(ExpWrap.eq("regionid", regionid));
		return cw.list();
	}
	
	public Province queryByProvinceid(Integer provinceid){
		if(provinceid==null){
			return null;
		}
		CriteriaWrap cw = new CriteriaWrap(Province.class.getName());
		cw.add(ExpWrap.eq("provinceid", provinceid));
		return cw.uniqueResult();
	}
}

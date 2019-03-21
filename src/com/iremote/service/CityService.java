package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.City;

public class CityService extends BaseService<City>{

	public List<City> queryByProvinceid(Integer provinceid){
		CriteriaWrap cw = new CriteriaWrap(City.class.getName());
		cw.add(ExpWrap.eq("provinceid", provinceid));
		return cw.list();
	}
	
	public List<City> queryAllCities(){
		CriteriaWrap cw = new CriteriaWrap(City.class.getName());
		return cw.list();
	}
	public City queryByCityid(Integer cityid){
		if(cityid==null){
			return null;
		}
		CriteriaWrap cw = new CriteriaWrap(City.class.getName());
		cw.add(ExpWrap.eq("cityid", cityid));
		return cw.uniqueResult();
	}
}

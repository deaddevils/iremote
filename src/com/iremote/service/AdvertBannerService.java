package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.AdvertBanner;

public class AdvertBannerService {

	public AdvertBanner queryById(Integer advertbannerid){
		CriteriaWrap cw = new CriteriaWrap(AdvertBanner.class.getName());
		cw.add(ExpWrap.eq("advertbannerid", advertbannerid));
		return cw.uniqueResult();
	}
	
	
}

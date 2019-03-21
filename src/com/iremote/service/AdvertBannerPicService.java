package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.AdvertBannerPic;

public class AdvertBannerPicService {

	public List<AdvertBannerPic> queryByAdvertbannerid(Integer advertbannerid){
		CriteriaWrap cw = new CriteriaWrap(AdvertBannerPic.class.getName());
		cw.add(ExpWrap.eq("advertbannerid", advertbannerid));
		if(cw.list()!=null&&cw.list().size()>0){
			return cw.list();
		}
		return null;
	}
}

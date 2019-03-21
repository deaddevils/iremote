package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.County;
import com.iremote.domain.Province;

import java.util.List;

public class CountyService extends  BaseService<County> {
    public List<Province> queryByCityId(Integer cityId){
        CriteriaWrap cw = new CriteriaWrap(County.class.getName());
        cw.add(ExpWrap.eq("cityid", cityId));
        return cw.list();
    }

    public County queryByAliLocationKey(String alilocationkey) {
        CriteriaWrap cw = new CriteriaWrap(County.class.getName());
        cw.add(ExpWrap.eq("alilocationkey", alilocationkey));
        return cw.uniqueResult();
    }
}

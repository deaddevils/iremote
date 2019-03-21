package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.DataDictionary;

import java.util.List;

public class DataDictionaryService extends BaseService<DataDictionary> {

    public List<DataDictionary> findByLanguage(String language) {
        CriteriaWrap cw = new CriteriaWrap(DataDictionary.class.getName());
        cw.add(ExpWrap.eq("language", language).ignoreCase());
        return cw.list();
    }
}

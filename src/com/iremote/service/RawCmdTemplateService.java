package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.RawCmdTemplate;
import org.hibernate.Query;

import java.util.List;

public class RawCmdTemplateService extends BaseService<RawCmdTemplate> {
    public RawCmdTemplate query(int rawCmdTemplateId) {
        CriteriaWrap cw = new CriteriaWrap(RawCmdTemplate.class.getName());
        cw.add(ExpWrap.eq("rawcmdtemplateid", rawCmdTemplateId));
        return cw.uniqueResult();
    }

    public List<RawCmdTemplate> queryByPhoneUserId(List<Integer> phoneUserIdList) {
        CriteriaWrap cw = new CriteriaWrap(RawCmdTemplate.class.getName());
        cw.add(ExpWrap.in("phoneuserid", phoneUserIdList));
        return cw.list();
    }

    public List<RawCmdTemplate> querySystemLevelTemplates() {
        CriteriaWrap cw = new CriteriaWrap(RawCmdTemplate.class.getName());
        cw.add(ExpWrap.isnull("phoneuserid"));
        return cw.list();
    }
}

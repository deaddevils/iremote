package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.AuthQrCode;
import com.iremote.domain.Camera;

public class AuthQrCodeService extends BaseService<AuthQrCode> {
    public AuthQrCode findByApplianceUuid(String applianceUuid) {
        CriteriaWrap cw = new CriteriaWrap(AuthQrCode.class.getName());
        cw.add(ExpWrap.eq("applianceuuid", applianceUuid));
        return cw.uniqueResult();
    }

    public AuthQrCode findByQid(String qid) {
        CriteriaWrap cw = new CriteriaWrap(AuthQrCode.class.getName());
        cw.add(ExpWrap.eq("qid", qid));
        return cw.uniqueResult();
    }
}

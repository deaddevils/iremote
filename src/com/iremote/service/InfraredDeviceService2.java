package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.InfraredDevice2;
import com.iremote.domain.InfreredCodeLiberay;
import com.iremote.domain.TableBrandPO;

import java.util.List;

@Deprecated
public class InfraredDeviceService2 {

    public List<TableBrandPO> queryProductor(String devicetype) {
        CriteriaWrap cw = new CriteriaWrap(TableBrandPO.class.getName());
        cw.add(ExpWrap.eq("devicetype", devicetype));
        return cw.list();
    }

    public List<InfraredDevice2> queryInfraredDevice(String productor, String devicetype) {
        CriteriaWrap cw = new CriteriaWrap(InfraredDevice2.class.getName());
        cw.add(ExpWrap.eq("brand_en", productor));
        cw.add(ExpWrap.eq("devicetype", devicetype));
        return cw.list();
    }

    /*查询code*/
    public String queryByCodeid(int codeid, String devicetype) {
        CriteriaWrap cw = new CriteriaWrap(InfraredDevice2.class.getName());
        cw.add(ExpWrap.eq("serial", codeid));
        cw.add(ExpWrap.eq("devicetype", devicetype));
        cw.addFields(new String[]{"code"});
        return cw.uniqueResult();
    }

}

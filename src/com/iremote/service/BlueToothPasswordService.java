package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.BlueToothPassword;
import com.iremote.domain.ZWaveDevice;
import org.hibernate.Query;

import java.util.Collection;
import java.util.List;

public class BlueToothPasswordService extends BaseService<BlueToothPassword> {
    public List<BlueToothPassword> findByZwaveDeviceIdAndPhoneUserIds(Integer zwaveDeviceId, Collection<Integer> phoneUserIds) {
        CriteriaWrap cw = new CriteriaWrap(BlueToothPassword.class.getName());
        cw.add(ExpWrap.eq("zwavedeviceid", zwaveDeviceId));
        cw.add(ExpWrap.in("phoneuserid", phoneUserIds));

        return cw.list();
    }

    public List<BlueToothPassword> findByZwaveDeviceId(Integer zwaveDeviceId) {
        CriteriaWrap cw = new CriteriaWrap(BlueToothPassword.class.getName());
        cw.add(ExpWrap.eq("zwavedeviceid", zwaveDeviceId));

        return cw.list();
    }

    public void deleteByZwaveDeviceId(Integer zwaveDeviceId) {
        String hql = "delete from bluetoothpassword where zwavedeviceid = :zwavedeviceid";

        Query query = HibernateUtil.getSession().createSQLQuery(hql);
        query.setInteger("zwavedeviceid",  zwaveDeviceId);

        query.executeUpdate();
    }

    public void deleteByZwaveDeviceIdAndPhoneUserId(Integer zwaveDeviceId, Integer phoneUserId) {
        String hql = "delete from bluetoothpassword where zwavedeviceid = :zwavedeviceid and phoneuserid = :phoneuserid";

        Query query = HibernateUtil.getSession().createSQLQuery(hql);
        query.setInteger("zwavedeviceid",  zwaveDeviceId);
        query.setInteger("phoneuserid",  phoneUserId);

        query.executeUpdate();
    }
    
    public BlueToothPassword findByZwaveDeviceIdAndPhoneUserId(Integer zwavedeviceid , Integer phoneuserid){
    	CriteriaWrap cw = new CriteriaWrap(BlueToothPassword.class.getName());
        cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
        cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
        return cw.uniqueResult();
    }
    
    public List<BlueToothPassword> findByPhoneUserId(Integer phoneuserid){
    	CriteriaWrap cw = new CriteriaWrap(BlueToothPassword.class.getName());
        cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
        if(cw.list()!=null&&cw.list().size()>0){
        	return cw.list();
        }
        return null;
    }

    public List<Integer> findPhoneUserId(Integer zwaveDeviceId) {
        CriteriaWrap cw = new CriteriaWrap(BlueToothPassword.class.getName());
        cw.add(ExpWrap.eq("zwavedeviceid", zwaveDeviceId));

        cw.addFields(new String[]{"phoneuserid"});
        return cw.list();
    }
}

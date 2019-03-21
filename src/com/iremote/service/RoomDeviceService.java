package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.RoomDevice;

import java.util.List;

public class RoomDeviceService extends BaseService<RoomDevice> {

    public List<RoomDevice> querybydeviceid(int deviceid) {
        CriteriaWrap cw = new CriteriaWrap(RoomDevice.class.getName());
        cw.add(ExpWrap.eq("deviceid", deviceid));
        return cw.list();
    }

    public void deleteAll(List<RoomDevice> roomDevices) {
        if (roomDevices == null) {
            return;
        }
        for (RoomDevice rd : roomDevices) {
            delete(rd);
        }
    }
}

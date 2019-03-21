package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.ZwaveDeviceActiveTime;

import java.util.Date;
import java.util.List;

public class ZwaveDeviceActiveTimeService extends BaseService<ZwaveDeviceActiveTime>  {

    public ZwaveDeviceActiveTime query(int zwavedeviceid){
        CriteriaWrap cw = new CriteriaWrap(ZwaveDeviceActiveTime.class.getName());
        cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
        return cw.uniqueResult();
    }

    public List<ZwaveDeviceActiveTime> queryNeedChangetoFaultDetectDevices(){
        CriteriaWrap cw = new CriteriaWrap(ZwaveDeviceActiveTime.class.getName());
        cw.add(ExpWrap.lt("nextactivetime", new Date()));
        cw.add(ExpWrap.eq("status", IRemoteConstantDefine.DOOR_LOCK_STATUS_NORMAL));
        return cw.list();
    }
}

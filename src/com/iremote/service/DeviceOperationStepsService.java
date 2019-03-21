package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.constant.DeviceOperationType;
import com.iremote.domain.DeviceOperationSteps;
import org.hibernate.criterion.Order;

import java.util.List;

public class DeviceOperationStepsService {

    public void saveOrUpdate(DeviceOperationSteps dos) {
        HibernateUtil.getSession().saveOrUpdate(dos);
    }

    public DeviceOperationSteps querybydeviceidandtype(String deviceid, DeviceOperationType type) {
        CriteriaWrap cw = new CriteriaWrap(DeviceOperationSteps.class.getName());
        cw.add(ExpWrap.eq("deviceid", deviceid));
        cw.add(ExpWrap.eq("optype", type.ordinal()));
        return cw.uniqueResult();
    }

	public List<DeviceOperationSteps> queryDSCTask(String deviceid) {
		CriteriaWrap cw = new CriteriaWrap(DeviceOperationSteps.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.eq("optype", DeviceOperationType.setdscarm.ordinal()));
		return cw.list();
	}

	public DeviceOperationSteps querybydeviceidandtype(String deviceid, DeviceOperationType type, Integer objid) {
		CriteriaWrap cw = new CriteriaWrap(DeviceOperationSteps.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.eq("optype", type.ordinal()));
		cw.add(ExpWrap.eq("objid", objid));
		return cw.uniqueResult();
	}

    public DeviceOperationSteps queryByZwavedeviceid(int zwavedeviceid) {
        CriteriaWrap cw = new CriteriaWrap(DeviceOperationSteps.class.getName());
        cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
        return cw.uniqueResult();
    }
	
	public DeviceOperationSteps querybyzwavedeviceidandtype(int zwavedeviceid , DeviceOperationType type)
	{
		CriteriaWrap cw = new CriteriaWrap(DeviceOperationSteps.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("optype", type.ordinal()));
		return cw.uniqueResult();
	}

    public DeviceOperationSteps queryByObjidAndType(int objid, DeviceOperationType type) {
        CriteriaWrap cw = new CriteriaWrap(DeviceOperationSteps.class.getName());
        cw.add(ExpWrap.eq("objid", objid));
        cw.add(ExpWrap.eq("optype", type.ordinal()));
        return cw.uniqueResult();
    }

	public List<DeviceOperationSteps> queryDSCPartitionDisarmbyPartitionid(int objid) {
		CriteriaWrap cw = new CriteriaWrap(DeviceOperationSteps.class.getName());
		cw.add(ExpWrap.eq("objid", objid));
		cw.add(ExpWrap.eq("optype", DeviceOperationType.setdscparititiondisarm.ordinal()));
		return cw.list();
	}

	public DeviceOperationSteps queryByZwavedeviceidAndObjidAndType(int zwavedeviceid, int objid, DeviceOperationType type) {
		CriteriaWrap cw = new CriteriaWrap(DeviceOperationSteps.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("objid", objid));
		cw.add(ExpWrap.eq("optype", type.ordinal()));
		return cw.uniqueResult();
	}

	public void update(DeviceOperationSteps dos)
	{
		HibernateUtil.getSession().update(dos);
	}

	public DeviceOperationSteps querybydeviceidandtypeandinfrareddeviceid(String deviceid , DeviceOperationType type,int infrareddeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(DeviceOperationSteps.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.eq("optype", type.ordinal()));
		cw.add(ExpWrap.eq("infrareddeviceid", infrareddeviceid));
		return cw.uniqueResult();
	}

	public DeviceOperationSteps querybydeviceidandtypeandinfrareddeviceidandkeyindex(String deviceid , DeviceOperationType type,int infrareddeviceid,int keyindex)
	{
		CriteriaWrap cw = new CriteriaWrap(DeviceOperationSteps.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.eq("keyindex", keyindex));
		cw.add(ExpWrap.eq("optype", type.ordinal()));
		cw.add(ExpWrap.eq("infrareddeviceid", infrareddeviceid));
		return cw.uniqueResult();
	}

	public DeviceOperationSteps queryByDeviceidAndTypeAndZwaveDeviceId(String deviceid , DeviceOperationType type,int zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(DeviceOperationSteps.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		cw.add(ExpWrap.eq("optype", type.ordinal()));
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		return cw.uniqueResult();
	}
}

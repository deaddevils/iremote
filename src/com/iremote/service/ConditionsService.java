package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.Hibernate.HqlWrap;
import com.iremote.domain.Associationscene;
import com.iremote.domain.Conditions;
import com.iremote.domain.ZWaveDevice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConditionsService extends BaseService<Associationscene>{

	public Conditions query(int conditionsid)
	{
		CriteriaWrap cw = new CriteriaWrap(Conditions.class.getName());
		cw.add(ExpWrap.eq("conditionsid" , conditionsid));
		return cw.uniqueResult();
	}

	public List<Conditions> query(ZWaveDevice device , int channel , int[] status)
	{
		CriteriaWrap cw = new CriteriaWrap(Conditions.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid" , device.getZwavedeviceid()));
		cw.add(ExpWrap.eq("channelid" , channel));
		cw.add(ExpWrap.in("status" , convert(status)));

		return cw.list();
	}

	public void delete(Conditions conditions)
	{
		HibernateUtil.getSession().delete(conditions);
	}

	public List<Conditions> querybyZwavedeviceid(int zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(Conditions.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid" , zwavedeviceid));

		return cw.list();
	}

	public List<Conditions> querybyZwavedeviceids(Collection<Integer> zWaveDeviceIds)
	{
		CriteriaWrap cw = new CriteriaWrap(Conditions.class.getName());
		cw.add(ExpWrap.in("zwavedeviceid" , zWaveDeviceIds));

		return cw.list();
	}

	public List<Conditions> queryByDeviceid(String deviceid){
		CriteriaWrap cw = new CriteriaWrap(Conditions.class.getName());
		cw.add(ExpWrap.eq("deviceid" , deviceid));

		return cw.list();
	}

	public static Object[] convert(int[] status){
		Object[] floats = new Object[status.length];
		for (int i = 0; i < status.length; i++) {
			floats[i] = (float)status[i];
		}
		return floats;
	}
}

package com.iremote.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.DeviceGroup;
import org.hibernate.Query;

public class DeviceGroupService extends BaseService<DeviceGroup>
{
	public DeviceGroup get(int id)
	{
		return (DeviceGroup)HibernateUtil.getSession().get(DeviceGroup.class, id);
	}
	
	public List<DeviceGroup> querybyphoneuserid(Collection<Integer> phoneuserids)
	{
		if ( phoneuserids == null || phoneuserids.size() == 0 )
			return new ArrayList<DeviceGroup>();
		CriteriaWrap cw = new CriteriaWrap(DeviceGroup.class.getName());
		cw.add(ExpWrap.in("phoneuserid", phoneuserids));
		return cw.list();
	}

	public void changeOwner(int dest, int orgl) {
		String hql = "update DeviceGroup set phoneuserid = :dest where phoneuserid = :orgl";
		Query query = HibernateUtil.getSession().createQuery(hql);
		query.setInteger("dest",  dest);
		query.setInteger("orgl",  orgl);
		query.executeUpdate();
	}
}

package com.iremote.service;

import java.util.Collection;
import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.DeviceGroupDetail;

public class DeviceGroupDetailService extends BaseService<DeviceGroupDetail>
{
	public List<DeviceGroupDetail> querybyZwavedeviceid(int zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(DeviceGroupDetail.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		return cw.list();
	}
	
	
	public List<DeviceGroupDetail> querybyZwavedeviceid(Collection<Integer> zwavedeviceid)
	{
		if ( zwavedeviceid == null || zwavedeviceid.size()== 0 )
			return null ;
		
		CriteriaWrap cw = new CriteriaWrap(DeviceGroupDetail.class.getName());
		cw.add(ExpWrap.in("zwavedeviceid", zwavedeviceid));
		return cw.list();
	}
}

package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.DeviceExtendInfo;

public class DeviceExtendInfoService extends BaseService<DeviceExtendInfo> 
{
	public List<DeviceExtendInfo> querybyzwavedeviceid(int zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(DeviceExtendInfo.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		return cw.list();
	}
}

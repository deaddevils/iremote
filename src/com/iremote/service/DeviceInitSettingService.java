package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.DeviceInitSetting;

public class DeviceInitSettingService extends BaseService<DeviceInitSetting>
{
	public List<DeviceInitSetting> query(String devicetype)
	{
		CriteriaWrap cw = new CriteriaWrap(DeviceInitSetting.class.getName());
		cw.add(ExpWrap.eq("devicetype", devicetype));
		return cw.list();
	}
	
	public DeviceInitSetting querybymid(String mid)
	{
		CriteriaWrap cw = new CriteriaWrap(DeviceInitSetting.class.getName());
		cw.add(ExpWrap.ignoreCaseEq("mid", mid));
		return cw.uniqueResult();
	}
}

package com.iremote.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.DeviceUpgradePackage;

public class DeviceUpgradePackageService extends BaseService<DeviceUpgradePackage>
{

	public List<DeviceUpgradePackage> query(String devicetype ,String productor, String model)
	{
		if ( StringUtils.isBlank(devicetype) 
				|| StringUtils.isBlank(productor)
				|| StringUtils.isBlank(model))
			return null ;
		CriteriaWrap cw = new CriteriaWrap(DeviceUpgradePackage.class.getName());
		cw.add(ExpWrap.eq("devicetype", devicetype));
		cw.add(ExpWrap.eq("productor", productor));
		cw.add(ExpWrap.eq("model", model));
		cw.addOrder(Order.desc("version1"));
		return cw.list();
	}
}

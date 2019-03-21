package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.GatewayCapability;

public class GatewayCapabilityService extends BaseService<GatewayCapability>
{
	public List<GatewayCapability> querybydeviceid(String deviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(GatewayCapability.class.getName());
		cw.add(ExpWrap.eq("remote.deviceid" , deviceid));
		
		return cw.list();
	}
}

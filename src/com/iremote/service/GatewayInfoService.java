package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.GatewayInfo;

public class GatewayInfoService extends BaseService<GatewayInfo>
{
	public GatewayInfo querybykey(String qrcodekey)
	{
		CriteriaWrap cw = new CriteriaWrap(GatewayInfo.class.getName());
		cw.add(ExpWrap.eq("qrcodekey", qrcodekey));
		return cw.uniqueResult();
	}
	
	public GatewayInfo querybydeviceid(String deviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(GatewayInfo.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		return cw.uniqueResult();
	}
	
}

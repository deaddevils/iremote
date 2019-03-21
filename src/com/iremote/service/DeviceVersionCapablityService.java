package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.DeviceVersionCapablity;
import com.iremote.domain.DeviceVersionInfo;

public class DeviceVersionCapablityService
{
	public List<DeviceVersionCapablity> queryByVersion(String productor , String model , String version)
	{
		DeviceVersionInfoService svr = new DeviceVersionInfoService();
		DeviceVersionInfo dvi = svr.query(productor, model, version);
		
		if ( dvi == null )
			return null ;
		
		CriteriaWrap cw = new CriteriaWrap(DeviceVersionCapablity.class.getName());
		cw.add(ExpWrap.eq("deviceversioninfoid", dvi.getDeviceversioninfoid()));
		
		return cw.list();
	}

}

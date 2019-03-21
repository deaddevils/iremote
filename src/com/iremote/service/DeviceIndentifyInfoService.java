package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.DeviceIndentifyInfo;

public class DeviceIndentifyInfoService extends BaseService<DeviceIndentifyInfo>
{

	public DeviceIndentifyInfo querybyQrcode(String qrcodeid)
	{
		CriteriaWrap cw = new CriteriaWrap(DeviceIndentifyInfo.class.getName());
		cw.add(ExpWrap.eq("qrcodeid", qrcodeid));
		return cw.uniqueResult();
	}
	
	public List<DeviceIndentifyInfo> querybyZwavedeviceid(int zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(DeviceIndentifyInfo.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		return cw.list();
	}
}

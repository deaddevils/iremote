package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.Camera;
import com.iremote.domain.DeviceCapability;
import com.iremote.domain.ZWaveDevice;

public class DeviceCapabilityService extends BaseService<DeviceCapability> {

	public DeviceCapability query(ZWaveDevice zwaveDevice,int capabilitycode)
	{
		CriteriaWrap cw = new CriteriaWrap(DeviceCapability.class.getName());
		cw.add(ExpWrap.eq("zwavedevice.zwavedeviceid", zwaveDevice.getZwavedeviceid()));
		cw.add(ExpWrap.eq("capabilitycode",capabilitycode));
		return cw.uniqueResult();
	}

	public DeviceCapability queryByCamera(Camera camera,int capabilitycode)
	{
		CriteriaWrap cw = new CriteriaWrap(DeviceCapability.class.getName());
		cw.add(ExpWrap.eq("camera.cameraid", camera.getCameraid()));
		cw.add(ExpWrap.eq("capabilitycode",capabilitycode));
		return cw.uniqueResult();
	}

	public DeviceCapability query(int zwavedeviceid,int capabilitycode)
	{
		CriteriaWrap cw = new CriteriaWrap(DeviceCapability.class.getName());
		cw.add(ExpWrap.eq("zwavedevice.zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("capabilitycode",capabilitycode));
		return cw.uniqueResult();
	}
}

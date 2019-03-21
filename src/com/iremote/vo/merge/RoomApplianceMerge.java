package com.iremote.vo.merge;

import com.iremote.domain.RoomAppliance;

public class RoomApplianceMerge implements IMerge<RoomAppliance> {

	@Override
	public void merge(RoomAppliance desc, RoomAppliance src) 
	{
		if ( desc == null || src == null )
			return ;
		desc.setDeviceid(src.getDeviceid());
		desc.setDevicetype(src.getDevicetype());
		desc.setMajortype(src.getMajortype());
		desc.setNuid(src.getNuid());
		desc.setApplianceid(src.getApplianceid());
		desc.setName(src.getName());
	}

}

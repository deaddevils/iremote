package com.iremote.vo.merge;

import java.util.Comparator;

import com.iremote.common.Utils;
import com.iremote.domain.RoomAppliance;

public class RoomApplianceComparator implements Comparator<RoomAppliance>
{
	@Override
	public int compare(RoomAppliance o1, RoomAppliance o2) 
	{
		int r = Utils.compareString(o1.getDeviceid(), o2.getDeviceid()) ;
		if ( r != 0 )
			return r ;
		r = Utils.compareString(o1.getApplianceid() , o2.getApplianceid());
		if ( r != 0 )
			return r ;
		
		return o1.getNuid() - o2.getNuid();
	}

}

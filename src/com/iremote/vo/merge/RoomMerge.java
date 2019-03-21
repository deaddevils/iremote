package com.iremote.vo.merge;

import com.iremote.common.Utils;
import com.iremote.domain.Room;
import com.iremote.domain.RoomAppliance;

public class RoomMerge implements IMerge<Room> {

	@Override
	public void merge(Room desc, Room src) 
	{
		if ( desc == null || src == null )
			return ;
		desc.setName(src.getName());
		desc.setPhoneuserid(src.getPhoneuserid());
		desc.setPhonenumber(src.getPhonenumber());
		
		Utils.merge(desc.getAppliancelist(), src.getAppliancelist(), new RoomApplianceComparator(), new RoomApplianceMerge());
		
		for ( RoomAppliance ra : desc.getAppliancelist())
			ra.setRoom(desc);
	}

}

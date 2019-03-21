package com.iremote.vo.merge;

import java.util.Comparator;

import com.iremote.domain.Room;

public class RoomComparator implements Comparator<Room>{

	@Override
	public int compare(Room o1, Room o2) 
	{
		if ( o1.getRoomdbid() != 0 && o2.getRoomdbid() != 0 )
			return o1.getRoomdbid() - o2.getRoomdbid();
		else if ( o1.getRoomid() != null && o2.getRoomid() != null )
			return o1.getRoomid().compareTo(o2.getRoomid());
		return -1 ;
	}

}

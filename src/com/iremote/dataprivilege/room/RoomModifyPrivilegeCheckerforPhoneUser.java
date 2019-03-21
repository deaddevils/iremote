package com.iremote.dataprivilege.room;

import com.iremote.domain.PhoneUser;
import com.iremote.domain.Room;
import com.iremote.service.RoomService;

public class RoomModifyPrivilegeCheckerforPhoneUser extends RoomModifyPrivilegeChecker<PhoneUser>
{
	@Override
	public boolean checkprivilege()
	{
		if ( this.roomdbid == null )
			return true; //action will return target not exists;
		
		RoomService rs = new RoomService();
		Room r = rs.query(roomdbid);
		
		if ( r == null )
			return true;
		
		if ( r.getPhoneuserid() == user.getPhoneuserid())
			return true ;
		
		return false;
	}

}

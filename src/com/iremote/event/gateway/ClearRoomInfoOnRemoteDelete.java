package com.iremote.event.gateway;

import java.util.List;

import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteEvent;
import com.iremote.domain.Room;
import com.iremote.service.RoomApplianceService;
import com.iremote.service.RoomService;

public class ClearRoomInfoOnRemoteDelete extends RemoteEvent implements ITextMessageProcessor{

	@Override
	public void run() 
	{
		RoomApplianceService ras = new RoomApplianceService();
		ras.delete(super.getDeviceid(), null);
		
		RoomService rs = new RoomService();
		List<Room> lst = rs.querybyphoneuserid(super.getPhoneuserid());
		
		for ( Room r : lst )
		{
			if ( r.getAppliancelist() == null || r.getAppliancelist().size() == 0 )  //delete empty rooms.
				rs.delete(r);
		}
	}

	@Override
	public String getTaskKey() {
		return super.getDeviceid();
	}

}

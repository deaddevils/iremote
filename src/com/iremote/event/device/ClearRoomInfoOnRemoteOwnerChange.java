package com.iremote.event.device;

import java.util.List;

import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.RemoteOwnerChangeEvent;
import com.iremote.domain.Room;
import com.iremote.service.RoomApplianceService;
import com.iremote.service.RoomService;

public class ClearRoomInfoOnRemoteOwnerChange extends RemoteOwnerChangeEvent implements ITextMessageProcessor
{

	@Override
	public void run()
	{
		RoomApplianceService ras = new RoomApplianceService();
		ras.delete(super.getDeviceid(), null);
		
		RoomService rs = new RoomService();
		List<Room> lst = rs.querybyphoneuserid(super.getOldownerid());
		
		for ( Room r : lst )
		{
			if ( r.getAppliancelist() == null || r.getAppliancelist().size() == 0 )  //delete empty rooms.
				rs.delete(r);
		}
		
	}

	@Override
	public String getTaskKey()
	{
		return getDeviceid();
	}

}

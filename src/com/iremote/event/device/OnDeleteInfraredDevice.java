package com.iremote.event.device;

import java.util.List;

import com.iremote.common.jms.ITextMessageProcessor;
import com.iremote.common.jms.vo.InfraredDeviceEvent;
import com.iremote.domain.Command;
import com.iremote.domain.UserShareDevice;
import com.iremote.service.CommandService;
import com.iremote.service.RoomApplianceService;
import com.iremote.service.SceneService;
import com.iremote.service.UserShareDeviceService;

public class OnDeleteInfraredDevice extends InfraredDeviceEvent implements ITextMessageProcessor{
	
	@Override
	public void run() 
	{
//		clearRoomAppliance();
//		clearSceneCommand();
//		clearUserShare();
	}
	
//	private void clearRoomAppliance()
//	{
//		RoomApplianceService ras = new RoomApplianceService();
//		ras.delete(getDeviceid(), getApplianceid());
//	}
//	
//	private void clearUserShare()
//	{
//		UserShareDeviceService usds = new UserShareDeviceService();
//		List<UserShareDevice> userShareDevices = usds.queryByInfrareddeviceid(super.getInfrareddeviceid());
//		if(userShareDevices != null)
//			for(UserShareDevice usd : userShareDevices)
//					usds.delete(usd);
//	}
//	
//	private void clearSceneCommand()
//	{
//		SceneService ss = new SceneService();
//		CommandService cs = new CommandService();
//		List<Command> clst = cs.querybyInfrareddeviceid(super.getInfrareddeviceid());
//		for ( Command c : clst )
//		{
//			c.getScene().getCommandlist().remove(c);
//			if ( c.getScene().getCommandlist().size() == 0 )
//				ss.delete(c.getScene());
//		}
//	}


	@Override
	public String getTaskKey() {
		return getDeviceid();
	}

}

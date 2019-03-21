package com.iremote.device;

import java.util.List;

import com.iremote.action.helper.TimerHelper;
import com.iremote.domain.*;
import com.iremote.service.*;

public class ClearInfraredDevice
{
	private InfraredDevice infrareddevice ;
	
	public ClearInfraredDevice(InfraredDevice infrareddevice)
	{
		super();
		this.infrareddevice = infrareddevice;
	}

	public void clear() 
	{
		clearRoomAppliance();
		clearRoomDevice();
		clearSceneCommand();
		clearUserShare();
		clearDeviceShare();
	}

	public void clearRoomDevice() {
		RoomDeviceService rds = new RoomDeviceService();
		List<RoomDevice> roomDevices = rds.querybydeviceid(infrareddevice.getInfrareddeviceid());
		rds.deleteAll(roomDevices);
	}

	public void clearRoomAppliance()
	{
		RoomApplianceService ras = new RoomApplianceService();
		List<RoomAppliance> lst = ras.query(infrareddevice.getDeviceid(), infrareddevice.getApplianceid());
		
		if ( lst != null )
		{
			for ( RoomAppliance ra : lst )
				ras.delete(ra);
		}
	}
	
	public void clearUserShare()
	{
		UserShareService uss = new UserShareService();
		UserShareDeviceService usds = new UserShareDeviceService();
		List<UserShareDevice> userShareDevices = usds.queryByInfrareddeviceid(infrareddevice.getInfrareddeviceid());
		if(userShareDevices != null)
			for(UserShareDevice usd : userShareDevices)
			{
				usd.getUserShare().getUserShareDevices().remove(usd);
				if ( usd.getUserShare().getUserShareDevices().size() == 0 )
					uss.delete(usd.getUserShare());
			}
	}
	
	public void clearSceneCommand()
	{
		TimerHelper.cancelTimer(this.infrareddevice.getTimer());
		this.infrareddevice.getTimer().clear();
		
		SceneService ss = new SceneService();
		CommandService cs = new CommandService();
		List<Command> clst = cs.querybyInfrareddeviceid(infrareddevice.getInfrareddeviceid());
		for ( Command c : clst )
		{
			c.getScene().getCommandlist().remove(c);
//			if ( c.getAssociationscene() != null && c.getAssociationscene().getCommandlist() != null)
//				c.getAssociationscene().getCommandlist().remove(c);
			if ( c.getScene().getCommandlist().size() == 0 )
				ss.delete(c.getScene());
		}
	}
	
	public void clearDeviceShare()
	{
		ZWaveDeviceShareService zdss = new ZWaveDeviceShareService();
		
		List<ZWaveDeviceShare> lst = zdss.querybyInfrareddeviceid(infrareddevice.getInfrareddeviceid());
		zdss.delete(lst);
	}
}

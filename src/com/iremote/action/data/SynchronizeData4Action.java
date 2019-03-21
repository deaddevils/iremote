package com.iremote.action.data;

import com.iremote.domain.InfraredDevice;
import com.iremote.domain.ZWaveDevice;
import com.iremote.vo.Appliance;

public class SynchronizeData4Action extends SynchronizeData3Action
{
	@Override
	protected void setZWaveDeviceData(ZWaveDevice z , Appliance a , boolean newdevice )
	{
		if ( newdevice == true )
			super.setZWaveDeviceData(z, a, newdevice);
		else 
			;// Do nothing 
	}
	
	@Override
	protected void setDeviceTimer(InfraredDevice z , Appliance a)
	{
		//Do nothing
	}
}

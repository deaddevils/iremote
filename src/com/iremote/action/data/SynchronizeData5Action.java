package com.iremote.action.data;

import java.util.List;
import com.iremote.domain.Remote;
import com.iremote.vo.Appliance;

public class SynchronizeData5Action extends SynchronizeData4Action
{

	@Override
	protected void updateZWaveAppliance(Remote remote , List<Appliance> appliancelist)
	{
		//do nothing
	}
	
	@Override
	protected void updateInfraredAppliance(Remote remote , List<Appliance> appliancelist)
	{
		//do nothing
	}
}

package com.iremote.thirdpart.cobbe.event;

import java.io.IOException;

public class ReportafterSleepSimulite implements Runnable
{
	private WifiLockSimulate sim ;

	public ReportafterSleepSimulite(WifiLockSimulate sim)
	{
		super();
		this.sim = sim;
	}

	@Override
	public void run()
	{
		try
		{
			sim.reportafterSleepSimulite();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	
}

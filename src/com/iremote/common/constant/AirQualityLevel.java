package com.iremote.common.constant;

public enum AirQualityLevel
{
	good(1),
	moderate(2),
	unhealthy_for_sensitive_groups(3), 
	unhealthy(4), 
	veryunhealthy(5),
	hazardous(6);
	
	private int level;

	private AirQualityLevel(int level)
	{
		this.level = level;
	}

	public int getLevel()
	{
		return level;
	}
	
	
}

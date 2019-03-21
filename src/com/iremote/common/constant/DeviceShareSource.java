package com.iremote.common.constant;

public enum DeviceShareSource
{
	thirdpart(0),
	phoneuser(1),
	phoneusertemp(2);
	
	private DeviceShareSource(int source)
	{
		this.source = source;
	}

	private int source;

	public int getSource()
	{
		return source;
	}

	
}

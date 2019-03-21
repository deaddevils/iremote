package com.iremote.common.constant;

public enum DahuaCameraReportType
{
	call("CallNoAnswered"),
	alarm("ProfileAlarmTransmit"),
	move("move");
	
	private String type;

	private DahuaCameraReportType(String type)
	{
		this.type = type;
	}

	public String getType()
	{
		return type;
	}
	
	
}

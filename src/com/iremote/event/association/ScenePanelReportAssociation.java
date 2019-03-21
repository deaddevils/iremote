package com.iremote.event.association;

public class ScenePanelReportAssociation extends DeviceReportAssociation
{
	@Override
	protected void initAssocationStatus()
	{
		super.associationstatus = 0 ;
		super.associationoldstatus = null ;
	}
	
	@Override
	protected int[] mapstatus(int status)
	{
		return new int[]{0,255};
	}
}

package com.iremote.common.jms.vo;

import java.util.Date;

public class RemoteOnlineEvent extends RemoteEvent
{
	private boolean haslogout;
	private boolean isNbiotDevice = false;
	private byte[] report ;

	public RemoteOnlineEvent()
	{
		super();
	}

	public RemoteOnlineEvent(String deviceid, Date eventtime, boolean haslogout, long taskid)
	{
		super(deviceid, eventtime, taskid);
		this.haslogout = haslogout;
	}

	public RemoteOnlineEvent(String deviceid, Date eventtime, boolean isNbiotDevice, boolean haslogout, byte[] report , long taskid)
	{
		super(deviceid, eventtime, taskid);
		this.haslogout = haslogout;
		this.isNbiotDevice = isNbiotDevice;
		this.report = report ;
	}

	public byte[] getReport() {
		return report;
	}

	public void setReport(byte[] report) {
		this.report = report;
	}

	public boolean isHaslogout()
	{
		return haslogout;
	}

	public void setHaslogout(boolean haslogout)
	{
		this.haslogout = haslogout;
	}

	public boolean isNbiotDevice() {
		return isNbiotDevice;
	}

	public void setNbiotDevice(boolean nbiotDevice) {
		isNbiotDevice = nbiotDevice;
	}
}

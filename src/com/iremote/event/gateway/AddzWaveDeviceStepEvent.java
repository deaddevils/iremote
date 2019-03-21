package com.iremote.event.gateway;

import java.util.Date;

import com.iremote.common.jms.vo.RemoteEvent;

public class AddzWaveDeviceStepEvent extends RemoteEvent 
{
	private int status ;

	public AddzWaveDeviceStepEvent() {
		super();
	}

	public AddzWaveDeviceStepEvent(String deviceid, Date eventtime, int status , long taskid) {
		super(deviceid, eventtime, taskid);
		this.status = status ;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}

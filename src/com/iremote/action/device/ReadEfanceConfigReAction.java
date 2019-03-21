package com.iremote.action.device;

import com.iremote.common.ErrorCodeDefine;
import com.opensymphony.xwork2.Action;

public class ReadEfanceConfigReAction{
	private int channel;
	private int resultCode = ErrorCodeDefine.SUCCESS;
	private int zwavedeviceid;

	public String execute(){
		return Action.SUCCESS;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public int getResultCode() {
		return resultCode;
	}

	public int getZwavedeviceid() {
		return zwavedeviceid;
	}

	public void setZwavedeviceid(int zwavedeviceid) {
		this.zwavedeviceid = zwavedeviceid;
	}

	
}

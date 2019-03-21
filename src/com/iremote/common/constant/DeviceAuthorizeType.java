package com.iremote.common.constant;

public enum DeviceAuthorizeType 
{
	valid(0),
	validonce(1),
	notvalid(2);
	
	private int validtype;

	private DeviceAuthorizeType(int validtype) {
		this.validtype = validtype;
	}

	public int getValidtype() {
		return validtype;
	}
	
}

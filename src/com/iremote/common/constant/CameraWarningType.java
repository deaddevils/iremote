package com.iremote.common.constant;

public enum CameraWarningType 
{
	move(501);
	
	private int warningtype;

	private CameraWarningType(int warningtype) {
		this.warningtype = warningtype;
	}

	public int getWarningtype() {
		return warningtype;
	}
	
}

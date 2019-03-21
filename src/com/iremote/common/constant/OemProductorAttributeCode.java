package com.iremote.common.constant;

public enum OemProductorAttributeCode 
{
	hasArmFunction("hasArmFunction"),
	hasAbroadSmsPermission("abrodsms");
	
	private String code ;

	private OemProductorAttributeCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}

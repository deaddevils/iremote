package com.iremote.dataprivilege.interceptorchecker;

public enum DataPrivilegeUserType 
{
	phoneuser("phoneuser"),
	thirdpart("thirdpart");
	
	private String type ;

	private DataPrivilegeUserType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}

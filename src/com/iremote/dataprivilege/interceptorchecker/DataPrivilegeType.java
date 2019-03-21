package com.iremote.dataprivilege.interceptorchecker;

public enum DataPrivilegeType
{
	READ(1) , 
	OPERATION(2),
	MODIFY(3) ,
	ATTRIBUTE(4);
	
	private int privilgelevel ;

	private DataPrivilegeType(int privilgelevel)
	{
		this.privilgelevel = privilgelevel;
	}

	public int getPrivilegelevel()
	{
		return privilgelevel;
	}
	
	
}

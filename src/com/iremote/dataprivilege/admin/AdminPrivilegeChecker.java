package com.iremote.dataprivilege.admin;

import com.iremote.dataprivilege.attribute.AttributeCheckerforPhoneuser;

public class AdminPrivilegeChecker extends AttributeCheckerforPhoneuser
{
	public AdminPrivilegeChecker() 
	{
		super();
		super.code = "admin";
		super.value = "true";
	}

}

package com.iremote.dataprivilege.usershare;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.iremote.dataprivilege.interceptorchecker.IURLDataPrivilegeChecker;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.UserShare;
import com.iremote.service.UserShareService;

public class UserShareModifyPrivilegeChecker implements IURLDataPrivilegeChecker<PhoneUser>
{

	private PhoneUser phoneuser ;
	private Integer shareid ;
	
	@Override
	public void setUser(PhoneUser user)
	{
		this.phoneuser = user ;
	}

	@Override
	public void setParameter(String parameter)
	{
		if ( !StringUtils.isBlank(parameter) )
			this.shareid = Integer.valueOf(parameter);
	}

	@Override
	public void SetParameters(Map<String, String> parameters)
	{
		
	}

	@Override
	public boolean checkprivilege()
	{
		if ( this.shareid == null )
			return true ;
		
		UserShareService uss = new UserShareService();
		UserShare us = uss.query(this.shareid);
		
		if ( us == null )
			return true; 
		
		if ( us.getShareuserid() == this.phoneuser.getPhoneuserid() 
				|| us.getTouserid() == this.phoneuser.getPhoneuserid())
			return true ;
		
		return false;
	}


}

package com.iremote.interceptor;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeCheckerStore;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeUserType;
import com.iremote.dataprivilege.interceptorchecker.IURLDataPrivilegeChecker;
import com.iremote.domain.PhoneUser;
import com.opensymphony.xwork2.ActionContext;

public class DataPrivilegeInterceptor extends DataPrivilegeBaseInterceptor<PhoneUser>
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected PhoneUser queryUser() 
	{
		return (PhoneUser)ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_USER);
	}

	@Override
	protected IURLDataPrivilegeChecker<PhoneUser> queryChecker(DataPrivilegeType privlegetype, String domain) 
	{
		return DataPrivilegeCheckerStore.getInstance().getPhoneUserPrivilgeChecker(privlegetype, domain);
	}

	@Override
	protected boolean needcheck(DataPrivilege dp) 
	{
		if ( dp.usertype() == null || dp.usertype().length == 0 )
			return true;
		for ( String str : dp.usertype())
			if ( DataPrivilegeUserType.phoneuser.getType().equals(str))
				return true;
		return false;
	}
}

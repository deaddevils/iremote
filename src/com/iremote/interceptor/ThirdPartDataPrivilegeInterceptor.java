package com.iremote.interceptor;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilege;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeCheckerStoreforThirdpart;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeType;
import com.iremote.dataprivilege.interceptorchecker.DataPrivilegeUserType;
import com.iremote.dataprivilege.interceptorchecker.IURLDataPrivilegeChecker;
import com.iremote.domain.ThirdPart;
import com.opensymphony.xwork2.ActionContext;

public class ThirdPartDataPrivilegeInterceptor extends DataPrivilegeBaseInterceptor<ThirdPart>
{
	private static final long serialVersionUID = 1L;

	@Override
	protected ThirdPart queryUser() 
	{
		return (ThirdPart)ActionContext.getContext().getSession().get(IRemoteConstantDefine.SESSION_THIRDPART);
	}

	@Override
	protected IURLDataPrivilegeChecker<ThirdPart> queryChecker(DataPrivilegeType privlegetype, String domain) 
	{
		return DataPrivilegeCheckerStoreforThirdpart.getInstance().getPhoneUserPrivilgeChecker(privlegetype, domain);
	}

	@Override
	protected boolean needcheck(DataPrivilege dp) 
	{
		if ( dp.usertype() == null || dp.usertype().length == 0 )
			return true;
		for ( String str : dp.usertype())
			if ( DataPrivilegeUserType.thirdpart.getType().equals(str))
				return true;
		return false;
	}
}

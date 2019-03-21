package com.iremote.action.helper;

import java.util.Arrays;
import java.util.List;

import com.iremote.asiainfo.helper.AsiainfoHttpHelper;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.service.IremotepasswordService;

public class ShareHelper {

	public static void shareDeviceBinding(PhoneUser srcuser , PhoneUser touser)
	{
		if ( srcuser == null || touser == null )
			return ;
		
		IremotepasswordService svr = new IremotepasswordService();
		List<Remote> lst  = svr.querybyPhoneUserid(Arrays.asList(new Integer[]{srcuser.getPhoneuserid()}));
		if ( lst == null || lst.size() == 0 )
			return ;
		for ( Remote r  : lst )
		{
			AsiainfoHttpHelper.deviceBinding(touser, r);
		}
		
	}
	
	public static void shareDeviceUnbinding(PhoneUser srcuser , PhoneUser touser)
	{
		if ( srcuser == null || touser == null )
			return ;
		
		IremotepasswordService svr = new IremotepasswordService();
		List<Remote> lst  = svr.querybyPhoneUserid(Arrays.asList(new Integer[]{srcuser.getPhoneuserid()}));
		if ( lst == null || lst.size() == 0 )
			return ;
		for ( Remote r  : lst )
		{
			AsiainfoHttpHelper.deviceUnbinding(touser, r);
		}
		
	}
}

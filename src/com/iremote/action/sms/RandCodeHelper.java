package com.iremote.action.sms;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.Randcode;
import com.iremote.service.RandcodeService;

public class RandCodeHelper {

	public static String createRandCode()
	{
		SecureRandom random = new SecureRandom();
		int r = random.nextInt(999999);
		
		return String.format("%06d" , r);
	}

	public static int checkRandcode(String countrycode ,String phonenumber , int type , String randcode , int platform)
	{
		RandcodeService svr = new RandcodeService();
		
		List<Randcode> lst = svr.querybyphonenumber(countrycode , phonenumber , type , platform);
		if ( lst == null || lst.size() == 0 )
			return ErrorCodeDefine.RAND_CODE_ERROR ;
		for ( Randcode rc : lst )
		{
			Date now = new Date();
			if ( rc.getRandcode().equals(randcode) )
			{
				if ( now.after(rc.getExpiretime()))
					return ErrorCodeDefine.RAND_CODE_TIME_OUT;
				return ErrorCodeDefine.SUCCESS;
			}
		}
		
		return ErrorCodeDefine.RAND_CODE_ERROR ;
	}

	public static int checkRandcode(String mail ,int type ,String randcode , int platform)
	{
		RandcodeService svr = new RandcodeService();

		List<Randcode> lst = svr.querybymail(mail , type , platform);
		if ( lst == null || lst.size() == 0 )
			return ErrorCodeDefine.RAND_CODE_ERROR ;
		for ( Randcode rc : lst )
		{
			Date now = new Date();
			if ( rc.getRandcode().equals(randcode) )
			{
				if ( now.after(rc.getExpiretime()))
					return ErrorCodeDefine.RAND_CODE_TIME_OUT;
				return ErrorCodeDefine.SUCCESS;
			}
		}

		return ErrorCodeDefine.RAND_CODE_ERROR ;
	}
}

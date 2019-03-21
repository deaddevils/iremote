package com.iremote.action.helper;

import java.util.List;

import com.iremote.domain.PhoneUser;
@SuppressWarnings("unused")
public class PhoneUserHelperTest {

	public static void main(String arg[])
	{
		PhoneUser phoneuser = new PhoneUser();
		//phoneuser.setPhoneuserid(1);
		List<PhoneUser> su = PhoneUserHelper.queryAuthorizedUser(phoneuser.getPhoneuserid());
	}
}

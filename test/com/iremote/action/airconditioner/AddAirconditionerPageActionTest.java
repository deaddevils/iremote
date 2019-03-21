package com.iremote.action.airconditioner;

import com.iremote.domain.PhoneUser;
import com.iremote.service.PhoneUserService;
import com.iremote.test.db.Db;

public class AddAirconditionerPageActionTest {
	public static void main(String[] args) {
		Db.init();
		PhoneUser pu = new PhoneUserService().query(149);
		AddAirconditionerPageAction action = new AddAirconditionerPageAction();
		action.setPhoneuser(pu);
		action.execute();
	}
}

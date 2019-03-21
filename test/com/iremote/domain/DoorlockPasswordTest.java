package com.iremote.domain;

import java.util.Random;

import com.iremote.test.db.Db;
import com.iremote.thirdpart.cobbe.event.DoorLockPasswordHelper;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;

public class DoorlockPasswordTest
{

	public static void main(String arg[])
	{
		Db.init();
		DoorlockPasswordService dpsvr = new DoorlockPasswordService();
		Random r = new Random(System.currentTimeMillis());

		DoorlockPassword doorlockpassword = DoorLockPasswordHelper.createLockTempPassword(1, 0xf3 + 0, String.format("%06d", r.nextInt(1000000)));

		dpsvr.save(doorlockpassword);

		Db.commit();
	}
}

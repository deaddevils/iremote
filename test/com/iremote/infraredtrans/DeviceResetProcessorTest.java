package com.iremote.infraredtrans;

import java.util.List;

import com.iremote.test.db.Db;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import com.iremote.thirdpart.wcj.service.DoorlockPasswordService;

public class DeviceResetProcessorTest
{
	public static void main(String arg[])
	{
		Db.init();

		DoorlockPasswordService dpsvr = new DoorlockPasswordService();
		List<DoorlockPassword> elst = dpsvr.queryCobbePassword(1136);
		
		long vt = System.currentTimeMillis() - 1000;
		for ( DoorlockPassword dp : elst )
		{
			if ( dp.getValidthrough().getTime() > vt )
				dp.getValidthrough().setTime(vt);
		}
		
		Db.commit();
	}
}

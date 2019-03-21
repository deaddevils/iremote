package com.iremote.action.device.doorlock;

import com.iremote.domain.DoorlockUser;

public class AddDoorlockCardActionTest
{
	public static void main(String arg[])
	{
		DoorlockUser du = new DoorlockUser();
		du.setValidfrom("2017-04-01 00:00:00");
		
		System.out.println(du.getValidfrom());
	}
}

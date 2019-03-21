package com.iremote.infraredtrans.zwavecommand.doorlock;

import com.iremote.domain.DoorlockUser;
import com.iremote.service.DoorlockUserService;

public class CreateDoorlockUserinneedTask implements Runnable 
{
	private int zwavedeviceid ;
	private int usertype ;
	private int usercode ;
	private String name;

	public CreateDoorlockUserinneedTask(int zwavedeviceid, int usertype, int usercode , String name) {
		super();
		this.zwavedeviceid = zwavedeviceid;
		this.usertype = usertype;
		this.usercode = usercode;
		this.name = name ;
	}

	@Override
	public void run() 
	{
		DoorlockUserService dus = new DoorlockUserService();
		DoorlockUser du = dus.query(zwavedeviceid, usertype, usercode);
		
		if ( du != null)
			return ;
		
		
		du = new DoorlockUser();
		du.setUsercode(usercode);
		du.setUsertype(usertype);
		du.setZwavedeviceid(zwavedeviceid);
		
		du.setUsername(name);
		
		dus.save(du);

	}

}

package com.iremote.asiainfo.task.devicemanage;

import java.util.List;

import com.iremote.asiainfo.helper.AsiainfoHttpHelper;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.PhoneUser;

public class BindingInfraredDeviceTaskProcessor implements Runnable {

	private PhoneUser user;
	private InfraredDevice device ;
	@SuppressWarnings("unused")
	private List<PhoneUser> shareuses;

	public BindingInfraredDeviceTaskProcessor(PhoneUser user, InfraredDevice device,
			List<PhoneUser> shareuses) {
		super();
		this.user = user;
		this.device = device;
		this.shareuses = shareuses;
	}

	@Override
	public void run() 
	{
		AsiainfoHttpHelper.deviceBinding(user, device);
//		for ( PhoneUser u : shareuses )
//			AsiainfoHttpHelper.deviceBinding(u, device);;
	}

}

package com.iremote.asiainfo.task.devicemanage;

import java.util.List;

import com.iremote.asiainfo.helper.AsiainfoHttpHelper;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.ZWaveDevice;

public class AddZwaveDeviceTaskProcessor implements Runnable {

	private PhoneUser user;
	private ZWaveDevice device ;
	@SuppressWarnings("unused")
	private List<PhoneUser> shareuses;

	public AddZwaveDeviceTaskProcessor(PhoneUser user, ZWaveDevice device,
			List<PhoneUser> shareuses) {
		super();
		this.user = user;
		this.device = device;
		this.shareuses = shareuses;
	}

	@Override
	public void run() 
	{
		AsiainfoHttpHelper.reportDeviceinfo(device);
		AsiainfoHttpHelper.deviceBinding(user, device);
//		for ( PhoneUser u : shareuses )
//			AsiainfoHttpHelper.deviceBinding(u, device);;
	}

}

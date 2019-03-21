package com.iremote.asiainfo.task.devicemanage;

import java.util.List;

import com.iremote.asiainfo.helper.AsiainfoHttpHelper;
import com.iremote.domain.InfraredDevice;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;
import com.iremote.domain.ZWaveDevice;
import com.iremote.service.InfraredDeviceService;
import com.iremote.service.UserShareService;
import com.iremote.service.ZWaveDeviceService;

public class UnbindRemoteTaskProcessor implements Runnable {

	private Remote remote;
	private PhoneUser user ;
	
	
	public UnbindRemoteTaskProcessor(Remote remote, PhoneUser user) {
		super();
		this.remote = remote;
		this.user = user;
	}

	@Override
	public void run() 
	{
		ZWaveDeviceService svr = new ZWaveDeviceService();
		List<ZWaveDevice> lst = svr.querybydeviceid(remote.getDeviceid());

		InfraredDeviceService ids = new InfraredDeviceService();
		List<InfraredDevice> idl = ids.querybydeviceid(remote.getDeviceid());
		
		UserShareService uss = new UserShareService();
		uss.querybyShareUser(user.getPhoneuserid(), 0);
		
		for ( ZWaveDevice d : lst )
			AsiainfoHttpHelper.deviceUnbinding(user, d);

		for ( InfraredDevice id : idl )
			AsiainfoHttpHelper.deviceUnbinding(user, id);
		
//		List<PhoneUser> su = PhoneUserHelper.queryAuthorizedUser(user.getPhoneuserid());
//		
//		for ( PhoneUser pu : su )
//		{
//			for ( ZWaveDevice d : lst )
//				AsiainfoHttpHelper.deviceUnbinding(pu, d);
//
//			for ( InfraredDevice id : idl )
//				AsiainfoHttpHelper.deviceUnbinding(pu, id);
//		}
		
		AsiainfoHttpHelper.deviceUnbinding(user, remote);
	}


}

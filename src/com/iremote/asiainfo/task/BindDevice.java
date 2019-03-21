package com.iremote.asiainfo.task;

import com.iremote.asiainfo.helper.AsiainfoHttpHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.PhoneUser;
import com.iremote.domain.Remote;

@Deprecated
public class BindDevice implements Runnable {

	private PhoneUser phoneuser;
	private Remote remote;

	public BindDevice(PhoneUser phoneuser, Remote remote) {
		super();
		this.phoneuser = phoneuser;
		this.remote = remote;
	}

	@Override
	public void run() 
	{
		if ( remote.getPlatform() != IRemoteConstantDefine.PLATFORM_ASININFO )
			return ;
		AsiainfoHttpHelper.deviceBinding(phoneuser, remote);		
	}

}

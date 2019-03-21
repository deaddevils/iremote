package com.iremote.task.devicecommand;

import com.iremote.infraredtrans.SynchronizeRequestHelper;
import com.iremote.infraredtrans.tlv.CommandTlv;

@Deprecated
public class ExecuteMultiDeviceCommand implements Runnable {

	private String deviceid ;
	private CommandTlv[] ct ;

	public ExecuteMultiDeviceCommand(String deviceid, CommandTlv[] ct) {
		super();
		this.deviceid = deviceid;
		this.ct = ct;
	}

	@Override
	public void run() {
		if ( deviceid == null || deviceid.length() == 0 || ct == null || ct.length == 0 )
			return ;
		for ( int i = 0 ; i < ct.length ; i ++ )
			SynchronizeRequestHelper.asynchronizeRequest(deviceid, ct[i], 8);

	}

}

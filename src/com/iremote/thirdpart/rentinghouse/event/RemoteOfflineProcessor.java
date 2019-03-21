package com.iremote.thirdpart.rentinghouse.event;

import com.iremote.common.IRemoteConstantDefine;

public class RemoteOfflineProcessor extends RemoteProcessor {

	@Override
	protected String getType() {
		return IRemoteConstantDefine.WARNING_TYPE_REMOTE_OFFLINE;
	}

}

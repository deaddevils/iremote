package com.iremote.infraredtrans;

import com.iremote.common.asycresponse.IAsyncResponse;

@Deprecated
public class SynchronizeVo implements IAsyncResponse{

	private byte[] content;

	private synchronized void setContent(byte[] content) {
		this.content = content;
	}

	@Override
	public void onAckResponse(byte[] response) {
		
		setContent((byte[])response);
	}

	@Override
	public byte[] getAckResponse(int timeoutsecond) {
		return content;
	}

	
}

package com.iremote.common.asycresponse;

public interface IAsyncResponse 
{
	void onAckResponse(byte[] response);
	Object getAckResponse(int timeoutsecond);
}

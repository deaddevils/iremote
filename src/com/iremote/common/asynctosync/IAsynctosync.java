package com.iremote.common.asynctosync;

@Deprecated
public interface IAsynctosync 
{
	String getkey();
	long getTimeoutMilliseconds();
	void sendRequest() throws Exception;
	//void registReport();
}

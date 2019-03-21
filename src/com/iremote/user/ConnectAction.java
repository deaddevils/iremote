package com.iremote.user;

import com.opensymphony.xwork2.Action;

public class ConnectAction {

	private int resultCode = 0 ;
	private String protocol = "https";
	private String version = "1.0.0";
	private int versionRequest = 0 ;
	
	public String execute()
	{
		return Action.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getVersion() {
		return version;
	}

	public int getVersionRequest() {
		return versionRequest;
	}
	
	
}

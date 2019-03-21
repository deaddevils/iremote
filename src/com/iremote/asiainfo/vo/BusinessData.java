package com.iremote.asiainfo.vo;

public class BusinessData {

	private String msgSeqNo;
	private String systemId;
	private String accessToken;
	private int count;
	private Object message;
	private String token ;
	
	public String getMsgSeqNo() {
		return msgSeqNo;
	}
	public void setMsgSeqNo(String msgSeqNo) {
		this.msgSeqNo = msgSeqNo;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}

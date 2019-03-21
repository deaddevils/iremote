package com.iremote.action.amazon.smarthome;

public class AwsSmartHomeRequestScope 
{
	private String type;
	private String token;
	
	public AwsSmartHomeRequestScope(String type, String token) {
		super();
		this.type = type;
		this.token = token;
	}
	public AwsSmartHomeRequestScope() {
		super();
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "AwsSmartHomeRequestScope{" +
				"type='" + type + '\'' +
				", token='" + token + '\'' +
				'}';
	}
}

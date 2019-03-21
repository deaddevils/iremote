package com.iremote.action.amazon.smarthome;

public class AwsSmartHomeRequestEvent 
{
	private AwsSmartHomeRequestHeader header;
	private AwsSmartHomeRequestPayload payload = new AwsSmartHomeRequestPayload();
	private AwsSmartHomeRequestEndpoint endpoint ;

	public AwsSmartHomeRequestHeader getHeader() {
		return header;
	}
	public void setHeader(AwsSmartHomeRequestHeader header) {
		this.header = header;
	}
	public AwsSmartHomeRequestPayload getPayload() {
		return payload;
	}
	public void setPayload(AwsSmartHomeRequestPayload payload) {
		this.payload = payload;
	}
	public AwsSmartHomeRequestEndpoint getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(AwsSmartHomeRequestEndpoint endpoint) {
		this.endpoint = endpoint;
	}

	@Override
	public String toString() {
		return "AwsSmartHomeRequestEvent{" +
				"header=" + header +
				", payload=" + payload +
				", endpoint=" + endpoint +
				'}';
	}
}

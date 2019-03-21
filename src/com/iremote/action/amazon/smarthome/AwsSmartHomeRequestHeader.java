package com.iremote.action.amazon.smarthome;

public class AwsSmartHomeRequestHeader 
{
	private String namespace;
	private String name;
	private String messageId;
	private String correlationToken;
	private String payloadVersion = "3";
	
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getCorrelationToken() {
		return correlationToken;
	}
	public void setCorrelationToken(String correlationToken) {
		this.correlationToken = correlationToken;
	}
	public String getPayloadVersion() {
		return payloadVersion;
	}
	public void setPayloadVersion(String payloadVersion) {
		this.payloadVersion = payloadVersion;
	}

	@Override
	public String toString() {
		return "AwsSmartHomeRequestHeader{" +
				"namespace='" + namespace + '\'' +
				", name='" + name + '\'' +
				", messageId='" + messageId + '\'' +
				", correlationToken='" + correlationToken + '\'' +
				", payloadVersion='" + payloadVersion + '\'' +
				'}';
	}
}

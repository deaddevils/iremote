package com.iremote.action.amazon.smarthome;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AwsSmartHomeRequestPayload 
{
	private List<AwsSmartHomeRequestEndpoint> endpoints = new ArrayList<AwsSmartHomeRequestEndpoint>();
	private AwsSmartHomeRequestScope scope;
	private AwsSmartHomeRequestCause cause;
	private String timestamp;

	public AwsSmartHomeRequestCause getCause() {
		return cause;
	}

	public void setCause(AwsSmartHomeRequestCause cause) {
		this.cause = cause;
	}

	public List<AwsSmartHomeRequestEndpoint> getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(List<AwsSmartHomeRequestEndpoint> endpoints) {
		this.endpoints = endpoints;
	}

	public AwsSmartHomeRequestScope getScope() {
		return scope;
	}

	public void setScope(AwsSmartHomeRequestScope scope) {
		this.scope = scope;
	}

	public String getTimestamp() {
		Instant time = Instant.now();
		this.timestamp = time.toString();
		return this.timestamp;
	}

	@Override
	public String toString() {
		return "AwsSmartHomeRequestPayload{" +
				"endpoints=" + endpoints +
				", scope=" + scope +
				", cause=" + cause +
				'}';
	}
}

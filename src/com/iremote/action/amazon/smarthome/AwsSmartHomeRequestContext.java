package com.iremote.action.amazon.smarthome;

import java.util.ArrayList;
import java.util.List;

public class AwsSmartHomeRequestContext 
{
	private List<AwsSmartHomeRequestProperty> properties;

	public AwsSmartHomeRequestContext() {
		super();
	}

	public AwsSmartHomeRequestContext(List<AwsSmartHomeRequestProperty> properties) {
		super();
		this.properties = properties;
	}

	public List<AwsSmartHomeRequestProperty> getProperties() {
		if (properties == null) {
			properties = new ArrayList<>();
		}
		return properties;
	}

	public void setProperties(List<AwsSmartHomeRequestProperty> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "AwsSmartHomeRequestContext{" +
				"properties=" + properties +
				'}';
	}
}

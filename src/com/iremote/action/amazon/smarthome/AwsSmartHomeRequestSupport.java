package com.iremote.action.amazon.smarthome;

public class AwsSmartHomeRequestSupport 
{
	private String name;

	public AwsSmartHomeRequestSupport() {
		super();
	}

	public AwsSmartHomeRequestSupport(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "AwsSmartHomeRequestSupport{" +
				"name='" + name + '\'' +
				'}';
	}
}

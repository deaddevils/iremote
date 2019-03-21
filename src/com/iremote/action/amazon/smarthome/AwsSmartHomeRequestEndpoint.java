package com.iremote.action.amazon.smarthome;

import java.util.*;

public class AwsSmartHomeRequestEndpoint 
{
	private String endpointId;
	private String friendlyName;
	private String description;
	private String manufacturerName;
	private String[] displayCategories;
	private AwsSmartHomeRequestScope scope ;
	private Map<String , String> cookie = new HashMap<String , String>() ;
	private List<AwsSmartHomeRequestCapability> capabilities = new ArrayList<AwsSmartHomeRequestCapability>();
	
	public String getEndpointId() {
		return endpointId;
	}
	public void setEndpointId(String endpointId) {
		this.endpointId = endpointId;
	}
	public String getFriendlyName() {
		return friendlyName;
	}
	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getManufacturerName() {
		return manufacturerName;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	public String[] getDisplayCategories() {
		return displayCategories;
	}
	public void setDisplayCategories(String[] displayCategories) {
		this.displayCategories = displayCategories;
	}
	public Map<String, String> getCookie() {
		return cookie;
	}
	public void setCookie(Map<String, String> cookie) {
		this.cookie = cookie;
	}
	public List<AwsSmartHomeRequestCapability> getCapabilities() {
		return capabilities;
	}
	public void setCapabilities(List<AwsSmartHomeRequestCapability> capabilities) {
		this.capabilities = capabilities;
	}
	public AwsSmartHomeRequestScope getScope() {
		return scope;
	}
	public void setScope(AwsSmartHomeRequestScope scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "AwsSmartHomeRequestEndpoint{" +
				"endpointId='" + endpointId + '\'' +
				", friendlyName='" + friendlyName + '\'' +
				", description='" + description + '\'' +
				", manufacturerName='" + manufacturerName + '\'' +
				", displayCategories=" + Arrays.toString(displayCategories) +
				", scope=" + scope +
				", cookie=" + cookie +
				", capabilities=" + capabilities +
				'}';
	}
}

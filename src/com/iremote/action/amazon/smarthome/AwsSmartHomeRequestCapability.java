package com.iremote.action.amazon.smarthome;

public class AwsSmartHomeRequestCapability {
    private String type;
    private String awsinterface;
    private String version = "3";
    private AwsSmartHomeRequestProperty properties;
    private Boolean supportsDeactivation;
    private AwsSmartHomeRequestConfiguration configuration;

    public Boolean getSupportsDeactivation() {
        return supportsDeactivation;
    }

    public void setSupportsDeactivation(Boolean supportsDeactivation) {
        this.supportsDeactivation = supportsDeactivation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInterface() {
        return awsinterface;
    }

    public void setInterface(String awsinterface) {
        this.awsinterface = awsinterface;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public AwsSmartHomeRequestProperty getProperties() {
        return properties;
    }

    public void setProperties(AwsSmartHomeRequestProperty properties) {
        this.properties = properties;
    }

    public AwsSmartHomeRequestConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(AwsSmartHomeRequestConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String toString() {
        return "AwsSmartHomeRequestCapability{" +
                "type='" + type + '\'' +
                ", awsinterface='" + awsinterface + '\'' +
                ", version='" + version + '\'' +
                ", properties=" + properties +
                ", supportsDeactivation=" + supportsDeactivation +
                ", configuration=" + configuration +
                '}';
    }
}

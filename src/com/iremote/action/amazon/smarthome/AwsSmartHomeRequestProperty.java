package com.iremote.action.amazon.smarthome;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AwsSmartHomeRequestProperty {
    private List<AwsSmartHomeRequestSupport> supported = new ArrayList<AwsSmartHomeRequestSupport>();
    private String namespace;
    private String name;
    private Object value;
    private String timeOfSample;
    private Integer uncertaintyInMilliseconds;
    private Boolean proactivelyReported;
    private Boolean retrievable;

    public AwsSmartHomeRequestProperty() {
        super();
    }

    public AwsSmartHomeRequestProperty(List<AwsSmartHomeRequestSupport> supported) {
        super();
        this.supported = supported;
    }

    public AwsSmartHomeRequestProperty(List<AwsSmartHomeRequestSupport> supported, Boolean proactivelyReported) {
        this.supported = supported;
        this.proactivelyReported = proactivelyReported;
    }

    public AwsSmartHomeRequestProperty(List<AwsSmartHomeRequestSupport> supported, Boolean proactivelyReported, Boolean retrievable) {
        this.supported = supported;
        this.proactivelyReported = proactivelyReported;
        this.retrievable = retrievable;
    }

    public List<AwsSmartHomeRequestSupport> getSupported() {
        return supported;
    }

    public void setSupported(List<AwsSmartHomeRequestSupport> supported) {
        this.supported = supported;
    }

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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getTimeOfSample() {
        return timeOfSample;
    }

    public void setTimeOfSample() {
        Instant time = Instant.now();
        this.timeOfSample = time.toString();
    }

    public Boolean getProactivelyReported() {
        return proactivelyReported;
    }

    public void setProactivelyReported(Boolean proactivelyReported) {
        this.proactivelyReported = proactivelyReported;
    }

    public Boolean getRetrievable() {
        return retrievable;
    }

    public void setRetrievable(Boolean retrievable) {
        this.retrievable = retrievable;
    }

    public Integer getUncertaintyInMilliseconds() {
        return uncertaintyInMilliseconds;
    }

    public void setUncertaintyInMilliseconds(Integer uncertaintyInMilliseconds) {
        this.uncertaintyInMilliseconds = uncertaintyInMilliseconds;
    }

    @Override
    public String toString() {
        return "AwsSmartHomeRequestProperty{" +
                "supported=" + supported +
                ", namespace='" + namespace + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", timeOfSample='" + timeOfSample + '\'' +
                ", uncertaintyInMilliseconds=" + uncertaintyInMilliseconds +
                ", proactivelyReported=" + proactivelyReported +
                ", retrievable=" + retrievable +
                '}';
    }
}

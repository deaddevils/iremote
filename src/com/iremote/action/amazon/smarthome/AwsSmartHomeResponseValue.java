package com.iremote.action.amazon.smarthome;

public class AwsSmartHomeResponseValue {
    private double value;
    private String scale;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }
}

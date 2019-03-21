package com.iremote.action.amazon.smarthome;

import java.util.ArrayList;
import java.util.List;

public class AwsSmartHomeRequestConfiguration {
    private boolean supportsScheduling;
    private List<String> supportedModes = new ArrayList<>();

    public boolean isSupportsScheduling() {
        return supportsScheduling;
    }

    public void setSupportsScheduling(boolean supportsScheduling) {
        this.supportsScheduling = supportsScheduling;
    }

    public List<String> getSupportedModes() {
        return supportedModes;
    }

    public void setSupportedModes(List<String> supportedModes) {
        this.supportedModes = supportedModes;
    }
}

package com.iremote.action.amazon.smarthome;

public class AwsSmartHomeResponse {
    private AwsSmartHomeRequestEvent event;
    private AwsSmartHomeRequestContext context;

    public AwsSmartHomeRequestEvent getEvent() {
        return event;
    }

    public void setEvent(AwsSmartHomeRequestEvent event) {
        this.event = event;
    }

    public AwsSmartHomeRequestContext getContext() {
        return context;
    }

    public void setContext(AwsSmartHomeRequestContext context) {
        this.context = context;
    }
}

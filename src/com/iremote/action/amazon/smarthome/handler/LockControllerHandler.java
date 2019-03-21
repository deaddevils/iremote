package com.iremote.action.amazon.smarthome.handler;

import com.iremote.action.amazon.smarthome.AwsSmartHomeRequestEvent;

public class LockControllerHandler extends PowerControllerHandler {
    @Override
    protected void setProperty(AwsSmartHomeRequestEvent directive) {
        super.setProperty(directive);
        property.setName("lockState");
        property.setNamespace("Alexa.LockController");

        if (resultCode != 0) {
            property.setValue("JAMMED");
        } else {
            if ("Unlock".equals(directive.getHeader().getName())) {
                property.setValue("UNLOCKED");
            } else {
                property.setValue("LOCKED");
            }
        }
    }
}

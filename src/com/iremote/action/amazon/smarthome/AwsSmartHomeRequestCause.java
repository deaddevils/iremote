package com.iremote.action.amazon.smarthome;

import java.util.Date;

public class AwsSmartHomeRequestCause {
    private String type;
    private Date timestamp;

    public String getType() {
        return type;
    }

    public void setType(EventChangeCauseEnum type) {
        this.type = type.name();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "AwsSmartHomeRequestCause{" +
                "type='" + type + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

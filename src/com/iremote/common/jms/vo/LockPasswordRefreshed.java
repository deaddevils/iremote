package com.iremote.common.jms.vo;

import java.util.List;

public class LockPasswordRefreshed {
    private List<String> alias;
    private int zwavedeviceid;
    private String deviceid;
    private int platform;

    public LockPasswordRefreshed() {
    }

    public LockPasswordRefreshed(List<String> alias, int zwavedeviceid, String deviceid, int platform) {
        this.alias = alias;
        this.zwavedeviceid = zwavedeviceid;
        this.deviceid = deviceid;
        this.platform = platform;
    }

    public List<String> getAlias() {
        return alias;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }

    public int getZwavedeviceid() {
        return zwavedeviceid;
    }

    public void setZwavedeviceid(int zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }
}

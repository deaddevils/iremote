package com.iremote.common.jms.vo;

import com.iremote.domain.ZWaveDevice;

public class ChannelEnableEvent {
    protected int channelid;
    protected String password;
    protected int enablestatus;
    protected ZWaveDevice zd;
    protected String deviceid;

    public ChannelEnableEvent(int channelid, String password, int enablestatus, ZWaveDevice zd) {
        this.channelid = channelid;
        this.password = password;
        this.enablestatus = enablestatus;
        this.zd = zd;
        this.deviceid = zd.getDeviceid();
    }

    public ChannelEnableEvent() {
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public int getChannelid() {
        return channelid;
    }

    public void setChannelid(int channelid) {
        this.channelid = channelid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnablestatus() {
        return enablestatus;
    }

    public void setEnablestatus(int enablestatus) {
        this.enablestatus = enablestatus;
    }

    public ZWaveDevice getZd() {
        return zd;
    }

    public void setZd(ZWaveDevice zd) {
        this.zd = zd;
    }
}

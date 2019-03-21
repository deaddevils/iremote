package com.iremote.common.jms.vo;

import java.util.Date;

public class SubdeviceStatusEvent {
    protected String type;
    protected int subdeviceid;
    protected int zwavedeviceid;
    protected String warningstatuses;
    protected Integer status;
    protected int channelid;
    protected int enablestatus;
    protected String deviceid;
    protected Date eventtime;

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public Date getEventtime() {
        return eventtime;
    }

    public void setEventtime(Date eventtime) {
        this.eventtime = eventtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSubdeviceid() {
        return subdeviceid;
    }

    public void setSubdeviceid(int subdeviceid) {
        this.subdeviceid = subdeviceid;
    }

    public int getZwavedeviceid() {
        return zwavedeviceid;
    }

    public void setZwavedeviceid(int zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public int getChannelid() {
        return channelid;
    }

    public void setChannelid(int channelid) {
        this.channelid = channelid;
    }

    public int getEnablestatus() {
        return enablestatus;
    }

    public void setEnablestatus(int enablestatus) {
        this.enablestatus = enablestatus;
    }

    public String getWarningstatuses() {
        return warningstatuses;
    }

    public void setWarningstatuses(String warningstatuses) {
        this.warningstatuses = warningstatuses;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

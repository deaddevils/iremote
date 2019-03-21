package com.iremote.common.jms.vo;

import com.iremote.common.Utils;

import java.util.Date;

public class DSCEvent {
    private int channelid;
    private int partitionid;
    private int dscparitionid;
    private String deviceid;
    private int zwavedevceid;
    private int armstatus;
    private Date eventtime = new Date();
    private String type;
    private int warningstatus;
    private long taskIndentify;
    private Integer phoneuserid;
    private Integer platform;
    private String partitionname;

    public DSCEvent() {
    }

    public Date getEventtime() {
        return eventtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEventtime(Date eventtime) {
        this.eventtime = eventtime;
    }

    public DSCEvent(int partitionid, String deviceid, int zwavedevceid ,int armstatus,int channelid,String type,int dscparitionid) {
        this.partitionid = partitionid;
        this.deviceid = deviceid;
        this.zwavedevceid = zwavedevceid;
        this.armstatus = armstatus;
        this.eventtime = new Date();
        this.type = type;
        this.channelid = channelid;
        this.dscparitionid = dscparitionid;
    }

    public DSCEvent(int partitionid, String deviceid, int zwavedevceid, int armstatus, Date eventtime, String type) {
        this.partitionid = partitionid;
        this.deviceid = deviceid;
        this.zwavedevceid = zwavedevceid;
        this.armstatus = armstatus;
        this.eventtime = eventtime;
        this.type = type;
    }

    public DSCEvent(int partitionid,Integer phoneuserid, int armstatus, Date eventtime, String type, Integer platform) {
        this.partitionid = partitionid;
        this.phoneuserid = phoneuserid;
        this.armstatus = armstatus;
        this.eventtime = eventtime;
        this.type = type;
        this.platform = platform;
    }

    public DSCEvent(int partitionid,Integer phoneuserid, int armstatus, Date eventtime, String type, Integer platform, String partitionname) {
        this.partitionid = partitionid;
        this.armstatus = armstatus;
        this.eventtime = eventtime;
        this.type = type;
        this.phoneuserid = phoneuserid;
        this.platform = platform;
        this.partitionname = partitionname;
    }

    public int getRemotePlatform(){
        if (this.platform == null) {
            return Utils.getRemotePlatform(this.getDeviceid());
        }
        return this.platform;
    }
    public int getDscparitionid() {
        return dscparitionid;
    }

    public void setDscparitionid(int dscparitionid) {
        this.dscparitionid = dscparitionid;
    }

    public int getChannelid() {
        return channelid;
    }

    public void setChannelid(int channelid) {
        this.channelid = channelid;
    }

    public int getPartitionid() {
        return partitionid;
    }

    public void setPartitionid(int partitionid) {
        this.partitionid = partitionid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public int getZwavedevceid() {
        return zwavedevceid;
    }

    public void setZwavedevceid(int zwavedevceid) {
        this.zwavedevceid = zwavedevceid;
    }

    public int getArmstatus() {
        return armstatus;
    }

    public void setArmstatus(int armstatus) {
        this.armstatus = armstatus;
    }

    public int getWarningstatus() {
        return warningstatus;
    }

    public void setWarningstatus(int warningstatus) {
        this.warningstatus = warningstatus;
    }

    public long getTaskIndentify() {

        return taskIndentify;
    }

    public Integer getPhoneuserid() {
        return phoneuserid;
    }

    public void setPhoneuserid(Integer phoneuserid) {
        this.phoneuserid = phoneuserid;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getPartitionname() {
        return partitionname;
    }

    public void setPartitionname(String partitionname) {
        this.partitionname = partitionname;
    }
}

package com.iremote.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="devicerawcmd")
public class DeviceRawCmd {
    private int devicerawcmdid;
    private int zwavedeviceid;
    private String name;
    private int cmdindex;
    private int cmdtype;
    private String rawcmd;
    private Date createtime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "generator", strategy = "increment")
    public int getDevicerawcmdid() {
        return devicerawcmdid;
    }

    public void setDevicerawcmdid(int devicerawcmdid) {
        this.devicerawcmdid = devicerawcmdid;
    }

    public int getZwavedeviceid() {
        return zwavedeviceid;
    }

    public void setZwavedeviceid(int zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCmdindex() {
        return cmdindex;
    }

    public void setCmdindex(int cmdindex) {
        this.cmdindex = cmdindex;
    }

    public int getCmdtype() {
        return cmdtype;
    }

    public void setCmdtype(int cmdtype) {
        this.cmdtype = cmdtype;
    }

    public String getRawcmd() {
        return rawcmd;
    }

    public void setRawcmd(String rawcmd) {
        this.rawcmd = rawcmd;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}

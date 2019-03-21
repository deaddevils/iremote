package com.iremote.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="zwavedeviceactivetime")
public class ZwaveDeviceActiveTime {
    private int zwavedeviceid;
    private Date lastactivetime;
    private Date nextactivetime;
    private int status;
    private Date createtime;

    @Id
    @GenericGenerator(name = "generator", strategy = "assigned")
    @Column(name = "zwavedeviceid")
    public int getZwavedeviceid() {
        return zwavedeviceid;
    }

    public void setZwavedeviceid(int zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public Date getLastactivetime() {
        return lastactivetime;
    }

    public void setLastactivetime(Date lastactivetime) {
        this.lastactivetime = lastactivetime;
    }

    public Date getNextactivetime() {
        return nextactivetime;
    }

    public void setNextactivetime(Date nextactivetime) {
        this.nextactivetime = nextactivetime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}

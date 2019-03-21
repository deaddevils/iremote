package com.iremote.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="timertask")
public class TimerTask {
    private Integer timertaskid;
    private Integer type;
    private Integer objid;
    private String jsonpara;
    private String deviceid;
    private Date excutetime;
    private Date expiretime;
    private Date createtime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "generator", strategy = "increment")
    public Integer getTimertaskid() {
        return timertaskid;
    }

    public void setTimertaskid(Integer timertaskid) {
        this.timertaskid = timertaskid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public String getJsonpara() {
        return jsonpara;
    }

    public void setJsonpara(String jsonpara) {
        this.jsonpara = jsonpara;
    }

    public Date getExcutetime() {
        return excutetime;
    }

    public void setExcutetime(Date excutetime) {
        this.excutetime = excutetime;
    }

    public Date getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(Date expiretime) {
        this.expiretime = expiretime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}

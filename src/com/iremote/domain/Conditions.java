package com.iremote.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="conditions")
public class Conditions {

    private int conditionsid;
    private Integer zwavedeviceid;
    private String deviceid;
    private Integer channelid;
    private String devicestatus;
    private String description;
    private Float status;
    private Scene scene;
    private String operator;
    private Integer statusesindex;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "generator", strategy = "increment")
    @Column(name = "conditionsid")
    public int getConditionsid() {
        return conditionsid;
    }

    public void setConditionsid(int conditionsid) {
        this.conditionsid = conditionsid;
    }

    public String getDevicestatus() {
        return devicestatus;
    }

    public void setDevicestatus(String devicestatus) {
        this.devicestatus = devicestatus;
    }

    @JSONField(serialize = false)
    @JSON(serialize=false)
    @ManyToOne(targetEntity=Scene.class,cascade={CascadeType.DETACH})
    @JoinColumn(name="scenedbid",referencedColumnName="scenedbid",nullable=true)
    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Integer getZwavedeviceid() {
        return zwavedeviceid;
    }

    public void setZwavedeviceid(Integer zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public Integer getChannelid() {
        return channelid;
    }

    public void setChannelid(Integer channelid) {
        this.channelid = channelid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getStatus() {
        return status;
    }

    public void setStatus(Float status) {
        this.status = status;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getStatusesindex() {
        return statusesindex;
    }

    public void setStatusesindex(Integer statusesindex) {
        this.statusesindex = statusesindex;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}

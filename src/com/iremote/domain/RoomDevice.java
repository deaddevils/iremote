package com.iremote.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="roomdevice")
public class RoomDevice {
    private Integer roomdeviceid;
    private Integer roomdbid;
    private Integer deviceid;
    private String command;
    private String devicetype;
    private Integer channel;
    private Integer otherplatformuseridmapid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "generator", strategy = "increment")
    public Integer getRoomdeviceid() {
        return roomdeviceid;
    }

    public void setRoomdeviceid(Integer roomdeviceid) {
        this.roomdeviceid = roomdeviceid;
    }

    public Integer getOtherplatformuseridmapid() {
        return otherplatformuseridmapid;
    }

    public void setOtherplatformuseridmapid(Integer otherplatformuseridmapid) {
        this.otherplatformuseridmapid = otherplatformuseridmapid;
    }

    public Integer getRoomdbid() {
        return roomdbid;
    }

    public void setRoomdbid(Integer roomdbid) {
        this.roomdbid = roomdbid;
    }

    public Integer getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Integer deviceid) {
        this.deviceid = deviceid;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "RoomDevice{" +
                "roomdeviceid=" + roomdeviceid +
                ", roomdbid=" + roomdbid +
                ", deviceid=" + deviceid +
                ", command='" + command + '\'' +
                ", devicetype='" + devicetype + '\'' +
                ", channel=" + channel +
                ", otherplatformuseridmapid=" + otherplatformuseridmapid +
                '}';
    }
}

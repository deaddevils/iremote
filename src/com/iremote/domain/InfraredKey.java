package com.iremote.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="infraredkey")
public class InfraredKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "generator", strategy = "increment")
    private int infraredkeyid;
    private int infrareddeviceid;
    private int keyindex;
    private String keycode;

    public int getInfraredkeyid() {
        return infraredkeyid;
    }

    public void setInfraredkeyid(int infraredkeyid) {
        this.infraredkeyid = infraredkeyid;
    }

    public int getInfrareddeviceid() {
        return infrareddeviceid;
    }

    public void setInfrareddeviceid(int infrareddeviceid) {
        this.infrareddeviceid = infrareddeviceid;
    }

    public int getKeyindex() {
        return keyindex;
    }

    public void setKeyindex(int keyindex) {
        this.keyindex = keyindex;
    }

    public String getKeycode() {
        return keycode;
    }

    public void setKeycode(String keycode) {
        this.keycode = keycode;
    }
}

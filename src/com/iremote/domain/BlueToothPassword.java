package com.iremote.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bluetoothpassword")
public class BlueToothPassword {
    private int bluetoothpasswordid;
    private int zwavedeviceid;
    private int phoneuserid;
    private String password;
    private Date validfrom;
    private Date validthrought;
    private Date createtime;

    public BlueToothPassword(){
    	
    }
    public BlueToothPassword(int zwavedeviceid, int phoneuserid, String password) {
        this.zwavedeviceid = zwavedeviceid;
        this.phoneuserid = phoneuserid;
        this.password = password;
        this.createtime = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "generator", strategy = "increment")
    public int getBluetoothpasswordid() {
        return bluetoothpasswordid;
    }

    public void setBluetoothpasswordid(int bluetoothpasswordid) {
        this.bluetoothpasswordid = bluetoothpasswordid;
    }

    public int getZwavedeviceid() {
        return zwavedeviceid;
    }

    public void setZwavedeviceid(int zwavedeviceid) {
        this.zwavedeviceid = zwavedeviceid;
    }

    public int getPhoneuserid() {
        return phoneuserid;
    }

    public void setPhoneuserid(int phoneuserid) {
        this.phoneuserid = phoneuserid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getValidfrom() {
        return validfrom;
    }

    public void setValidfrom(Date validfrom) {
        this.validfrom = validfrom;
    }

    public Date getValidthrought() {
        return validthrought;
    }

    public void setValidthrought(Date validthrought) {
        this.validthrought = validthrought;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}

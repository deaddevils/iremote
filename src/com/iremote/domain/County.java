package com.iremote.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="county")
public class County {
    private int countyid;
    private int cityid;
    private String code;
    private String name;
    private String alilocationkey;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "generator", strategy = "increment")
    public int getCountyid() {
        return countyid;
    }

    public void setCountyid(int countyid) {
        this.countyid = countyid;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlilocationkey() {
        return alilocationkey;
    }

    public void setAlilocationkey(String alilocationkey) {
        this.alilocationkey = alilocationkey;
    }
}
package com.iremote.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Deprecated
@Entity
@Table(name = "infrareddevice2")
public class InfraredDevice2{

    private Integer id;
    private Integer serial;
    private String model;
    private String pinyin;
    private String brand_cn;
    private String brand_en;
    private String code;
    private String devicetype;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "generator", strategy = "increment")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getBrand_cn() {
        return brand_cn;
    }

    public void setBrand_cn(String brand_cn) {
        this.brand_cn = brand_cn;
    }

    public String getBrand_en() {
        return brand_en;
    }

    public void setBrand_en(String brand_en) {
        this.brand_en = brand_en;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }
}

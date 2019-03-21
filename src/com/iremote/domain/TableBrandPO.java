package com.iremote.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Deprecated
@Entity
@Table(name="table_brand")
public class TableBrandPO {
    private Integer id;
    private Integer serial;
    private String brand_cn;
    private String brand_en;
    private String devicetype;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "generator", strategy = "increment")
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="serial")
    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    @Column(name="brand_cn")
    public String getBrand_cn() {
        return brand_cn;
    }

    public void setBrand_cn(String brand_cn) {
        this.brand_cn = brand_cn;
    }

    @Column(name="brand_en")
    public String getBrand_en() {
        return brand_en;
    }

    public void setBrand_en(String brand_en) {
        this.brand_en = brand_en;
    }

    @Column(name="devicetype")
    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }
}

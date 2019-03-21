package com.iremote.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="datadictionary")
public class DataDictionary {
    private int datadictionaryid;
    private String key;
    private String language;
    private String value;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "generator", strategy = "increment")
    public int getDatadictionaryid() {
        return datadictionaryid;
    }

    public void setDatadictionaryid(int datadictionaryid) {
        this.datadictionaryid = datadictionaryid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package com.iremote.domain;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "rawcmdtemplatedtl")
public class RawCmdTemplateDtl {
    private int rawcmdtemplatedtlid;
    private String name;
    private int cmdindex;
    private int cmdtype;
    private String rawcmd;
    private RawCmdTemplate rawCmdTemplate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "generator", strategy = "increment")
    public int getRawcmdtemplatedtlid() {
        return rawcmdtemplatedtlid;
    }

    public void setRawcmdtemplatedtlid(int rawcmdtemplatedtlid) {
        this.rawcmdtemplatedtlid = rawcmdtemplatedtlid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCmdindex() {
        return cmdindex;
    }

    public void setCmdindex(int cmdindex) {
        this.cmdindex = cmdindex;
    }

    public int getCmdtype() {
        return cmdtype;
    }

    public void setCmdtype(int cmdtype) {
        this.cmdtype = cmdtype;
    }

    public String getRawcmd() {
        return rawcmd;
    }

    public void setRawcmd(String rawcmd) {
        this.rawcmd = rawcmd;
    }

    @JSONField(serialize = false)
    @ManyToOne(targetEntity=RawCmdTemplate.class,cascade={CascadeType.DETACH})
    @JoinColumn(name="rawcmdtemplateid",referencedColumnName="rawcmdtemplateid")
    public RawCmdTemplate getRawCmdTemplate() {
        return rawCmdTemplate;
    }

    public void setRawCmdTemplate(RawCmdTemplate rawCmdTemplate) {
        this.rawCmdTemplate = rawCmdTemplate;
    }
}

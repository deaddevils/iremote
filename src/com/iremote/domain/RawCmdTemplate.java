package com.iremote.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.iremote.common.IRemoteConstantDefine;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="rawcmdtemplate")
public class RawCmdTemplate {
    private int rawcmdtemplateid;
    private Integer phoneuserid;
    private String name;
    private int type = IRemoteConstantDefine.TYPE_OTHER_OPERATE_CODE;
    private String templatetype;
    private Date createtime;
    private List<RawCmdTemplateDtl> rawCmdTemplateDtlList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "generator", strategy = "increment")
    public int getRawcmdtemplateid() {
        return rawcmdtemplateid;
    }

    public void setRawcmdtemplateid(int rawcmdtemplateid) {
        this.rawcmdtemplateid = rawcmdtemplateid;
    }

    public Integer getPhoneuserid() {
        return phoneuserid;
    }

    public void setPhoneuserid(Integer phoneuserid) {
        this.phoneuserid = phoneuserid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTemplatetype() {
        return templatetype;
    }

    public void setTemplatetype(String templatetype) {
        this.templatetype = templatetype;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @JSONField(serialize = false)
    @OneToMany(targetEntity=RawCmdTemplateDtl.class,cascade={CascadeType.ALL,CascadeType.REMOVE},orphanRemoval=true,fetch=FetchType.LAZY,mappedBy="rawCmdTemplate")
    public List<RawCmdTemplateDtl> getRawCmdTemplateDtlList() {
        return rawCmdTemplateDtlList;
    }

    public void setRawCmdTemplateDtlList(List<RawCmdTemplateDtl> rawCmdTemplateDtlList) {
        this.rawCmdTemplateDtlList = rawCmdTemplateDtlList;
    }
}

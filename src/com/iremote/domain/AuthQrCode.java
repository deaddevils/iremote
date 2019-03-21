package com.iremote.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="authqrcode")
public class AuthQrCode {
    private int authqrcodeid;
    private String qid;
    private String authtype;
    private int status;
    private Date createtime;
    private String applianceuuid;
    private Date authtime;
    private String operator;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "generator", strategy = "increment")
    public int getAuthqrcodeid() {
        return authqrcodeid;
    }

    public void setAuthqrcodeid(int authqrcodeid) {
        this.authqrcodeid = authqrcodeid;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getAuthtype() {
        return authtype;
    }

    public void setAuthtype(String authtype) {
        this.authtype = authtype;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getApplianceuuid() {
        return applianceuuid;
    }

    public void setApplianceuuid(String applianceuuid) {
        this.applianceuuid = applianceuuid;
    }

    public Date getAuthtime() {
        return authtime;
    }

    public void setAuthtime(Date authtime) {
        this.authtime = authtime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}

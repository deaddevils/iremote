package com.iremote.vo;

public class RemoteControlVO {
    private Integer codeid;
    private String ctrlcode;

    public RemoteControlVO(Integer codeid, String ctrlcode) {
        this.codeid = codeid;
        this.ctrlcode = ctrlcode;
    }
    public RemoteControlVO(){}

    public Integer getCodeid() {
        return codeid;
    }

    public void setCodeid(Integer codeid) {
        this.codeid = codeid;
    }

    public String getCtrlcode() {
        return ctrlcode;
    }

    public void setCtrlcode(String ctrlcode) {
        this.ctrlcode = ctrlcode;
    }
}

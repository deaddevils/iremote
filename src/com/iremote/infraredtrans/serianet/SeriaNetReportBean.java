package com.iremote.infraredtrans.serianet;

public class SeriaNetReportBean {
    private byte[] cmd;
    private String deviceid;
    private long reportid = System.currentTimeMillis();

    public byte[] getCmd() {
        return cmd;
    }

    public void setCmd(byte[] cmd) {
        this.cmd = cmd;
    }

    public long getReportid() {
        return reportid;
    }

    public void setReportid(long reportid) {

        this.reportid = reportid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}

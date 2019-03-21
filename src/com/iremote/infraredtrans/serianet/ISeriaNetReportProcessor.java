package com.iremote.infraredtrans.serianet;

import com.iremote.common.taskmanager.IMulitReportTask;

public interface ISeriaNetReportProcessor  extends IMulitReportTask {
    void setReport(SeriaNetReportBean zrb);
//    void setNoSessionZwaveDevice(ZWaveDevice zwavedevice);
}

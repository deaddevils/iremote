package com.iremote.infraredtrans.serianet.dsc;

import com.iremote.infraredtrans.serianet.SeriaNetReportBean;
import com.iremote.test.db.Db;

public class DSCZoneAlarmRestoreProcessorTest {
    public static void main(String arg[]) {
        Db.init();

        SeriaNetReportBean zrb = new SeriaNetReportBean();
        zrb.setCmd(new byte[]{107, 3, 0, 15, 0, 93, 0, 11, 54, 48, 50, 49, 48, 48, 51, 53, 66, 13, 10, 116});
        zrb.setDeviceid("iRemote8005000000014");

        DSCZoneAlarmRestoreProcessor pro = new DSCZoneAlarmRestoreProcessor();
        pro.setReport(zrb);
        pro.run();

        Db.commit();
    }
}

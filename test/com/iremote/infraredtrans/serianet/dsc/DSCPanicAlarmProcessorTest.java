package com.iremote.infraredtrans.serianet.dsc;

import com.iremote.infraredtrans.serianet.SeriaNetReportBean;
import com.iremote.infraredtrans.serianet.dsc.alarm.DSCPanicAlarmProcessor;
import com.iremote.infraredtrans.serianet.dsc.alarm.DSCPanicAlarmRestoralProcessor;
import com.iremote.test.db.Db;

public class DSCPanicAlarmProcessorTest {
    public static void main(String[] args) {
        Db.init();

        SeriaNetReportBean zrb = new SeriaNetReportBean();
//        zrb.setCmd(new byte[]{107,3,0,13,0,93,0,7,54,50,53,57,68,13,10,4});
        zrb.setDeviceid("iRemote8005000000066");

        DSCPanicAlarmRestoralProcessor pro = new DSCPanicAlarmRestoralProcessor();
        pro.setReport(zrb);
        pro.run();

        Db.commit();
    }
}

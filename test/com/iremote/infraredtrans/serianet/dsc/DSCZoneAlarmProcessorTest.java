package com.iremote.infraredtrans.serianet.dsc;

import com.iremote.infraredtrans.serianet.SeriaNetReportBean;
import com.iremote.test.db.Db;

public class DSCZoneAlarmProcessorTest {
    public static void main(String arg[])
    {
        Db.init();

        SeriaNetReportBean zrb = new SeriaNetReportBean();
        zrb.setCmd(new byte[]{107,3,0,15,0,93,0,11,54,48,49,49,48,48,51,53,66,13,10,116});
        zrb.setDeviceid("iRemote2005000000704");

        DSCZoneAlarmProcessor pro = new DSCZoneAlarmProcessor();
        pro.setReport(zrb);
        pro.run();

        Db.commit();
    }
}

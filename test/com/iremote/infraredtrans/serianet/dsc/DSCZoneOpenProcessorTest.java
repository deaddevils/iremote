package com.iremote.infraredtrans.serianet.dsc;

import com.iremote.infraredtrans.serianet.SeriaNetReportBean;
import com.iremote.test.db.Db;

public class DSCZoneOpenProcessorTest {
    public static void main(String arg[])
    {
        Db.init();

        SeriaNetReportBean zrb = new SeriaNetReportBean();
        zrb.setCmd(new byte[]{107,3,0,14,0,93,0,10,54,48,57,48,48,49,51,48,13,10,59});
        zrb.setDeviceid("iRemote2005000000704");

        DSCZoneOpenProcessor pro = new DSCZoneOpenProcessor();
        pro.setReport(zrb);
        pro.run();

        Db.commit();
    }
}

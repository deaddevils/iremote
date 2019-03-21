package com.iremote.infraredtrans.serianet.dsc;

import com.iremote.infraredtrans.serianet.SeriaNetReportBean;
import com.iremote.test.db.Db;

public class DSCParitionDisArmedProccessorTest {public static void main(String arg[])
{
    Db.init();

    SeriaNetReportBean zrb = new SeriaNetReportBean();
    zrb.setCmd(new byte[]{107,3,0,12,0,93,0,8,54,53,53,49,68,49,13,10,68});
    zrb.setDeviceid("iRemote8005000000014");

    DSCPartitionDisarmedProcessor pro = new DSCPartitionDisarmedProcessor();
    pro.setReport(zrb);
    pro.run();

    Db.commit();
}
}

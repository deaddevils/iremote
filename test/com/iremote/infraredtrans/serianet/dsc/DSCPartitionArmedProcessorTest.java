package com.iremote.infraredtrans.serianet.dsc;

import com.iremote.infraredtrans.serianet.SeriaNetReportBean;
import com.iremote.test.db.Db;

public class DSCPartitionArmedProcessorTest 
{
	public static void main(String arg[])
	{
		Db.init();
		
		SeriaNetReportBean zrb = new SeriaNetReportBean();
		zrb.setCmd(new byte[]{107,3,0,13,0,93,0,9,54,53,50,49,50,48,48,13,10,4});
		zrb.setDeviceid("iRemote2005000000704");
		
		DSCPartitionArmedProcessor pro = new DSCPartitionArmedProcessor();
		pro.setReport(zrb);
		pro.run();
		
		Db.commit();
	}
}

package com.iremote.common.schedule;

import javax.servlet.ServletException;

import com.iremote.common.taskmanager.MultiReportTaskManager;
import com.iremote.infraredtrans.zwavecommand.IZwaveReportProcessor;
import com.iremote.infraredtrans.zwavecommand.ZwaveReportBean;
import com.iremote.infraredtrans.zwavecommand.doorlock.DoorlockReportProcessor;
import com.iremote.infraredtrans.zwavecommand.doorlock.DoorlockSelfDefineReportProcessor;
import com.iremote.interceptor.JSMInitFilter;
@SuppressWarnings("deprecation")
public class DoorlockSelfDefineReportProcessorTest {

	
	public static void main(String arg[])
	{
		JSMInitFilter f = new JSMInitFilter();
		try {
			f.init(null);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MultiReportTaskManager<IZwaveReportProcessor> taskmgr = new MultiReportTaskManager<IZwaveReportProcessor>();
		
		byte[] b = new byte[]{30,(byte)9,(byte)0,(byte)50,(byte)0,(byte)71,(byte)0,(byte)1,(byte)43,(byte)0,(byte)70,(byte)0,(byte)22,(byte)128,(byte)7,(byte)0,(byte)176,(byte)16,(byte)1,(byte)3,(byte)0,(byte)21,(byte)116,(byte)101,(byte)109,(byte)112,(byte)117,(byte)115,(byte)101,(byte)114,(byte)16,(byte)4,(byte)21,(byte)14,(byte)14,(byte)0,(byte)31,(byte)0,(byte)2,(byte)0,(byte)31,(byte)0,(byte)104,(byte)0,(byte)4,(byte)87,(byte)24,(byte)111,(byte)167,(byte)0,(byte)79,(byte)0,(byte)1,(byte)1,(byte)146};
		byte[] b2 = new byte[]{30,(byte)9,(byte)0,(byte)35,(byte)0,(byte)71,(byte)0,(byte)1,(byte)43,(byte)0,(byte)70,(byte)0,(byte)7,(byte)98,(byte)3,(byte)0,(byte)0,(byte)0,(byte)254,(byte)254,(byte)0,(byte)31,(byte)0,(byte)2,(byte)0,(byte)30,(byte)0,(byte)104,(byte)0,(byte)4,(byte)87,(byte)24,(byte)111,(byte)167,(byte)0,(byte)79,(byte)0,(byte)1,(byte)1,(byte)222};
		
		DoorlockSelfDefineReportProcessor pro1 = new DoorlockSelfDefineReportProcessor();
		DoorlockReportProcessor pro2 = new DoorlockReportProcessor();
		
		String deviceid = "iRemote4005000000261";
		ZwaveReportBean zrb = new ZwaveReportBean();
		zrb.setReport(deviceid, b);
		
		ZwaveReportBean zrb2 = new ZwaveReportBean();
		zrb2.setReport(deviceid, b2);
		
		pro1.setReport(zrb);
		pro2.setReport(zrb2);
		
		taskmgr.addTask(deviceid, pro1);
		taskmgr.addTask(deviceid, pro2);
		System.out.println(pro1.merge(pro2));
		
	}
}

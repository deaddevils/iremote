package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;

import javax.servlet.ServletException;

import org.hibernate.Transaction;

import com.iremote.common.Utils;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.commandclass.CommandParser;
import com.iremote.common.commandclass.CommandValue;
import com.iremote.common.taskmanager.MultiReportTaskManager;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.infraredtrans.zwavecommand.IZwaveReportProcessor;
import com.iremote.infraredtrans.zwavecommand.ZwaveCommandProcessorStore;
import com.iremote.infraredtrans.zwavecommand.ZwaveReportBean;
import com.iremote.interceptor.JSMInitFilter;
import com.iremote.mock.TestConnectionContext;
import com.iremote.test.db.Db;

@SuppressWarnings(value={"unused", "deprecation"})
public class ZWaveReportProcessorTest {

	private static MultiReportTaskManager<IZwaveReportProcessor> taskmgr = new MultiReportTaskManager<IZwaveReportProcessor>();
	
	public static void main(String arg[]) throws BufferOverflowException, IOException
	{
		
		
//		
//		ZWaveReportProcessorTest t = new ZWaveReportProcessorTest();
//		t.testWakeupcommand();
//		
		ZWaveReportProcessorTest t = new ZWaveReportProcessorTest();
		//t.testMeterReport();
		t.testDoorlockOpenReport();

	}
	
	
	
	public void testMeterReport()
	{
		JSMInitFilter f = new JSMInitFilter();
		try {
			f.init(null);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] b1 = new byte[]{30,(byte)9,(byte)0,(byte)39,(byte)0,(byte)71,(byte)0,(byte)4,(byte)0,(byte)0,(byte)39,(byte)17,(byte)0,(byte)70,(byte)0,(byte)8,(byte)50,(byte)2,(byte)129,(byte)76,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)31,(byte)0,(byte)2,(byte)0,(byte)26,(byte)0,(byte)104,(byte)0,(byte)4,(byte)87,(byte)55,(byte)2,(byte)169,(byte)0,(byte)79,(byte)0,(byte)1,(byte)1,(byte)25};
		ZWaveReportProcessor pro = new ZWaveReportProcessor();
		try {
			pro.process(b1, new TestConnectionContext());
		} catch (BufferOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void testDoorlockReport()
	{
		JSMInitFilter f = new JSMInitFilter();
		try {
			f.init(null);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] b1 = new byte[]{30,(byte)9,(byte)0,(byte)50,(byte)0,(byte)71,(byte)0,(byte)1,(byte)11,(byte)0,(byte)70,(byte)0,(byte)22,(byte)128,(byte)7,(byte)0,(byte)176,(byte)16,(byte)1,(byte)3,(byte)0,(byte)21,(byte)110,(byte)111,(byte)114,(byte)109,(byte)117,(byte)115,(byte)101,(byte)114,(byte)16,(byte)6,(byte)3,(byte)0,(byte)19,(byte)0,(byte)31,(byte)0,(byte)2,(byte)0,(byte)44,(byte)0,(byte)104,(byte)0,(byte)4,(byte)87,(byte)80,(byte)92,(byte)80,(byte)0,(byte)79,(byte)0,(byte)1,(byte)1,(byte)24};
		byte[] b2 = new byte[]{30,(byte)9,(byte)0,(byte)35,(byte)0,(byte)71,(byte)0,(byte)1,(byte)13,(byte)0,(byte)70,(byte)0,(byte)7,(byte)98,(byte)3,(byte)0,(byte)0,(byte)0,(byte)254,(byte)254,(byte)0,(byte)31,(byte)0,(byte)2,(byte)2,(byte)102,(byte)0,(byte)104,(byte)0,(byte)4,(byte)87,(byte)26,(byte)249,(byte)215,(byte)0,(byte)79,(byte)0,(byte)1,(byte)1,(byte)102};
		byte[] b3 = new byte[]{30,(byte)9,(byte)0,(byte)35,(byte)0,(byte)71,(byte)0,(byte)1,(byte)13,(byte)0,(byte)70,(byte)0,(byte)7,(byte)98,(byte)3,(byte)0,(byte)0,(byte)0,(byte)254,(byte)254,(byte)0,(byte)31,(byte)0,(byte)2,(byte)2,(byte)103,(byte)0,(byte)104,(byte)0,(byte)4,(byte)87,(byte)26,(byte)249,(byte)215,(byte)0,(byte)79,(byte)0,(byte)1,(byte)1,(byte)103};
		byte[] b4 = new byte[]{30,(byte)9,(byte)0,(byte)50,(byte)0,(byte)71,(byte)0,(byte)1,(byte)13,(byte)0,(byte)70,(byte)0,(byte)22,(byte)128,(byte)7,(byte)0,(byte)176,(byte)16,(byte)1,(byte)3,(byte)0,(byte)21,(byte)110,(byte)111,(byte)114,(byte)109,(byte)117,(byte)115,(byte)101,(byte)114,(byte)16,(byte)4,(byte)23,(byte)12,(byte)27,(byte)0,(byte)31,(byte)0,(byte)2,(byte)2,(byte)104,(byte)0,(byte)104,(byte)0,(byte)4,(byte)87,(byte)26,(byte)249,(byte)215,(byte)0,(byte)79,(byte)0,(byte)1,(byte)1,(byte)34};
		byte[] b5 = new byte[]{30,(byte)9,(byte)0,(byte)35,(byte)0,(byte)71,(byte)0,(byte)1,(byte)13,(byte)0,(byte)70,(byte)0,(byte)7,(byte)98,(byte)3,(byte)255,(byte)0,(byte)0,(byte)254,(byte)254,(byte)0,(byte)31,(byte)0,(byte)2,(byte)2,(byte)105,(byte)0,(byte)104,(byte)0,(byte)4,(byte)87,(byte)26,(byte)249,(byte)220,(byte)0,(byte)79,(byte)0,(byte)1,(byte)1,(byte)157};
		byte[] b6 = new byte[]{30,(byte)9,(byte)0,(byte)35,(byte)0,(byte)71,(byte)0,(byte)1,(byte)13,(byte)0,(byte)70,(byte)0,(byte)7,(byte)98,(byte)3,(byte)255,(byte)0,(byte)0,(byte)254,(byte)254,(byte)0,(byte)31,(byte)0,(byte)2,(byte)0,(byte)172,(byte)0,(byte)104,(byte)0,(byte)4,(byte)87,(byte)70,(byte)173,(byte)76,(byte)0,(byte)79,(byte)0,(byte)1,(byte)1,(byte)222};
		
		ZWaveReportProcessor pro = new ZWaveReportProcessor();
		try {
			pro.process(b1, new TestConnectionContext());
//			sleep(300);
//			pro.process(b2, new TestConnectionContext());
//			sleep(100);
//			pro.process(b3, new TestConnectionContext());
//			sleep(100);
//			pro.process(b4, new TestConnectionContext());
//			sleep(5000);
//			pro.process(b5, new TestConnectionContext());
		} catch (BufferOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testAirCoditioner()
	{
		
	}
	
	private void sleep(int timemills)
	{
		try {
			Thread.sleep(timemills);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testMultiChannelBinarySwitch() throws BufferOverflowException, IOException
	{
		ZWaveReportProcessor pro = new ZWaveReportProcessor();
		//pro.deviceid = "iRemote2005000000010";
		
		CommandTlv rst = new CommandTlv(30 , 9);
		rst.addUnit(new TlvIntUnit(71 , 10 , 4 ));
		rst.addUnit(new TlvByteUnit(70 , new byte[]{0x60,0x0d,0x02,0x01,0x25,0x03,(byte)0x00} ));
		rst.addUnit(new TlvByteUnit(104 , new byte[]{85, 59 ,88, 98} ));
		pro.process(rst.getByte(), null);
	}
	
	public void testMultiChannelBinarySwitch3() throws BufferOverflowException, IOException
	{
		ZWaveReportProcessor pro = new ZWaveReportProcessor();
		//pro.deviceid = "iRemote2005000000010";
		
		CommandTlv rst = new CommandTlv(30 , 9);
		rst.addUnit(new TlvIntUnit(71 , 11 , 4 ));
		rst.addUnit(new TlvByteUnit(70 , new byte[]{0x60,0x0d,0x03,0x01,0x25,0x03,(byte)0xff} ));
		rst.addUnit(new TlvByteUnit(104 , new byte[]{85, 59 ,88, 98} ));
		pro.process(rst.getByte(), null);
	}
	
	public void testWakeupcommand() throws BufferOverflowException, IOException
	{
		ZWaveReportProcessor pro = new ZWaveReportProcessor();
		CommandTlv tlv = pro.process(new byte[]{30,9,0,25,0,71,0,1,4,0,70,0,2,(byte)132,7,0,31,0,2,0,(byte)53,0,104,0,4,86,92,72,27,81}, new TestConnectionContext());
		byte[] b = tlv.getByte();
		Utils.print("" , b, b.length);
	}
	
	public void testDoorlockOpenReport() throws BufferOverflowException, IOException
	{
		Db.init();
		ZWaveReportProcessor pro = new ZWaveReportProcessor();
		pro.process(new byte[]{30,(byte)9,(byte)0,(byte)45,(byte)0,(byte)71,(byte)0,(byte)1,(byte)17,(byte)0,(byte)70,(byte)0,(byte)22,(byte)128,(byte)7,(byte)0,(byte)177,(byte)16,(byte)1,(byte)3,(byte)0,(byte)22,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)16,(byte)1,(byte)16,(byte)6,(byte)8,(byte)0,(byte)31,(byte)0,(byte)2,(byte)2,(byte)218,(byte)0,(byte)104,(byte)0,(byte)4,(byte)88,(byte)208,(byte)221,(byte)9,(byte)245}, new TestConnectionContext("iRemote2005000000816"));
	}
}

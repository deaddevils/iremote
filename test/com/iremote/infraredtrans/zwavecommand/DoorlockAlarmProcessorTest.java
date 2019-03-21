package com.iremote.infraredtrans.zwavecommand;

import java.io.IOException;
import java.nio.BufferOverflowException;

import javax.servlet.ServletException;

import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.message.MessageManager;
import com.iremote.infraredtrans.ZWaveReportProcessor;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.interceptor.JSMInitFilter;
import com.iremote.mock.TestConnectionContext;
import com.iremote.test.db.Db;
import com.iremote.test.db.Env;

@SuppressWarnings("deprecation")
public class DoorlockAlarmProcessorTest {

	public static void main(String arg[])
	{
		HibernateUtil.beginTransaction();
		testAlarm();
		HibernateUtil.commit();
		HibernateUtil.closeSession();
	}
	
	public static void testAlarm()
	{
		JSMInitFilter f = new JSMInitFilter();
		try {
			f.init(null);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		MessageManager.init();
		
		byte[] b1 = new byte[]{30,(byte)9,(byte)0,(byte)37,(byte)0,(byte)71,(byte)0,(byte)1,(byte)16,(byte)0,(byte)70,(byte)0,(byte)9,(byte)113,(byte)5,(byte)6,(byte)16,(byte)0,(byte)255,(byte)6,(byte)16,(byte)0,(byte)0,(byte)31,(byte)0,(byte)2,(byte)1,(byte)2,(byte)0,(byte)104,(byte)0,(byte)4,(byte)87,(byte)77,(byte)130,(byte)1,(byte)0,(byte)79,(byte)0,(byte)1,(byte)1,(byte)4};
		ZWaveReportProcessor pro = new ZWaveReportProcessor();
		try {
			
			pro.process(b1, new TestConnectionContext());
			
		} catch (BufferOverflowException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void test()
	{
		Db.init();
		Env.getInstance().need();
		
		int nuid = 0 ;
		CommandTlv ct = new CommandTlv(30 , 9);
		ct.addUnit(new TlvIntUnit(71 , nuid , 2));
		ct.addUnit(new TlvIntUnit(104 , (int)(System.currentTimeMillis()/1000) , 4));
		ct.addUnit(new TlvByteUnit(70 , new byte[]{(byte)0x80,0x07,}));
		
		byte cmd[] = ct.getByte();
		ZWaveReportProcessor pro = new ZWaveReportProcessor();
		try {
			
			pro.process(cmd, new TestConnectionContext());
			
		} catch (BufferOverflowException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Db.commit();
		
	}
}

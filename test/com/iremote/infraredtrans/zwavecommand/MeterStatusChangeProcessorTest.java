package com.iremote.infraredtrans.zwavecommand;

import java.io.IOException;
import java.nio.BufferOverflowException;

import javax.servlet.ServletException;

import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.infraredtrans.ZWaveReportProcessor;
import com.iremote.interceptor.JSMInitFilter;
import com.iremote.mock.TestConnectionContext;
@SuppressWarnings("deprecation")
public class MeterStatusChangeProcessorTest {

	
	public static void main(String arg[])
	{
		JSMInitFilter f = new JSMInitFilter();
		try {
			f.init(null);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		
		HibernateUtil.beginTransaction();

		byte[] b1 = new byte[]{30,(byte)9,(byte)0,(byte)39,(byte)0,(byte)71,(byte)0,(byte)4,(byte)0,(byte)0,(byte)39,(byte)23,(byte)0,(byte)70,(byte)0,(byte)8,(byte)50,(byte)2,(byte)129,(byte)76,(byte)0,(byte)0,(byte)0,(byte)60,(byte)0,(byte)31,(byte)0,(byte)2,(byte)112,(byte)149,(byte)0,(byte)104,(byte)0,(byte)4,(byte)87,(byte)89,(byte)53,(byte)194,(byte)0,(byte)79,(byte)0,(byte)1,(byte)1,(byte)238};
		byte[] b2 = new byte[]{30,(byte)9,(byte)0,(byte)39,(byte)0,(byte)71,(byte)0,(byte)4,(byte)0,(byte)0,(byte)39,(byte)23,(byte)0,(byte)70,(byte)0,(byte)8,(byte)50,(byte)2,(byte)1,(byte)68,(byte)0,(byte)22,(byte)229,(byte)223,(byte)0,(byte)31,(byte)0,(byte)2,(byte)112,(byte)207,(byte)0,(byte)104,(byte)0,(byte)4,(byte)87,(byte)89,(byte)54,(byte)120,(byte)0,(byte)79,(byte)0,(byte)1,(byte)1,(byte)149};
		
		ZWaveReportProcessor pro = new ZWaveReportProcessor();
		try {
			pro.process(b1, new TestConnectionContext());
			pro.process(b2, new TestConnectionContext());
		} catch (BufferOverflowException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		HibernateUtil.commit();
		HibernateUtil.closeSession();
	}
}

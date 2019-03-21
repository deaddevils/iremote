package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;

import com.iremote.mock.TestConnectionContext;
import com.iremote.test.db.Db;
import com.iremote.test.db.Env;

public class ReportProcessorRunnerTest
{

	public static void main(String arg[])
	{
		Env.getInstance().need();
		Db.init();
		byte[] rq = new byte[]{100,(byte)2,(byte)0,(byte)130,(byte)0,(byte)1,(byte)0,(byte)2,(byte)0,(byte)0,(byte)0,(byte)2,(byte)0,(byte)20,(byte)105,(byte)82,(byte)101,(byte)109,(byte)111,(byte)116,(byte)101,(byte)50,(byte)48,(byte)48,(byte)53,(byte)48,(byte)48,(byte)48,(byte)48,(byte)48,(byte)49,(byte)51,(byte)51,(byte)55,(byte)0,(byte)101,(byte)0,(byte)16,(byte)106,(byte)23,(byte)119,(byte)179,(byte)249,(byte)239,(byte)229,(byte)83,(byte)44,(byte)55,(byte)206,(byte)186,(byte)200,(byte)11,(byte)245,(byte)161,(byte)0,(byte)105,(byte)0,(byte)4,(byte)0,(byte)0,(byte)0,(byte)15,(byte)0,(byte)3,(byte)0,(byte)2,(byte)0,(byte)29,(byte)0,(byte)4,(byte)0,(byte)8,(byte)49,(byte)46,(byte)50,(byte)46,(byte)49,(byte)50,(byte)65,(byte)69,(byte)0,(byte)108,(byte)0,(byte)1,(byte)2,(byte)0,(byte)109,(byte)0,(byte)1,(byte)0,(byte)0,(byte)22,(byte)0,(byte)1,(byte)0,(byte)0,(byte)73,(byte)0,(byte)4,(byte)240,(byte)203,(byte)47,(byte)225,(byte)0,(byte)76,(byte)0,(byte)16,(byte)105,(byte)244,(byte)151,(byte)65,(byte)107,(byte)195,(byte)146,(byte)137,(byte)44,(byte)73,(byte)96,(byte)219,(byte)187,(byte)250,(byte)118,(byte)104,(byte)0,(byte)26,(byte)0,(byte)2,(byte)1,(byte)2,(byte)0,(byte)31,(byte)0,(byte)1,(byte)1,(byte)226};
		
		IRemoteRequestProcessor pro = ProcessorStore.getInstance().getProcessor(rq , 0);
		try
		{
			pro.process(rq, new TestConnectionContext("iRemote2005000001337"));
		} catch (BufferOverflowException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		Db.commit();
	}
}

package com.iremote.performance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.iremote.common.Utils;

public class RemoteSimulatorMain {

	private int delay = 10 ;
	private int count = 4000 ;
	private String remoteip ;
	private int port ;

	private static ExecutorService executor = Executors.newCachedThreadPool();
	private List<RemoteSimulatorThread> threadlst = new ArrayList<RemoteSimulatorThread>(count);
	
	public static void main(String arg[])
	{
		RemoteSimulatorMain rsm = new RemoteSimulatorMain();
		rsm.initpara(arg);

		rsm.start();
	}
	
	private void initpara(String arg[])
	{
		for ( int i = 0 ; i < arg.length ; i ++ )
		{
			if ( "-count".equals(arg[i]))
			{
				i ++ ;
				count = Integer.valueOf(arg[i]);
			}
			else if ( "-delay".equals(arg[i]))
			{
				i ++ ;
				delay = Integer.valueOf(arg[i]);
			}
			else if ( "-remoteip".equals(arg[i]))
			{
				i ++ ;
				remoteip = arg[i] ;
			}
			else if ( "-port".equals(arg[i]))
			{
				i ++ ;
				port = Integer.valueOf(arg[i]);
			}
		}
	}
	
	private void start()
	{
		for ( int i = 0 ; i < count ; i ++ )
		{
			RemoteSimulatorThread rst = new RemoteSimulatorThread(String.format("TiRemote2005000%05d", i) , delay , remoteip , port);
			
			executor.execute(rst);
			
			threadlst.add(rst);
//			try {
//				Thread.sleep(1 * 1000 );
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		
		for ( ; ; )
		{
			int lc = 0 ;
			int discount = 0 ;
			int report = 0 ;
			int notification = 0 ;
			int totalreport = 0 ;
			for ( RemoteSimulatorThread t : threadlst )
			{
				if ( t.isLogined())
					lc ++ ;
				discount += t.getDisconnect();
				report += t.getReport();
				totalreport += t.getTotalreport();
				notification += t.getNotification();
			}
			System.out.println(String.format("Logined : %d/%d , disconnect count %d , total report count %d , notification count %d , report count %d ,  %s", lc , threadlst.size() , discount , totalreport , notification, report, Utils.formatTime(new Date())));
			
			if ( discount > 1000 )
			{
				for ( int i = 1 ; i < threadlst.size() ; i ++  )
				{
					RemoteSimulatorThread t = threadlst.get(i);
					t.stop();
				}
			}
			
			try {
				Thread.sleep(1*1000);
			} catch (InterruptedException e) {
				return ;
			}
		}
	}
	

}

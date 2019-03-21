package com.iremote.infraredtrans;

import java.net.InetAddress;

import org.xsocket.connection.IServer;
import org.xsocket.connection.Server;
import org.xsocket.connection.IConnection.FlushMode;

public class RemoteServerTest {

	private static IServer srv;

	public static void main(String arg[])
	{
		try
		{
			InetAddress address=InetAddress.getByName("0.0.0.0"); 
			srv = new Server(address, 28921, new RemoteHandler2());
			srv.setFlushmode(FlushMode.ASYNC);
			srv.setConnectionTimeoutMillis(5000);
			srv.setIdleTimeoutMillis(180000);
			srv.start();
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
	}
}

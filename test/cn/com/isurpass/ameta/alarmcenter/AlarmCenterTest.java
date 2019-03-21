package cn.com.isurpass.ameta.alarmcenter;

import java.net.InetSocketAddress;
import java.net.Socket;

public class AlarmCenterTest 
{
	private static Socket socket;
	
	public static void main(String arg[])
	{
		String msg = "0x02<?xml version=\"1.0\"?>"
				+ "<Packet ID=\"527\">"
				+ "<Signal EvType=\"SYS\" Event=\"*A\">"
						+ "<Area>1</Area>"
						+ "<Zone>1</Zone>"
						+ "<Date>02/04/2018 15:30:23</Date>"
					+ "</Signal>"
				+ "</Packet>0x03";
		
		try
		{
			socket = new Socket();
			socket.connect(new InetSocketAddress("199.243.105.14", 31025 ));
			
			socket.getOutputStream().write(msg.getBytes());
			
			socket.getOutputStream().flush();
			
			Thread t = new Thread(new ReadDataRunner(socket));
			t.start();
		}
		catch(Throwable t )
		{
			t.printStackTrace();
		}
		
	}
	
	
}

package cn.com.isurpass.ameta.alarmcenter;

import java.net.Socket;

public class ReadDataRunner implements Runnable 
{
	private Socket socket;
	
	public ReadDataRunner(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() 
	{
		for ( ; socket.isConnected() && !socket.isClosed() ;)
		{

			try
			{
				byte[] b = new byte[socket.getInputStream().available()];
			
				socket.getInputStream().read(b);
				
				System.out.println(new String(b));
			}
			catch(Throwable t )
			{
				t.printStackTrace();
			}
		}

	}

}

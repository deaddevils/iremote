package com.iremote.remote;

import java.io.IOException;
import java.net.Socket;

import com.iremote.common.Utils;
import com.iremote.infraredtrans.RemoteHandler;
@SuppressWarnings("unused")
public class ReadThread implements Runnable {

	private Socket socket;
	
	public ReadThread(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() 
	{
		for ( ;; )
		{
			try {
				read();
				Thread.sleep(1000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private void read() throws IOException
	{
		int t = socket.getInputStream().available();
		if ( t == 0 )
			return ;
		byte[] b = new byte[t];
		socket.getInputStream().read(b);
		
		System.out.println("read");
		Utils.print("" , b, t);
	}
}

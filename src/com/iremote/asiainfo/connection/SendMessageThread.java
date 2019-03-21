package com.iremote.asiainfo.connection;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.Utils;

public class SendMessageThread implements Runnable {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(SendMessageThread.class);
	
	private WebSocketClientConnection conn ;
	private String message ;
	private byte[] bytemsg;
	private IOException exception ;

	public SendMessageThread(WebSocketClientConnection conn, byte[] message) {
		super();
		this.conn = conn;
		this.bytemsg = message;
	}
	
	public SendMessageThread(WebSocketClientConnection conn, String message) {
		super();
		this.conn = conn;
		this.message = message;
	}

	@Override
	public void run() 
	{
		if ( conn == null )
			return ;

		if ( message != null && message.length() > 0 )
			conn.send(message);
		else if ( bytemsg != null && bytemsg.length > 0 )
		{
			bytemsg = Utils.wrapRequest(bytemsg);
			Utils.print("write message" , bytemsg , bytemsg.length);

			conn.send(this.bytemsg);
		}
	}

	public IOException getException() {
		return exception;
	}

}

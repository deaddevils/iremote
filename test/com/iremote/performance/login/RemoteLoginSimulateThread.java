package com.iremote.performance.login;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.NonBlockingConnection;

import com.iremote.common.Utils;
import com.iremote.infraredtrans.RemoteHandler2;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
@SuppressWarnings("unused")
public class RemoteLoginSimulateThread implements Runnable ,IDataHandler ,IConnectHandler ,IDisconnectHandler {

	protected INonBlockingConnection socket;
	protected String deviceid;
	private boolean logined = false ;
	private Random rand ;
	private int disconnect = 0 ;

	public RemoteLoginSimulateThread(String deviceid) {
		super();
		this.deviceid = deviceid;
	}

	@Override
	public void run() 
	{
		rand = new Random(this.hashCode()) ;
//		try {
//			Thread.sleep(rand.nextInt(15) * 1000 );
//		} catch (InterruptedException e) {
//			return;
//		}
		
		for ( ; ; )
		{
			try
			{
				if (socket != null && socket.isOpen() == true )
					heartbeat();
				else
				{
					connect();
					heartbeat();
				}
			}
			catch(Throwable t)
			{
				//t.printStackTrace();
			}
			try {
				Thread.sleep(rand.nextInt(15) * 1000 );
			} catch (InterruptedException e) {
				return;
			}
		}
		
	}
	
	protected void connect() throws UnknownHostException, IOException, InterruptedException
	{
		socket = new NonBlockingConnection("127.0.0.1", 8921, this);
		//socket = new NonBlockingConnection("192.168.1.10", 8921, this);
		//socket = new NonBlockingConnection("test.isurpass.com.cn", 8921, this);
		socket.setIdleTimeoutMillis(18*1000);
	}
	
	private void login(byte reqeust[]) throws IOException, InterruptedException
	{
		byte[] t = TlvWrap.readTag(reqeust, 100, 4);

		CommandTlv rst = new CommandTlv(100 , 2);
		rst.addUnit(new TlvByteUnit(2,deviceid.getBytes()));
		rst.addUnit(new TlvIntUnit(105 , 20 , 2));
		write(rst);
	}
	
	protected void heartbeat() throws IOException, InterruptedException
	{
		CommandTlv rst = new CommandTlv(102 , 1);
		write(rst);
	}
	
	protected void write(CommandTlv ct) throws IOException
	{
		byte[] r = ct.getByte();
		r = Utils.wrapRequest(r);
		
		//System.out.println("write");
		//RemoteHandler.print(r, r.length);
	
		Utils.print("send ", r);
		socket.write(r);
		socket.flush();
	}

	@Override
	public boolean onDisconnect(INonBlockingConnection arg0) throws IOException {
		logined = false ;
		disconnect ++ ;
		return false;
	}

	public int getDisconnect() {
		return disconnect;
	}

	@Override
	public boolean onConnect(INonBlockingConnection arg0)
			throws IOException, BufferUnderflowException, MaxReadSizeExceededException {

		return false;
	}

	@Override
	public boolean onData(INonBlockingConnection nbc)
			throws IOException, BufferUnderflowException, ClosedChannelException, MaxReadSizeExceededException {
		ByteBuffer bf = ByteBuffer.allocate(1024);
		int length = nbc.read(bf);
		byte b[] = bf.array();
		byte[][] request = splitRequest(b , length);
		
		for ( int i = 0 ; i < request.length ; i ++ )
		{
			Utils.print("Receive", request[i]);
			processRequest(request[i]);
		}
		
		return true;
	}
	
	protected void processRequest(byte[] request) throws IOException
	{
		if (( request[0] & 0xff) == 100 && ( request[1] & 0xff) == 1 )
		{
			try {
				login(request);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else if ( ( request[0] & 0xff) == 100 && ( request[1] & 0xff) == 3 )
			logined = true ;
		else if ( ( request[0] & 0xff) == 4 && ( request[1] & 0xff) == 1 )
			infraredDeviceOperationResponse(request);
	}
	
	private void infraredDeviceOperationResponse(byte[] request) throws IOException
	{
		int s = TlvWrap.readInt(request, 31, 4);

		CommandTlv rst = new CommandTlv(4 , 2);
		rst.addUnit(new TlvIntUnit(31 , s , 2));
		rst.addUnit(new TlvIntUnit(1 , 0 , 2));
		write(rst);
	}
	
	protected byte[][] splitRequest(byte[] content, int length) 
	{
		List<byte[]> lst = new ArrayList<byte[]>();
		
		for ( int i = findRequestStart(content , 0 , length) ; i < length && i != -1 ; i = findRequestStart(content , i , length))
		{
			byte[] b = getRequestData(content , i , length);
			if ( b == null )
				break;
			b = unwrapRequest(b);
			lst.add(b);
			i += ( b.length + 2 ) ;
		}
		return lst.toArray(new byte[0][0]);
	}
	
	protected int findRequestStart(byte[] content , int index , int length)
	{
		for ( int i = index ; i < length -1 ; i ++ )
		{
			if ( content[i] == 0x7e && content[i+1] != 0x7e )
				return i ;
		}
		return -1 ;
	}
	
	protected byte[] getRequestData(byte[] content , int index , int length)
	{
		int e = findRequestEnd(content , index , length );
		if ( e == -1 )
			return null ;
		int l = e - index - 1;
		if ( l == 0 )
			return null ;
		byte[] b = new byte[l];
		System.arraycopy(content, index + 1 , b, 0, l);
		return b ;
	}
	
	protected int findRequestEnd(byte[] content , int index , int length)
	{
		for ( int i = index + 1 ; i < length ; i ++ )
		{
			if ( content[i] == 0x7e )
				return i ;
		}
		
		return -1 ;
	}
	
	public byte[] unwrapRequest(byte[] b)
	{
		byte[] ub = new byte[b.length];
		
		for ( int i = 0 , ui = 0 ; i < b.length ; i ++ , ui ++ )
		{
			if ( b[i] == 0x7d )
			{
				i++ ;
				if ( b[i] == 0x01 )
					ub[ui] = 0x7d;
				else if ( b[i] == 0x02 )
					ub[ui] = 0x7e ;
			}
			else 
				ub[ui] = b[i];
		}
		return ub;
	}
	
	public boolean isLogined() {
		return logined;
	}
}

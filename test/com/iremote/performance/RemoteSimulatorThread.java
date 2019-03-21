package com.iremote.performance;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.IConnectHandler;
import org.xsocket.connection.IDataHandler;
import org.xsocket.connection.IDisconnectHandler;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.NonBlockingConnection;

import com.iremote.common.Utils;
import com.iremote.infraredtrans.RemoteHandler;
import com.iremote.infraredtrans.RemoteHandler2;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;

@SuppressWarnings("unused")
public class RemoteSimulatorThread implements Runnable ,IDataHandler ,IConnectHandler ,IDisconnectHandler{

	private static CommandTlv[] DEVICE_REPORT ;
	
	private INonBlockingConnection socket;
	private RemoteHandler2 rh2 = new RemoteHandler2();
	
	private int delay = 30 ;
	private String deviceid;
	private int count = 0 ;
	private String remoteip = "test.isurpass.com.cn" ;
	private int port = 8921;
	private int heartbeatcount = 0 ;
	
	private boolean logined = false ;
	private int disconnect = 0 ;
	private int totalreport = 0 ;
	private int notification = 0 ;
	private int report = 0 ;
	
	private Random rand ;
	
	private boolean run = true;
	
	static 
	{
		List<CommandTlv> lst = new ArrayList<CommandTlv>();
		
		CommandTlv t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 2 , 2));										//nuid = 2 , type = 1 ;
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x9c,(byte)0x02, 0x02,(byte)0x00 , (byte)0xff}));  //Tamper Alarm
		lst.add(t);
		
		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 2 , 2));										
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x9c,(byte)0x02, 0x02,(byte)0x01 , (byte)0xff}));  //SMOKE Alarm
		lst.add(t);

		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 3 , 2));										//nuid = 3 , type = 3 ;
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x9c,(byte)0x02, 0x03,(byte)0x02 , (byte)0xff}));  //GAS LEAK Alarm
		lst.add(t);

		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 4 , 2));										//nuid = 4 , type = 2 ;
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x9c,(byte)0x02,0x04,(byte)0x05 ,(byte)0xff}));  //WATER LEAK Alarm
		lst.add(t);

		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 4 , 2));											
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x80,(byte)0x03,(byte)0xff}));  //Battery low Alarm
		lst.add(t);
				
		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 5 , 2));										  //nuid = 5 , type = 4 ;
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x20,(byte)0x01,(byte)0xff}));  //Door sensor open 
		lst.add(t);
		
		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 5 , 2));
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x20,(byte)0x01,(byte)0x00}));  //Door sensor close 
		lst.add(t);
						
		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 7 , 2));										  //nuid = 7 , type = 6
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x30,(byte)0x03,(byte)0xff}));  //move in
		lst.add(t);
		
		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 7 , 2));										  //nuid = 7 , type = 6
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x30,(byte)0x03,(byte)0x00}));  //move out
		lst.add(t);
		
		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 8 , 2));										  //nuid = 8 	type = 5
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x62,(byte)0x03,(byte)0xff}));  //door lock close
		lst.add(t);
		
		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 8 , 2));										  //nuid = 8 
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x62,(byte)0x03,(byte)0x00}));  //door lock open
		lst.add(t);
	
		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 6 , 2));											//nuid = 6 , type = 3 ;
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x20,(byte)0x03,(byte)0xff}));  //alarm
		lst.add(t);
		
		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 4 , 2));
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x80,(byte)0x03,(byte)0x90}));  //Battery 
		lst.add(t);

		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 9 , 2));										  //nuid = 9 , type = 7 
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x25,(byte)0x03,(byte)0xff}));  //switch on
		lst.add(t);
		
		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 9 , 2));										  //nuid = 9 , type = 7
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x25,(byte)0x03,(byte)0x00}));  //switch off
		lst.add(t);
		
		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 10 , 2));										  //nuid = 10 , type = 9 
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x60,(byte)0x0d,(byte)0x01 , 0 , 0x25 , 0x03 , 0x00 ,(byte)0xff}));  // channel 1 switch on
		lst.add(t);
		
		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 10 , 2));										  //nuid = 10 , type = 9 
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x60,(byte)0x0d,(byte)0x01 , 0 , 0x25 , 0x03 , 0x00 ,(byte)0x00}));  // channel 1 switch off
		lst.add(t);
		
		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 10 , 2));										  //nuid = 10 , type = 9 
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x60,(byte)0x0d,(byte)0x02 , 0 , 0x25 , 0x03 , 0x00 ,(byte)0xff}));  // channel 2 switch on
		lst.add(t);
		
		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 10 , 2));										  //nuid = 10 , type = 9 
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x60,(byte)0x0d,(byte)0x02 , 0 , 0x25 , 0x03 , 0x00 ,(byte)0x00}));  // channel 2 switch off
		lst.add(t);
		
		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 10 , 2));										  //nuid = 10 , type = 9 
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x60,(byte)0x0d,(byte)0x03 , 0 , 0x25 , 0x03 , 0x00 ,(byte)0xff}));  // channel 3 switch on
		lst.add(t);
		
		t = new CommandTlv(30 , 9);
		t.addUnit(new TlvIntUnit(71 , 10 , 2));										  //nuid = 10 , type = 9 
		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x60,(byte)0x0d,(byte)0x03 , 0 , 0x25 , 0x03 , 0x00 ,(byte)0x00}));  // channel 3 switch off
		lst.add(t);
		
//		t = new CommandTlv(30 , 9);
//		t.addUnit(new TlvIntUnit(71 , 11 , 2));										  //nuid = 11	type = 1 
//		t.addUnit(new TlvByteUnit(70,new byte[]{(byte)0x71,(byte)0x05,(byte)0x03}));  //Tamper Alarm
		lst.add(t);
		
		DEVICE_REPORT = lst.toArray(new CommandTlv[0]);
	}
	
	public RemoteSimulatorThread(String deviceid , int delay , String remoteip , int port) {
		super();
		this.delay = delay - 1;
		this.deviceid = deviceid;
		count = delay - 2 ;
		if ( StringUtils.isNotEmpty(remoteip))
			this.remoteip = remoteip ;
		if ( port != 0 )
			this.port = port ;
	}

	@Override
	public void run()
	{
		rand = new Random(this.hashCode()) ;
		try {
			Thread.sleep(rand.nextInt(15) * 1000 );
		} catch (InterruptedException e) {
			
		}
		
		count = rand.nextInt(delay);
		
		for ( ; run ; )
		{
			tick();
			try
			{
				Thread.sleep(1 * 1000 );
			}
			catch(Throwable t )
			{
				//t.printStackTrace();
			}
		}
	}
	
	public void init()
	{
		rand = new Random(this.hashCode()) ;
		count = rand.nextInt(delay);
		heartbeatcount = rand.nextInt(15);
	}
	
	public void tick()
	{
		try
		{
			if (socket != null && socket.isOpen() == true )
			{
				count ++ ;
				if ( count >= delay )
				{
					int index = sendRandomreport();
					count = (rand.nextInt(3) - 1 ) * rand.nextInt(3);
					report ++ ;
					totalreport ++ ;
					if ( index <= 10 )
						notification ++ ;
				}
				
				heartbeatcount ++ ;
				if ( heartbeatcount >= 15 )
				{
					heartbeat();
					heartbeatcount = 0 ;
				}
			}
			else
			{
				connect();
				heartbeat();
			}
		}
		catch(Throwable t )
		{
			
		}
	}
	
	public void stop()
	{
		run = false ;
	}
	
	public void run1() 
	{
		try
		{
			connect();
			login();
			setowner();
			
			for ( ; ; )
			{
				count ++ ;
				if ( count >= delay )
				{
					sendRandomreport();
					count = 0 ;
				}
				
				heartbeatcount ++ ;
				if ( heartbeatcount >= 15 )
				{
					heartbeat();
					heartbeatcount = 0 ;
				}
				
				Thread.sleep(1 * 1000 );
				//Thread.sleep(delay  * 1000 );
			}
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
	}

	private void connect() throws UnknownHostException, IOException, InterruptedException
	{
		//socket = new NonBlockingConnection("120.24.52.86" , 18921);
		//socket = new NonBlockingConnection("127.0.0.1" , 8921 , this);
		//socket = new NonBlockingConnection("test.isurpass.com.cn" , 18921 , this);
		
		socket = new NonBlockingConnection(remoteip , port , this);
		socket.setIdleTimeoutMillis(18 * 1000);
		
		sendRandomreport();
	}
	
	private void login() throws IOException, InterruptedException
	{
		CommandTlv rst = new CommandTlv(100 , 2);
		rst.addUnit(new TlvByteUnit(2,deviceid.getBytes()));
		rst.addUnit(new TlvIntUnit(105 , 20 , 2));
		write(rst);
	}
	
	private void heartbeat() throws IOException, InterruptedException
	{
		CommandTlv rst = new CommandTlv(102 , 1);
		write(rst);
	}
	
	private void setowner() throws IOException
	{
		CommandTlv rst = new CommandTlv(101 , 3);
		rst.addUnit(new TlvByteUnit(12, String.format("13512340%03d", Integer.valueOf(deviceid.substring(deviceid.length() - 3)).intValue() ).getBytes()));
		rst.addUnit(new TlvByteUnit(13, ("ssid" + deviceid.substring(deviceid.length() - 3)).getBytes()));
		rst.addUnit(new TlvIntUnit(14 , 114044323 , 4));
		rst.addUnit(new TlvIntUnit(15 , 22645638 , 4));
		rst.addUnit(new TlvByteUnit(13, "127.0.0.1".getBytes()));
		rst.addUnit(new TlvByteUnit(18, "EA:23:54:19:34".getBytes()));
		write(rst);
	}
	
	private void write(CommandTlv ct) throws IOException
	{
		byte[] r = ct.getByte();
		r = Utils.wrapRequest(r);
		
//		System.out.println("write");
//		RemoteHandler.print(r, r.length);
		
		socket.write(r);
		socket.flush();
	}
	
	private int sendRandomreport() throws IOException
	{
		int index = rand.nextInt(DEVICE_REPORT.length);
		CommandTlv tlv = DEVICE_REPORT[ index ]  ;
		write(tlv);
		
		return index ;
	}

	@Override
	public boolean onDisconnect(INonBlockingConnection arg0) throws IOException {
		logined = false ;
		disconnect ++ ;
		socket = null ;
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
			if (( request[i][0] & 0xff) == 100 && ( request[i][1] & 0xff) == 1 )
			{
				try {
					login(request[i]);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else if ( ( request[i][0] & 0xff) == 100 && ( request[i][1] & 0xff) == 3 )
				logined = true ;
		}
		
		return true;
	}
	
	private void login(byte reqeust[]) throws IOException, InterruptedException
	{
		byte[] t = TlvWrap.readTag(reqeust, 100, 4);

		CommandTlv rst = new CommandTlv(100 , 2);
		rst.addUnit(new TlvByteUnit(2,deviceid.getBytes()));
		rst.addUnit(new TlvIntUnit(105 , 20 , 2));
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

	public int getReport() {
		int r = report ;
		report = 0 ;
		return r;
	}
	
	public int getTotalreport()
	{
		return totalreport;
	}

	public int getNotification() {
		return notification;
	}
}

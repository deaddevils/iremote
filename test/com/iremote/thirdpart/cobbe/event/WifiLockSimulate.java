package com.iremote.thirdpart.cobbe.event;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.BufferUnderflowException;
import java.nio.channels.ClosedChannelException;

import org.xsocket.MaxReadSizeExceededException;
import org.xsocket.connection.INonBlockingConnection;
import org.xsocket.connection.NonBlockingConnection;

import com.iremote.common.Utils;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.infraredtrans.tlv.TlvWrap;
import com.iremote.performance.login.RemoteLoginSimulateThread;
@SuppressWarnings("unused")
public class WifiLockSimulate extends RemoteLoginSimulateThread
{
	//private String url = "test.isurpass.com.cn";
	private String url = "127.0.0.1";
	private String defaultdeviceid = "iRemote3006100000242";
	//private String defaultdeviceid = "iRemote3006100000002";
	private boolean hasSetOwn = false ;
	private String phonenumber = "86:13502876070";
	private String ssid = "mockssid";
	private String ip = "192.168.1.100";
	private String mac = "xxxxxxxxxxxx";
	private int homeid = 1;
	private Byte[] SET_PASSWORD_REQUEST = new Byte[]{(byte)128, 7, 0, (byte)128, 16 ,1,1};
	private Byte[] DELETE_PASSWORD_REQUEST = new Byte[]{(byte)0x80,0x07,0x00,(byte)0x80,0x10,0x01,0x09};
	private Byte[] SET_FINGERPRINT_REQUEST = new Byte[]{(byte)0x80,0x07,0x00,(byte)0xA0,0x10,0x01,0x07};
	private Byte[] DELETE_FINGERPRINT_REQUEST = new Byte[]{(byte)0x80,0x07,0x00,(byte)0xA0,0x10,0x01,0x09};
	
	private byte[] NEW_CARD_REPORT = new byte[]{(byte)0x80,0x09 ,0x07 ,(byte)0xff,(byte)0xff,0x1,0x20,0x21,0x22,0x23,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,0x03};
	private byte[] DOOR_OPEN_BY_CARD_REPORT = new byte[]{(byte)0x80,0x09 ,0x07 ,0x01,0x00,0x1,0x20,0x21,0x22,0x23,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x03};

	private Byte[] ADD_CARD_REQUEST = new Byte[]{(byte)0x80,0x09 ,0x01 ,(byte)0xff,(byte)0xff};
	private byte[] ADD_CARD_REPORT = new byte[]{(byte)0x80,0x09 ,0x02 ,0x01,0x00,0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
	
	private Byte[] DELETE_CARD_REQUEST = new Byte[]{(byte)0x80,0x09 ,0x03 };
	private byte[] DELETE_CARD_RESPONSE = new byte[]{(byte)0x80,0x09 ,0x04 ,0x01,0x00,0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
	
	private byte[] OPEN_REPORT  = new byte[]{98, 3 ,1};
	private byte[] CLOSE_REPORT  = new byte[]{98 ,3 ,(byte)255};
	
	
	public WifiLockSimulate(String deviceid)
	{
		super(deviceid);
		if ( super.deviceid == null || super.deviceid.length() == 0 )
			super.deviceid = defaultdeviceid ;
	}

	public static void main(String arg[])
	{
		try
		{
			String d = null ;
			if ( arg != null && arg.length > 0 )
				d = arg[0] ;
			WifiLockSimulate srs = new WifiLockSimulate(d);
			srs.connect();

			for ( ; ; )
			{
				srs.heartbeat();
				Utils.sleep(5 * 1000);
//				if ( srs.DOOR_OPEN_BY_CARD_REPORT != null )
//				{
//					srs.sendReport(srs.DOOR_OPEN_BY_CARD_REPORT , 4);
//					//srs.DOOR_OPEN_BY_CARD_REPORT = null ;
//					
//				}
			}
		}
		catch(Throwable t )
		{
			t.printStackTrace();
		}
		
	}
	
	public void reportafterSleepSimulite() throws IOException
	{
		Utils.sleep(1*1000);
		sendReport(OPEN_REPORT , 10);
		Utils.sleep(2*1000);
		sendSleepReport();
		Utils.sleep(2*1000);
		sendReport(CLOSE_REPORT , 12);
	}
	
	private void sendSleepReport() throws IOException
	{
		CommandTlv rst = new CommandTlv(104 , 3);
		write(rst);
	}
	
	private void sendReport(byte[] report , int sequence) throws IOException
	{
		CommandTlv rst = new CommandTlv(30 , 9);
		rst.addUnit(new TlvByteUnit(70,report));
		rst.addUnit(new TlvIntUnit(71 ,11201, 4));
		rst.addUnit(new TlvIntUnit(104 , 0 , 4));
		rst.addUnit(new TlvIntUnit(31 ,sequence, 2));
		
		write(rst);
	}
	
	@Override
	public boolean onData(INonBlockingConnection nbc)
			throws IOException, BufferUnderflowException, ClosedChannelException, MaxReadSizeExceededException 
	{
		boolean bl = super.onData(nbc);
		
//		if ( super.isLogined() == true && hasSetOwn == false  )
//			setRemoteOwn();
		
		if ( super.isLogined() == true && hasSetOwn == false  )
		{
			Thread t = new Thread(new ReportafterSleepSimulite(this));
//			t.start();
			
			hasSetOwn = true;
		}
		
		return bl ;
	}
	
	@Override
	protected void processRequest(byte[] request) throws IOException
	{
		if ( Utils.isByteMatch(new Byte[]{(byte)0xfe , (byte)0xf0}, request)
				|| Utils.isByteMatch(new Byte[]{(byte)0xfe , (byte)0xf2}, request))
		{
			CommandTlv rst = new CommandTlv(0xfe , request[1] & 0xff);
			if ( TlvWrap.readInteter(request, 1, 4) != null )
				rst.addUnit(new TlvIntUnit(1,0,2));
			write(rst);
		}
		byte[] cmd = TlvWrap.readTag(request, 70, 4);
		if ( cmd != null && Utils.isByteMatch(SET_PASSWORD_REQUEST, cmd))
		{
			//Utils.sleep(5000);
			reposeack(request);
			
			
			byte userid = cmd[7];
			if ( ( userid & 0xff ) == 0xff )
				userid = 10 ;
			byte[] response = new byte[]{(byte)0x80,0x07,0x00,(byte)0x80,0x10,0x01,0x03,userid,0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
			CommandTlv rst = new CommandTlv(30 , 9);
			rst.addUnit(new TlvByteUnit(70,response));
			rst.addUnit(new TlvIntUnit(71 ,TlvWrap.readInt(request, 71, 4), 4));
			rst.addUnit(new TlvIntUnit(104 , 0 , 4));
		
			write(rst);
		}
		else if ( cmd != null &&  Utils.isByteMatch(DELETE_PASSWORD_REQUEST, cmd))
		{
			reposeack(request);
			
			Utils.sleep(5000);
			byte userid = cmd[7];
			if ( ( userid & 0xff ) == 0xff )
				userid = 10 ;
			byte[] response = new byte[]{(byte)0x80,0x07,0x00,(byte)0x80,0x10,0x01,0x0A , userid , 0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
			CommandTlv rst = new CommandTlv(30 , 9);
			rst.addUnit(new TlvByteUnit(70,response));
			rst.addUnit(new TlvIntUnit(71 ,TlvWrap.readInt(request, 71, 4), 4));
			rst.addUnit(new TlvIntUnit(104 , 0 , 4));
			
			write(rst);
		}
		else if ( cmd != null &&  Utils.isByteMatch(SET_FINGERPRINT_REQUEST, cmd))
		{
			Utils.sleep(2000);
			reposeack(request);
			
			Utils.sleep(5000);
			
			byte[] response = new byte[]{(byte)0x80,0x07,0x00,(byte)0xA0,0x10,0x01,0x08,0x20,0x03,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
			CommandTlv rst = new CommandTlv(30 , 9);
			rst.addUnit(new TlvByteUnit(70,response));
			rst.addUnit(new TlvIntUnit(71 ,TlvWrap.readInt(request, 71, 4), 4));
			rst.addUnit(new TlvIntUnit(104 , 0 , 4));
			
			write(rst);
			
			Utils.sleep(5000);
			
			response = new byte[]{(byte)0x80,0x07,0x00,(byte)0xA0,0x10,0x01,0x08,0x20,0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
			rst = new CommandTlv(30 , 9);
			rst.addUnit(new TlvByteUnit(70,response));
			rst.addUnit(new TlvIntUnit(71 ,TlvWrap.readInt(request, 71, 4), 4));
			rst.addUnit(new TlvIntUnit(104 , 0 , 4));
			
			write(rst);
		}
		else if ( cmd != null &&  Utils.isByteMatch(DELETE_FINGERPRINT_REQUEST, cmd))
		{
			Utils.sleep(2000);
			reposeack(request);
			
			byte userid = cmd[7];
			byte[] response = new byte[]{(byte)0x80,0x07,0x00,(byte)0xA0,0x10,0x01,0x0A,userid , 0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
			CommandTlv rst = new CommandTlv(30 , 9);
			rst.addUnit(new TlvByteUnit(70,response));
			rst.addUnit(new TlvIntUnit(71 ,TlvWrap.readInt(request, 71, 4), 4));
			rst.addUnit(new TlvIntUnit(104 , 0 , 4));
			
			write(rst);
		}
		else if ( cmd != null && Utils.isByteMatch(ADD_CARD_REQUEST, cmd))
		{
			Utils.sleep(2000);
			reposeack(request);
			
			CommandTlv rst = new CommandTlv(30 , 9);
			rst.addUnit(new TlvByteUnit(70,ADD_CARD_REPORT));
			rst.addUnit(new TlvIntUnit(71 ,TlvWrap.readInt(request, 71, 4), 4));
			rst.addUnit(new TlvIntUnit(104 , 0 , 4));
			
			write(rst);
		}
		else if ( cmd != null && Utils.isByteMatch(DELETE_CARD_REQUEST, cmd))
		{
			Utils.sleep(2000);
			reposeack(request);
			
			CommandTlv rst = new CommandTlv(30 , 9);
			rst.addUnit(new TlvByteUnit(70,DELETE_CARD_RESPONSE));
			rst.addUnit(new TlvIntUnit(71 ,TlvWrap.readInt(request, 71, 4), 4));
			rst.addUnit(new TlvIntUnit(104 , 0 , 4));
			write(rst);
		}
		else
		{
			super.processRequest(request);
			//reposeack(request);
		}
	}
	
	private void reposeack(byte[] request) throws IOException
	{
		CommandTlv ack = new CommandTlv(30 , 8);
		
		ack.addUnit(new TlvIntUnit(1 , 0 , 4));
		ack.addUnit(new TlvIntUnit(31 ,TlvWrap.readInt(request, 31, 4), 2));
		write(ack);
	}
	
	private void setRemoteOwn() throws IOException
	{
		hasSetOwn = true ;
		
		CommandTlv rst = new CommandTlv(101 , 3);
		rst.addUnit(new TlvByteUnit(12,phonenumber.getBytes()));
		rst.addUnit(new TlvByteUnit(13,ssid.getBytes()));
		rst.addUnit(new TlvIntUnit(14 , 0 , 2));
		rst.addUnit(new TlvIntUnit(15 , 0 , 2));
		rst.addUnit(new TlvByteUnit(17,ip.getBytes()));
		rst.addUnit(new TlvByteUnit(18,mac.getBytes()));
		rst.addUnit(new TlvIntUnit(15 , homeid , 2));
		write(rst);
	}
	
	protected void connect() throws UnknownHostException, IOException, InterruptedException
	{
		//socket = new NonBlockingConnection("127.0.0.1", 8921, this);
		//socket = new NonBlockingConnection("192.168.1.10", 8921, this);
		socket = new NonBlockingConnection(url, 8921, this);
		socket.setIdleTimeoutMillis(18*1000);
	}
}

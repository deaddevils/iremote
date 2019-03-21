package com.iremote.remote;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.hibernate.Transaction;

import com.iremote.common.Utils;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.Remote;
import com.iremote.infraredtrans.RemoteHandler;
import com.iremote.infraredtrans.RemoteHandler2;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvByteUnit;
import com.iremote.infraredtrans.tlv.TlvIntUnit;
import com.iremote.service.IremotepasswordService;
@SuppressWarnings("unused")
public class RemoteTest 
{
	private Socket socket;
	
	private static String deviceid = "iRemote2005000000062";
	private static byte[] sk = new byte[]{(byte)0xd5,(byte)0x23,(byte)0xca,(byte)0x91,(byte)0x3b,(byte)0x0b,(byte)0x96,(byte)0x45,(byte)0x20,(byte)0x1e,(byte)0xbf,(byte)0x0d,(byte)0x13,(byte)0xde,(byte)0x18,(byte)0xea};

	public static void main(String[] arg) throws Exception
	{
		if ( arg.length >= 1 )
			deviceid = arg[0] ;
		
		if ( arg.length > 1 )
		{
			sk = new byte[arg.length - 1 ];
			for ( int i = 1 ; i < arg.length ; i ++ )
				sk[i -1 ] = Integer.valueOf(arg[i], 16).byteValue();
		}
		
		updateRemoteSecurityKey();
		readRemoteSecurityKey();
//		if ( 1 == 1 )
//			return ;
		RemoteTest tt = new RemoteTest();
		tt.connect();
		tt.login();
		//tt.tempalarm();
		
		tt.setonwer();
	}
	
	private static void updateRemoteSecurityKey()
	{
		HibernateUtil.beginTransaction();
		IremotepasswordService svr = new IremotepasswordService();
		Remote remote = svr.getIremotepassword(deviceid);
		remote.setSecritykey(sk);
		
		HibernateUtil.commit();
		HibernateUtil.closeSession();
		
	}
	
	private static void readRemoteSecurityKey()
	{
		IremotepasswordService svr = new IremotepasswordService();
		Remote remote = svr.getIremotepassword(deviceid);
		
		for ( int i = 0 ; i < remote.getSecritykey().length ; i ++ )
		{
			System.out.print( Integer.toHexString(remote.getSecritykey()[i] & 0xff));
			System.out.print(" ");
		}
		
		HibernateUtil.closeSession();
		
	}
	
	private void connect() throws UnknownHostException, IOException, InterruptedException
	{
		socket = new Socket("127.0.0.1" , 8921);
		//Thread.sleep(1500);
		waitforresponse();
		
		ReadThread rt = new ReadThread(socket);
		
		Thread t = new Thread(rt);
		t.start();
	}
	
	private void login() throws IOException, InterruptedException
	{
		CommandTlv rst = new CommandTlv(100 , 2);
		rst.addUnit(new TlvByteUnit(2,"iRemote2005000000010".getBytes()));
		
		write(rst);
		
		waitforresponse();
		
	}
	
	private void tempalarm() throws IOException, InterruptedException
	{
		CommandTlv rst = new CommandTlv(30 , 9);
		rst.addUnit(new TlvIntUnit(71,20,4));
		rst.addUnit(new TlvByteUnit(70,new byte[]{(byte)156,2,40,0,(byte)255,0,0}));
		rst.addUnit(new TlvByteUnit(104,new byte[]{85 ,54 ,50, (byte)246}));
		write(rst);
		
		waitforresponse();
	}
	
	private void setonwer() throws IOException, InterruptedException
	{
		CommandTlv rst = new CommandTlv(101 , 3);
		rst.addUnit(new TlvByteUnit(12,"13502876070".getBytes()));
		rst.addUnit(new TlvByteUnit(13,"wifissid".getBytes()));
		rst.addUnit(new TlvIntUnit(14,1111111,4));
		rst.addUnit(new TlvIntUnit(15,1111112,4));
		rst.addUnit(new TlvByteUnit(17,"192.168.1.23".getBytes()));
		rst.addUnit(new TlvByteUnit(18,"EA:23:54:19:34".getBytes()));
		rst.addUnit(new TlvIntUnit(73,123,4));
		
		write(rst);
		
		waitforresponse();
	}
	
	private void waitforresponse() throws InterruptedException
	{
		Thread.sleep(1500);
	}
	
	private void write(CommandTlv ct) throws IOException
	{
		byte[] r = ct.getByte();
		r = Utils.wrapRequest(r);
		
		System.out.println("write");
		Utils.print("" , r, r.length);
		
		socket.getOutputStream().write(r);
		socket.getOutputStream().flush();
	}
}

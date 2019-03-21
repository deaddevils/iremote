package com.iremote.asiainfo.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.iremote.asiainfo.vo.AsiainfoMessage;

public class AsiainfoMessageHelper {

	private static int sequence = 1;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static ByteWrap encodeMessage(AsiainfoMessage head)
	{
		ByteWrap bw = new ByteWrap(64 + head.getMessagelength() + head.getMessageinfolength() + 1);
		bw.appendint(head.getProtocol(), 1);
		bw.appendint(head.getVersion(), 2);
		bw.appendint(head.getMessageid() , 2);
		bw.appendint(head.getEncryptflag() , 1);
		bw.appendint(head.getMessagelength(), 2);
		bw.appendint(head.getMessageinfolength(), 4);
		bw.appendString(head.getSequence(), 22);
		bw.appendint(head.getPackagecount(), 2);
		bw.appendint(head.getPackagenumber(), 2);
		bw.appendString(head.getClientid(), 20);
		bw.appendString(head.getManufactorid()	, 5);
		bw.appendint(head.getServicetype(), 1);
		bw.appendbytes(head.getMessage(), head.getMessagelength());
		bw.appendbytes(head.getBytemessageinfo(), head.getMessageinfolength());
		bw.appendbyte((byte)0);//Parity Byte 
		return bw;
	}
	
	public static AsiainfoMessage decodeMessage(ByteWrap bw)
	{
		AsiainfoMessage head = new AsiainfoMessage();
		head.setProtocol(bw.getint(1));
		head.setVersion(bw.getint(2));
		head.setMessageid(bw.getint(2));
		head.setEncryptflag(bw.getint(1));
		head.setMessagelength(bw.getint(2));
		head.setMessageinfolength(bw.getint(4));
		head.setSequence(bw.getString(22));
		head.setPackagecount(bw.getint(2));
		head.setPackagenumber(bw.getint(2));
		head.setClientid(bw.getString(20));
		head.setManufactorid(bw.getString(5));
		head.setServicetype(bw.getint(1));
		head.setMessage(bw.getbytes(head.getMessagelength()));
		head.setMessageinfo(bw.getString(head.getMessageinfolength()));
		return head ;
	}
	
	public static AsiainfoMessage createMessageHead(int messageid)
	{
		AsiainfoMessage head = new AsiainfoMessage();

		head.setMessageid(messageid);
		head.setSequence(createMessageSequence());

		return head;
	}
	
	public static AsiainfoMessage createMessageHead(int messageid , String deviceid)
	{
		AsiainfoMessage message = createMessageHead(messageid);
		message.setClientid(deviceid);
		return message ;
	}
	
	public static String createMessageSequence()
	{
		int s = 0 ;
		String t ;
		synchronized(AsiainfoMessageHelper.class)
		{
			s = sequence++;
			if ( sequence > 9999999 )
				sequence = 1 ;
			t = sdf.format(new Date());
		}
		
		return String.format("R%s%07d", t,s);
		
	}
	
	public static void main(String arg[])
	{
		sequence = 9999995;
		for ( int i = 0 ; i < 10 ; i ++ )
			System.out.println(createMessageSequence());
	}
}

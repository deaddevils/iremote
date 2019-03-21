package com.iremote.infraredtrans;

import org.xsocket.connection.INonBlockingConnection;

public class XSocketConnectionWrap2 extends XSocketConnectionWrap {

	public XSocketConnectionWrap2(INonBlockingConnection nbc) 
	{
		super(nbc);
	}
	
	@Override
	protected byte[] wrapRequest(byte[] b) 
	{
		int c = 0 ;
		for ( int i = 0 ; i < b.length - 1 ; i ++ )
			c =  ( c ^ b[i]);
		b[b.length -1] = (byte)c;
		
		byte[] wb = new byte[b.length * 2 + 2];
		
		wb[0] = 0x7e;
		int i = 0 , wi = 1;
		for (  ; i < b.length ; i ++ , wi ++ )
		{
			if ( b[i] == 0x7e )
			{
				wb[wi++] = 0x7d;
				wb[wi] = 0x02;
			}
			else if ( b[i] == 0x7d )
			{
				wb[wi++] = 0x7d;
				wb[wi] = 0x01;
			}
			else 
				wb[wi] = b[i];
		}
		wb[wi] = 0x7e;
		byte[] rb = new byte[wi + 1];
		System.arraycopy(wb, 0, rb, 0, wi+1);
		
		return rb;
	}
}

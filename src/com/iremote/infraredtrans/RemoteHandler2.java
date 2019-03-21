package com.iremote.infraredtrans;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.List;

import org.xsocket.connection.INonBlockingConnection;

public class RemoteHandler2 extends RemoteHandler {

	//private static Log log = LogFactory.getLog(RemoteHandler2.class);
	
	@Override
	protected int getSoftversion()
	{
		return 2;
	}
	
	@Override
	protected IConnectionContext createConnectionContext(INonBlockingConnection nbc)
	{
		return new XSocketConnectionWrap2(nbc);
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

	@Override
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

	@Override
	protected void processRequest(byte[][] request, INonBlockingConnection nbc)
			throws BufferOverflowException, IOException
	{
		for ( int i = 0 ; i < request.length ; i ++ )
			XSocketReportProcessor.getInstance().processRequest(request[i], createConnectionContext(nbc) );
	}
}

package com.iremote.infraredtrans;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xsocket.connection.INonBlockingConnection;

import com.iremote.common.Utils;
import com.iremote.infraredtrans.tlv.CommandTlv;

public class XSocketConnectionWrap implements IConnectionContext 
{
	private static Log log = LogFactory.getLog(XSocketConnectionWrap.class);
	
	private INonBlockingConnection nbc;

	public XSocketConnectionWrap(INonBlockingConnection nbc) 
	{
		super();
		this.nbc = nbc;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if ( !this.getClass().isInstance(obj))
			return false ;
		XSocketConnectionWrap xsc = (XSocketConnectionWrap)obj;
		return this.nbc.equals(xsc.nbc);
	}

	@Override
	public void close() throws IOException 
	{
		nbc.close();
	}

	@Override
	public Remoter getAttachment() 
	{
		return (Remoter)nbc.getAttachment();
	}
	
	@Override
	public void setAttachment(Remoter obj) 
	{
		nbc.setAttachment(obj);
	}
	
	@Override
	public String getRemoteAddress() 
	{
		return nbc.getRemoteAddress().getHostAddress();
	}

	@Override
	public int getRemotePort() {
		
		return nbc.getRemotePort();
	}

	@Override
	public void setIdleTimeoutMillis(int timeoutmillis) 
	{
		nbc.setIdleTimeoutMillis(timeoutmillis);
	}

	public INonBlockingConnection getNbc() 
	{
		return nbc;
	}

	@Override
	public void flush()
	{
		try
		{
			nbc.flush();
		}
		catch (Throwable t)
		{
			log.error(t.getMessage() , t);
		}

	}

	@Override
	public String getDeviceid() 
	{
		if ( nbc == null || nbc.getAttachment() == null )
			return "";
		Remoter r = (Remoter)nbc.getAttachment();
		return r.getUuid();
//		if ( r.isHaslogin() == true )
//			return r.getUuid();
//		return r.getToken();
	}
	
	public void sendData(CommandTlv ct) 
	{
		byte[] r = ct.getByte();
		if ( r == null )
			return;
		r = wrapRequest(r);
		
		if ( nbc.isOpen() == false )
			return ;

		if ( log.isInfoEnabled())
			Utils.print(String.format("Send data to %s(%d)" , this.getAttachment().getUuid() , this.getConnectionHashCode()),r );
		try
		{
			nbc.write(r);
		}
		catch (Throwable e)
		{
			log.error(e.getMessage() , e);
		}

	}

	protected byte[] wrapRequest(byte[] b)
	{
		int c = 0 ;
		for ( int i = 4 ; i < b.length - 1 ; i ++ )
			c =  ( c ^ b[i]);
		b[b.length -1] = (byte)c;
		return b;
	}

	public boolean isOpen()
	{
		return nbc.isOpen();
	}

	@Override
	public long getIdleTimeoutMillis() {
		return nbc.getIdleTimeoutMillis();
	}
	@Override
	public boolean isTimeout()
	{
		return false;
	}

	@Override
	public int getConnectionHashCode() 
	{
		if ( nbc != null )
			return nbc.hashCode();
		return 0;
	}
}

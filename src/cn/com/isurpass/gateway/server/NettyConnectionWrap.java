package cn.com.isurpass.gateway.server;

import java.io.IOException;

import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.tlv.CommandTlv;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public class NettyConnectionWrap implements IConnectionContext
{
	private static AttributeKey<Remoter> CONNECTION_ATTR = AttributeKey.valueOf("CONNECTION_ATTR"); 
	
	private ChannelHandlerContext ctx;

	public NettyConnectionWrap(ChannelHandlerContext ctx)
	{
		super();
		this.ctx = ctx;
	}

	@Override
	public void close() throws IOException
	{
		ctx.close();
	}

	@Override
	public Remoter getAttachment()
	{
		return ctx.channel().attr(CONNECTION_ATTR).get();
	}

	@Override
	public String getDeviceid()
	{
		return ctx.channel().attr(CONNECTION_ATTR).get().getUuid();
	}

	@Override
	public String getRemoteAddress()
	{
		return ctx.channel().remoteAddress().toString();
	}

	@Override
	public void setIdleTimeoutMillis(int timeoutmillis)
	{
	}

	@Override
	public void flush()
	{
		ctx.flush();
	}

	@Override
	public boolean isOpen() 
	{
		return ctx.channel().isOpen();
	}

	@Override
	public void sendData(CommandTlv ct) 
	{
		ctx.channel().write(ct);
		ctx.channel().flush();
	}

	@Override
	public long getIdleTimeoutMillis() 
	{
		return 183 * 1000;
	}

	@Override
	public boolean equals(Object arg0) 
	{
		if ( !this.getClass().isInstance(arg0))
			return false ;
		
		NettyConnectionWrap other = (NettyConnectionWrap)arg0;
		
		return this.ctx.equals(other.ctx);
	}
	
	@Override
	public void setAttachment(Remoter obj) 
	{
		ctx.channel().attr(CONNECTION_ATTR).set(obj);
	}
	
	@Override
	public boolean isTimeout()
	{
		return false;
	}

	@Override
	public int getRemotePort() 
	{
		return 0;
	}
	
	@Override
	public int getConnectionHashCode() 
	{
		if ( ctx != null )
			return ctx.hashCode();
		return 0;
	}
}

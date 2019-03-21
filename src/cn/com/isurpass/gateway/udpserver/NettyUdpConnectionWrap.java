package cn.com.isurpass.gateway.udpserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.Utils;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.tlv.CommandTlv;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public class NettyUdpConnectionWrap implements IConnectionContext 
{
	private static Log log = LogFactory.getLog(NettyUdpConnectionWrap.class);
	private static int DEFAULT_TIME_OUT = 30 ;
	
	private ChannelHandlerContext ctx;
	private InetSocketAddress recipient;
	private Remoter remote ;
	private boolean open = true ;
	private long timeouttime = System.currentTimeMillis() + DEFAULT_TIME_OUT * 1000 ;

	public NettyUdpConnectionWrap(ChannelHandlerContext ctx ,InetSocketAddress recipient) {
		super();
		this.ctx = ctx;
		this.recipient = recipient;
	}
	
	public void refresh(InetSocketAddress recipient)
	{
		this.recipient = recipient;
		timeouttime = System.currentTimeMillis() + DEFAULT_TIME_OUT * 1000 ;
	}

	@Override
	public void setAttachment(Remoter remote) 
	{
		this.remote = remote;
	}

	@Override
	public Remoter getAttachment() 
	{
		return remote;
	}

	@Override
	public String getDeviceid() 
	{
		return remote.getUuid();
	}

	@Override
	public String getRemoteAddress() 
	{
		return ctx.channel().remoteAddress().toString();
	}

	@Override
	public int getRemotePort() 
	{
		return 0;
	}

	@Override
	public void setIdleTimeoutMillis(int timeoutmillis) 
	{

	}

	@Override
	public long getIdleTimeoutMillis() 
	{
		return DEFAULT_TIME_OUT * 1000;
	}
	
	public boolean isTimeout()
	{
		return this.timeouttime < System.currentTimeMillis();
	}

	@Override
	public boolean isOpen() 
	{
		return open;
	}

	@Override
	public void close() throws IOException 
	{
		open = false ;
		this.timeouttime = System.currentTimeMillis() - 1 ; 
	}

	@Override
	public void sendData(CommandTlv ct) 
	{
		byte[] data = Utils.wrapRequest(ct.getByte());
		
		if ( log.isInfoEnabled())
			Utils.print(String.format("Send udp data to %s" , this.remote.getUuid()),data );
		ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(data), recipient));
	}

	@Override
	public void flush()
	{
		ctx.flush();
	}

	@Override
	public int getConnectionHashCode() 
	{
		if ( ctx != null )
			return ctx.hashCode();
		return 0 ;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if ( !this.getClass().isInstance(obj))
			return false ;
		
		NettyUdpConnectionWrap other = (NettyUdpConnectionWrap)obj;
		
		return this.ctx.equals(other.ctx);
	}

}

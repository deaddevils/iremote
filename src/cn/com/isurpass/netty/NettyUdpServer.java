package cn.com.isurpass.netty;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class NettyUdpServer 
{
	private static Log log = LogFactory.getLog(NettyUdpServer.class);
	
	protected int port ;
	protected ChannelInitializer<NioDatagramChannel> channelinitializer;
	protected ChannelFuture channelfuture;
	private EventLoopGroup workerGroup = new NioEventLoopGroup();  

	public NettyUdpServer(int port, ChannelInitializer<NioDatagramChannel> channelinitializer) 
	{
		super();
		this.port = port;
		this.channelinitializer = channelinitializer;
	}

	public void start()
	{
		try
		{
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioDatagramChannel.class);
			b.handler(channelinitializer);
			
			channelfuture = b.bind(port).sync();
		}
		catch(Throwable t )
		{
			log.error(t.getMessage(), t);
		}
	}
	
	public void destroy()
	{
		try
		{
			channelfuture.channel().close().sync();
	        workerGroup.shutdownGracefully().sync();  
		} 
		catch (Throwable t)
		{
			log.error(t.getMessage(), t);
		}  
		
	}
}

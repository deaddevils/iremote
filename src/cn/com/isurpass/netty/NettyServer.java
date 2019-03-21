package cn.com.isurpass.netty;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer
{
	private static Log log = LogFactory.getLog(NettyServer.class);
			
	private EventLoopGroup bossGroup = new NioEventLoopGroup();  
	private EventLoopGroup workerGroup = new NioEventLoopGroup();  
	protected int port ;
	private ChannelInitializer<SocketChannel> channelinitializer;
	protected ChannelFuture channelfuture;

	public NettyServer(int port, ChannelInitializer<SocketChannel> channelinitializer)
	{
		super();
		this.port = port;
		this.channelinitializer = channelinitializer;
	}

	public void start()
	{
        try {  
            ServerBootstrap b = new ServerBootstrap();  
            b.group(bossGroup, workerGroup)
        	.channel(NioServerSocketChannel.class)  
        	.childHandler(channelinitializer)
            .childOption(ChannelOption.SO_KEEPALIVE, true);  
  
            channelfuture = b.bind(port);  
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
	        bossGroup.shutdownGracefully().sync();  
		} 
		catch (Throwable t)
		{
			log.error(t.getMessage(), t);
		}  
		
	}
}

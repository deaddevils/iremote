package cn.com.isurpass.netty;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient 
{
	private static Log log = LogFactory.getLog(NettyServer.class);
	
	private EventLoopGroup bossGroup = new NioEventLoopGroup();  
	protected String ip;
	protected int port ;
	private ChannelInitializer<SocketChannel> channelinitializer;
	protected ChannelFuture channelfuture;

	public NettyClient(String ip , int port, ChannelInitializer<SocketChannel> channelinitializer)
	{
		super();
		this.ip = ip ;
		this.port = port;
		this.channelinitializer = channelinitializer;
	}

	public void start()
	{
        try {  
            Bootstrap b = new Bootstrap();
            b.group(bossGroup)
        	.channel(NioSocketChannel.class)  
            .handler(channelinitializer);

            channelfuture = b.connect(ip, port);
            channelfuture.sync();
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
			channelfuture.channel().closeFuture().sync();
		} 
		catch (Throwable t)
		{
			log.error(t.getMessage(), t);
		}  
		
        bossGroup.shutdownGracefully();  
	}
}

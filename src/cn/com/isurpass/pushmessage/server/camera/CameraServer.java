package cn.com.isurpass.pushmessage.server.camera;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class CameraServer implements Runnable
{
	private static Log log = LogFactory.getLog(CameraServer.class);
	
	@Override
	public void run()
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup();  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
        	Bootstrap b = new Bootstrap();
            b.group(bossGroup)
            	.channel(NioDatagramChannel.class)  
            	.option(ChannelOption.SO_BROADCAST,true)
            	.handler(new CameraMessageHandler());
  
            ChannelFuture f = b.bind(9100).sync();  
  
            f.channel().closeFuture().sync();  
        }
        catch(Throwable t )
        {
        	log.error(t.getMessage(), t);
        }
        finally 
        {  
            workerGroup.shutdownGracefully();  
            bossGroup.shutdownGracefully();  
        }  
	}
	
	public static void start()
	{
		Thread t = new Thread(new CameraServer());
		t.start();
		
	}
	
	public static void main(String arg[])
	{
		CameraServer.start();
	}

}

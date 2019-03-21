package com.iremote.nettry;

import java.io.File;

import javax.net.ssl.SSLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

public class GatewayServer implements Runnable
{
	private static Log log = LogFactory.getLog(GatewayServer.class);
	
	public void start() throws InterruptedException, SSLException
	{

		EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try 
        {
        	SslContext sslCtx = SslContextBuilder.forServer(new File("/opt/tools/sslkey/notification.isurpass.com.cn_bundle.crt"), new File("/opt/tools/sslkey/notification.isurpass.com.cn_pkcs8.key")).build();
        	//SslContext sslCtx = SslContextBuilder.forServer(new File("E:\\key\\notification.isurpass.com.cn_bundle.crt"), new File("E:\\key\\notification.isurpass.com.cn_pkcs8.key")).build();

    		ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new ServerChannelInitializer(sslCtx));
 
            // Start the server.
            ChannelFuture f = b.bind(8922).sync();
 
            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } 
        finally 
        {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

	@Override
	public void run()
	{
		try
		{
			this.start();
		} 
		catch (Throwable e)
		{
			log.error(e.getMessage() , e);
		} 
	}
	
	public static void main(String arg[])
	{
		Thread t = new Thread(new GatewayServer());
		t.start();
	}
}

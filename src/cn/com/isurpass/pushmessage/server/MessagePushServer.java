package cn.com.isurpass.pushmessage.server;

import java.io.File;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.isurpass.pushmessage.pusher.ThirdpartMessagePusher;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.util.AttributeKey;

public class MessagePushServer implements Runnable
{
	private static Log log = LogFactory.getLog(MessagePushServer.class);
	
	private int port = 8001 ;
	private ChannelInitializer<SocketChannel> channelinitializer;

	public MessagePushServer(int port , ChannelInitializer<SocketChannel> channelinitializer)
	{
		super();
		this.port = port;
		this.channelinitializer = channelinitializer;
	}

	public static final AttributeKey<ThirdpartMessagePusher> thirdparter = AttributeKey.valueOf("thirdparter");
	public static final AttributeKey<String> salt = AttributeKey.valueOf("salt");
	
	@Override
	public void run()
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup();  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap b = new ServerBootstrap();  
            b.group(bossGroup, workerGroup)
            	.channel(NioServerSocketChannel.class)  
                //.childHandler(new MessageHandlerInitializer())
            	.childHandler(channelinitializer)
                .childOption(ChannelOption.SO_KEEPALIVE, true);  
  
            ChannelFuture f = b.bind(port).sync();  
  
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
	
	public static void main(String arg[])
	{
		Thread t0 = new Thread(new MessagePushServer(8000 , new MessageHandlerInitializer("#@@#")));
		t0.start();
		
		Thread t = new Thread(new MessagePushServer(8001 , new MessageHandlerInitializer("#@@#")));
		t.start();
		
		Thread t2 = new Thread(new MessagePushServer(8002, new MessageHandlerInitializer("\n")));
		t2.start();
		/*
		Thread t3 = new Thread(new MessagePushServer(8043, new MessageSSLHandlerInitializer(createCASslContext())));
		t3.start();
		
		Thread t4 = new Thread(new MessagePushServer(9100, new MessageSSLHandlerInitializer(createSSLContext())));
		t4.start();
		*/
	}
	
	//private static final String CERTIFICATE_FILE_BASE_DIR = "E:\\key\\";
	private static final String CERTIFICATE_FILE_BASE_DIR = "/opt/tools/sslkey/gatewayssl/";
	
	private static SSLContext createSSLContext()
	{
		return SslContextFactory.getSSLContent(CERTIFICATE_FILE_BASE_DIR + "testsvr.keystore", CERTIFICATE_FILE_BASE_DIR + "server.truststore", "String12345678");
	}
	
	private static SslContext createSslContext()
	{
        try
		{
			return SslContextBuilder.forServer(new File(CERTIFICATE_FILE_BASE_DIR + "testsvr.crt") , new File(CERTIFICATE_FILE_BASE_DIR + "testsvr.pkcs8"))
					.trustManager(new File(CERTIFICATE_FILE_BASE_DIR + "cacert.pem"))
					.build();
		} 
        catch (SSLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private static SslContext createCASslContext()
	{
        try
		{
			return SslContextBuilder.forServer(new File(CERTIFICATE_FILE_BASE_DIR + "sslt.isurpass.com.cn_bundle.crt") 
					, new File(CERTIFICATE_FILE_BASE_DIR + "sslt.isurpass.com.cn_pkcs8.key"))
					.build();
		} 
        catch (SSLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}

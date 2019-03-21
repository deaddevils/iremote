package com.iremote.nettry;

import java.io.File;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.isurpass.pushmessage.server.SslContextFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

public class NettyClientSimulator
{
	//static final String HOST = System.getProperty("host", "112.74.20.113");
	//public static final String HOST = System.getProperty("host", "sslt.isurpass.com.cn");
	public static final String HOST = System.getProperty("host", "iremote.tecus.co");
	//static final String HOST = System.getProperty("host", "127.0.0.1");
	//public static final int PORT = Integer.parseInt(System.getProperty("port", "8043"));
	public static final int PORT = Integer.parseInt(System.getProperty("port", "9100"));
    
    private static Log log = LogFactory.getLog(NettyClientSimulator.class);
    
	 public static void main(String[] args) throws Exception 
	 {
	        	        
	        EventLoopGroup group = new NioEventLoopGroup();
	        try {
	            Bootstrap b = new Bootstrap();
	            b.group(group)
	             .channel(NioSocketChannel.class)
	             .handler(new SecureChatClientInitializer(createDefaultSslContext()));
	            
	            Channel ch = b.connect(HOST, PORT).sync().channel();
	            ch.closeFuture().sync();
	        }
	        catch(Throwable t)
	        {
	        	log.error(t.getMessage() , t);
	        }
	        finally {
	            group.shutdownGracefully();
	        }
	 }
	 
	private static SslContext createDefaultSslContext()
	{
		try
		{
			return SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		} 
		catch (SSLException e)
		{
			e.printStackTrace();
		}
		return null ;
	}
	
	private static SslContext createSslContext()
	{
		//return SslContextFactory.getSSLContent("E:\\key\\gateway.keystore", "E:\\key\\gateway.truststore", "String12345678");
		
        try
		{
			return SslContextBuilder.forClient().keyManager(new File("E:\\key\\gateway.crt") , new File("E:\\key\\gateway.pkcs8"))
					.trustManager(new File("E:\\key\\cacert.pem"))
					.build();
		} 
        catch (SSLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private static SSLContext createSelfSignSslContext()
	{
		return SslContextFactory.getSSLContent("E:\\key\\gateway.keystore", "E:\\key\\gateway.truststore", "String12345678");
	}
}

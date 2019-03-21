package cn.com.isurpass.gateway.server;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.isurpass.gateway.server.processor.EscapeCoder;
import cn.com.isurpass.gateway.server.processor.gateway.AesEncryptDecoder;
import cn.com.isurpass.gateway.server.processor.gateway.AesEncryptEncoder;
import cn.com.isurpass.gateway.server.processor.gateway.GatewayMessageHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class GatewaySSLHandlerInitializer extends ChannelInitializer<SocketChannel>
{
	private static Log log = LogFactory.getLog(GatewaySSLHandlerInitializer.class);
	
	private SslContext context;
	private SSLContext sslcontext ;

	public GatewaySSLHandlerInitializer() 
	{
		super();
	}

	public GatewaySSLHandlerInitializer(SslContext context)
	{
		super();
		this.context = context;
	}
	
	public GatewaySSLHandlerInitializer(SSLContext context)
	{
		super();
		this.sslcontext = context;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception
	{
		log.info("Request arrive");
		ChannelPipeline pipeline = ch.pipeline();
		
		if ( context != null )
			pipeline.addFirst("ssl", new SslHandler(context.newEngine(ch.alloc() ), true));
		else if ( sslcontext != null )
		{
			SSLEngine ssl = sslcontext.createSSLEngine();
			ssl.setUseClientMode(false);
			pipeline.addFirst("ssl",new SslHandler(ssl));  
		}

		pipeline.addLast("idlehandler" , new IdleStateHandler(0 , 0 , 183, TimeUnit.SECONDS));
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(512,Unpooled.wrappedBuffer(new byte[]{126}))) ;    
        pipeline.addLast("escapecoder", new EscapeCoder());
        pipeline.addLast("encryptdecoder", new AesEncryptDecoder());
        pipeline.addLast("encryptencoder", new AesEncryptEncoder());
        
        pipeline.addLast("handler", new GatewayMessageHandler());
		
	}
}

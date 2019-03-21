package cn.com.isurpass.pushmessage.server;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class MessageSSLHandlerInitializer extends ChannelInitializer<SocketChannel>
{
	private SslContext context;
	private SSLContext sslcontext ;

	public MessageSSLHandlerInitializer(SslContext context)
	{
		super();
		this.context = context;
	}
	
	public MessageSSLHandlerInitializer(SSLContext context)
	{
		super();
		this.sslcontext = context;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception
	{
	
		ChannelPipeline pipeline = ch.pipeline();
		
		if ( context != null )
			pipeline.addFirst("ssl", new SslHandler(context.newEngine(ch.alloc() ), true));
		else if ( sslcontext != null )
		{
			SSLEngine ssl = sslcontext.createSSLEngine();
			ssl.setUseClientMode(false);
			pipeline.addFirst("ssl",new SslHandler(ssl));  
		}
		pipeline.addLast(new IdleStateHandler(0 , 0 , 180, TimeUnit.SECONDS));
        pipeline.addLast("decoder", new StringDecoder(Charset.forName("UTF-8")));
        pipeline.addLast("encoder", new StringEncoder(Charset.forName("UTF-8")));
        pipeline.addLast("separator", new SeparatorEncoder("\n"));

        pipeline.addLast("login", new LoginHandler());
	}

}

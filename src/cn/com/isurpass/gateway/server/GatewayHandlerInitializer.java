package cn.com.isurpass.gateway.server;

import java.util.concurrent.TimeUnit;

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
import io.netty.handler.timeout.IdleStateHandler;

public class GatewayHandlerInitializer extends ChannelInitializer<SocketChannel>
{
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(GatewayHandlerInitializer.class);

	@Override
	protected void initChannel(SocketChannel ch) throws Exception
	{
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast("idlehandler" , new IdleStateHandler(0 , 0 , 183, TimeUnit.SECONDS));
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(512,Unpooled.wrappedBuffer(new byte[]{126}))) ;    
        pipeline.addLast("escapecoder", new EscapeCoder());
        pipeline.addLast("encryptdecoder", new AesEncryptDecoder());
        pipeline.addLast("encryptencoder", new AesEncryptEncoder());
        
        pipeline.addLast("handler", new GatewayMessageHandler());
		
	}
}

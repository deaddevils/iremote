package cn.com.isurpass.gateway.server;

import java.util.concurrent.TimeUnit;

import cn.com.isurpass.gateway.server.processor.EscapeCoder;
import cn.com.isurpass.gateway.server.processor.lock.LockMessageHandler;
import cn.com.isurpass.gateway.server.processor.lock.TeaEncryptDecoder;
import cn.com.isurpass.gateway.server.processor.lock.TeaEncryptEncoder;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

public class LockHandlerInitializer extends ChannelInitializer<SocketChannel>
{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception
	{
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast("idlehandler" , new IdleStateHandler(0 , 0 , 183, TimeUnit.SECONDS));
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(512,Unpooled.wrappedBuffer(new byte[]{126}))) ;    
        pipeline.addLast("escapecoder", new EscapeCoder());
        pipeline.addLast("encryptdecoder", new TeaEncryptDecoder());
        pipeline.addLast("encryptencoder", new TeaEncryptEncoder());
        
        pipeline.addLast("handler", new LockMessageHandler());
		
	}
}

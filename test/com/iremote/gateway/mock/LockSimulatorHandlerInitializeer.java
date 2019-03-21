package com.iremote.gateway.mock;

import java.util.concurrent.TimeUnit;

import com.iremote.nettry.ByteArraySeparatorEncoder;

import cn.com.isurpass.gateway.server.processor.EscapeCoder;
import cn.com.isurpass.gateway.server.processor.gateway.AesEncryptDecoder;
import cn.com.isurpass.gateway.server.processor.lock.TeaEncryptDecoder;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

public class LockSimulatorHandlerInitializeer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception 
	{
		ChannelPipeline pipeline = ch.pipeline();
		
		pipeline.addLast("idlehandler" , new IdleStateHandler(0 , 0 , 30, TimeUnit.SECONDS));
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(512,Unpooled.wrappedBuffer(new byte[]{126}))) ;    
        //pipeline.addLast("separator", new ByteArraySeparatorEncoder(new byte[]{126}));
        pipeline.addLast("escapecoder", new EscapeCoder());
        pipeline.addLast("encriyptencoder", new GatewaySimulatorEncryptEncoder());
        pipeline.addLast("encryptdecoder", new TeaEncryptDecoder());

        pipeline.addLast("handler", new LockSimulatorMessageHandler());

	}
	
	

}

package cn.com.isurpass.ameta.alarmcenter;

import com.iremote.gateway.mock.GatewaySimulatorMessageHandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class AlarmCenterHandlerInitializer extends ChannelInitializer<SocketChannel> 
{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception 
	{
		ChannelPipeline pipeline = ch.pipeline();
		
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(512,Unpooled.wrappedBuffer("0x03".getBytes()))) ;
		
		pipeline.addLast("handler", new GatewaySimulatorMessageHandler());
	}

}

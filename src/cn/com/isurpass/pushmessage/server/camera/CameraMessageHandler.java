package cn.com.isurpass.pushmessage.server.camera;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class CameraMessageHandler  extends SimpleChannelInboundHandler<DatagramPacket> 
{
	private static Log log = LogFactory.getLog(CameraMessageHandler.class);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception
	{
		log.info(msg.content().toString(Charset.forName("UTF-8")));
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		//super.exceptionCaught(ctx, cause);
		if ( cause instanceof IOException)
			log.info(cause.getMessage());
		else 
			log.error(cause.getMessage() , cause);
	}
}

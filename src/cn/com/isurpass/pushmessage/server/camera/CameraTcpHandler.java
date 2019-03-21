package cn.com.isurpass.pushmessage.server.camera;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CameraTcpHandler extends SimpleChannelInboundHandler<String> 
{
	private static Log log = LogFactory.getLog(CameraTcpHandler.class);
			

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception
	{		
		log.info(msg);
	}
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		if ( cause instanceof IOException)
			log.info(cause.getMessage());
		else 
			log.error(cause.getMessage() , cause);
	}
}

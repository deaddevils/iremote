package com.iremote.server.netty;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GatewayMessageSimulator extends SimpleChannelInboundHandler<String>
{
	private static Log log = LogFactory.getLog(GatewayMessageSimulator.class);
	
	private String str  ;
	
	public GatewayMessageSimulator(String str)
	{
		super();
		this.str = str;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception
	{
		log.error(msg);
		
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{
		super.channelActive(ctx);
		log.info("channel active");
		ctx.channel().write("Hello world , this is " + str);
		ctx.channel().write("Hello world , this is " + str);
		ctx.channel().write("Hello world , this is " + str);
		ctx.channel().write("Hello world , this is " + str);
		ctx.channel().flush();
	}
	

}

package com.iremote.nettry;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.Utils;
import com.iremote.common.schedule.ScheduleManager;
import com.iremote.infraredtrans.LoginProcessor;
import com.iremote.infraredtrans.tlv.CommandTlv;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageHandlerSimulator extends SimpleChannelInboundHandler<ByteBuf>
{
	private static Log log = LogFactory.getLog(MessageHandlerSimulator.class);
	
	private boolean sendmssage = false ;

	public MessageHandlerSimulator(boolean sendmssage)
	{
		super();
		this.sendmssage = sendmssage;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception
	{
		if ( msg.readableBytes() == 0 )
			return ;
		byte[] b = new byte[msg.readableBytes()];
		msg.readBytes(b);
		Utils.print("MessageHandlerSimulator Receive", b);
		
		if ( sendmssage == true )
			return ;
		
		CommandTlv ct = LoginProcessor.createTime();
		b = ct.getByte();
		Utils.print("MessageHandlerSimulator send", b);
		
		ctx.channel().write( b);
		ctx.channel().flush();
		
		
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{
		super.channelActive(ctx);
		
		if ( sendmssage == true )
		{
			ScheduleManager.excuteEvery(5, 1, "netty", new HeartBeatSender(ctx));
		}
		
//		log.info("channel active");
//		if ( sendmssage == false )
//			return ;
//		ctx.channel().write(new byte[]{ 1 , 2, 3, 4,5, 6,7 });
//		ctx.channel().write(new byte[]{ 0,1 , 2, 3, 4,5, 6,7 });
//		log.info("flush channel");
//		ctx.channel().flush();
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

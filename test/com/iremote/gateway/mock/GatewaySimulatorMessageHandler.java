package com.iremote.gateway.mock;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.Utils;
import com.iremote.infraredtrans.IRemoteRequestProcessor;
import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.tlv.CommandTlv;

import cn.com.isurpass.gateway.server.NettyConnectionWrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

public class GatewaySimulatorMessageHandler extends SimpleChannelInboundHandler<byte[]>
{
	private static Log log = LogFactory.getLog(GatewaySimulatorMessageHandler.class);
	
	private String deviceid = "iRemote2005000001339";
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] content) throws Exception 
	{
		Utils.print("Receive data", content);
		IRemoteRequestProcessor pro = GatewaySimulatorProcessorStore.getInstance().getProcessor(content , 0);
		
		if ( pro == null )
			return ;
		
		NettyConnectionWrap ncw = new NettyConnectionWrap(ctx);
		CommandTlv ct = pro.process(content, ncw );
		
		if ( ct == null )
			return ;
		
		ncw.sendData(ct);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception 
	{
		super.channelActive(ctx);
		
		Remoter r = new Remoter();
		r.setUuid(deviceid);
		r.setHaslogin(true);
		
		NettyConnectionWrap ncw = new NettyConnectionWrap(ctx);
		ncw.setAttachment(r);
		
		heartBeat(ncw);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception 
	{
		if ( cause instanceof IOException)
			log.info(cause.getMessage());
		else 
			log.error(cause.getMessage() , cause);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception 
	{
		super.userEventTriggered(ctx, evt);
		
		if (evt instanceof IdleStateEvent)  
			heartBeat(new NettyConnectionWrap(ctx));
	}

	private void heartBeat(NettyConnectionWrap ncw)
	{
		CommandTlv ct = new CommandTlv(102 , 1);
		
		try 
		{
			ncw.sendData(ct);
		} 
		catch (Throwable t) 
		{
			log.error(t.getMessage() , t);
		} 
	}
}

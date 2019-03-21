package cn.com.isurpass.gateway.server.processor;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.infraredtrans.DisconnectionProcessor;
import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.ReportProcessor;
import com.iremote.task.device.RemoteDisconnectTaskProcessor;

import cn.com.isurpass.gateway.server.NettyConnectionWrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

public abstract class BaseMessageHandler extends SimpleChannelInboundHandler<byte[]>
{
	private static Log log = LogFactory.getLog(BaseMessageHandler.class);
		
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] buf) throws Exception 
	{
		getReportProcessor().processRequest(buf , new NettyConnectionWrap(ctx));
	}
	
	protected abstract ReportProcessor getReportProcessor();
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception 
	{
		super.channelActive(ctx);
		
		NettyConnectionWrap ncw = new NettyConnectionWrap(ctx);
		
		Remoter r = new Remoter();
		r.setToken("");
		r.setSoftversion(getSoftversion());
		ncw.setIdleTimeoutMillis(183 * 1000);
		ncw.setAttachment(r);

	}

	private int getSoftversion()
	{
		return 1 ;
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
	public void channelInactive(ChannelHandlerContext ctx) throws Exception 
	{
		NettyConnectionWrap ncw = new NettyConnectionWrap(ctx);
		
		if ( log.isInfoEnabled())
			log.info(String.format("channelInactive %s(%d)", ncw.getDeviceid() , ncw.getConnectionHashCode()));
		
		if ( !ncw.getAttachment().isHaslogin())
			return ;
		
		getReportProcessor().addTask(ncw.getDeviceid(), new DisconnectionProcessor( ncw , ncw.getDeviceid()));

		super.channelInactive(ctx);
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception 
	{
		super.userEventTriggered(ctx, evt);

		if (evt instanceof IdleStateEvent)  
		{
			NettyConnectionWrap ncw = new NettyConnectionWrap(ctx);
			if ( log.isInfoEnabled())
				log.info(String.format("idleTimeout %s(%d)", ncw.getDeviceid() , ncw.getConnectionHashCode()));
			
			if ( !ncw.getAttachment().isHaslogin())
				return ;
			
			getReportProcessor().addTask(ncw.getDeviceid(), new RemoteDisconnectTaskProcessor( ncw , ncw.getDeviceid()));
		}
	}
}

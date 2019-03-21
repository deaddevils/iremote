package cn.com.isurpass.pushmessage.server;

import java.io.IOException;

import cn.com.isurpass.netty.NettyLog;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.taskmanager.QueueTaskManager;

import cn.com.isurpass.pushmessage.processor.LoginPushMessageProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class LoginHandler extends SimpleChannelInboundHandler<String>
{
	private static Log log = LogFactory.getLog(LoginHandler.class);

	private static QueueTaskManager<Runnable> logintaskmgt = new QueueTaskManager<Runnable>(1);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception
	{
		log.info(msg);
		if ( StringUtils.isBlank(msg))
			return ;

		LoginPushMessageProcessor lp = new LoginPushMessageProcessor(msg , ctx);
		logintaskmgt.addTask("LoginHandler" , lp);
	}


	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
	{
		super.userEventTriggered(ctx, evt);

		if (evt instanceof IdleStateEvent)
		{
	      IdleStateEvent event = (IdleStateEvent) evt;
	      if (event.state() == IdleState.ALL_IDLE)
	    	  ctx.close();
		}
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
	
	public static void shutdown()
	{
		logintaskmgt.shutdown();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);

		String salt = Utils.createsecuritytoken(32);
		ctx.channel().attr(MessagePushServer.salt).set(salt);
		sendSalt(salt, ctx);
	}

	private void sendSalt(String salt, ChannelHandlerContext ctx)
	{
		JSONObject rst = new JSONObject();
		rst.put("salt", salt);
		ctx.writeAndFlush(rst.toJSONString());

		NettyLog.sendmessageinfolog(ctx, rst.toJSONString());
	}
}

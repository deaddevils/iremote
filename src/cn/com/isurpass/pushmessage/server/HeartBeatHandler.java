package cn.com.isurpass.pushmessage.server;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.com.isurpass.netty.NettyLog;
import cn.com.isurpass.pushmessage.pusher.ThirdpartMessagePusher;
import cn.com.isurpass.pushmessage.pusher.ThirdpartMessagePusherStore;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HeartBeatHandler extends SimpleChannelInboundHandler<String> 
{
	private static Log log = LogFactory.getLog(HeartBeatHandler.class);
		
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception
	{
		ThirdpartMessagePusher tp = ctx.channel().attr(MessagePushServer.thirdparter).get();

		NettyLog.receivemessageinfolog(ctx, msg);

		if ( StringUtils.isBlank(msg))
			return ;
		
		String [] ma = msg.split("\n");

		for ( String m : ma )
		{
			JSONObject json = JSON.parseObject(m);
			
			if ( json.containsKey("lastid"))
			{
				int lid = json.getIntValue("lastid");
				if ( tp != null )
				{
					tp.setLastid(lid);
					ThirdpartMessagePusherStore.getInstance().execute(tp);
				}
				sendResult(ctx ,tp);
			}
			else if ( ( json.containsKey("action") && "heartbeat".equalsIgnoreCase(json.getString("action")))
						||( json.containsKey("msgtype") && "heartbeat".equalsIgnoreCase(json.getString("msgtype"))))
			{
				sendResult(ctx ,tp);
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		//super.exceptionCaught(ctx, cause);
		if ( cause instanceof IOException)
		{
			log.info(cause.getMessage());
			ThirdpartMessagePusher tp = ctx.channel().attr(MessagePushServer.thirdparter).get();
			if ( tp != null )
				tp.stop();
		}
		else 
			log.error(cause.getMessage() , cause);
	}
	
	private void sendResult(ChannelHandlerContext ctx, ThirdpartMessagePusher tp)
	{
		JSONObject rst = new JSONObject();
		rst.put("resultcode", 0);
		rst.put("msgtype", "heartbeat");
		ctx.writeAndFlush(rst.toJSONString());
		NettyLog.sendmessageinfolog(ctx, rst.toJSONString());
	}
	
	private void sendHeartbeat(ChannelHandlerContext ctx, ThirdpartMessagePusher tp)
	{
		JSONObject rst = new JSONObject();
		rst.put("resultcode", 0);
		ctx.writeAndFlush(rst.toJSONString());
		NettyLog.sendmessageinfolog(ctx, rst.toJSONString());
	}
	
	private void log(String msg , ThirdpartMessagePusher tp)
	{
		if ( log.isInfoEnabled() && tp != null )
			log.info(String.format("%s:%s", tp.getThirdpartid() ,msg));
		else  
			log.info(msg);
	}

}

package cn.com.isurpass.netty;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.domain.ThirdPart;

import cn.com.isurpass.pushmessage.server.MessagePushServer;
import io.netty.channel.ChannelHandlerContext;

public class NettyLog
{
	private static Log log = LogFactory.getLog(NettyLog.class);
	
	public static void sendmessageinfolog(ChannelHandlerContext ctx , String content)
	{
		info("Send to" , ctx , content);
	}
	
	public static void receivemessageinfolog(ChannelHandlerContext ctx , String content)
	{
		info("Receive from " , ctx , content);
	}
	
	private static void info(String action ,ChannelHandlerContext ctx , String content )
	{
		if ( !log.isInfoEnabled())
			return ;
		
		ThirdPart tp = null ;
		if ( ctx.channel().hasAttr(MessagePushServer.thirdparter) )
			tp = ctx.channel().attr(MessagePushServer.thirdparter).get().getThirdpart();
		
		String name = "";
		if ( tp != null )
			name = tp.getName();
		
		log.info(String.format("%s %s : %s ", action, name, content));
	}
}

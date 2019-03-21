package cn.com.isurpass.camera.dahua.processor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.constant.DahuaCameraReportType;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.CameraEvent;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

public class DahuaReportHandler  extends SimpleChannelInboundHandler<String> 
{
	private static Log log = LogFactory.getLog(DahuaReportHandler.class);
	private static List<String> SUPPORT_TYPE = Arrays.asList(new String[]{DahuaCameraReportType.call.getType(),DahuaCameraReportType.alarm.getType()});
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception
	{
		log.info(msg);
		JSONObject json = JSON.parseObject(msg);
		
		if ( !json.containsKey("alarmInfo"))
			return ;
		
		JSONObject ai = json.getJSONObject("alarmInfo");
		
		if ( !ai.containsKey("devid") || !ai.containsKey("type"))
			return ;
		
		if ( !SUPPORT_TYPE.contains(ai.getString("type")) )
			return ;
		
		JMSUtil.sendmessage(IRemoteConstantDefine.DAHUA_CAMERA_REPORT, new CameraEvent(ai.getString("devid") , ai.getString("type")));
		JMSUtil.commitmessage();
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
	    	 ctx.close();
	}
}

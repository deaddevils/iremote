package cn.com.isurpass.camera.dahua.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.constant.DahuaCameraReportType;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.CameraEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DahuaReportForAndroidHandler extends SimpleChannelInboundHandler<String>
{
	private static Log log = LogFactory.getLog(DahuaReportForAndroidHandler.class);
	private static List<String> SUPPORT_TYPE = Arrays.asList(new String[]{DahuaCameraReportType.call.getType(),DahuaCameraReportType.alarm.getType()});
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception
	{
		log.info(msg);
		
		if ( !msg.contains("data") && !msg.contains("registration_ids"))
			return ;
		if ( !msg.contains("msg") || !msg.contains("Android"))
			return ;
		String data [ ] = msg.split("::");
		if (data.length < 4)
			return;
		String devid = data[1].replace(" ","");
		String type = data[3].replace(" ","");
		if ( !SUPPORT_TYPE.contains(type))
			return ;
		
		JMSUtil.sendmessage(IRemoteConstantDefine.DAHUA_CAMERA_REPORT, new CameraEvent(devid, type));
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

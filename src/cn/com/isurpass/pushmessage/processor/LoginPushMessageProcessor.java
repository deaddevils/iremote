package cn.com.isurpass.pushmessage.processor;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.ThirdPart;
import com.iremote.domain.ThirdPartToken;
import com.iremote.service.ThirdPartService;
import com.iremote.service.ThirdPartTokenService;

import cn.com.isurpass.netty.NettyLog;
import cn.com.isurpass.pushmessage.pusher.ThirdpartMessagePusher;
import cn.com.isurpass.pushmessage.pusher.ThirdpartMessagePusherStore;
import cn.com.isurpass.pushmessage.server.HeartBeatHandler;
import cn.com.isurpass.pushmessage.server.MessagePushServer;
import io.netty.channel.ChannelHandlerContext;

public class LoginPushMessageProcessor implements Runnable
{
	private String parameter ;
	private ChannelHandlerContext ctx;

	public LoginPushMessageProcessor(String parameter, ChannelHandlerContext ctx)
	{
		super();
		this.parameter = parameter;
		this.ctx = ctx;
	}

	@Override
	public void run()
	{
		if ( StringUtils.isBlank(this.parameter))
			return ;

		JSONObject json = JSON.parseObject(parameter);

		ThirdPartToken token = validateIdentity(json);

		if (token == null)
			return;

		createSuccessResponse(json, token);
	}

	private ThirdPartToken validateIdentity(JSONObject json) {
		if ( !json.containsKey("code") || (!json.containsKey("token") && !json.containsKey("sign")))
		{
			sendResult(ErrorCodeDefine.NO_PRIVILEGE);
			return null;
		}

		ThirdPartTokenService svr = new ThirdPartTokenService();
		ThirdPartToken token = svr.query(json.getString("code"));

		if ( token == null )
			return null;

		if (token.getToken() == null || token.getValidtime().getTime() < System.currentTimeMillis())
		{
			sendResult(ErrorCodeDefine.USERNAME_OR_PASSWORD_WRONG_3);
			return null;
		}

		if (json.containsKey("token"))
		{
			if (!token.getToken().equals(json.getString("token")))
			{
				sendResult(ErrorCodeDefine.USERNAME_OR_PASSWORD_WRONG_3);
				return null;
			}
		}
		else if (json.containsKey("sign"))
		{
			String sign = getSign(json, token);
			if (!json.getString("sign").equals(sign))
			{
				sendResult(ErrorCodeDefine.USERNAME_OR_PASSWORD_WRONG_3);
				return null;
			}
		}
		else
			return null;

		return token;
	}

	private String getSign(JSONObject json, ThirdPartToken token) {
		String salt = ctx.channel().attr(MessagePushServer.salt).get();
		String sign = json.getString("code") + token.getToken() + salt;
		for (int i = 0; i < 100; i++)
        {
            sign = DigestUtils.md5Hex(sign);
        }
		return sign;
	}

	private void createSuccessResponse(JSONObject json, ThirdPartToken token) {
		ThirdPartService tps = new ThirdPartService();
		ThirdPart tp = tps.query(json.getString("code"));

		ThirdpartMessagePusher t = new ThirdpartMessagePusher(tp, ctx);
		ctx.channel().attr(MessagePushServer.thirdparter).set(t);

		ctx.channel().pipeline().remove("login");
		ctx.channel().pipeline().addLast("heartbeat" ,new HeartBeatHandler() );

		ThirdpartMessagePusher to = ThirdpartMessagePusherStore.getInstance().get(String.valueOf(token.getThirdpartid()));
		if ( to != null )
			to.stop();

		ThirdpartMessagePusherStore.getInstance().put(String.valueOf(token.getThirdpartid()) , t);

		sendResult(0);
	}

	private void sendResult(int resultcode)
	{
		JSONObject rst = new JSONObject();
		rst.put("resultcode", resultcode);
		ctx.writeAndFlush(rst.toJSONString());
		
		NettyLog.sendmessageinfolog(ctx, rst.toJSONString());
	}

}


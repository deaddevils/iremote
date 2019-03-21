package com.iremote.nettry;

import com.iremote.infraredtrans.tlv.CommandTlv;

import io.netty.channel.ChannelHandlerContext;
import net.sf.json.JSONObject;

public class HeartBeatSender implements Runnable
{
	ChannelHandlerContext ctx;

	public HeartBeatSender(ChannelHandlerContext ctx)
	{
		super();
		this.ctx = ctx;
	}

	@Override
	public void run()
	{
//		CommandTlv rst = new CommandTlv(102 , 1);
//		ctx.channel().write(rst.getByte());
//		ctx.channel().flush();
		JSONObject json = new JSONObject();
		json.put("code", "fjsdf");
		json.put("token", "fdafdafdafdaf");
		ctx.channel().write(json.toString().getBytes());
		ctx.channel().flush();
		//ctx.close();
	}

}

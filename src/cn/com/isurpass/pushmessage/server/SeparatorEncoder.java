package cn.com.isurpass.pushmessage.server;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class SeparatorEncoder extends MessageToMessageEncoder<String>
{
	private String separator ;

	public SeparatorEncoder(String separator)
	{
		super();
		this.separator = separator;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception
	{
		out.add(separator);
		out.add(msg);
		out.add(separator);
	}

}

package cn.com.isurpass.pushmessage.server;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

@Deprecated
public class ByteArraySeparatorDecoder extends ByteToMessageDecoder
{
	private static Log log = LogFactory.getLog(ByteArraySeparatorDecoder.class);
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
	{
		out.add(new byte[]{125});
		out.add(in);
		log.info("data arrive");
		//Utils.print("", in.array());
	}

}

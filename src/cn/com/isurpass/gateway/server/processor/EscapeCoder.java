package cn.com.isurpass.gateway.server.processor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.Utils;

import cn.com.isurpass.gateway.server.NettyConnectionWrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

public class EscapeCoder extends MessageToMessageCodec<ByteBuf , byte[]> 
{
	private static Log log = LogFactory.getLog(EscapeCoder.class);
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf content, List<Object> out) throws Exception 
	{
		byte[] c = new byte[content.readableBytes()];
		content.readBytes(c);
		out.add(Utils.unwrapRequest(c));
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, byte[] content, List<Object> out) throws Exception 
	{
		if ( log.isInfoEnabled())
		{
			NettyConnectionWrap ncw = new NettyConnectionWrap(ctx);
			Utils.print(String.format("Send data to %s(%d)" , ncw.getDeviceid() , ncw.getConnectionHashCode()),content );
		}
		
		out.add(Unpooled.wrappedBuffer(Utils.wrapRequest(content)));
	}

}

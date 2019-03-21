package com.iremote.nettry;

import java.util.List;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class ByteArraySeparatorEncoder extends MessageToMessageEncoder<byte[]>
{	
	private byte[] separator ;

	public ByteArraySeparatorEncoder(byte[] separator)
	{
		super();
		this.separator =  separator;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception
	{
		out.add(Unpooled.wrappedBuffer(separator));
		out.add(Unpooled.wrappedBuffer(msg));
		out.add(Unpooled.wrappedBuffer(separator));
	}

}

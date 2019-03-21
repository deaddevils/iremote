package cn.com.isurpass.gateway.udpserver;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.Utils;

import cn.com.isurpass.gateway.server.NettyConnectionWrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageCodec;

public class UdpEscapeCoder extends MessageToMessageCodec<DatagramPacket , byte[]> 
{
	private static Log log = LogFactory.getLog(UdpEscapeCoder.class);

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

	@Override
	protected void decode(ChannelHandlerContext arg0, DatagramPacket packet, List<Object> out) throws Exception 
	{
        ByteBuf buf = (ByteBuf) packet.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        out.add(Utils.unwrapRequest(req));
	}

}

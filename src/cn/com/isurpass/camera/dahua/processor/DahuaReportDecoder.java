package cn.com.isurpass.camera.dahua.processor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.Utils;

import cn.com.isurpass.camera.dahua.server.DahuaReportProcessorInitializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class DahuaReportDecoder extends ByteToMessageDecoder
{
	private static Log log = LogFactory.getLog(DahuaReportDecoder.class);

	private static int HEAD_LENGTH = 37 ;
	private static int LENGTH_FILED_LENGTH = 5 ;
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
	{
		log.info("data arrive");
		int bc = in.readableBytes();
		if (bc < HEAD_LENGTH)
			return ;
		in.markReaderIndex();
		
		in.readByte();
		int l1 = ( in.readShort() & 0xFFFF );
		
		if ( l1 + LENGTH_FILED_LENGTH > bc )
		{
			in.resetReaderIndex();
			return ;
		}
		
		byte[] tb = new byte[l1];
		in.readBytes(tb);
		
		int l2 = ( in.readShort() & 0xFFFF );
		
		if ( l1 + l2 + LENGTH_FILED_LENGTH > bc )
		{
			in.resetReaderIndex();
			return ;
		}
		
		byte[] cb = new byte[l2];
		in.readBytes(cb);
		
		Utils.print("Report Content", cb);
		out.add(new String(cb));
	}

}

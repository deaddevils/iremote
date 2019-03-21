package cn.com.isurpass.camera.dahua.processor;

import com.iremote.common.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class DahuaReportForAndroidDecoder extends ByteToMessageDecoder
{
	private static Log log = LogFactory.getLog(DahuaReportForAndroidDecoder.class);

	private static int HEAD_LENGTH = 37 ;
	private static int LENGTH_FILED_LENGTH = 5 ;
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception
	{
		log.info("data arrive");
		int bc = in.readableBytes();
		byte[] data = new byte[bc];
		in.readBytes(data);
		Utils.print("receive data", data);
		log.info(new String(data));
		out.add(new String(data));

	}

}

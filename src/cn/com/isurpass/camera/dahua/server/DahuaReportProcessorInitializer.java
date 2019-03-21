package cn.com.isurpass.camera.dahua.server;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.isurpass.camera.dahua.processor.DahuaReportDecoder;
import cn.com.isurpass.camera.dahua.processor.DahuaReportHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.IdleStateHandler;

public class DahuaReportProcessorInitializer extends ChannelInitializer<SocketChannel>
{
	private static Log log = LogFactory.getLog(DahuaReportProcessorInitializer.class);
	
	private SslContext context;
	
	public DahuaReportProcessorInitializer(SslContext context)
	{
		super();
		this.context = context;
	}

	public DahuaReportProcessorInitializer() {
		super();
	}



	@Override
	protected void initChannel(SocketChannel ch) throws Exception
	{
		log.info("Request arrvie");
		
		ChannelPipeline pipeline = ch.pipeline();
		
		if ( context != null )
			pipeline.addFirst("ssl", context.newHandler(ch.alloc()));

		pipeline.addLast(new IdleStateHandler(0 , 0 , 60, TimeUnit.SECONDS));
		
        pipeline.addLast("decoder", new DahuaReportDecoder());
        pipeline.addLast("handler", new DahuaReportHandler());
	}

}

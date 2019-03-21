package cn.com.isurpass.pushmessage.server;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class MessageHandlerInitializer extends ChannelInitializer<SocketChannel>
{
	private String separator ;
	private static Log log = LogFactory.getLog(MessageHandlerInitializer.class);
	
	public MessageHandlerInitializer(String separator)
	{
		super();
		this.separator = separator;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception
	{
		if ( log.isInfoEnabled())
			log.info("accept request from " + ch.remoteAddress().getAddress().getHostAddress());
		
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast(new IdleStateHandler(0 , 0 , 180, TimeUnit.SECONDS));
//		if ( "\n".equals(separator))
//			pipeline.addLast("framer", new LineBasedFrameDecoder(8192)) ;
        pipeline.addLast("decoder", new StringDecoder(Charset.forName("UTF-8")));
        pipeline.addLast("encoder", new StringEncoder(Charset.forName("UTF-8")));
        //pipeline.addLast("separator", new SeparatorEncoder("#@@#"));
        pipeline.addLast("separator", new SeparatorEncoder(separator));

        pipeline.addLast("login", new LoginHandler());
	}

}

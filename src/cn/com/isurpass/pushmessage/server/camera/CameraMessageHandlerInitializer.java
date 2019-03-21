package cn.com.isurpass.pushmessage.server.camera;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import cn.com.isurpass.pushmessage.server.SeparatorEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class CameraMessageHandlerInitializer extends ChannelInitializer<SocketChannel>
{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception
	{
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast(new IdleStateHandler(0 , 0 , 180, TimeUnit.SECONDS));
		pipeline.addLast("framer", new LineBasedFrameDecoder(8192)) ;
        pipeline.addLast("decoder", new StringDecoder(Charset.forName("UTF-8")));
        pipeline.addLast("encoder", new StringEncoder(Charset.forName("UTF-8")));
        pipeline.addLast("separator", new SeparatorEncoder("\n"));

        pipeline.addLast("msghandler", new CameraTcpHandler());
		
	}
}
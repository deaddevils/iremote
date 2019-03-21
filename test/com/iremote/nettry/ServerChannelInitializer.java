package com.iremote.nettry;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.ssl.SslContext;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public ServerChannelInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }
    @Override
    protected void initChannel(SocketChannel ch) throws Exception 
    {
        ChannelPipeline pipeline = ch.pipeline();
        
        pipeline.addFirst(sslCtx.newHandler(ch.alloc()));
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,Unpooled.wrappedBuffer(new byte[]{126}))) ;    
        pipeline.addLast("separator", new ByteArraySeparatorEncoder(new byte[]{126}));      
        pipeline.addLast("handler", new MessageHandlerSimulator(false));

    }
}

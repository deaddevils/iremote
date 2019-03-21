/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.iremote.nettry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

/**
 * Creates a newly configured {@link ChannelPipeline} for a new channel.
 */
public class SecureChatClientInitializer extends ChannelInitializer<SocketChannel> {

    //static final String HOST = System.getProperty("host", "test.isurpass.com.cn");
	//static final String HOST = System.getProperty("host", "127.0.0.1");
   // static final int PORT = Integer.parseInt(System.getProperty("port", "9100"));
    
	private SslContext context;
	private SSLContext sslcontext ;

	public SecureChatClientInitializer(SslContext context)
	{
		super();
		this.context = context;
	}
	
	public SecureChatClientInitializer(SSLContext context)
	{
		super();
		this.sslcontext = context;
	}

    @Override
    public void initChannel(SocketChannel ch) throws Exception 
    {
        ChannelPipeline pipeline = ch.pipeline();

		if ( context != null )
			pipeline.addLast(context.newHandler(ch.alloc(), NettyClientSimulator.HOST, NettyClientSimulator.PORT));
			//pipeline.addFirst("ssl", new SslHandler(context.newEngine(ch.alloc() ), true));
		else if ( sslcontext != null )
		{
			SSLEngine ssl = sslcontext.createSSLEngine();
			ssl.setUseClientMode(true);
			pipeline.addFirst("ssl",new SslHandler(ssl));  
		}
		
        //pipeline.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
        //pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,Unpooled.wrappedBuffer(new byte[]{126}))) ;    
        //pipeline.addLast("separator", new ByteArraySeparatorEncoder(new byte[]{126}));
        pipeline.addLast("separator", new ByteArraySeparatorEncoder("\n".getBytes()));
        pipeline.addLast("handler", new MessageHandlerSimulator(true));
        
    }
}

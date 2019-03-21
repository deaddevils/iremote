package cn.com.isurpass.pushmessage.server.camera;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

public class UdpClient
{
	   public void run(int port)throws Exception{
	        EventLoopGroup group = new NioEventLoopGroup();
	        try {
	            Bootstrap b = new Bootstrap();
	            b.group(group).channel(NioDatagramChannel.class)
	            		.option(ChannelOption.SO_BROADCAST,true)
	                    .handler(new CameraMessageHandler());

	            Channel ch = b.bind(0).sync().channel();
	            ch.writeAndFlush(
	                    new DatagramPacket(
	                            Unpooled.copiedBuffer("dsfdfafd\nkdskdskaf\n", CharsetUtil.UTF_8),
	                            new InetSocketAddress("192.168.8.10",port))).sync();
	            if(!ch.closeFuture().await(1500)){
	                System.out.println("≤È—Ø≥¨ ±£°£°£°");
	            }
	        }
	        
	        finally {
	            group.shutdownGracefully();
	        }
	    }
	   
	public static void main(String arg[])
	{
		UdpClient uc = new UdpClient();
		try
		{
			uc.run(9100);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	 }
}

package cn.com.isurpass.gateway.udpserver;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.TagDefine;
import com.iremote.common.Utils;
import com.iremote.common.jms.JMSUtil;
import com.iremote.common.jms.vo.RemoteOnlineEvent;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.IConnectionContext;
import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.ReportProcessor;
import com.iremote.infraredtrans.tlv.TlvWrap;

import cn.com.isurpass.gateway.server.processor.gateway.GatewayReportProcessor;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class GatewayUdpSeverHandler extends SimpleChannelInboundHandler<DatagramPacket> 
{
		private static Log log = LogFactory.getLog(GatewayUdpSeverHandler.class);
		
		private ReportProcessor reportprocessor = new GatewayReportProcessor();
	
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception
        {
                ByteBuf buf = (ByteBuf) packet.copy().content();
                byte[] req = new byte[buf.readableBytes()];
                buf.readBytes(req);
                byte[][] rs = Utils.splitRequest(req , req.length);
                
                if ( rs == null )
                	return ;
                
                String deviceid = TlvWrap.readString(rs[0], TagDefine.TAG_GATEWAY_DEVICE_ID, TagDefine.TAG_HEAD_LENGTH);
                
                IConnectionContext cc = ConnectionManager.getConnection(deviceid); 
                
                NettyUdpConnectionWrap nbc = null ;
                if ( cc != null && cc instanceof NettyUdpConnectionWrap )
                {
                	nbc = ( NettyUdpConnectionWrap) cc ;
                	nbc.refresh(packet.sender());
                }
                else 
                {
                	nbc = new NettyUdpConnectionWrap(ctx , packet.sender());
                
	                Remoter r = new Remoter();
	                r.setHaslogin(true);  // udp device do not login
	                r.setUuid(deviceid);
	                
	                nbc.setAttachment(r);
	                
	                ConnectionManager.addConnection(deviceid, nbc);
            		JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_REMOTE_ONLINE, new RemoteOnlineEvent(deviceid , new Date() ,false, 0));
            		
            		RemoteOnlineEvent re = new RemoteOnlineEvent(deviceid , new Date() ,false, 0);
            		re.setReport(rs[0]);
            		JMSUtil.sendmessage(IRemoteConstantDefine.WARNING_TYPE_UDP_GATEWAY_ONLINE, re);
            		JMSUtil.commitmessage();
                }
                
                if ( log.isInfoEnabled())
                	Utils.print(String.format("Receive udp data from %s(%s)", deviceid,packet.sender().getAddress().getHostAddress()), req);
                
                for ( int i = 0 ; i < rs.length ; i ++ )
	                reportprocessor.processRequest( rs[i], nbc);

        }

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
		{
			log.error(cause.getMessage() , cause);
			super.exceptionCaught(ctx, cause);
		}
        
        
}

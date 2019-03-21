package com.iremote.server.netty;

import java.io.IOException;
import java.nio.BufferOverflowException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.common.Utils;
import com.iremote.infraredtrans.ConnectionManager;
import com.iremote.infraredtrans.CurrentTimeProcessor;
import com.iremote.infraredtrans.HearBeatProcessor;
import com.iremote.infraredtrans.IRemoteRequestProcessor;
import com.iremote.infraredtrans.LoginProcessor;
import com.iremote.infraredtrans.ProcessorStore;
import com.iremote.infraredtrans.Remoter;
import com.iremote.infraredtrans.tlv.CommandTlv;
import com.iremote.infraredtrans.tlv.TlvIntUnit;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;

public class MessageHandler extends SimpleChannelInboundHandler<ByteBuf> 
{
//	private static QueueTaskManager<Runnable> logintaskmgt = new QueueTaskManager<Runnable>(1);
//	private static QueueTaskManager<Runnable> heartbeattaskmgt = new QueueTaskManager<Runnable>(3 , true , new MockDbSessionFactory(), new SingleTaskQueueFactory<Runnable>());
//	private static QueueTaskManager<Runnable> reporttaskmgt = new QueueTaskManager<Runnable>(3);
	
	private static Log log = LogFactory.getLog(MessageHandler.class);
	private static AttributeKey<Remoter> ID_ATTR = AttributeKey.valueOf("ID"); 

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) 
    {
		if ( cause instanceof IOException)
			log.info(cause.getMessage());
		else 
			log.error(cause.getMessage() , cause);
        ctx.close();
    }

	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf message) throws Exception 
	{
	     byte[] b = message.array();
	     
	     if ( b == null || b.length == 0 )
	    	 return ;
	     
	    Remoter r = ctx.channel().attr(ID_ATTR).get();
	    if ( r == null )
	    	return ;
	    
		if ( log.isInfoEnabled())
			Utils.print(String.format("Receive data from %s(%d)", getUuid(r) , ctx.channel().hashCode()) , b );
		
		b = Utils.unwrapRequest(b);
		
		try
		{
			processRequest(b , r , ctx);
		}
		catch(Throwable t)
		{
			log.error(t.getMessage() , t);
		}
	}
	
	private void processRequest(byte[] rq ,  Remoter remoter , ChannelHandlerContext ctx ) throws BufferOverflowException, IOException
	{
		IRemoteRequestProcessor pro = ProcessorStore.getInstance().getProcessor(rq , 0);
		if ( pro == null )
			return;
		
		String deviceid = getUuid(remoter);
		if ( pro instanceof CurrentTimeProcessor || pro instanceof HearBeatProcessor)
		{
			if ( isLogin(remoter) == false 
					&& ( remoter.getSequence() % 4 ) == 1)  //1 login change every 1 minutes 
				sendLoginRequest(ctx , remoter);
			else 
				;//heartbeattaskmgt.addTask(deviceid , new ReportProcessorRunner(pro ,deviceid, rq ,  new NettyConnectionWrap(ctx)));
			return ;
		}
		else 
		{
			if ( pro instanceof LoginProcessor ) 
			{
				//logintaskmgt.addTask("loginprocess" , new ReportProcessorRunner(pro , deviceid ,rq ,  new NettyConnectionWrap(ctx)));
				return ;
			}
			else if ( isLogin(remoter) == true && ConnectionManager.contants(deviceid) )
			{
				//reporttaskmgt.addTask(deviceid , new ReportProcessorRunner(pro ,deviceid, rq ,  new NettyConnectionWrap(ctx)));
			}
			else if ( isLogin(remoter))
			{
				remoter.setHaslogin(false);
			}
		}

		
	}

	private void sendLoginRequest(ChannelHandlerContext ctx , Remoter r) throws BufferOverflowException, IOException
	{

		LoginProcessor lp = new LoginProcessor();
		
		CommandTlv ct = lp.createLoginRquest();
		ct.addOrReplaceUnit(new TlvIntUnit(31 , r.getSequence() , 1));
		ct.addUnit(new TlvIntUnit(105 , 15 , 2));
		
		byte[] b = ct.getByte();
		b = Utils.wrapRequest(b);
		
		ctx.write(b);
		if ( log.isInfoEnabled())
			Utils.print(String.format("Send data to %s(%d)", getUuid(r) , ctx.channel().hashCode()) , b , b.length);
	}
	
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception 
	{
		super.userEventTriggered(ctx, evt);
		
		if (evt instanceof IdleStateEvent) 
		{  
	      IdleStateEvent event = (IdleStateEvent) evt;  
	      if (event.state() == IdleState.ALL_IDLE) 
	      {
	      }
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception 
	{
		log.info("channelInactive");
		super.channelInactive(ctx);
		ctx.close();
	}


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{
		super.channelActive(ctx);
		
		Remoter r = new Remoter();
		r.setToken("");
		r.setSoftversion(2);

		ctx.channel().attr(ID_ATTR).set(r);
		
		ctx.channel().write(new byte[]{126 , 0, 1,2,3,4,5,6,7,8,9,126});
		ctx.channel().flush();
		
		log.info("connect.....");
	}

	public static String getUuid(Remoter r)
	{
		if ( r.isHaslogin() == true )
			return r.getUuid();
		return r.getToken();
	}
	
	private boolean isLogin(Remoter r)
	{
		return r.isHaslogin();
	}
}

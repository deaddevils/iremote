package cn.com.isurpass.pushmessage.pusher;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.store.IExpire;
import com.iremote.domain.EventtoThirdpart;
import com.iremote.domain.ThirdPart;
import com.iremote.thirdpart.wcj.service.EventtoThirdpartService;

import cn.com.isurpass.netty.NettyLog;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class ThirdpartMessagePusher implements IExpire , Runnable
{	
	private static Log log = LogFactory.getLog(ThirdpartMessagePusher.class);
	
	private ThirdPart thirdpart;
	private int lastid ;
	private int currentid ;
	private ChannelHandlerContext ctx;
	private Channel channel ;
	private boolean running = false ;
	private boolean stop = false ;
	private boolean renew = false ;
	
	public ThirdpartMessagePusher(ThirdPart tp , ChannelHandlerContext ctx)
	{
		super();
		this.thirdpart = tp ;
		this.ctx = ctx;
		channel = ctx.channel();
	}
	
	@Override
	public void run()
	{
		if ( running == true )
			return ;
		
		running = true ;
		renew = false ;
		
		for ( ; !stop ; )
		{
			if ( !channel.isActive())
				return ;
			
			List<EventtoThirdpart> lst = null ;
			
			try
			{
				HibernateUtil.prepareSession(IRemoteConstantDefine.HIBERNATE_SESSION_REMOTE);
				lst = query();
			}
			catch(Throwable t)
			{
				log.error(t.getMessage() , t);
			}
			finally
			{
				HibernateUtil.closeSession();
			}
			
			int sleep = 1000;
			try
			{
				if ( lst != null )
				{
					for ( EventtoThirdpart e : lst )
					{
						if ( renew == true )  
						{
							renew = false ;
							sleep = 5000;
							break;
						}
						sendmessage(e);		
						Utils.sleep(100);
						sleep -= 100 ;
					}
				}
			}
			catch(Throwable t)
			{
				log.error(t.getMessage() , t);
			}
			
			if ( sleep > 0 )
			{
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					log.info(e.getMessage());
					ctx.close();
					return ;
				}
			}
		}
		ctx.close();
	}
	
	private synchronized List<EventtoThirdpart> query()
	{
		EventtoThirdpartService svr = new EventtoThirdpartService();
		
		int maxid = svr.queryMaxId();
		
		if ( maxid == 0 || maxid == currentid )
			return null;
		
		List<EventtoThirdpart> lst = svr.query(this.getThirdpartid(), currentid , maxid ,10);
		
		if ( lst == null || lst.size() == 0 )
		{
			currentid = maxid ;
			return null;
		}
		return lst ;
	}
	
	private synchronized void sendmessage(EventtoThirdpart e)
	{
		JSONObject json = (JSONObject)JSON.toJSON(e);
		
		json.put("lastid", lastid);
		json.put("newid", e.getId());
		json.put("newtime", Utils.formatTime(e.getEventtime()));
		json.put("floatparm", e.getFloatparam());
		json.remove("eventtime");
		json.remove("thirdpartid");
		
		String v = json.toJSONString();
		ctx.writeAndFlush(v);
		NettyLog.sendmessageinfolog(ctx, v);
		
		lastid = e.getId();
		currentid = lastid ;
	}

	public int getThirdpartid()
	{
		if ( this.thirdpart != null )
			return this.thirdpart.getThirdpartid();
		return 0;
	}

	public synchronized int getLastid()
	{
		return lastid;
	}
	public synchronized void setLastid(int lastid)
	{
		if ( this.lastid == lastid )
			return ;
		this.lastid = lastid;
		this.currentid = lastid;
		renew = true;
	}
	public ChannelHandlerContext getCtx()
	{
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx)
	{
		this.ctx = ctx;
	}
	@Override
	public long getExpireTime()
	{
		return 0;
	}

	public boolean isRunning()
	{
		return running;
	}

	public void setRunning(boolean running)
	{
		this.running = running;
	}

	public boolean isStop()
	{
		return stop;
	}

	public void setStop(boolean stop)
	{
		this.stop = stop;
	}
	
	public void stop()
	{
		this.setStop(true);
	}

	public ThirdPart getThirdpart()
	{
		return thirdpart;
	}


}

package cn.com.isurpass.pushmessage.pusher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.iremote.common.store.SingleObjectStore;

public class ThirdpartMessagePusherStore extends SingleObjectStore<ThirdpartMessagePusher>
{
	private static ThirdpartMessagePusherStore instance = new ThirdpartMessagePusherStore() ;
	private static ExecutorService exeservice = Executors.newCachedThreadPool();

	public static ThirdpartMessagePusherStore getInstance()
	{
		return instance ;
	}
	
	public void execute(ThirdpartMessagePusher tp)
	{
		exeservice.execute(tp);
	}
	
	public static void shutdown()
	{
		exeservice.shutdownNow();
	}
}

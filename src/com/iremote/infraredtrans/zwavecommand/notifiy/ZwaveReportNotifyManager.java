package com.iremote.infraredtrans.zwavecommand.notifiy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;

public class ZwaveReportNotifyManager
{
	private static ZwaveReportNotifyManager instance = new ZwaveReportNotifyManager();
	private Map<String , LinkedBlockingQueue<ZwaveReportConsumerWrap>> ConsumerStore = new ConcurrentHashMap<String , LinkedBlockingQueue<ZwaveReportConsumerWrap>>();
	
	private ZwaveReportNotifyManager()
	{
		
	}
	
	public static ZwaveReportNotifyManager getInstance()
	{
		return instance ;
	}
	
	public void regist(String deviceid , int nuid , Byte[] report, IZwaveReportConsumer consumer)
	{
		regist(deviceid , nuid , report , IRemoteConstantDefine.DEFAULT_TIME_OUT_SECOND,consumer);
	}
	
	public void regist(String deviceid , int nuid , Byte[] report, int timeoutsecond, IZwaveReportConsumer consumer)
	{		
		String key = makeKey(deviceid , nuid);
		LinkedBlockingQueue<ZwaveReportConsumerWrap> lst = ConsumerStore.get(key);
		if ( lst == null )
		{
			lst = new LinkedBlockingQueue<ZwaveReportConsumerWrap>();
			ConsumerStore.put(key, lst);
		}
		lst.add(new ZwaveReportConsumerWrap(consumer , report,timeoutsecond));
	}
	
	public void unregist(String deviceid , int nuid , Byte[] report, IZwaveReportConsumer consumer)
	{
		String key = makeKey(deviceid , nuid);
		LinkedBlockingQueue<ZwaveReportConsumerWrap> que = ConsumerStore.get(key);
		if ( que == null )
			return ;
	
		List<ZwaveReportConsumerWrap> rl = new ArrayList<ZwaveReportConsumerWrap>();
		for ( ZwaveReportConsumerWrap cw : que )
		{
			if ( cw.getComsumer() == consumer && Utils.isByteMatch(cw.getReport(), report)  )
				rl.add(cw);
		}
		
		que.removeAll(rl);
		
		if ( que.size() == 0 )
			ConsumerStore.remove(key);
	}
	
	public List<IZwaveReportConsumer> getConsumerList(String deviceid , int nuid , byte[] report)
	{
		String key = makeKey(deviceid , nuid);
		LinkedBlockingQueue<ZwaveReportConsumerWrap> que = ConsumerStore.get(key);
		
		if ( que == null )
			return null;
		
		List<IZwaveReportConsumer> rstl = new ArrayList<IZwaveReportConsumer>();
		List<ZwaveReportConsumerWrap> rl = new ArrayList<ZwaveReportConsumerWrap>();
	
		for ( ZwaveReportConsumerWrap wp : que )
		{
			if ( wp.isExpired() )
			{
				rl.add(wp);
				continue;
			}
			
			IZwaveReportConsumer c = wp.getComsumer();
			
			if ( c != null && c.isFinished() )
			{
				rl.add(wp);
				continue;
			}
			
			if ( Utils.isByteMatch(wp.getReport(), report))
			{
				rstl.add(c);
			}
		}
		
		que.removeAll(rl);
		
		if ( que.isEmpty())
			ConsumerStore.remove(key);
		
		return rstl ;
	}
	
	private String makeKey(String deviceid , int nuid)
	{
		return String.format("%s_%d", deviceid , nuid);
	}
	
}

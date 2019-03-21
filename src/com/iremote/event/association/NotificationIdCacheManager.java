package com.iremote.event.association;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

public class NotificationIdCacheManager
{
	private static NotificationIdCacheManager instance = new NotificationIdCacheManager();
	
	private Cache<String , NotificationIdCacheWrap> cache;
	
	private NotificationIdCacheManager()
	{
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("notifcationidcache", 
						CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class,
								NotificationIdCacheWrap.class, ResourcePoolsBuilder.heap(10000))
						.withExpiry(Expirations.timeToLiveExpiration(Duration.of(5, TimeUnit.SECONDS))))
				.build();
		
		cacheManager.init();

		cache = cacheManager.getCache("notifcationidcache", String.class, NotificationIdCacheWrap.class);
	}
	
	public static NotificationIdCacheManager getInstance()
	{
		return instance ;
	}
	
	public void put(int zwavedeviceid , String message , List<Integer> nidlst)
	{
		cache.put(String.valueOf(zwavedeviceid), new NotificationIdCacheWrap(message ,nidlst));
	}
	
	public NotificationIdCacheWrap get(int zwavedeviceid)
	{
		return cache.get(String.valueOf(zwavedeviceid));
	}
}

package com.iremote.infraredtrans.zwavecommand.cache;

import java.util.concurrent.TimeUnit;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

import com.iremote.common.Utils;

public class ZwaveReportCacheManager
{
	private static ZwaveReportCacheManager instance = new ZwaveReportCacheManager();
	
	private Cache<String , IZwaveReportCache> cache;
	
	private ZwaveReportCacheManager()
	{
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("zwavereportcache", 
						CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class,
								IZwaveReportCache.class, ResourcePoolsBuilder.heap(10000))
						.withExpiry(Expirations.timeToLiveExpiration(Duration.of(5, TimeUnit.SECONDS))))
				.build();
		
		cacheManager.init();

		cache = cacheManager.getCache("zwavereportcache", String.class, IZwaveReportCache.class);
		
	}
	
	public static ZwaveReportCacheManager getInstance()
	{
		return instance ;
	}
	
	public void put(String deviceid  , int nuid , IZwaveReportCache report)
	{
		cache.put(makeKey( deviceid  ,nuid , report), report);
	}
	
	public IZwaveReportCache get(String deviceid  , int nuid , IZwaveReportCache report)
	{
		return cache.get(makeKey( deviceid  ,nuid , report));
	}
	
	private String makeKey(String deviceid  , int nuid , IZwaveReportCache report)
	{
		return String.format("%s_%d_%s", deviceid  , nuid, report.getCacheKey());
	}
	
	public static void main(String arg[])
	{
		for ( int i = 0 ; i < 100 ; i ++ )
		{
			getInstance().cache.put(String.valueOf(i), new ZwaveReportCache(null));
		}
		
		Utils.sleep(10000);
		
		for ( int i = 0 ; i < 100 ; i ++ )
		{
			IZwaveReportCache c = getInstance().cache.get(String.valueOf(i));
			if ( c == null )
				System.out.println(String.format("%d is null", i));
			else 
				System.out.println(String.format("%d is valid", i));
		}
	}
	
}

package com.iremote.infraredtrans;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ConnectionManager {

	private static Log log = LogFactory.getLog(ConnectionManager.class);

	private static Map<String , List<IConnectionContext>> map = new java.util.concurrent.ConcurrentHashMap<String , List<IConnectionContext>>();
		
	public static void addConnection(String deviceid ,IConnectionContext connection)
	{
		List<IConnectionContext> lst = map.get(deviceid);
		if ( lst == null )
		{
			lst = Collections.synchronizedList(new ArrayList<IConnectionContext>());
			map.put(deviceid , lst);
		}
		
		if ( !lst.contains(connection))
			lst.add(connection);
	}
	
	public static boolean contants(String uuid)
	{
		IConnectionContext cc = getConnection(uuid);
		return ( cc != null );
	}
	
	public static boolean removeConnection(IConnectionContext connection)
	{
		return removeConnection(connection , true);
	}

	//return true if these is no connection exist between this gateway and server . In other words, the gateway is offline.
	//return false if these are some connections exists between this gateway and server.
	public static boolean removeConnection(IConnectionContext connection , boolean close)
	{
		if ( close )
		{
			try {
				connection.close();
			} catch (Throwable e) {
				log.error("" , e);
			}
		}

		String uuid = connection.getDeviceid();
		if ( StringUtils.isBlank(uuid))
			return false ;
		if (!map.containsKey(uuid) )
			return true ;

		List<IConnectionContext> lst = map.get(uuid);
		
		if ( lst.contains(connection) )
		{
			lst.remove(connection);
			if ( log.isInfoEnabled())
				log.info(String.format("remove %s(%d)" , uuid , connection.getConnectionHashCode()));
		}
		if ( lst.size() == 0 )
			map.remove(uuid);
		return ( lst.size() == 0 );
	}
	
	public static IConnectionContext getConnection(String deviceid)
	{
		if ( StringUtils.isBlank(deviceid))
			return null ;
		List<IConnectionContext> lst = map.get(deviceid);
		
		if ( lst == null || lst.size() == 0 )
			return null ; 
		
		IConnectionContext conn = null ;
		List<IConnectionContext> rl = new ArrayList<IConnectionContext>();
		
		for ( IConnectionContext cc : lst )
		{	
			if ( cc.isTimeout())
				rl.add(cc);
			else 
				conn = cc ;
		}
		
		for ( IConnectionContext cc : rl)
			removeConnection(cc , false);
		
		return conn;
	}

	public static List<IConnectionContext> getAllConnection(String deviceid)
	{
		if ( StringUtils.isBlank(deviceid))
			return null ;
		List<IConnectionContext> lst = map.get(deviceid);

		if ( lst == null || lst.size() == 0 )
			return null ;

		List<IConnectionContext> rl = new ArrayList<IConnectionContext>();

		for ( IConnectionContext cc : lst )
		{
			if ( cc.isTimeout())
				rl.add(cc);
		}

		for ( IConnectionContext cc : rl)
			removeConnection(cc , false);

		List<IConnectionContext> rst = new ArrayList<IConnectionContext>();
		rst.addAll(lst);

		return rst ;
	}

	public static boolean isOnline(String deviceid)
	{
		IConnectionContext cc = getConnection(deviceid);
		if ( cc == null )
			return false ;
		return cc.isOpen();
	}
	
	public static int getNumberofOnlineGateway()
	{
		return map.size();
	}
	
	public static void destory()
	{
		for ( List<IConnectionContext> lst : map.values())
		{
			for ( IConnectionContext cc : lst)
			{
				try {
					cc.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
		}
	}
	
	protected static Collection<List<IConnectionContext>> getAllConnection()
	{
		return map.values();
	}
}

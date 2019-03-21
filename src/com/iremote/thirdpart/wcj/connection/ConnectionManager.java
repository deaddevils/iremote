package com.iremote.thirdpart.wcj.connection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {

	private static ConnectionManager instance = new ConnectionManager();
	
	private Map<Integer , Connection> map = new ConcurrentHashMap<Integer, Connection>();
	
	private ConnectionManager()
	{
		
	}
	
	public static ConnectionManager getInstance()
	{
		return instance;
	}
	
	public Connection getConnection(int thirdpartid)
	{
		if ( map.containsKey(thirdpartid))
			return map.get(thirdpartid);
		
		synchronized(map)
		{
			if ( map.containsKey(thirdpartid))
				return map.get(thirdpartid);
			Connection c = new Connection();
			map.put(thirdpartid, c);
			return c;
		}
	}
}

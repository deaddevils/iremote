package com.iremote.event.association;

import java.util.List;

public class NotificationIdCacheWrap
{
	private String message ;
	private List<Integer> nidlst ;
	
	public NotificationIdCacheWrap(String message, List<Integer> nidlst)
	{
		super();
		this.message = message;
		this.nidlst = nidlst;
	}
	public String getMessage()
	{
		return message;
	}
	public List<Integer> getNidlst()
	{
		return nidlst;
	}
}

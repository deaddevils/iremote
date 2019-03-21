package com.iremote.common.Hibernate;

import java.util.List;

public class QueryUtil {

	public static <T> List<T> list(QueryWrap cw ,BasicScroll scroll)
	{
		if(scroll!=null)
		{
			scroll.compute(cw.count());
			cw.setFirstResult(scroll.getFrom() - 1);
			cw.setMaxResults(scroll.getPageSize());
		}
		else 
		{
			cw.setMaxResults(0);
			cw.setFirstResult(0);
		}
		
		return cw.list();
	}
}

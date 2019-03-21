package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.SystemParameter;

public class SystemParameterService extends BaseService<SystemParameter> 
{
	
	public List<SystemParameter> query()
	{
		CriteriaWrap cw = new CriteriaWrap(SystemParameter.class.getName());
		return cw.list();
	}
	
	public SystemParameter getValue(String key)
	{
		CriteriaWrap cw = new CriteriaWrap(SystemParameter.class.getName());
		cw.add(ExpWrap.eq("key", key));
		
		return cw.uniqueResult();
	}
	
	public String getStringValue(String key)
	{
		CriteriaWrap cw = new CriteriaWrap(SystemParameter.class.getName());
		cw.add(ExpWrap.eq("key", key));
		
		SystemParameter sp = cw.uniqueResult();
		if ( sp == null )
			return null ;
		return sp.getStrvalue();
	}
	
	public int getIntValue(String key , int defaultvalue)
	{
		SystemParameter sp = getValue(key);
		if ( sp == null || sp.getIntvalue() == null )
			return defaultvalue;
		return sp.getIntvalue();
	}
	
	public int getIntValue(String key)
	{
		CriteriaWrap cw = new CriteriaWrap(SystemParameter.class.getName());
		cw.add(ExpWrap.eq("key", key));
		
		SystemParameter sp = cw.uniqueResult();
		if ( sp == null )
			return -1 ;
		return sp.getIntvalue();
	}
	
	public SystemParameter querybykey(String key)
	{
		CriteriaWrap cw = new CriteriaWrap(SystemParameter.class.getName());
		cw.add(ExpWrap.eq("key", key));
		return cw.uniqueResult();
	}
}

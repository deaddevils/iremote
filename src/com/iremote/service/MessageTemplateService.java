package com.iremote.service;

import java.util.List;

import org.hibernate.criterion.Order;

import com.iremote.common.Utils;
import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.MessageTemplate;

public class MessageTemplateService {

	public List<MessageTemplate> query()
	{
		CriteriaWrap cw = new CriteriaWrap(MessageTemplate.class.getName());
		return cw.list();
	}
	
	public MessageTemplate query(String key , int platform , String language)
	{
		CriteriaWrap cw = new CriteriaWrap(MessageTemplate.class.getName());
		cw.add(ExpWrap.eq("key", key));
		cw.add(ExpWrap.eq("platform", platform));
		cw.add(ExpWrap.eq("language", language));
		
		List<MessageTemplate> lst = cw.list();
		if ( lst == null || lst.size() == 0 )
			return null; 
		return lst.get(0);
	}
	
	//Query template of the platform , on case template not exists, query template of platform 0. 
	public MessageTemplate queryPlatformorDefaultTempalte(String key , int platform , String language)
	{
		CriteriaWrap cw = new CriteriaWrap(MessageTemplate.class.getName());
		cw.add(ExpWrap.eq("key", key));
		cw.add(ExpWrap.in("platform", new int[]{0, platform}));
		cw.add(ExpWrap.eq("language", Utils.getUserLanguage(platform , language)));
		cw.addOrder(Order.desc("platform"));
		
		List<MessageTemplate> lst = cw.list();
		if ( lst == null || lst.size() == 0 )
			return null; 
		return lst.get(0);
	}
	
	public String querytemplate(String key , int platform , String language)
	{
		MessageTemplate mt = query(key , platform , language);
		if ( mt == null )
			return null;
		return mt.getValue();
	}
}

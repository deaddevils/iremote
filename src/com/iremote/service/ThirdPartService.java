package com.iremote.service;

import java.util.List;

import org.jasypt.digest.PooledStringDigester;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.ThirdPart;

public class ThirdPartService 
{
	private static PooledStringDigester digester = new PooledStringDigester();
	
	static 
	{
		digester.setPoolSize(8);
		digester.setIterations(1062);
	}
	
	public int save(ThirdPart thirdpart)
	{
		return (Integer)HibernateUtil.getSession().save(thirdpart);
	}
	
	public ThirdPart query(String code)
	{
		CriteriaWrap cw = new CriteriaWrap(ThirdPart.class.getName());
		cw.add(ExpWrap.eq("code", code));
		return cw.uniqueResult();
	}
	public List<ThirdPart> query()
	{
		CriteriaWrap cw = new CriteriaWrap(ThirdPart.class.getName());
		return cw.list();
	}
	
	public String encryptPassword(String code , String password)
	{		
		String pw = code + password ;
		return digester.digest(pw);
	}
	
	public boolean checkPassword(String code , String password , String enpassword)
	{
		return digester.matches(code + password, enpassword);
	}
}

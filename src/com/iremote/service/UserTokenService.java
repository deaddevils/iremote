package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.Hibernate.HqlWrap;
import com.iremote.domain.UserToken;

public class UserTokenService {

	public void save(UserToken usertoken)
	{
		HibernateUtil.getSession().save(usertoken);
	}
	
	public void delete(UserToken usertoken)
	{
		HibernateUtil.getSession().delete(usertoken);
	}
	
	public UserToken querybytoken(String token)
	{
		CriteriaWrap cw = new CriteriaWrap(UserToken.class.getName());
		cw.add(ExpWrap.eq("token" , token));
		return cw.uniqueResult();
	}
	
	public void updateLastUpdatetime(int tokenid)
	{
		HqlWrap hw = new HqlWrap();
		hw.addifnotnull("Update UserToken set lastupdatetime = now() where tokenid = ? " , tokenid);
		hw.executeUpdate();
	}
	
	public void deletebyphonuserid(int phoneuserid , String excepttoken)
	{
		CriteriaWrap cw = new CriteriaWrap(UserToken.class.getName());
		cw.add(ExpWrap.eq("phoneuserid" , phoneuserid));
		
		List<UserToken> lst = cw.list();
		
		for (UserToken ut : lst  )
		{
			if ( ut.getToken().equals(excepttoken))
				continue;
			HibernateUtil.getSession().delete(ut);
		}
	}
}

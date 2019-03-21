package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.Card;

public class CardService extends BaseService<Card>
{
	public Card query(int cardid)
	{
		CriteriaWrap cw = new CriteriaWrap(Card.class.getName());
		cw.add(ExpWrap.eq("cardid", cardid));
		return cw.uniqueResult();
	}
	
	public Card queryCardbykey(String sha1key , Integer thirdpartid)
	{
		if ( thirdpartid == null )
			thirdpartid = 0 ;
		
		CriteriaWrap cw = new CriteriaWrap(Card.class.getName());
		cw.add(ExpWrap.eq("sha1key", sha1key));
		cw.add(ExpWrap.eq("thirdpartid", thirdpartid));
		return cw.uniqueResult();
	}
	
	
}

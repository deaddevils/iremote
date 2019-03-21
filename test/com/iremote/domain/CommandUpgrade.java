package com.iremote.domain;

import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.criterion.Restrictions;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;

@SuppressWarnings("unused")
public class CommandUpgrade implements Runnable{

	
	public static void main(String arg[] )
	{
		CommandUpgrade ru = new CommandUpgrade();
		ru.upgrad(null);
	}
	
	public void upgrad(String arg[])
	{
		
		for ( ; ; )
		{
			HibernateUtil.beginTransaction();
			
			List<Command> lst = query();
			
			if ( lst == null || lst.size() == 0 )
				break;
			
			for ( Command c : lst )
			{
				if ( c.getInfraredcode() != null && c.getInfraredcode().length > 0 )
					c.setInfraredcodebase64(Base64.encodeBase64String(c.getInfraredcode()));
				if ( c.getZwavecommand() != null && c.getZwavecommand().length > 0 )
					c.setZwavecommandbase64(Base64.encodeBase64String(c.getZwavecommand()));
			}
			
			HibernateUtil.commit();

		}
		
		HibernateUtil.closeSession();
		
	}
	
	private List<Command> query()
	{
		CriteriaWrap cw = new CriteriaWrap(Command.class.getName());
		cw.add(ExpWrap.or(Restrictions.isNotNull("infraredcode"),Restrictions.isNotNull("zwavecommand")));
		cw.add(Restrictions.isNull("infraredcodebase64"));
		cw.add(Restrictions.isNull("zwavecommandbase64"));
		cw.setMaxResults(100);
		
		return cw.list();
	}
	
	
	@Override
	public void run() {
		this.upgrad(null);
		
	}
}

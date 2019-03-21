package com.iremote.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Utils;
import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.NotificationSetting;
import com.iremote.vo.NotifyUserList;

public class NotificationSettingService {

	public NotificationSetting query(int phoneuserid , int notificationtype)
	{
		CriteriaWrap cw = new CriteriaWrap(NotificationSetting.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
		cw.add(ExpWrap.eq("notificationtype", notificationtype));
		return cw.uniqueResult();
	}
	
	public List<NotificationSetting> query(int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(NotificationSetting.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
		return cw.list();
	}
	
	public void delete(NotificationSetting notificationSetting)
	{
		HibernateUtil.getSession().delete(notificationSetting);
	}
	
	public void saveorUpdate(NotificationSetting notificationSetting)
	{
		calculateTime(notificationSetting);
		HibernateUtil.getSession().saveOrUpdate(notificationSetting);
	}
	
	public NotifyUserList warningUsers(List<Integer> phoneuserid, int platform, String deviceid)
	{
		NotifyUserList nu = new NotifyUserList();
		if ( phoneuserid == null || phoneuserid.size() == 0 )
			return nu;
		
		List<Integer> lst = new ArrayList<Integer>();
		lst.addAll(phoneuserid);
		
		Calendar d = Calendar.getInstance();
		int ns = d.get(Calendar.HOUR_OF_DAY) * 3600 + d.get(Calendar.MINUTE) * 60 + d.get(Calendar.SECOND);
		
		if ( lst.size() > 0 )
		{
			//UserInOutService ios = new UserInOutService();
			//List<Integer> ihl = ios.queryinhomeuser(phoneuserid, deviceid);

			String hql = " from NotificationSetting where phoneuserid in :phoneuserid " +
			" and ( ( startsecond < endsecond and startsecond<= :ns and  endsecond >= :ns ) " +
			"		or ( startsecond > endsecond and ( startsecond <= :ns or  endsecond >= :ns ) ) ) " ;

			Query query = HibernateUtil.getSession().createQuery(hql);
			query.setParameterList("phoneuserid", phoneuserid);
			query.setInteger("ns", ns);
			
			List<NotificationSetting> nslst = query.list();
			
			for (NotificationSetting n : nslst)
			{
				if ( n.getAthome() == IRemoteConstantDefine.NOTIFICATION_DO_NOT_NOTIFY_ME ) // && ihl.contains(n.getPhoneuserid())) 
					continue;
				if ( n.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_SMS )
					nu.getSmsUser().add(n.getPhoneuserid());
				else if ( n.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_CALL )
					nu.getCallUser().add(n.getPhoneuserid());
				else if ( n.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_NOTIFICATION) // &&n.getApp()!=null&&n.getApp()!=0)
				{
					nu.getNotificationUser().add(n.getPhoneuserid());
				}
				else if ( n.getNotificationtype() == IRemoteConstantDefine.WARNING_SEND_TYPE_MAIL )
					nu.getMailUser().add(n.getPhoneuserid());
			}
		}
		
		nu.getMessageUser().addAll(phoneuserid);
		//nu.getMessageUser().removeAll(nu.getNotificationUser());
		
		return nu;
	}

	public List<NotificationSetting> queryValidSettingForDoorBell(int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(NotificationSetting.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));

		return cw.list();
	}

	public List<NotificationSetting> queryValidSettingForDoorBell(Collection<Integer> phoneUserIdList, int type) {
		CriteriaWrap cw = new CriteriaWrap(NotificationSetting.class.getName());
		cw.add(ExpWrap.in("phoneuserid", phoneUserIdList));
		cw.add(ExpWrap.eq("notificationtype", type));

		return cw.list();
	}
	
	public List<NotificationSetting> queryValidSetting(int phoneuserid)
	{
		Calendar d = Calendar.getInstance();
		int ns = d.get(Calendar.HOUR_OF_DAY) * 3600 + d.get(Calendar.MINUTE) * 60 + d.get(Calendar.SECOND);
		
		String hql = " from NotificationSetting where phoneuserid = :phoneuserid " +
		" and ( ( startsecond < endsecond and startsecond<= :ns and  endsecond >= :ns ) " +
		"		or ( startsecond > endsecond and ( startsecond <= :ns or  endsecond >= :ns ) ) ) " ;
		
		Query query = HibernateUtil.getSession().createQuery(hql);
		query.setParameter("phoneuserid", phoneuserid);
		query.setInteger("ns", ns);
		
		return query.list();
	}

	public List<NotificationSetting> queryValidSetting(List<Integer> phoneuserid,int type)
	{
		if ( phoneuserid == null || phoneuserid.size() == 0 )
			return new ArrayList<NotificationSetting>();
		Calendar d = Calendar.getInstance();
		int ns = d.get(Calendar.HOUR_OF_DAY) * 3600 + d.get(Calendar.MINUTE) * 60 + d.get(Calendar.SECOND);

		String hql = " from NotificationSetting where phoneuserid in :phoneuserid " +
				"and notificationtype = :notificationtype" +
				" and ( ( startsecond < endsecond and startsecond<= :ns and  endsecond >= :ns ) " +
				"		or ( startsecond > endsecond and ( startsecond <= :ns or  endsecond >= :ns ) ) ) " ;

		Query query = HibernateUtil.getSession().createQuery(hql);
		query.setParameterList("phoneuserid", phoneuserid);
		query.setInteger("ns", ns);
		query.setInteger("notificationtype", type);

		return query.list();
	}
	
	public void calculateTime(NotificationSetting n)
	{
		int st = Utils.time2second(n.getStarttime());
		n.setStartsecond(st);
		int et = Utils.time2second(n.getEndtime());
		if ( et != 0 )
			et += 59 ;
		n.setEndsecond(et);
	}
	

}

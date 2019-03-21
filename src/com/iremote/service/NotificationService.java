package com.iremote.service;

import java.util.*;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Order;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.Notification;
import com.iremote.domain.notification.INotification;

public class NotificationService {

	private static Log log = LogFactory.getLog(NotificationService.class);
	private static final String NOTIFICATION_CLASS_BASE_NAME = "com.iremote.domain.notification.Notification_";
	private static final String WARNING = "warning";
	private static final List<String> DEVICE_TYPE = Arrays.asList(new String[]{"1" , "2", "3", "4", "5", "6", "camera", "warning"});
	private static Set<String> warningtypeset = new HashSet<>();

	static{
		warningtypeset.add("unalarmdooropen");
		warningtypeset.add("lowbattery");
		warningtypeset.add("unalarmtampleralarm");
		warningtypeset.add("unalarmsos");
		warningtypeset.add("unalarmsmoke");
		warningtypeset.add("unalarmgasleak");
		warningtypeset.add("unalarmwaterleak");
		warningtypeset.add("unalarmpassworderror5times");
		warningtypeset.add("unalarmlockkeyerror");
		warningtypeset.add("unalarmmovein");
		warningtypeset.add("unalarmdoorlockopen");
		warningtypeset.add("unalarmbulliedopenlock");
		warningtypeset.add("unalarmlocklockerror");
		warningtypeset.add("unalarmlockkeyevent");
		warningtypeset.add("unalarmdscalarm");
		warningtypeset.add("unalarmpoweroverload");
		warningtypeset.add("unalarmcameradectectmove");
		warningtypeset.add("cameradectectmove");
		warningtypeset.add("arm");
		warningtypeset.add("inhomearm");
		warningtypeset.add("disarm");
		warningtypeset.add("partitionarm");
		warningtypeset.add("partitioninhomearm");
		warningtypeset.add("partitiondisarm");
		warningtypeset.add("partitionarmwithoutcode");
		warningtypeset.add("partitionarmwithcode");
		warningtypeset.add("partitiondisarmusercode");
		warningtypeset.add("partitionarmusercode");
		warningtypeset.add(IRemoteConstantDefine.NOTIFICATION_TYPE_DOOR_LOCK_OPEN_BY_BLUE_TOOTH);
		warningtypeset.add(IRemoteConstantDefine.NOTIFICATION_TYPE_BLUE_TOOTH_SEQUENCE_CHANGED);
		warningtypeset.add(IRemoteConstantDefine.NOTIFICATION_TYPE_BLUE_TOOTH_KEY_REFRESH);
		warningtypeset.add(IRemoteConstantDefine.NOTIFICATION_TYPE_LOCK_TIMES_SYNCHRONIZED);
	}
	
	public Integer save(Notification notification)
	{
		return (Integer)HibernateUtil.getSession().save(notification);
	}
	
	public Integer saveByDeviceType(Notification notification)
	{
		Object n = null ;
		if ( IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE.equals(notification.getMajortype()))
			n = createNotification(notification , notification.getDevicetype());
		else if ( IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA.equals(notification.getMajortype()) )
			n = createNotification(notification , IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA);

		if (notification.getWarningstatus() != null || warningtypeset.contains(notification.getMessage())) {
			Object nn = createNotification(notification, "warning");
			HibernateUtil.getSession().save(nn);
		}

		if (n == null)
			return null;
		return (Integer)HibernateUtil.getSession().save(n);
	}
	
	public INotification queryByDeviceType(int notificationid ,String majortype, String devicetype)
	{
		CriteriaWrap cw = createCriteriaWrapbyDeviceType(majortype , devicetype);
		if ( cw == null )
			return null ;
		cw.add(ExpWrap.eq("notificationid", notificationid));
		return (INotification)cw.uniqueResult();
	}
	
	private Object createNotification(Notification notification , String devicetype)
	{
		if ( !DEVICE_TYPE.contains(devicetype))
			return null;
		
		String cn = NOTIFICATION_CLASS_BASE_NAME + devicetype;
		try {
			Object n = Class.forName(cn).newInstance();
			PropertyUtils.copyProperties(n, notification);
			return n ;
		} catch (Throwable e) {
			log.warn(e.getMessage());
			return null ;
		} 
	}
	
	public void delete(Notification notification)
	{
		HibernateUtil.getSession().delete(notification);
	}
	
	public void clear(int phoneuserid)
	{
		delete(phoneuserid);
		
		for ( String k : DEVICE_TYPE)
		{
			if ( IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA.equals(k))
				delete(phoneuserid , k , null);
			else 
				delete(phoneuserid , IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE , k);
		}
	}
	
	
	public void delete(int phoneuserid)
	{
		String hql = " update Notification set deleteflag = 1 , deletephoneuserid = :userid where phoneuserid = :phoneuserid and deleteflag != 1 ";
		Query query = HibernateUtil.getSession().createQuery(hql);
		query.setParameter("userid", phoneuserid);
		query.setParameter("phoneuserid", phoneuserid);
		
		query.executeUpdate();
	}
	
	private void delete(int phoneuserid , String majortype , String devicetype)
	{
		String cn = this.createNotificationClassNamebyDevicetype(majortype, devicetype);
		if ( StringUtils.isBlank(cn))
			return ;
		
		String hql = " update " + cn + " set deleteflag = 1 , deletephoneuserid = :userid where phoneuserid = :phoneuserid and deleteflag != 1 ";
		Query query = HibernateUtil.getSession().createQuery(hql);
		query.setParameter("userid", phoneuserid);
		query.setParameter("phoneuserid", phoneuserid);
		
		query.executeUpdate();

	}
	
	public Notification query(int notificationid)
	{
		CriteriaWrap cw = new CriteriaWrap(Notification.class.getName());
		cw.add(ExpWrap.eq("notificationid", notificationid));
		return cw.uniqueResult();
	}
	
	public Notification query(Notification n)
	{
		CriteriaWrap cw = new CriteriaWrap(Notification.class.getName());
		cw.add(ExpWrap.eq("deviceid" , n.getDeviceid()));
		cw.add(ExpWrap.eq("nuid" , n.getNuid()));
		cw.add(ExpWrap.eq("message" , n.getMessage()));
		cw.add(ExpWrap.eq("eclipseby" , 0));
		cw.addOrder(Order.desc("notificationid"));
		cw.setMaxResults(1);
		List<Notification>  lst = cw.list();
		if ( lst == null || lst.size() == 0 )
			return null ;
		return lst.get(0);
	}
	
	public List<Notification> querybyreportime(Date start , String majortype , String devicetype , int page , Integer phoneuserid )
	{
		CriteriaWrap cw = new CriteriaWrap(Notification.class.getName());
		if ( IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA.equals(majortype) 
				|| StringUtils.isNotBlank(devicetype))
			cw = createCriteriaWrapbyDeviceType(majortype , devicetype);
		if ( cw == null )
			return new ArrayList<Notification>();
		cw.addifNotNull(ExpWrap.lt("reporttime", start));

		if (!WARNING.equals(devicetype)){
			cw.addifNotNull(ExpWrap.eq("majortype", majortype));
			cw.addifNotNull(ExpWrap.eq("devicetype", devicetype));
		}

		if ( phoneuserid != null )
			cw.add(ExpWrap.eq("phoneuserid", phoneuserid));

		cw.add(ExpWrap.eq("eclipseby", 0));
		cw.add(ExpWrap.in("status", new int[]{IRemoteConstantDefine.NOTIFICATION_STATUS_USER_ARM , IRemoteConstantDefine.NOTIFICATION_STATUS_USER_DISARM , IRemoteConstantDefine.NOTIFICATION_STATUS_USER_INHOME_ARM }));
		cw.add(ExpWrap.eq("deleteflag", 0));
		cw.addOrder(Order.desc("notificationid"));
		cw.setFirstResult(30 * (page-1));
		cw.setMaxResults(30);
		return cw.list();
	}

	public void setNotificationEclipse(List<Integer> notificationid)
	{
		if ( notificationid == null || notificationid.size() == 0 )
			return ;
		
		String hql = "update Notification set eclipseby = 1 where notificationid in (:notificationid)";
		Query query = HibernateUtil.getSession().createQuery(hql);
		query.setParameterList("notificationid", notificationid);
		
		query.executeUpdate();
	}
	
	public void setNotificationEclipse(String majortype , String devicetype ,List<Integer> notificationid)
	{
		if ( notificationid == null || notificationid.size() == 0 )
			return ;
		String cn = this.createNotificationClassNamebyDevicetype(majortype, devicetype);
		if ( StringUtils.isBlank(cn))
			return ;
		
		String hql = "update " + cn + " set eclipseby = 1 where notificationid in (:notificationid)";
		Query query = HibernateUtil.getSession().createQuery(hql);
		query.setParameterList("notificationid", notificationid);
		
		query.executeUpdate();
	}

	private CriteriaWrap createCriteriaWrapbyDeviceType(String majortype , String devicetype)
	{
		String cn = createNotificationClassNamebyDevicetype(majortype , devicetype);
		if ( StringUtils.isBlank(cn))
			return null ;
		return new CriteriaWrap(cn);
	}
	
	private String createNotificationClassNamebyDevicetype(String majortype , String devicetype)
	{		
		if ( IRemoteConstantDefine.DEVICE_MAJORTYPE_ZWAVE.equals(majortype) 
				&& StringUtils.isNotBlank(devicetype)
				&& DEVICE_TYPE.contains(devicetype))
			return NOTIFICATION_CLASS_BASE_NAME + devicetype;
		else if ( IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA.equals(majortype) )
			return NOTIFICATION_CLASS_BASE_NAME + IRemoteConstantDefine.DEVICE_MAJORTYPE_CAMERA;
		else 
			return null;
	}
	
}

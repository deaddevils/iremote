package com.iremote.thirdpart.wcj.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.thirdpart.wcj.domain.DoorlockPassword;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Order;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DoorlockPasswordService {

	public void save(DoorlockPassword dp )
	{
		HibernateUtil.getSession().save(dp);
	}
	
	public void update(DoorlockPassword dp)
	{
		HibernateUtil.getSession().update(dp);
	}
	
	public void delete(DoorlockPassword password)
	{
		HibernateUtil.getSession().delete(password);
	}
	
	public DoorlockPassword queryLatestActivePassword(int zwavedeviceid , int doorlockpasswordusertype , int usercode)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockPassword.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("usercode", usercode));
		cw.add(ExpWrap.eq("status", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE));
		cw.add(ExpWrap.eq("usertype", doorlockpasswordusertype));
		cw.add(ExpWrap.ge("validthrough", new Date()));
		cw.addOrder(Order.desc("doorlockpasswordid"));
		cw.setMaxResults(1);
		
		List<DoorlockPassword> lst = cw.list();
		if ( lst == null || lst.size() == 0 )
			return null ;
		return lst.get(0);
	}
	
//	public DoorlockPassword queryLatestActivePassword(int zwavedeviceid , int usercode)
//	{
//		CriteriaWrap cw = new CriteriaWrap(DoorlockPassword.class.getName());
//		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
//		cw.add(ExpWrap.eq("usercode", usercode));
//		cw.add(ExpWrap.eq("status", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE));
//		//cw.add(ExpWrap.eq("synstatus", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING));
//		cw.add(ExpWrap.ge("validthrough", new Date()));
//		cw.addOrder(Order.desc("doorlockpasswordid"));
//		cw.setMaxResults(1);
//		
//		List<DoorlockPassword> lst = cw.list();
//		if ( lst == null || lst.size() == 0 )
//			return null ;
//		return lst.get(0);
//	}
	
	public List<DoorlockPassword> queryByZwavedeviceid(int zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockPassword.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		return cw.list();
	}
	
	public List<DoorlockPassword> queryActivePassword(int zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockPassword.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("status", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE));
		cw.add(ExpWrap.ge("validthrough", new Date()));
		return cw.list();
	}
	
	public List<DoorlockPassword> queryActivePassword2(int zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockPassword.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.in("synstatus",
				new int[] { IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING,
						IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_SENT,
						IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET,
						IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_DATE_FAILED_MANY_TIMES }));
		cw.add(ExpWrap.eq("status", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE));
		cw.add(ExpWrap.ge("validthrough", new Date()));
		return cw.list();
	}
	
	public List<DoorlockPassword> queryActivePasswordByUsercode(int zwavedeviceid,int usercode)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockPassword.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("usercode", usercode));
		cw.add(ExpWrap.eq("status", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE));
		cw.add(ExpWrap.ge("validthrough", new Date()));
		cw.setLockMode(LockMode.UPGRADE);
		return cw.list();
	}
	
	public DoorlockPassword queryLatestActivePassword2(int zwavedeviceid , int doorlockpasswordusertype , int usercode)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockPassword.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("usercode", usercode));
		cw.add(ExpWrap.or(ExpWrap.eq("status", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE),
				ExpWrap.and(ExpWrap.eq("synstatus", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_FAILED),
						ExpWrap.eq("status", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE))));
		cw.add(ExpWrap.eq("usertype", doorlockpasswordusertype));
		//cw.add(ExpWrap.ge("validthrough", new Date()));
		cw.addOrder(Order.desc("doorlockpasswordid"));
		cw.setMaxResults(1);
		cw.setLockMode(LockMode.UPGRADE);
		List<DoorlockPassword> lst = cw.list();
		if ( lst == null || lst.size() == 0 )
			return null ;
		return lst.get(0);
	}
	//new int[]{IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE,IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE}
	public DoorlockPassword queryLatestActivePassword2(int zwavedeviceid , int doorlockpasswordusertype ,String tid){
		CriteriaWrap cw = new CriteriaWrap(DoorlockPassword.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.or(ExpWrap.eq("status", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE),
				ExpWrap.and(ExpWrap.eq("synstatus", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_FAILED),
						ExpWrap.eq("status", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE))));
		cw.add(ExpWrap.eq("usertype", doorlockpasswordusertype));
		cw.add(ExpWrap.eq("tid",tid));
		//cw.add(ExpWrap.ge("validthrough", new Date()));
		cw.addOrder(Order.desc("doorlockpasswordid"));
		cw.setMaxResults(1);
		cw.setLockMode(LockMode.UPGRADE);
		List<DoorlockPassword> lst = cw.list();
		if ( lst == null || lst.size() == 0 )
			return null ;
		return lst.get(0);
	}
	
	public DoorlockPassword queryLatestActivePassword3(int zwavedeviceid , int doorlockpasswordusertype , int usercode)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockPassword.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("usercode", usercode));
		cw.add(ExpWrap.eq("synstatus", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING));
		cw.add(ExpWrap.eq("status", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE));
		cw.add(ExpWrap.eq("usertype", doorlockpasswordusertype));
		cw.addOrder(Order.desc("doorlockpasswordid"));
		cw.setMaxResults(1);
		cw.setLockMode(LockMode.UPGRADE);
		List<DoorlockPassword> lst = cw.list();
		if ( lst == null || lst.size() == 0 )
			return null ;
		return lst.get(0);
	}
	public DoorlockPassword queryLatestActivePassword3(int zwavedeviceid , int doorlockpasswordusertype ,String tid){
		CriteriaWrap cw = new CriteriaWrap(DoorlockPassword.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("synstatus", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING));
		cw.add(ExpWrap.eq("status", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE));
		cw.add(ExpWrap.eq("usertype", doorlockpasswordusertype));
		cw.add(ExpWrap.eq("tid",tid));
		cw.addOrder(Order.desc("doorlockpasswordid"));
		cw.setMaxResults(1);
		cw.setLockMode(LockMode.UPGRADE);
		List<DoorlockPassword> lst = cw.list();
		if ( lst == null || lst.size() == 0 )
			return null ;
		return lst.get(0);
	}
	public List<DoorlockPassword> queryWaitingPassword(Integer zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockPassword.class.getName());
		cw.addifNotNull(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.in("synstatus", new int[]{IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING , IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET}));
		cw.add(ExpWrap.eq("status", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE));
		cw.add(ExpWrap.ge("validthrough", new Date()));
		cw.add(ExpWrap.le("errorcount", 10));
		return cw.list();
	}
	
	public List<DoorlockPassword> queryCobbePassword(int zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockPassword.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("status", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE));
		//cw.add(ExpWrap.le("validthrough", new Date()));
		cw.add(ExpWrap.eq("passwordtype", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_TYPE_COBBE_TEMP));
		return cw.list();
	}

	public List<DoorlockPassword> queryUnsynchronizedPassword(int zwavedeviceid){
		List<Integer> statusList = Arrays.asList(IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE,
				IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE);

		CriteriaWrap cw = new CriteriaWrap(DoorlockPassword.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.gt("validthrough", new Date()));
		cw.add(ExpWrap.or(ExpWrap.eq("synstatus", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING),
				ExpWrap.eq("synstatus", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET)));
		cw.add(ExpWrap.in("status", statusList));

		cw.addOrder(Order.asc("validfrom"));
		return cw.list();
	}

	public List<Integer> listUnsynchronizedPasswordId(int zwavedeviceid){
		String hql = "select doorlockpasswordid from doorlockpassword where zwavedeviceid = :zwavedeviceid " +
				" and ((  status = :status1 and synstatus = :synstatus1 and validthrough > :now) " +
				"or (synstatus = :synstatus4 and status = :status1) " +
				"or (synstatus = :synstatus1 and status = :status9)) " +
				"order by doorlockpasswordid";

		Query query = HibernateUtil.getSession().createSQLQuery(hql);
		query.setInteger("zwavedeviceid", zwavedeviceid);
		query.setInteger("status1", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE);
		query.setInteger("status9", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE);
		query.setInteger("synstatus1", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING);
		query.setInteger("synstatus4", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET);
		query.setDate("now", new Date());

		return query.list();
	}

	public DoorlockPassword getForUpdate(int doorlockpasswordid) {
		CriteriaWrap cw = new CriteriaWrap(DoorlockPassword.class.getName());

		cw.setLockMode(LockMode.UPGRADE);
		cw.add(ExpWrap.eq("doorlockpasswordid", doorlockpasswordid));

		return cw.uniqueResult();
	}

	public List<Integer> listUnsynchronizedZwaveDoorLockPassword(){
		String hql = "select doorlockpasswordid from doorlockpassword where locktype = :locktype " +
				" and ((  status = :status1 and synstatus = :synstatus1 and validthrough > :now) " +
				"or (synstatus = :synstatus4 and status = :status1) " +
				"or (synstatus = :synstatus1 and status = :status9)) " +
				"order by doorlockpasswordid";

		Query query = HibernateUtil.getSession().createSQLQuery(hql);
		query.setInteger("locktype",  IRemoteConstantDefine.DOOR_TYPE_ZWAVE_LOCK);
		query.setInteger("status1", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_ACTIVE);
		query.setInteger("status9", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_STATUS_DELETE);
		query.setInteger("synstatus1", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_WAITING);
		query.setInteger("synstatus4", IRemoteConstantDefine.DOOR_LOCK_PASSWORD_SYN_STATUS_VALIDTIME_RESET);
		query.setDate("now", new Date());

		return query.list();
	}

	public DoorlockPassword findByTidAndZwavedeviceid(String tid, int zwavedeviceid) {
		CriteriaWrap cw = new CriteriaWrap(DoorlockPassword.class.getName());
		cw.add(ExpWrap.eq("tid", tid));
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		return cw.uniqueResult();
	}
}

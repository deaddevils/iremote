package com.iremote.service;

import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.UserShare;
import org.hibernate.criterion.Order;

public class UserShareService {

	public int save(UserShare us)
	{
		return (Integer)HibernateUtil.getSession().save(us);
	}
	
	public void delete(UserShare us)
	{
		HibernateUtil.getSession().delete(us);
	}
	
	public UserShare query(int shareid)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.eq("shareid", shareid));
		
		List<UserShare> lst = cw.list();
		if ( lst == null || lst.size() != 1 )
			return null ;
		return lst.get(0);
	}
	
	public List<UserShare> query(int fruser , int touser)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.eq("shareuserid", fruser));
		cw.add(ExpWrap.eq("touserid", touser));
		return cw.list();
	}

	public List<UserShare> queryDeviceLevelShare(int phoneuserid)	{
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.eq("shareuserid", phoneuserid));
		cw.add(ExpWrap.eq("sharetype",  IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL));
		cw.add(ExpWrap.eq("sharedevicetype", IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_SPECIFY));
		return cw.list();
	}

	public List<UserShare> querybyShareUser(int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.eq("shareuserid", phoneuserid));
		return cw.list();
	}
	
	public List<UserShare> querybytoUser(int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.eq("touserid", phoneuserid));
		return cw.list();
	}
	
	public List<UserShare> querybytoUser(int phoneuserid , int status)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.eq("touserid", phoneuserid));
		cw.add(ExpWrap.eq("status", status));
		return cw.list();
	}
	
	public List<Integer> queryPhoneuseridbytoUser(int phoneuserid , int status)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.eq("touserid", phoneuserid));
		cw.add(ExpWrap.eq("status", status));
		cw.addFields(new String[]{"shareuserid"});
		return cw.list();
	}
	
	public List<UserShare> querybyShareUser(int phoneuserid1 , int phoneuserid2 )
	{
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.or(ExpWrap.eq("shareuserid", phoneuserid1) , ExpWrap.eq("shareuserid", phoneuserid2)));
		cw.add(ExpWrap.eq("status", 1));
		return cw.list();
	}
	
	public List<UserShare> queryUnprocessedShareRequest(int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.eq("touserid", phoneuserid));
		cw.add(ExpWrap.eq("status", 0));
		return cw.list();
	}

	public List<UserShare> queryShared(int fruser , int touser, int type, int sharedevicetype)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.eq("shareuserid", fruser));
		cw.add(ExpWrap.eq("touserid", touser));
		cw.add(ExpWrap.eq("sharetype", type));
		cw.add(ExpWrap.eq("sharedevicetype", sharedevicetype));
		return cw.list();
	}

    public List<UserShare> queryShared(int fruser , int touser, int type)
    {
        CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
        cw.add(ExpWrap.eq("shareuserid", fruser));
        cw.add(ExpWrap.eq("touserid", touser));
        cw.add(ExpWrap.eq("sharetype", type));
        return cw.list();
    }

	public List<UserShare> querySharedWithStatus(int fruser , int touser, int type, int sharedevicetype, int status)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.eq("shareuserid", fruser));
		cw.add(ExpWrap.eq("touserid", touser));
		cw.add(ExpWrap.eq("sharetype", type));
		cw.add(ExpWrap.eq("sharedevicetype", sharedevicetype));
		cw.add(ExpWrap.eq("status", status));
		return cw.list();
	}

	public List<UserShare> querybytoUserForFalimySharing(int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.eq("touserid", phoneuserid));
		cw.add(ExpWrap.eq("sharetype", IRemoteConstantDefine.USER_SHARE_TYPE_FAMILY));
		cw.add(ExpWrap.eq("status", IRemoteConstantDefine.USER_SHARE_STATUS_NORMAL));
		return cw.list();
	}

	public List<UserShare> queryWhichNotResponse(int phoneuserid, int touser) {
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.eq("shareuserid", phoneuserid));
		cw.add(ExpWrap.eq("touserid", touser));
		cw.add(ExpWrap.eq("status", IRemoteConstantDefine.USER_SHARE_STATUS_WAIT_FOR_RESPONSE));
		return cw.list();
	}

	public List<UserShare> queryWhichNotResponse(int phoneuserid, int touser, int type, int sharedevicetype) {
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.eq("shareuserid", phoneuserid));
		cw.add(ExpWrap.eq("touserid", touser));
		cw.add(ExpWrap.eq("sharetype", type));
		cw.add(ExpWrap.eq("sharedevicetype", sharedevicetype));
		cw.add(ExpWrap.eq("status", IRemoteConstantDefine.USER_SHARE_STATUS_WAIT_FOR_RESPONSE));
		return cw.list();
	}

	public List<Integer> queryTouseridListByShareuseridAndType(int shareuserid,int sharetype, int sharedevicetype)
	{
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.eq("shareuserid", shareuserid));
		cw.add(ExpWrap.eq("sharetype", sharetype));
		cw.add(ExpWrap.eq("sharedevicetype", sharedevicetype));
		cw.addFields(new String[]{"touserid"});
		return cw.list();
	}

	public List<Integer> queryFriendIdsByTouserid(int touserid){
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.eq("touserid", touserid));
		cw.add(ExpWrap.eq("sharetype",  IRemoteConstantDefine.USER_SHARE_TYPE_NORMAL));
		cw.add(ExpWrap.eq("sharedevicetype", IRemoteConstantDefine.USER_SHARE_DEVICE_TYPE_ALL));
		cw.add(ExpWrap.eq("status", IRemoteConstantDefine.USER_SHARE_STATUS_NORMAL));
		cw.addFields(new String[]{"shareuserid"});
		return cw.list();
	}

	public List<Integer> queryToUserId(int shareUserId, int shareType, int shareDeviceType) {
		CriteriaWrap cw = new CriteriaWrap(UserShare.class.getName());
		cw.add(ExpWrap.eq("shareuserid", shareUserId));
		cw.add(ExpWrap.eq("sharetype",  shareType));
		cw.add(ExpWrap.eq("sharedevicetype", shareDeviceType));
		cw.addFields(new String[]{"touserid"});
		return cw.list();
	}
}

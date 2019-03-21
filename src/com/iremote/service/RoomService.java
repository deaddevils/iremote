package com.iremote.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.Room;
import org.hibernate.Query;

public class RoomService extends BaseService<Room> {

	public Room query(int roomdbid)
	{
		CriteriaWrap cw = new CriteriaWrap(Room.class.getName());
		cw.add(ExpWrap.eq("roomdbid", roomdbid));
		return cw.uniqueResult();
	}
	
	public List<Room> querybyphoneuserid(int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(Room.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
		return cw.list();
	}
	
	public List<Room> querybyphoneuserid(Collection<Integer> phoneuserid)
	{
		if ( phoneuserid == null || phoneuserid.size() == 0 )
			return new ArrayList<Room>();
		CriteriaWrap cw = new CriteriaWrap(Room.class.getName());
		cw.add(ExpWrap.in("phoneuserid", phoneuserid));
		return cw.list();
	}
	
	public void saveOrUpdate(Room room)
	{
		HibernateUtil.getSession().saveOrUpdate(room);
	}
	
	public void delete(Room room)
	{
		HibernateUtil.getSession().delete(room);
	}

	public void changeOwner(int dest, String destphonenumber, int orgl) {
		String hql = "update Room set phoneuserid = :dest,phonenumber = :destphonenumber where phoneuserid = :orgl";
		Query query = HibernateUtil.getSession().createQuery(hql);
		query.setInteger("dest",  dest);
		query.setInteger("orgl",  orgl);
		query.setString("destphonenumber",  destphonenumber);
		query.executeUpdate();
	}
}

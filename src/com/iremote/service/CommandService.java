package com.iremote.service;

import java.util.Collection;
import java.util.List;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.common.Hibernate.HqlWrap;
import com.iremote.domain.Command;

public class CommandService
{
	public List<Command> querybydeviceid(String deviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(Command.class.getName());
		cw.add(ExpWrap.eq("deviceid", deviceid));
		return cw.list();
	}
		
	public void deletebyApplianceid(String applianceid)
	{
		HqlWrap hw = new HqlWrap();
		hw.addifnotnull("delete from Command where applianceid = ?", applianceid);
		hw.executeUpdate();
	}
	
	public void deletebyZwavedeviceid(int zwavedeviceid)
	{
		HqlWrap hw = new HqlWrap();
		hw.addifnotnull("delete from Command where zwavedeviceid = ?", zwavedeviceid);
		hw.executeUpdate();
	}
	
	public void deletebyInfrareddeviceid(int infrareddeviceid)
	{
		HqlWrap hw = new HqlWrap();
		hw.addifnotnull("delete from Command where infrareddeviceid = ?", infrareddeviceid);
		hw.executeUpdate();
	}
	
	public List<Command> querybyInfrareddeviceid(int infrareddeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(Command.class.getName());
		cw.add(ExpWrap.eq("infrareddeviceid", infrareddeviceid));
		return cw.list();
	}
	
	public List<Command> querybyZwavedeviceid(int zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(Command.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		return cw.list();
	}

	public List<Command> querybyZwavedeviceids(Collection zwavedeviceids)
	{
		CriteriaWrap cw = new CriteriaWrap(Command.class.getName());
		cw.add(ExpWrap.in("zwavedeviceid", zwavedeviceids));
		return cw.list();
	}

	public List<Command> querybyInfrareddeviceids(Collection infrareddeviceids)
	{
		CriteriaWrap cw = new CriteriaWrap(Command.class.getName());
		cw.add(ExpWrap.in("infrareddeviceid", infrareddeviceids));
		return cw.list();
	}

	public List<Command> querybyCameraids(Collection cameraids)
	{
		CriteriaWrap cw = new CriteriaWrap(Command.class.getName());
		cw.add(ExpWrap.in("cameraid", cameraids));
		return cw.list();
	}

	public List<Command> querybyLaunchscenedbid(int scenedbid)
	{
		CriteriaWrap cw = new CriteriaWrap(Command.class.getName());
		cw.add(ExpWrap.eq("launchscenedbid", scenedbid));
		return cw.list();
	}
	
	public void delete(Command cmd)
	{
		HibernateUtil.getSession().delete(cmd);
	}
	
	
	public List<Command> query(int first , int max)
	{
		CriteriaWrap cw = new CriteriaWrap(Command.class.getName());
		cw.setFirstResult(first);
		cw.setMaxResults(max);
		return cw.list();
	}
}

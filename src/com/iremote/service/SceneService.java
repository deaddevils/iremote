package com.iremote.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.Scene;
import org.hibernate.Query;

public class SceneService {

	public Scene query(int scenedbid)
	{
		return (Scene) HibernateUtil.getSession().get(Scene.class, scenedbid);
	}
	
	public List<Scene> queryScenebyPhoneuserid(int phoneuserid)
	{
		CriteriaWrap cw = new CriteriaWrap(Scene.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneuserid));
		cw.add(ExpWrap.eq("scenetype", IRemoteConstantDefine.SCENE_TYPE_SCENE));
		return cw.list();
	}

	public List<Scene> queryByPhoneUserId(int phoneUserId) {
		CriteriaWrap cw = new CriteriaWrap(Scene.class.getName());
		cw.add(ExpWrap.eq("phoneuserid", phoneUserId));
		return cw.list();
	}
	
	public List<Scene> queryScenebyPhoneuserid(Collection<Integer> phoneuserid)
	{
		if ( phoneuserid == null || phoneuserid.size() == 0 )
			return new ArrayList<Scene>();
		
		CriteriaWrap cw = new CriteriaWrap(Scene.class.getName());
		cw.add(ExpWrap.in("phoneuserid", phoneuserid));
		cw.add(ExpWrap.eq("scenetype", IRemoteConstantDefine.SCENE_TYPE_SCENE));
		return cw.list();
	}
	
	public Integer save(Scene scene)
	{
		return (Integer)HibernateUtil.getSession().save(scene);
	}
	
	public void saveOrUpdate(Scene scene)
	{
		HibernateUtil.getSession().saveOrUpdate(scene);
	}
	
	public void delete(Scene scene)
	{
		if ( scene != null )
			HibernateUtil.getSession().delete(scene);
	}

	public void changeOwner(int dest, int orgl) {
		String hql = "update Scene set phoneuserid = :dest where phoneuserid = :orgl";
		Query query = HibernateUtil.getSession().createQuery(hql);
		query.setInteger("dest",  dest);
		query.setInteger("orgl",  orgl);
		query.executeUpdate();
	}
}

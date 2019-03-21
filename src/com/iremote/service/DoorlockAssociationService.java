package com.iremote.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.domain.DoorlockAssociation;
import com.iremote.domain.Scene;

public class DoorlockAssociationService extends BaseService<DoorlockAssociation>{

	public List<DoorlockAssociation> querybyzwavedeviceidandusercodeandobjtype(int zwavedeviceid , int usercode,int objtype)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockAssociation.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("usercode", usercode));
		cw.add(ExpWrap.eq("objtype", objtype));
		return cw.list();
	}
	
	public void deletebyzwavedeviceidandusercodeandobjtype(int zwavedeviceid , int usercode,int objtype)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockAssociation.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("usercode", usercode));
		cw.add(ExpWrap.eq("objtype", objtype));
		List<DoorlockAssociation> list = cw.list();
		if(list!=null&&list.size()>0){
			for(DoorlockAssociation l:list){
				delete(l);
			}
		}
	}
	
	public List<DoorlockAssociation> queryByZwavedeviceidAndUsercode(int zwavedeviceid , int usercode)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockAssociation.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("usercode", usercode));
		return cw.list();
	}
	
	public List<DoorlockAssociation> queryByZwavedeviceidAndObjtype(Collection<Integer> zwavedeviceid ,int objtype){
		if ( zwavedeviceid == null || zwavedeviceid.size() == 0 )
			return new ArrayList<DoorlockAssociation>();
		
		CriteriaWrap cw = new CriteriaWrap(DoorlockAssociation.class.getName());
		cw.add(ExpWrap.in("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("objtype", objtype));
		return cw.list();
		
	}
	public void deletebyobjtypeandobjid(int objtype,int objid){
		CriteriaWrap cw = new CriteriaWrap(DoorlockAssociation.class.getName());
		cw.add(ExpWrap.eq("objtype", objtype));
		cw.add(ExpWrap.eq("objid", objid));
		List<DoorlockAssociation> list = cw.list();
		if(list!=null&&list.size()>0){
			for(DoorlockAssociation l:list){
				delete(l);
			}
		}
	}
	
	public void deletebyzwavedeviceidandusercode(int zwavedeviceid , int usercode)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockAssociation.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		cw.add(ExpWrap.eq("usercode", usercode));
		List<DoorlockAssociation> list = cw.list();
		if(list!=null&&list.size()>0){
			for(DoorlockAssociation l:list){
				delete(l);
			}
		}
	}
	
	public void deletebyzwavedeviceid(int zwavedeviceid)
	{
		CriteriaWrap cw = new CriteriaWrap(DoorlockAssociation.class.getName());
		cw.add(ExpWrap.eq("zwavedeviceid", zwavedeviceid));
		List<DoorlockAssociation> list = cw.list();
		if(list!=null&&list.size()>0){
			for(DoorlockAssociation l:list){
				delete(l);
			}
		}
	}
	
	public void deletebyobjtypeandobjid(int objtype,Collection<Integer> objid)
	{
		if ( objid == null || objid.size() == 0 )
			return;
		
		CriteriaWrap cw = new CriteriaWrap(DoorlockAssociation.class.getName());
		cw.add(ExpWrap.in("objid", objid));
		cw.add(ExpWrap.eq("objtype", objtype));
		List<DoorlockAssociation> list = cw.list();
		if(list!=null&&list.size()>0){
			for(DoorlockAssociation d:list){
				delete(d);
			}
		}
	}
}

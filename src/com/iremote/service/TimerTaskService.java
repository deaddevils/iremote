package com.iremote.service;

import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HqlWrap;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.domain.Timer;
import com.iremote.domain.TimerTask;

import java.util.List;

public class TimerTaskService extends BaseService<TimerTask> {

    public List<TimerTask> queryAll() {
        HqlWrap hql = new HqlWrap();
        hql.add("from TimerTask");
        return hql.list();
    }

    public void deleteByTypeAndObjid(int type,int objid){
    	CriteriaWrap cw = new CriteriaWrap(TimerTask.class.getName());
		cw.add(ExpWrap.eq("type", type));
		cw.add(ExpWrap.eq("objid", objid));
		
		List<TimerTask> lst = cw.list();
		if ( lst == null || lst.size() == 0 )
			return ;
		
		for ( TimerTask du : lst )
			delete(du);
    }
    public TimerTask queryByTypeAndObjid(int type, int objid) {
        CriteriaWrap cw = new CriteriaWrap(TimerTask.class.getName());
        cw.add(ExpWrap.eq("type", type));
        cw.add(ExpWrap.eq("objid", objid));
        return cw.uniqueResult();
    }

    public TimerTask query(int timerTaskId) {
        CriteriaWrap cw = new CriteriaWrap(TimerTask.class.getName());
        cw.add(ExpWrap.eq("timertaskid", timerTaskId));
        return cw.uniqueResult();
    }
    
    public List<TimerTask> queryByDeviceid(String deviceid) {
        CriteriaWrap cw = new CriteriaWrap(TimerTask.class.getName());
        cw.add(ExpWrap.eq("deviceid", deviceid));
        return cw.list();
    }
}

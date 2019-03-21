package com.iremote.service;

import java.util.Collection;
import java.util.List;

import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.CriteriaWrap;
import com.iremote.common.Hibernate.ExpWrap;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.Scene;
import com.iremote.domain.Timer;

public class TimerService {

	public Timer query(int timerid)
	{
		CriteriaWrap cw = new CriteriaWrap(Timer.class.getName());
		cw.add(ExpWrap.eq("timerid", timerid));
		return cw.uniqueResult();
	}

	public List<Timer> queryByZWaveDeviceId(Collection<Integer> zWaveDeviceId)
	{
		CriteriaWrap cw = new CriteriaWrap(Timer.class.getName());
		cw.add(ExpWrap.in("zwavedevice.zwavedeviceid", zWaveDeviceId));
		return cw.uniqueResult();
	}

	public List<Timer> queryByInfrareddevice(Collection<Integer> infrareddevice)
	{
		CriteriaWrap cw = new CriteriaWrap(Timer.class.getName());
		cw.add(ExpWrap.in("infrareddevice.infrareddeviceid", infrareddevice));
		return cw.uniqueResult();
	}

	public void saveOrUpdate(Timer timer)
	{
		HibernateUtil.getSession().saveOrUpdate(timer);
	}
	
	public List<Timer> queryServerTriggerTimer(int start , int max)
	{
		CriteriaWrap cw = new CriteriaWrap(Timer.class.getName());
		cw.add(ExpWrap.eq("executor", IRemoteConstantDefine.DEVICE_TIMER_EXECUTOR_SERVER));
		cw.add(ExpWrap.eq("valid", IRemoteConstantDefine.DEVICE_TIMER_STATUS_VALID ));
		cw.setFirstResult(start);
		cw.setMaxResults(max);
		return cw.list();
	}
	
	public List<Timer> query(int first , int max)
	{
		CriteriaWrap cw = new CriteriaWrap(Timer.class.getName());
		cw.setFirstResult(first);
		cw.setMaxResults(max);
		return cw.list();
	}

	public List<Timer> querySunScene() {
		CriteriaWrap cw = new CriteriaWrap(Timer.class.getName());
		cw.add(ExpWrap.eq("executor", IRemoteConstantDefine.DEVICE_TIMER_EXECUTOR_SERVER));
		cw.add(ExpWrap.eq("valid", IRemoteConstantDefine.DEVICE_TIMER_STATUS_VALID ));
		cw.add(ExpWrap.or(ExpWrap.eq("scenetype", IRemoteConstantDefine.SCENE_TIME_TYPE_SUNRISE),
				ExpWrap.eq("scenetype", IRemoteConstantDefine.SCENE_TIME_TYPE_SUNSET)));
		return cw.list();
	}
}

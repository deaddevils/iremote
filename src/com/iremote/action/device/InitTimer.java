package com.iremote.action.device;

import java.util.List;

import com.iremote.common.schedule.ScheduleManager;
import com.iremote.domain.TimerTask;
import com.iremote.infraredtrans.SceneProcessorRunner;
import com.iremote.service.TimerTaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iremote.action.helper.TimerHelper;
import com.iremote.common.IRemoteConstantDefine;
import com.iremote.common.Hibernate.HibernateUtil;
import com.iremote.domain.Timer;
import com.iremote.service.TimerService;

public class InitTimer implements Runnable {

	private static Log log = LogFactory.getLog(InitTimer.class);
	private final int batchsize = 100;
	@Override
	public void run() 
	{
		HibernateUtil.prepareSession(IRemoteConstantDefine.HIBERNATE_SESSION_GUI);
		HibernateUtil.beginTransaction();
		try
		{
			for ( int i = 0 ; ; i ++ )
			{
				TimerService ts = new TimerService();
				List<Timer> lst = ts.queryServerTriggerTimer(i * batchsize, batchsize);
				if ( lst == null || lst.size() == 0 )
					break ;
				TimerHelper.addTimer(lst);
			}

			restoreTimerTask();
			createSunSceneManageTask();
		}
		catch(Throwable t)
		{
			log.error(t.getMessage() , t);
		}
		finally
		{
			HibernateUtil.commit();
			HibernateUtil.closeSession();
		}

	}

	private void createSunSceneManageTask() {
		int EVERY_DAY = 127;
		ScheduleManager.excuteTimer(EVERY_DAY, 0, 0, new SceneProcessorRunner(System.currentTimeMillis(), true));

	}


	private void restoreTimerTask() {
		TimerTaskService tts = new TimerTaskService();
		List<TimerTask> timerTasks = tts.queryAll();
		if (timerTasks == null || timerTasks.size() == 0) {
			return;
		}
		for (TimerTask timerTask : timerTasks) {
			if (timerTask.getExpiretime() == null) {
				continue;
			}
			if (timerTask.getExpiretime().getTime() > System.currentTimeMillis()) {
				ScheduleManager.excuteWithOutSaveInDB(IRemoteConstantDefine.TASK_GROUP_NAME_TIMER_TASK, timerTask);
			} else {
				tts.delete(timerTask);
			}
		}
	}

}

package com.iremote.common.schedule;

import java.util.HashSet;
import java.util.Set;

import com.iremote.domain.TimerTask;
import com.iremote.service.TimerTaskService;
import com.iremote.task.timertask.TimerTaskJobStore;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.TimeOfDay;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.alibaba.fastjson.JSON;
import com.iremote.common.IRemoteConstantDefine;
	
public class ScheduleManager {

	private static Log log = LogFactory.getLog(ScheduleManager.class);
	private static Scheduler quartzscheduler;
	private static SchedulerFactory schedualerfactory;
	private static final int[] INT_TO_WEEK = new int[]{0 ,64,32,16,8,4,2,1};
	
	static 
	{
		try {
			schedualerfactory = new StdSchedulerFactory();
			quartzscheduler = schedualerfactory.getScheduler();
			quartzscheduler.start();
		} catch (SchedulerException e) {
			log.error(e.getMessage() , e);
		}
	}
	
	public static void shutdown()
	{  
		try {
			quartzscheduler.clear();        
			quartzscheduler.shutdown(true);
		} catch (SchedulerException e) {
			log.error(e.getMessage() , e);
		}  
	}

	public static void excutein(int second , Runnable runner)
	{
		excutein(second,runner,0,null);
	}

	public static void excuteWithOutSaveInDB(String groupname, TimerTask timerTask){
		long second = timerTask.getExcutetime().getTime() - System.currentTimeMillis();

        Runnable runner = TimerTaskJobStore.getInstance().getProcessor(timerTask.getType(), timerTask.getTimertaskid());
		excutein((int) second/1000, runner, timerTask.getTimertaskid(), groupname);
	}

	public static void excuteWithSaveInDB(String groupname, TimerTask timerTask){
		TimerTaskService tts = new TimerTaskService();
		tts.saveOrUpdate(timerTask);
		long second = timerTask.getExcutetime().getTime() - System.currentTimeMillis();
		log.info(second);
		Runnable runner = TimerTaskJobStore.getInstance().getProcessor(timerTask.getType(), timerTask.getTimertaskid());
		excutein((int) second/1000, runner, timerTask.getTimertaskid(), groupname);
	}

	public static void excutein(int second , Runnable runner, int id , String groupname)
	{
		SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()  
								    .startAt(DateBuilder.futureDate(second, IntervalUnit.SECOND)) 
								    .build();
		
		JobBuilder jobBuilder = JobBuilder.newJob(QuertzJob.class);
		if (groupname != null)
			jobBuilder.withIdentity(String.valueOf(id), groupname);
		JobDetail jd = jobBuilder.build();
		jd.getJobDataMap().put("task", runner);
		
		try {
			quartzscheduler.scheduleJob(jd , trigger);
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
		if ( log.isInfoEnabled())
		{
			log.info(String.format("Timer task will run in %d seconds, key = %s_%s", second, jd.getKey().getName() , jd.getKey().getGroup()));
		}
	}
	
	public static void excuteTimer(int week , int time , int id, Runnable runner)
	{
		excute(timerWeektoInt(week) , time % 256 , time / 256 , id , IRemoteConstantDefine.QUARTZ_GROUP_DEVICE_TIMER , runner);
	}
	
	public static void excute(Set<Integer> week , int hour , int min , int id , String group, Runnable runner)
	{
		Trigger  trigger = TriggerBuilder.newTrigger().withSchedule(
									DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
									.onDaysOfTheWeek(week)
									.startingDailyAt(TimeOfDay.hourAndMinuteOfDay(hour, min))
									.withInterval(24, IntervalUnit.HOUR)
									).build();
		
		JobDetail jd = JobBuilder.newJob(QuertzJob.class).withIdentity(String.valueOf(id),group).build();
		jd.getJobDataMap().put("task", runner);
		
		try {
			quartzscheduler.scheduleJob(jd , trigger);
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
		if ( log.isInfoEnabled())
		{
			String m = String.format("Timer task at %02d:%02d week %s scheduled , key = %s_%s",  hour, min , JSON.toJSONString(week),String.valueOf(id),group);
			log.info(m);
		}
	}
	
	public static void excuteEvery(int second , int id , String group , Runnable runner)
	{
		Trigger  trigger = TriggerBuilder.newTrigger().withSchedule(
				DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
				.onEveryDay()
				.withInterval(second, IntervalUnit.SECOND)
				).build();
		
		JobDetail jd = JobBuilder.newJob(QuertzJob.class).withIdentity(String.valueOf(id),group).build();
		jd.getJobDataMap().put("task", runner);
		
		try {
			quartzscheduler.scheduleJob(jd , trigger);
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
		if ( log.isInfoEnabled())
		{
			String m = String.format("Timer task scheduled , run every %s sencond , key = %s_%s",  second , String.valueOf(id),group);
			log.info(m);
		}
	}
	
	public static void cancelJob(int id , String group)
	{
		JobKey jk = JobKey.jobKey(String.valueOf(id) , group);
		try {
			quartzscheduler.deleteJob(jk);
		} catch (SchedulerException e) {
			log.error(e.getMessage() , e);
		}
		
		if ( log.isInfoEnabled())
		{
			String m = String.format("Cancel timer task key = %s_%s", String.valueOf(id),group);
			log.info(m);
		}
	}
	
	private static Set<Integer> timerWeektoInt(int week)
	{
		Set<Integer> s = new HashSet<Integer>();
		for ( int i = 1 ; i < INT_TO_WEEK.length ; i ++ )
			if ( (week & INT_TO_WEEK[i]) != 0 )
				s.add(i);
		return s;
	}
}

package com.iremote.common.schedule;

import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.TimeOfDay;

import com.alibaba.fastjson.JSON;
import com.iremote.common.Utils;

import org.quartz.DateBuilder.IntervalUnit;
@SuppressWarnings("unused")
public class ScheduleManagerTest {

	public static void main(String arg[])
	{
		MemoryLeakTest();
//		test();
//		ScheduleManager.excutein(5, new Runnable(){
//			@Override
//			public void run() {
//				System.out.println("Hello world 5");
//				
//			}});
//		
//		ScheduleManager.excutein(10, new Runnable(){
//			@Override
//			public void run() {
//				System.out.println("Hello world 10");
//				
//			}});
//		
//		ScheduleManager.excutein(15, new Runnable(){
//			@Override
//			public void run() {
//				System.out.println("Hello world 15");
//				
//			}});
//		
//		ScheduleManager.excutein(25, new Runnable(){
//			@Override
//			public void run() {
//				System.out.println("Hello world 25");
//				
//			}});
	}
	
	
	private static void MemoryLeakTest()
	{
		ScheduleManager.excutein(1 , new ScheduleJob(0));
		Utils.sleep(30 * 1000);
		for ( long i = 0 ; i < 100000 ; i ++  )
		{
			ScheduleManager.excutein(30 , new ScheduleJob(i));
			//Utils.sleep(1);
		}
		System.out.println("Loop finished");
		System.gc();
	}
	
	private static void test()
	{
		System.out.println(JSON.toJSONString(DailyTimeIntervalScheduleBuilder.ALL_DAYS_OF_THE_WEEK));
		System.out.println(JSON.toJSONString(DailyTimeIntervalScheduleBuilder.MONDAY_THROUGH_FRIDAY));
//		System.out.println(JSON.toJSONString(ScheduleManager.timerWeektoInt(65)));
//		ScheduleManager.excute(new TestJob());
//		try {
//			Thread.sleep(10*1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ScheduleManager.cancelJob(1,"group");
	}
}
